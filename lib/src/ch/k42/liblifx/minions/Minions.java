package ch.k42.liblifx.minions;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
 * Created by Thomas on 30.01.14.
 */
public class Minions {
    public static String toHexString(byte[] array){
        if(array.length==0)
            return "";

        StringBuffer sbuf = new StringBuffer();
        for(int i=0;i<array.length-1;i++){
            sbuf.append(String.format("%02X:",array[i]));
        }
        sbuf.append(String.format("%02X",array[array.length-1]));
        return sbuf.toString();
    }

    public static final Uint32 secondsToLIFXTime(int seconds){
        long fade = ((seconds)*225);
        fade *= fade; // square it
        fade &= 0xFFFFFFFF; // mod 2^32
        return new Uint32(fade);
    }

    public static List<InetAddress> getBroadcastAddresses(){
        List<InetAddress> broadcasts = new ArrayList<InetAddress>();
        System.setProperty("java.net.preferIPv4Stack", "true"); // yah.. its just that ugly, no other way around..
        try
        {


            Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces();
            while (niEnum.hasMoreElements())
            {
                NetworkInterface ni = niEnum.nextElement();
                for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses())
                {
                    if(interfaceAddress.getAddress().equals(InetAddress.getLoopbackAddress()))
                        continue;

                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if(broadcast!=null)
                        broadcasts.add(broadcast);
                }
            }
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        return broadcasts;
    }
}
