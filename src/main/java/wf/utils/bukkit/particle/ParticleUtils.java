package wf.utils.bukkit.particle;


import org.bukkit.Particle;
import org.bukkit.World;
import wf.utils.java.math.MathUtils;

public class ParticleUtils {

    public static void drawCircle(World world, double x, double y, double z, double size, double each, double step, int count,
                                  double offsetX, double offsetY, double offsetZ, double extra, Particle particle, Particle.DustOptions data) {

        for (double d = 0; d <= each; d += step) {
            world.spawnParticle(particle,x + Math.cos(d) * size, y, z + Math.sin(d) * size, count, offsetX, offsetY,
                    offsetZ, extra, data);
        }
    }

    public static void drawCircleChunk(World world, double x, double y, double z, double size, double start ,double each, double step, int count,
                                  double offsetX, double offsetY, double offsetZ, double extra, Particle particle, Particle.DustOptions data) {

        for (double d = start; d <= each; d += step) {
            world.spawnParticle(particle,x + MathUtils.cos(d) * size, y, z + MathUtils.sin(d) * size, count, offsetX, offsetY,
                    offsetZ, extra, data);
        }
    }

    public static void drawCirclePiece(World world, double x, double y, double z, double size, double angle, int count,
                                  double offsetX, double offsetY, double offsetZ, double extra, Particle particle, Particle.DustOptions data) {
            world.spawnParticle(particle,x + MathUtils.cos(angle) * size, y, z + MathUtils.sin(angle) * size, count, offsetX, offsetY,
                    offsetZ, extra, data);
    }



    public static void drawLine(World world, double x1, double y1, double z1, double x2, double y2, double z2, double step, int count,
                                double offsetX, double offsetY, double offsetZ, double extra, Particle particle, Particle.DustOptions data, boolean force) {

        double[] confusion = MathUtils.getConfusion(x2 - x1, y2 - y1, z2 - z1);
        confusion = new double[]{confusion[0] * step, confusion[1] * step ,confusion[2] * step, confusion[3]};
        int each = (int) Math.round(confusion[3] / step);

        for (double d = 0; d < each; d++) {
            world.spawnParticle(particle,x1 + (confusion[0] * d), y1 + (confusion[1] * d), z1 + (confusion[2] * d),
                    count, offsetX, offsetY, offsetZ, extra, data, force);
        }
    }


}


//location.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(Color.WHITE, 5));