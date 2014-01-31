package ch.k42.liblifx.model;

import ch.k42.liblifx.net.MacAddress;

/**
 * Created by Thomas on 30.01.14.
 */
public class Bulb {
    private MacAddress address;
    private int port;

    public Bulb(MacAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public MacAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
