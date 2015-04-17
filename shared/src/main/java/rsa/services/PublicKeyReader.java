package rsa.services;

import rsa.exceptions.ReadingPublicKeyInfoException;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by tochur on 16.04.15.
 */
public class PublicKeyReader {
    public static BigInteger scanForModulus(DataInputStream in) throws ReadingPublicKeyInfoException {
        int length = 0;
        try {
            length = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadingPublicKeyInfoException("Couldn't read \"int (length of modulus)\" from passed stream (IO Exception)");
        }
        byte[] modulus = new byte[length];
        try {
            in.readFully(modulus);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
            throw new ReadingPublicKeyInfoException("Unfortunately couldn't read \"modulus Value\" from passed stream (IO Exception)");
        }
        return new BigInteger(modulus);
    }

    public static BigInteger scanForExponent(DataInputStream in) throws ReadingPublicKeyInfoException {
        int length = 0;
        try {
            length = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadingPublicKeyInfoException("Couldn't read \"int (length of exponent)\" from passed stream (IO Exception)");
        }
        byte[] exponent = new byte[length];
        try {
            in.readFully(exponent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadingPublicKeyInfoException("Unfortunately couldn't read \"exponent Value\" from passed stream (IO Exception)");
        }
        return new BigInteger(exponent);
    }
}
