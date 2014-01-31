package ch.k42.liblifx.net;

import ch.k42.liblifx.model.Bulb;

import java.util.Set;

/**
 * Created by Thomas on 31.01.14.
 */
public interface BulbDiscovery {
    public Set<Bulb> getBulbs();
    public void registerBulb(Bulb b);
}
