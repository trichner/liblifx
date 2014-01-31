package ch.k42.liblifx.minions;

/**
 * Created by Thomas on 30.01.14.
 */
public class Uint32 {
    public static final int MAX = 0xFFFFFFFF;
    private long uint;
    public Uint32(long uint) {
        if(uint<0)
            throw new IllegalArgumentException("Unsigned int was negative! uint32=" +uint);

        if((uint & 0xFFFFFFFF)!=uint)
            throw new IllegalArgumentException("Unsigned int bigger than 32bit! uint32=" + uint);
        this.uint = uint;
    }

    public Uint32(byte[] uint32){
        this.uint = (((int) uint32[0]) | (((int) uint32[1]) << 8) | (((int) uint32[2]) << 16) | (((int) uint32[3]) << 24));
    }

    /**
     * Converts the unsigned integer to a signed one
     * @return
     */
    public long getLong(){
        return this.uint;
    }

    /**
     * Returns the integer in a byte array, little endian
     * @return byte array with length 2
     */
    public byte[] getBytesLE(){
        return getBytesLE(this.uint);
    }

    /**
     * Returns the integer in a byte array, little endian
     * @return byte array with length 2
     */
    public static byte[] getBytesLE(long uint){
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (uint & 0xFF);
        bytes[1] = (byte) ((uint >>> 8) & 0xFF);
        bytes[2] = (byte) ((uint >>> 16) & 0xFF);
        bytes[3] = (byte) ((uint >>> 24) & 0xFF);
        return bytes;
    }



    private static final long make32bit(long i){
        return i & 0xFFFFFFFF;
    }

    @Override
    public String toString() {
        return Long.toString(uint);
    }

    @Override
    public int hashCode() {
        return (int) uint;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Uint32){
            Uint32 u = (Uint32) obj;
            return u.uint==this.uint;
        }
        return false;
    }
}
