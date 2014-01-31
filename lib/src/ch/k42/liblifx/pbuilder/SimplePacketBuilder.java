package ch.k42.liblifx.pbuilder;

import ch.k42.liblifx.minions.Uint16;
import ch.k42.liblifx.net.MacAddress;
import ch.k42.liblifx.net.PacketType;
import ch.k42.liblifx.net.RawPacket;
import sun.management.resources.agent_pt_BR;

import java.nio.ByteBuffer;

/**
 * Created by Thomas on 31.01.14.
 */
public class SimplePacketBuilder {
    public static final RawPacket DISCOVERY_PACKET = new RawPacket(RawPacket.PROTOCOL, MacAddress.MAC_ZERO,MacAddress.MAC_ZERO, PacketType.GET_PAN_GATEWAY.type);

    public static final RawPacket getPowerState(MacAddress target,MacAddress site){
        return assembleSimple(PacketType.GET_POWER_STATE,target,site);
    }

    public static final RawPacket setPowerState(boolean on,MacAddress target,MacAddress site){
        byte[] payload = new byte[2];
        payload[1] = 0;
        payload[0] = (byte) (on ? 1 : 0);
        return assembleSimple(PacketType.SET_POWER_STATE,target,site,payload);
    }

    public static final RawPacket getTags(MacAddress target,MacAddress site){
        return assembleSimple(PacketType.GET_TAGS,target,site);
    }

    public static final RawPacket getVersion(MacAddress target,MacAddress site){
        return assembleSimple(PacketType.GET_VERSION,target,site);
    }

    public static final RawPacket getLightState(MacAddress target,MacAddress site){
        return assembleSimple(PacketType.GET_LIGHT_STATE,target,site);
    }

    public static final RawPacket setReboot(MacAddress target,MacAddress site){
        return assembleSimple(PacketType.GET_LIGHT_STATE,target,site);
    }



    private static RawPacket assembleSimple(PacketType packetType, MacAddress target,MacAddress site,byte[] payload){
        return new RawPacket(RawPacket.PROTOCOL,target,site, packetType.type, ByteBuffer.wrap(payload));
    }

    private static RawPacket assembleSimple(PacketType packetType, MacAddress target,MacAddress site){
        return new RawPacket(RawPacket.PROTOCOL,target,site, packetType.type);
    }
}
