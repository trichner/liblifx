package ch.k42.liblifx.net;

import ch.k42.liblifx.minions.Minions;
import ch.k42.liblifx.minions.Uint16;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Thomas on 30.01.14.
 */
public class RawPacket {
//    packet
//    {
//        uint16 size;              // LE
//        uint16 protocol;
//        uint32 reserved1;         // Always 0x0000
//        byte   target_mac_address[6];
//        uint16 reserved2;         // Always 0x00
//        byte   site[6];           // MAC address of gateway PAN controller bulb
//        uint16 reserved3;         // Always 0x00
//        uint64 timestamp;
//        uint16 packet_type;       // LE
//        uint16 reserved4;         // Always 0x0000
//
//        varies payload;           // Documented below per packet type
//    }

    public static final Uint16 PROTOCOL = new Uint16((byte)0x00,(byte)0x34); // I have no idea what it actually means...

    private Uint16 protocol;
    private MacAddress target;
    private MacAddress site;
    private byte[] timestamp;
    private Uint16 packetType;
    private ByteBuffer payload = ByteBuffer.allocate(0);

    public RawPacket(Uint16 protocol, MacAddress target, MacAddress site, Uint16 packetType, ByteBuffer payload) {
        this.protocol = protocol;
        this.target = target;
        this.site = site;
        this.packetType = packetType;
        this.payload = payload;
    }

    public RawPacket(Uint16 protocol, MacAddress target, MacAddress site, Uint16 packetType) {
        this(protocol, target, site, packetType, ByteBuffer.allocate(0));
    }

    public RawPacket(ByteBuffer packet){
        byte lByte,uByte;
        packet.order(ByteOrder.LITTLE_ENDIAN);

        lByte = packet.get();   // size
        uByte = packet.get();
        int size = new Uint16(lByte,uByte).getInt();

        lByte = packet.get();   // protocol
        uByte = packet.get();
        this.protocol = new Uint16(lByte,uByte);

        packet.getInt(); // 32bit reserved

        byte[] target = new byte[6]; // target mac
        packet.get(target);
        this.target = new MacAddress(target);

        packet.getShort(); // 16bit reserved

        byte[] site = new byte[6]; // target mac
        packet.get(site);
        this.site = new MacAddress(site);

        packet.getShort(); // 16bit reserved

        this.timestamp = new byte[8]; // target mac
        packet.get(timestamp);

        lByte = packet.get();   // packetType
        uByte = packet.get();
        this.packetType = new Uint16(lByte,uByte);

        packet.getShort(); // 16bit reserved

        if(size>36){
            byte[] paybuf = new byte[size-36];
            packet.get(paybuf);
            this.payload = ByteBuffer.wrap(paybuf);
        }


    }

    public byte[] getBytes(){
        final byte[] zero16 = {0x00,0x00};
        int length = 36+payload.capacity();
        ByteBuffer buf = ByteBuffer.allocate(length);

        buf.put(Uint16.getBytesLE(length)); // size
        buf.put(protocol.getBytesLE());     // protocol
        buf.put(zero16).put(zero16);        // res1
        buf.put(target.getAddress());       // target MAC
        buf.put(zero16);                    // res2
        buf.put(site.getAddress());         // site MAC
        buf.put(zero16);                    // res3
        buf.put(zero16).put(zero16).put(zero16).put(zero16); //timestamp TODO
        buf.put(packetType.getBytesLE());   // packetType
        buf.put(zero16);                    // res4
        buf.put(payload.array());           // payload

        return buf.array();
    }

    public Uint16 getProtocol() {
        return protocol;
    }

    public MacAddress getTarget() {
        return target;
    }

    public MacAddress getSite() {
        return site;
    }

    public Uint16 getPacketType() {
        return packetType;
    }

    public ByteBuffer getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("LIFX packet\n{\n");
        int size = 36 + payload.capacity();
        sbuf.append("\tsize\t\t: ").append(size).append('\n');
        sbuf.append("\tprotocol\t: ").append(protocol).append('\n');
        sbuf.append("\ttarget\t\t: ").append(target).append('\n');
        sbuf.append("\tsite\t\t: ").append(site).append('\n');
        sbuf.append("\ttimestamp\t: ").append(Minions.toHexString(timestamp)).append('\n');
        sbuf.append("\tpacket_type\t: ").append(String.format("0x%02X",packetType.getInt())).append('\n');
        sbuf.append("\tpayload\t\t: ").append(Minions.toHexString(payload.array())).append('\n');
        sbuf.append("}\n");
        return sbuf.toString();
    }
}
