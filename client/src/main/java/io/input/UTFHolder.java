/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.input;

/**
 *
 * @author robert
 */
public class UTFHolder
{
    private int remainedNumberOfBytes = 0;
    private int startingNumberOfBytes = 0;
    private final byte[] byteArray = new byte[3];

    private char ch = ' ';
    private boolean ready = false;

    public UTFHolder()
    {

    }

    public void addByte(byte b) throws UTFCodingException
    {
        int currentByte = getCurrentByte();
        // first byte
        if (currentByte == 0)
        {
            // 0b0...
            if (checkIsBitClear(b, 7))
            {
                ready = true;
                ch = (char)b;
            } // 0b110...
            else if (checkIsBitSet(b, 6) && checkIsBitClear(b, 5))
            {
                remainedNumberOfBytes = 1;
                startingNumberOfBytes = 2;
                byteArray[currentByte] = b;
            } // 0b111...
            else if (checkIsBitClear(b, 4))
            {
                remainedNumberOfBytes = 2;
                startingNumberOfBytes = 3;
                byteArray[currentByte] = b;
            }
            else
            {
                throw new UTFCodingException();
            }
        } // second and third bytes
        else if (currentByte == 1 || currentByte == 2)
        {
            if (checkIsBitSet(b, 7) && checkIsBitClear(b, 6))
            {
                byteArray[currentByte] = b;
                --remainedNumberOfBytes;

                if (remainedNumberOfBytes == 0)
                {
                    convertToUTF8();
                    startingNumberOfBytes = remainedNumberOfBytes = 0;
                    ready = true;
                }
            }
        }
        else
        {
            throw new UTFCodingException();
        }
    }

    private void convertToUTF8()
    {
        short b1 = (short)(byteArray[0] & 0xFF);
        short b2 = (short)(byteArray[1] & 0xFF);
        short b3 = (short)(byteArray[2] & 0xFF);

        if (startingNumberOfBytes == 2)
        {
            ch = (char)(((b1 & 0x001F) << 6) | (b2 & 0x003F));
        }
        else if (startingNumberOfBytes == 3)
        {
            ch = (char)(((b1 & 0b00001111) << 12) | 
                    ((b2 & 0b00111111) << 6) | (b3 & 0b00111111));
        }
    }

    public boolean getReady()
    {
        return ready;
    }

    public char getChar()
    {
        ready = false;
        remainedNumberOfBytes = startingNumberOfBytes = 0;
        return ch;
    }

    private boolean checkIsBitSet(byte b, int bit)
    {
        return ((b & (1 << bit)) != 0);
    }

    private boolean checkIsBitClear(byte b, int bit)
    {
        return !checkIsBitSet(b, bit);
    }

    private int getCurrentByte()
    {
        return startingNumberOfBytes - remainedNumberOfBytes;
    }
}

class UTFCodingException extends Exception
{
    public UTFCodingException()
    {
        super("Bad UTF-8 coding");
    }
}
