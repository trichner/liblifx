package ch.k42.liblifx.phandlers;

import ch.k42.liblifx.net.RawPacket;

/**
 * Created by Thomas on 31.01.14.
 */
public class PacketPrinter implements PacketHandler {
    @Override
    public void handle(RawPacket packet) {
        System.out.println(packet);
    }
}
