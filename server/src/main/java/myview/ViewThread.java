package myview;

import java.io.PrintStream;

public class ViewThread implements Runnable{
    PrintStream stdOut;

    public ViewThread(PrintStream stdOut){
        this.stdOut = stdOut;
        stdOut.print("Waiting for clients ...");
    }

    public void run(){
        while(true) {
            stdOut.print(".");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                stdOut.println("Interuuped exception");
                ie.printStackTrace();
            }
        }
    }
}