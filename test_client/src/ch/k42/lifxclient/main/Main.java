package ch.k42.lifxclient.main;

import ch.k42.liblifx.minions.Minions;
import ch.k42.liblifx.net.*;
import ch.k42.liblifx.pbuilder.PacketBuilder;
import ch.k42.liblifx.pbuilder.SetDimAbsPacketBuilder;
import ch.k42.liblifx.pbuilder.SetLightPacketBuilder;
import ch.k42.liblifx.pbuilder.SimplePacketBuilder;
import ch.k42.liblifx.phandlers.PacketPrinter;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

/**
 * Created by Thomas on 31.01.14.
 */
public class Main {

    public static final int LIFX_PORT = 56700;

    public static void main(String[] args) {

        List<InetAddress> broadcasts = Minions.getBroadcastAddresses();
        if(broadcasts.size()<1)
            System.err.println("No broadcast address found!");
        if(broadcasts.size()>1)
            System.out.println("Found several networks, using: " + broadcasts.get(0));

        InetAddress broadcast = broadcasts.get(0);
        PacketDispatcher dispatcher = new PacketDispatcherImpl();
        try {
            UDPClient client = new UDPClient(broadcast,LIFX_PORT,dispatcher);
            //dispatcher.registerPacketHandler(PacketType.R_PAN_GATEWAY,new PacketPrinter());
            //dispatcher.registerPacketHandler(PacketType.R_POWER_STATE,new PacketPrinter());
            dispatcher.registerDefaultHandler(new PacketPrinter());
            client.start();
            // setup finished

            MacAddress target = new MacAddress(new byte[]{(byte)0xD0,(byte)0x73,(byte)0xD5,(byte)0x00,(byte)0x29,(byte)0xD3});

            client.sendPacket(SimplePacketBuilder.DISCOVERY_PACKET);
            client.sendPacket(SimplePacketBuilder.getPowerState(target,target));
            //client.sendPacket(SimplePacketBuilder.getTags(target,target));
            //client.sendPacket(SimplePacketBuilder.getVersion(target, target));
            client.sendPacket(SimplePacketBuilder.setPowerState(true,target,target));
            SetLightPacketBuilder packetBuilder = new SetLightPacketBuilder();
            packetBuilder.setColor(Color.RED).setFadeTime(10).setTarget(target).setSite(target);
            client.sendPacket(packetBuilder.build());


            Thread.sleep(5000);
            client.sendPacket(SimplePacketBuilder.getLightState(target,target));

            SetDimAbsPacketBuilder dimPacketB = new SetDimAbsPacketBuilder();
            dimPacketB.setBrightness(1).setFadeTime(10).setTarget(target).setSite(target);
            client.sendPacket(dimPacketB.build());


            for(int i=0;i<200;i++){
                Thread.sleep(1000);
                client.sendPacket(SimplePacketBuilder.getLightState(target,target));
            }




            client.shutdown();

        } catch (SocketException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
