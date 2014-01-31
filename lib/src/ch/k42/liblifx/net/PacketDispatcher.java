package ch.k42.liblifx.net;

import ch.k42.liblifx.phandlers.PacketHandler;

/**
 * Created by Thomas on 31.01.14.
 */
public interface PacketDispatcher {
    public void dispatch(RawPacket packet);
    public void registerPacketHandler(PacketType event, PacketHandler handler);
    public void registerDefaultHandler(PacketHandler handler);
}
