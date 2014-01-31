package ch.k42.liblifx.net;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

/**
 * Created by Thomas on 30.01.14.
 */
public class UDPClient{
    private class UDPListener extends Thread{
        boolean active = true;
        @Override
        public void run() {
            byte[] ret;
            DatagramPacket datagram;
            RawPacket rpacket;
            while (active){
                ret = new byte[BUFFER_SIZE];
                try {
                    datagram = new DatagramPacket(ret,ret.length);
                    UDPClient.this.socket.receive(datagram);
                    rpacket = new RawPacket(ByteBuffer.wrap(datagram.getData()));
                    UDPClient.this.dispatcher.dispatch(rpacket);
                } catch (IOException e) {
                    // eat it, we don't care (Timeout?)
                }
            }
        }
        public void shutdown(){
            this.active = false;
        }
    }

    private final static int BUFFER_SIZE = 512;
    private final static int SOCKET_TIMEOUT = 500;
    private DatagramSocket socket;
    private PacketDispatcher dispatcher;
    private InetAddress broadcast;
    private UDPListener listener;

    public UDPClient(InetAddress broadcast,int port,PacketDispatcher dispatcher) throws SocketException {
        this.broadcast = broadcast;
        this.dispatcher = dispatcher;
        this.listener = new UDPListener();
        this.socket = new DatagramSocket(port);
        this.socket.setSoTimeout(SOCKET_TIMEOUT);
    }

    public void start(){
        listener.start();
    }
    public void shutdown(){
        listener.shutdown();
        try {
            listener.join(600);
        } catch (InterruptedException e) {
            // eat it, I don't care
        }
    }

    public void sendPacket(RawPacket rpacket) throws IOException {
        byte[] buf = rpacket.getBytes();
        DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcast,56700);
        socket.send(packet);
    }
}
