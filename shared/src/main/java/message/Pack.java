package message;

/**
 * Created by tochur on 18.04.15.
 */
public class Pack {
    private byte[] dataArray;
    private byte[] signArray;
    private int dataArrayLength;
    private int signArrayLength;
    /**
     * Part of the message, every package holds one portion of data that are encrypted, and signed.
     *
     * @param dataArray - encrypted data. Main info, Other are only wraps this array content.
     * @param signArray - byte array, holds sign.
     */
    public Pack(byte[] dataArray, byte[] signArray){
        this.dataArray = dataArray;
        this.signArray = signArray;
        this.dataArrayLength = dataArray.length;
        this.signArrayLength = signArray.length;
    }

    public byte[] getDataArray() {
        return dataArray;
    }

    public byte[] getSignArray() {
        return signArray;
    }

    public int getDataArrayLength() {
        return dataArrayLength;
    }

    public int getSignArrayLength() {
        return signArrayLength;
    }
}
