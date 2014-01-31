package ch.k42.liblifx.pbuilder;

import ch.k42.liblifx.minions.Minions;
import ch.k42.liblifx.minions.Uint16;
import ch.k42.liblifx.minions.Uint32;
import ch.k42.liblifx.net.MacAddress;
import ch.k42.liblifx.net.PacketType;
import ch.k42.liblifx.net.RawPacket;

import javax.crypto.Mac;
import java.awt.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Thomas on 31.01.14.
 */
public class SetLightPacketBuilder extends PacketBuilder {
    private static final Uint16 TYPE = PacketType.SET_LIGHT_STATE.type;
    private byte stream = 0x00; // not used

    private Uint16 hue = Uint16.UINT16_ZERO;
    private Uint16 saturation = Uint16.UINT16_ZERO;
    private Uint16 brightness = Uint16.UINT16_ZERO;
    private Uint16 kelvin = Uint16.UINT16_ZERO;
    private Uint32 fadeTime = new Uint32(4000000);

    public SetLightPacketBuilder setColor(Color color){
        float[] hsb = new float[3];
        hsb = Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),hsb);
        //System.out.println("hue: " + hsb[0] + "    sat: "+hsb[1] + "    b: "+hsb[2]);
        hue        = new Uint16((int) (hsb[0]*Uint16.MAX));
        saturation = new Uint16((int) (hsb[1]*Uint16.MAX));
        brightness = new Uint16((int) (hsb[2]*Uint16.MAX));
        //System.out.println(hue + " - " + saturation + " - " + brightness);
        return this;
    }

    public SetLightPacketBuilder setTemperature(int temp){
        this.kelvin = new Uint16(temp);
        return this;
    }

    public SetLightPacketBuilder setFadeTime(int seconds){
        this.fadeTime = Minions.secondsToLIFXTime(seconds);
        return this;
    }





    private ByteBuffer assemblePayload(){
        ByteBuffer buf = ByteBuffer.allocate(13);
        buf.put(stream);
        buf.put(hue.getBytesLE());
        buf.put(saturation.getBytesLE());
        buf.put(brightness.getBytesLE());
        buf.put(kelvin.getBytesLE());
        buf.put(fadeTime.getBytesLE());
        System.out.println(buf + Arrays.toString(buf.array()));
        return buf;
    }


    @Override
    public RawPacket build() {
        return new RawPacket(RawPacket.PROTOCOL,target,site,TYPE,assemblePayload());
    }
}
