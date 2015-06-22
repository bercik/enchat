package message.types;

/**
 * @author Created by tochur on 18.04.15.
 *
 * This is direct wrapper of byte[].
 * To send some info between client and server, It must be changed to
 * byte[] and signed.
 *
 *  Pack format is:
 *      dataArrayLength - (int32)
 *      dataArray       - ( byte[byteArrayLength] )
 *      signArrayLength - (int32)
 *      signArray       - ( byte[signArrayLength] )
 *
 * All information's (despite public key - during connection creating)
 * Transmitted bytes (real information) are encrypted and signed.
 */
public class Pack {
    //holds encrypted data
    private byte[] dataArray;
    //holds sign
    private byte[] signArray;

    /**
     * Creates Pack from prepared data.
     *
     * @param dataArray - encrypted data. Main info, Other are only wraps this array content.
     * @param signArray - byte array, holds sign.
     */
    public Pack(byte[] dataArray, byte[] signArray){
        this.dataArray = dataArray;
        this.signArray = signArray;
    }

    public byte[] getDataArray() {
        return dataArray;
    }

    public byte[] getSignArray() {
        return signArray;
    }

    public int getDataArrayLength() {
        return dataArray.length;
    }

    public int getSignArrayLength() {
        return signArray.length;
    }
}
