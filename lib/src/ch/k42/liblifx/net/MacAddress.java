package ch.k42.liblifx.net;

import ch.k42.liblifx.minions.Minions;

/**
 * Created by Thomas on 30.01.14.
 */
public class MacAddress {

    public static final MacAddress MAC_ZERO = new MacAddress(new byte[]{0,0,0,0,0,0});
    public static final MacAddress MAC_DEAD = new MacAddress(new byte[]{(byte)0xDE,(byte)0xAD,(byte)0xBE,(byte)0xEF,(byte)0xDE,(byte)0xAD});
    public static final MacAddress MAC_BROADCAST = new MacAddress(new byte[]{(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255});

    byte[] address = new byte[6];

    public MacAddress(byte[] address) {
        this.address = address;
    }

    public byte[] getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return Minions.toHexString(address);
    }
}
