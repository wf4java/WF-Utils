package wf.utils.bukkit.world;

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

}
