package ch.k42.liblifx.pbuilder;

import ch.k42.liblifx.net.MacAddress;
import ch.k42.liblifx.net.RawPacket;

/**
 * Created by Thomas on 31.01.14.
 */
public abstract class PacketBuilder {
    protected MacAddress target =  MacAddress.MAC_DEAD;
    protected MacAddress site =  MacAddress.MAC_DEAD;

    public PacketBuilder setTarget(MacAddress target){
        this.target = target;
        return this;
    }

    public PacketBuilder setSite(MacAddress site){
        this.site = site;
        return this;
    }

    public abstract RawPacket build();
}
