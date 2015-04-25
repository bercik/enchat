package message;

import user.ActiveUser;

/**
 * Created by tochur on 18.04.15.
 */
public class Pack {
    private byte[] dataArray;
    private byte[] signArray;

    /**
     * Part of the message, every package holds one portion of data that are encrypted, and signed.
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
