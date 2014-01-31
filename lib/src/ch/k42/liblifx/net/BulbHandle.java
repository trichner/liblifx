package ch.k42.liblifx.net;

import java.awt.*;

/**
 * Created by Thomas on 30.01.14.
 */
public interface BulbHandle {
    public void setPower(boolean state);
    public boolean getPower();

    public void setColor(Color color);
    public Color getColor();

    public void setLabel(String label);
    public String getLabel();

    public void setDimAbsolute(int dim);
    public void setDimRelative(int dim);

}
