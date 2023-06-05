package wf.utils.bukkit.world;

import org.bukkit.Location;
import org.bukkit.World;
import wf.utils.java.math.MathUtils;

public class WorldUtils {

    public static boolean hasBlockInLine(World world, double x1, double y1, double z1, double x2, double y2, double z2, double step) {

        double[] confusion = MathUtils.getConfusion(x2 - x1, y2 - y1, z2 - z1);
        confusion = new double[]{confusion[0] * step, confusion[1] * step ,confusion[2] * step, confusion[3]};
        int each = (int) Math.round(confusion[3] / step);

        for (double d = 0; d < each; d++) {
            if(!world.getBlockAt((int) Math.round(x1 + (confusion[0] * d)), (int) Math.round(y1 + (confusion[1] * d)),
                    (int) Math.round(z1 + (confusion[2] * d))).isEmpty()) return true;
        }
        return false;
    }

    public static boolean locationInLocations(Location l1, Location l2, Location el){
        if(!l1.getWorld().equals(el.getWorld())) return false;
        if(el.getX() < Math.min(l1.getX(), l2.getX()) || el.getX() > Math.max(l1.getX(), l2.getX())) return false;
        if(el.getY() < Math.min(l1.getY(), l2.getY()) || el.getY() > Math.max(l1.getY(), l2.getY())) return false;
        if(el.getZ() < Math.min(l1.getZ(), l2.getZ()) || el.getZ() > Math.max(l1.getZ(), l2.getZ())) return false;

        return true;
    }

}
