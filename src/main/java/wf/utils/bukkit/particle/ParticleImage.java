package wf.utils.bukkit.particle;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.event.EventHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParticleImage {


    public static void drawImage(BufferedImage img, World world, double x, double y, double z, double size, double count){

        for(int w = 0; w < img.getWidth(); w++){
            for(int h = 0; h < img.getHeight(); h++){
                Color color = new Color(img.getRGB(w, h),true);
                if(color.getAlpha() == 0) continue;

                drawPixel(color, world,x + (size * w),y - (size * h), z, size, size,0,1d / count);

            }
        }

    }



    public static void drawPixel(Color color, World world, double x, double y, double z, double w, double h, double l, double step){

        for(double ix = x; ix <= x + w; ix += step){
            for(double iy = y; iy <= y + h; iy += step){
                for(double iz = z; iz <= z + l; iz += step){

                    world.spawnParticle(Particle.REDSTONE, ix, iy, iz,1,new Particle.DustOptions(
                            org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()),1));

                }
            }
        }

    }


}
