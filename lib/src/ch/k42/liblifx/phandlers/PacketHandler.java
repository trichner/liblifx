package ch.k42.liblifx.phandlers;

import ch.k42.liblifx.net.RawPacket;

/**
 * Created by Thomas on 31.01.14.
 */
public interface PacketHandler {
    public void handle(RawPacket packet);
}
