package ch.k42.liblifx.minions;

/**
 * Created by Thomas on 31.01.14.
 */
public class Int16 {
    private short int16;

    public Int16(short int16) {
        this.int16 = int16;
    }

    public short getShort(){
        return int16;
    }


    /**
     * Returns the integer in a byte array, little endian
     * @return byte array with length 2
     */
    public byte[] getBytesLE(){
        return getBytesLE(int16);
    }

    /**
     * Returns the integer in a byte array, little endian
     * @return byte array with length 2
     */
    public static byte[] getBytesLE(int int16){
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (int16 & 0xFF);
        bytes[1] = (byte) ((int16 >>> 8) & 0xFF);
        return bytes;
    }
}
