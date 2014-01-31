package ch.k42.liblifx.minions;

/**
 * Created by Thomas on 30.01.14.
 */
public class Uint16 {
    public static final int MAX = 0xFFFF;
    public static final Uint16 UINT16_ZERO = new Uint16(0);
    private int uint;
    public Uint16(int uint) {
        if(uint<0)
            throw new IllegalArgumentException("Unsigned int was negative! uint16=" +uint);

        if((uint & 0xFFFF)!=uint)
            throw new IllegalArgumentException("Unsigned int bigger than 16bit! uint=" + uint);
        this.uint = uint;
    }


//    public void Uint16(byte[] bytes){
//        if(bytes.length!=2) throw new IllegalArgumentException("Not 16bit");
//        this.uint = (((int) bytes[0]) | (((int) bytes[1]) << 8));
//    }

    public Uint16(byte lByte,byte uByte){
        this.uint = (((int) lByte) | (((int) uByte) << 8));
    }

    /**
     * Converts the unsigned integer to a signed one
     * @return
     */
    public int getInt(){
        return this.uint;
    }

    /**
     * Returns the integer in a byte array, little endian
     * @return byte array with length 2
     */
    public byte[] getBytesLE(){
        return getBytesLE(uint);
    }

    /**
     * Returns the integer in a byte array, little endian
     * @return byte array with length 2
     */
    public static byte[] getBytesLE(int uint){
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (uint & 0xFF);
        bytes[1] = (byte) ((uint >>> 8) & 0xFF);
        return bytes;
    }



    private static final int make16bit(int i){
        return i & 0xFFFF;
    }

    public Uint16 add(Uint16 uint16){
        this.uint = make16bit(this.uint + uint16.getInt());
        return this;
    }

    public Uint16 mult(Uint16 uint16){
        this.uint = make16bit(this.uint * uint16.getInt());
        return this;
    }

    @Override
    public String toString() {
        return Integer.toString(uint);
    }

    @Override
    public int hashCode() {
        return uint;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Uint16){
            Uint16 u = (Uint16) obj;
            return u.uint==this.uint;
        }
        return false;
    }
}
