package ch.k42.liblifx.minions;

import ch.k42.liblifx.minions.Minions;
import ch.k42.liblifx.minions.Uint16;
import ch.k42.liblifx.net.MacAddress;
import ch.k42.liblifx.net.RawPacket;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

/**
 * Created by Thomas on 30.01.14.
 */
public class Main {
    public static void main(String[] args) {
        try {

            for(InetAddress a : Minions.getBroadcastAddresses()){
                System.out.println(a);
            }


            DatagramSocket socket = new DatagramSocket(56700);


            MacAddress target = MacAddress.MAC_ZERO;
            MacAddress site = MacAddress.MAC_ZERO;
            Uint16 protocol = new Uint16((byte)0x00,(byte)0x34);
            Uint16 type = new Uint16((byte) 0x02,(byte) 0x00);

            RawPacket rpacket = new RawPacket(protocol,target,site,type);

            System.out.println("sending packet");
            byte[] buf  = rpacket.getBytes();
            DatagramPacket packet = new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.0.255"),56700);
            socket.send(packet);
            System.out.println("Packet sent.");

            for(int i=0;i<10;i++){
                byte[] ret = new byte[1024];
                DatagramPacket packet1 = new DatagramPacket(ret,ret.length);
                socket.receive(packet1);
                System.out.println("received packet, size: " + packet1.getData().length);
                System.out.println(Minions.toHexString(packet1.getData()));
                rpacket = new RawPacket(ByteBuffer.wrap(packet1.getData()));
                System.out.println(rpacket);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
