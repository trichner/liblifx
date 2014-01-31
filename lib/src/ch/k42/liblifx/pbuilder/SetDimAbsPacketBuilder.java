package ch.k42.liblifx.pbuilder;

import ch.k42.liblifx.minions.Int16;
import ch.k42.liblifx.minions.Minions;
import ch.k42.liblifx.minions.Uint16;
import ch.k42.liblifx.minions.Uint32;
import ch.k42.liblifx.net.PacketType;
import ch.k42.liblifx.net.RawPacket;

import java.nio.ByteBuffer;

/**
 * Created by Thomas on 31.01.14.
 */
public class SetDimAbsPacketBuilder extends PacketBuilder {
    private static final Uint16 packetType = PacketType.SET_DIM_ABSOLUTE.type;

    private Int16 brightness = new Int16((short)(Short.MAX_VALUE/2));
    private Uint32 fadeTime = Minions.secondsToLIFXTime(5);

    public SetDimAbsPacketBuilder setBrightness(double brightness){
        this.brightness = new Int16((short) (brightness * Short.MAX_VALUE));
        return this;
    }

    public SetDimAbsPacketBuilder setFadeTime(int seconds){
        this.fadeTime = new Uint32(seconds); //Minions.secondsToLIFXTime(seconds);
        return this;
    }

    private ByteBuffer assemblePayload(){
        ByteBuffer buf = ByteBuffer.allocate(6);
        buf.put(brightness.getBytesLE());
        buf.put(fadeTime.getBytesLE());
        return buf;
    }

    @Override
    public RawPacket build() {
        return new RawPacket(RawPacket.PROTOCOL,target,site,packetType,assemblePayload());
    }
}
