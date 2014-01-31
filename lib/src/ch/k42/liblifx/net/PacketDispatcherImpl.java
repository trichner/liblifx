package ch.k42.liblifx.net;

import ch.k42.liblifx.minions.Uint16;
import ch.k42.liblifx.phandlers.PacketHandler;

import java.util.*;

/**
 * Created by Thomas on 31.01.14.
 */
public class PacketDispatcherImpl implements PacketDispatcher {

    private Map<Uint16,List<PacketHandler>> handlers = new HashMap<Uint16, List<PacketHandler>>();
    private PacketHandler defaultHandler=null;

    @Override
    public void dispatch(RawPacket packet) {
        Uint16 type = packet.getPacketType();
        if(handlers.containsKey(type)){
            for(PacketHandler h : handlers.get(type)){
                h.handle(packet);
            }
        }else {
            if(defaultHandler!=null)
            defaultHandler.handle(packet);
        }

    }

    @Override
    public void registerPacketHandler(PacketType event, PacketHandler handler) {
        synchronized (handlers){
            if(handlers.containsKey(event)){
                handlers.get(event).add(handler);
            }else{
                List<PacketHandler> l = new ArrayList<PacketHandler>();
                l.add(handler);
                handlers.put(event.type,l);
            }
        }
    }

    @Override
    public void registerDefaultHandler(PacketHandler handler) {
        this.defaultHandler = handler;
    }
}
