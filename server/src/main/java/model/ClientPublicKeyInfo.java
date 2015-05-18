package model;

import rsa.PublicKeyInfo;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by tochur on 18.05.15.
 */
public class ClientPublicKeyInfo {
    PublicKey publicKey;
    BigInteger modulus;
    BigInteger exponent;

    public ClientPublicKeyInfo(PublicKey publicKey, BigInteger modulus, BigInteger exponent){
        this.publicKey = publicKey;
        this.modulus = modulus;
        this.exponent = exponent;
    }

    public PublicKey getPublicKey(){
        return publicKey;
    }

    public BigInteger getModulus(){
        return modulus;
    }

    public BigInteger getExponent(){
        return exponent;
    }
}
