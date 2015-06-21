package io.input;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LinuxNonBlockingInput implements IInput
{
    // poprzednia konfifuracja konsoli
    private String ttyConfig;

    // klasa dekodująca wejściowe bajty na UTF-8
    private final UTFHolder uTFHolder;

    // bufor wczytanych znaków (głównie dla celów debugowania)
    private final LinkedList<Character> charBuffer = new LinkedList<>();
    // specjalny klawisz (np. ESC, ARROW_UP, ...)
    private Key specialKey;
    // czy mamy specjalny klawisz
    private boolean hasSpecialKey = false;

    public LinuxNonBlockingInput()
    {
        uTFHolder = new UTFHolder();
    }

    public void init() throws IOException, InterruptedException
    {
        setTerminalToCBreak();
    }

    @Override
    public void update() throws IOException
    {
        // zmienne pomocnicze
        boolean first = true;
        boolean isEscChSeq = false;
        List<Character> escChSeq = new ArrayList<>();

        while (System.in.available() != 0)
        {
            byte c = (byte)System.in.read();
            // odkomentuj poniższe, aby zobaczyć sekwencję zwracanych bajtów
            //System.out.println((int)c);
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
                char readCh = uTFHolder.getChar();

                if (isEscChSeq)
                {
                    escChSeq.add(readCh);
                }
                else if (first && readCh == Key.ESCAPE_CHAR)
                {
                    isEscChSeq = true;
                    escChSeq.add(readCh);
                }
                else
                {
                    // read all characters remaining in stream
                    // and stores them in buffer
                    charBuffer.addLast(readCh);
                }

                first = false;
            }
        }

        // jeżeli przechwyciliśmy sekwencję bajtów po klawiszu esc
        if (isEscChSeq)
        {
            // musimy skonwertować listę Character do tablicy char
            char[] array = new char[escChSeq.size()];
            for (int i = 0; i < array.length; ++i)
            {
                array[i] = escChSeq.get(i);
                // odkomentuj poniższe, żeby zobaczyć sekwencję kodów klawisza
                // System.out.println((int)array[i]);
            }
            // pobieramy jaki to klawisz
            Key key = Key.getKey(array);
            // jeżeli znany klawisz to ustawiamy, że wczytano specjalny klaiwsz
            if (key != Key.UNKNOWN)
            {
                specialKey = key;
                hasSpecialKey = true;
            }
        }
    }

    @Override
    public Key getSpecialKey()
    {
        hasSpecialKey = false;
        return specialKey;
    }

    @Override
    public boolean hasSpecialKey()
    {
        return hasSpecialKey;
    }

    @Override
    public boolean hasChar()
    {
        return charBuffer.size() > 0;
    }

    @Override
    public char getChar()
    {
        return charBuffer.removeFirst();
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
