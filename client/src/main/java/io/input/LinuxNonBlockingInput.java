package io.input;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LinuxNonBlockingInput implements IInput
{
    // poprzednia konfifuracja konsoli
    private String ttyConfig;
    
    // klasa dekodująca wejściowe bajty na UTF-8
    private final UTFHolder uTFHolder;

    // nowo wczytany znak
    private char ch;
    // czy mamy nowo wczytany znak
    private boolean hasCh = false;
    
    public LinuxNonBlockingInput() 
            throws IOException, InterruptedException
    {
        uTFHolder = new UTFHolder();
        
        setTerminalToCBreak();
    }
    
    @Override
    public void update() throws IOException
    {
        if (System.in.available() != 0)
        {
            byte c = (byte)System.in.read();
            try
            {
                uTFHolder.addByte(c);
            }
            catch (UTFCodingException ex)
            {
                Logger.getLogger(LinuxNonBlockingInput.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
            
            if (uTFHolder.getReady())
            {
                ch = uTFHolder.getChar();
                hasCh = true;
            }
        }
    }
    
    @Override
    public boolean hasChar()
    {
        return hasCh;
    }
    
    @Override
    public char getChar()
    {
        hasCh = false;
        return ch;
    }
    
    @Override
    public void close()
    {
        try
        {
            stty(ttyConfig.trim());
        }
        catch (IOException | InterruptedException e)
        {
            System.err.println("Exception restoring tty config");
        }
    }
    
    private void setTerminalToCBreak()
            throws IOException, InterruptedException
    {
        ttyConfig = stty("-g");

        // set the console to be character-buffered instead of line-buffered
        stty("-icanon min 1");

        // disable character echoing
        stty("-echo");
    }

    /**
     * Execute the stty command with the specified arguments against the current
     * active terminal.
     */
    private String stty(final String args)
            throws IOException, InterruptedException
    {
        String cmd = "stty " + args + " < /dev/tty";

        return exec(new String[]
        {
            "sh",
            "-c",
            cmd
        });
    }

    /**
     * Execute the specified command and return the output (both stdout and
     * stderr).
     */
    private String exec(final String[] cmd)
            throws IOException, InterruptedException
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        Process p = Runtime.getRuntime().exec(cmd);
        int c;
        InputStream in = p.getInputStream();

        while ((c = in.read()) != -1)
        {
            bout.write(c);
        }

        in = p.getErrorStream();

        while ((c = in.read()) != -1)
        {
            bout.write(c);
        }

        p.waitFor();

        String result = new String(bout.toByteArray());
        return result;
    }
}