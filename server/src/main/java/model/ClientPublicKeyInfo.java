package model;

import java.math.BigInteger;
import java.security.PublicKey;

/**
 * Container that wraps the Client PublicKey.
 * It is created then user connect to server, and then user to encrypt and decrypt messages.
 *
 * @author Created by tochur on 18.05.15.
 */
public class ClientPublicKeyInfo {
    PublicKey publicKey;
    BigInteger modulus;
    BigInteger exponent;

    /**
     * Creates new ClientPublicKeyInfo set
     * @param publicKey PublicKey - object user by Encryption utils.
     * @param modulus BigInteger - row part of the key
     * @param exponent BigInteger - row part of the key
     */
    public ClientPublicKeyInfo(PublicKey publicKey, BigInteger modulus, BigInteger exponent){
        this.publicKey = publicKey;
        this.modulus = modulus;
        this.exponent = exponent;
    }

    /**
     * Returns the publicKey
     * @return PublicKey - hold in a container
     */
    public PublicKey getPublicKey(){
        return publicKey;
    }

    /**
     * Returns the modulus of the publicKey
     * @return BigInteger, modulus of the key.
     */
    public BigInteger getModulus(){
        return modulus;
    }

    /**
     * Returns the exponent of the publicKey
     * @return BigInteger, exponent of the key.
     */
    public BigInteger getExponent(){
        return exponent;
    }
}
