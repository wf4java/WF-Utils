package wf.utils.bukkit.entity;



import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import wf.utils.bukkit.world.WorldUtils;
import wf.utils.java.math.MathUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


public class EntityUtils {


    public static Collection<LivingEntity> getEntityInRadius(Player player, double x, double y, boolean removeThis){
        Collection<LivingEntity> entities = player.getWorld().getNearbyLivingEntities(player.getLocation(), x, y);
        if(removeThis) entities.remove((LivingEntity) player);
        return entities;
    }

    public static Collection<LivingEntity> getEntityInCube(Player player, double x, double y, double z, boolean removeThis){
        Collection<LivingEntity> entities = player.getWorld().getNearbyLivingEntities(player.getLocation(), x, y, z);
        if(removeThis) entities.remove((LivingEntity) player);
        return entities;
    }

    public static Collection<LivingEntity> getLivingInRadius(Location location, double x, double y){
        return location.getWorld().getNearbyLivingEntities(location, x, y);
    }

    public static Collection<LivingEntity> getLivingEntityInCube(Location location, double x, double y, double z){
        return location.getWorld().getNearbyLivingEntities(location, x, y, z);
    }

    public static Collection<Entity> getEntityInCube(Location location, double x, double y, double z){
        return location.getWorld().getNearbyEntities(location, x, y, z);
    }


    public static int getEntityAgreedEntitiesCount(LivingEntity entity){
        int count = 0;
        for(LivingEntity e : entity.getWorld().getLivingEntities()){
            if(!(e instanceof Mob)) continue;
            Mob mob = (Mob) e;
            if(mob.getTarget().equals(entity)) continue;
            count++;
        }
        return count;
    }
    public static ArrayList<LivingEntity> getEntityAgreedEntities(LivingEntity entity){
        ArrayList<LivingEntity> agreedEntities = new ArrayList<>();
        for(LivingEntity e : entity.getWorld().getLivingEntities()){
            if(!(e instanceof Mob)) continue;
            Mob mob = (Mob) e;
            if(mob.getTarget().equals(entity)) continue;
            agreedEntities.add(mob);
        }
        return agreedEntities;
    }

    public static void addEffect(LivingEntity entity, PotionEffect effect){
        entity.addPotionEffect(effect,true);
    }

    public static void addEffect(Collection<LivingEntity> entities, PotionEffect effect){
        entities.forEach(e -> addEffect(e, effect));
    }

    public static void damage(LivingEntity entity, double damage, boolean netDamage){
        if(netDamage){
            entity.damage(0);
            float health = (float) (entity.getHealth() - damage);
            entity.setHealth(health < 0 ? 0 : health);
        } else entity.damage(damage);
    }

    public static void damage(Collection<LivingEntity> entities, double damage, boolean netDamage){
        entities.forEach(e -> damage(e, damage, netDamage));
    }


    public static void fireTicks(LivingEntity entity, int fireTicks){
        entity.setFireTicks(fireTicks);
    }

    public static void fireTicks(Player player, Collection<LivingEntity> entities, int fireTicks){
        entities.forEach(e -> fireTicks(e, fireTicks));
    }


    public static boolean entityIsInCube(Location l1, Location l2, Entity entity){
        return WorldUtils.locationInLocations(l1, l2, entity.getLocation());
    }

    public static void removeEntityIf(Function<Entity, Boolean> condition){
        for(World world : Bukkit.getWorlds()){
            removeEntityIf(condition, world);
        }
    }

    public static void removeLivingEntityIf(Function<LivingEntity, Boolean> condition){
        for(World world : Bukkit.getWorlds()){
            removeLivingEntityIf(condition, world);
        }
    }

    public static void removeEntityByClassIf(Function<Entity, Boolean> condition, Class<?> cl){
        for(World world : Bukkit.getWorlds()){
            removeEntityByClassIf(condition, cl, world);
        }
    }


    public static void removeEntityByClassIf(Function<Entity, Boolean> condition, Class<?> cl, World world){
        for(Entity entity : world.getEntitiesByClasses(cl)){
            if(!condition.apply(entity)) continue;
            entity.remove();
        }
    }



    public static void removeEntityIf(Function<Entity, Boolean> condition, World world){
        for(Entity entity : world.getEntities()){
            if(!condition.apply(entity)) continue;
            entity.remove();
        }
    }

    public static void removeLivingEntityIf(Function<LivingEntity, Boolean> condition, World world){
        for(LivingEntity entity : world.getLivingEntities()){
            if(!condition.apply(entity)) continue;
            entity.remove();
        }
    }

    public static void removeEntityInCube(Function<Entity, Boolean> condition, Location l, double w, double h){
        for(Entity entity : getEntityInCube(l, w, h, w)){
            if(!condition.apply(entity)) continue;
            entity.remove();
        }
    }



    public static void lookAt(Entity entity, Location location){
        entity.teleport(entity.getLocation().setDirection(getLookAtVector(entity.getLocation(), location)));
    }

    public static Vector getLookAtVector(Location at, Location to){
        double dx = to.getX() - at.getX();
        double dy = to.getY() - at.getY() - 1.5;
        double dz = to.getZ() - at.getZ();
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
        float pitch = (float) Math.toDegrees(Math.atan2(Math.sqrt(dx * dx + dz * dz), dy)) - 90;

        return new Vector(
                Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)),
                Math.sin(Math.toRadians(pitch)),
                Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
    }

    public static double getYawSideAngle(Location at, Location to){
        return MathUtils.roundToNearestNumber(at.clone().setDirection(getLookAtVector(at, to)).getYaw() + 90,90);
    }

    public static double getPitchSideAngle(Location at, Location to){
        return MathUtils.roundToNearestNumber(at.clone().setDirection(getLookAtVector(at, to)).getPitch(),90);
    }

    public static void setYawSideAngle(Entity entity, Location to){
        setYaw(entity, (float) MathUtils.roundToNearestNumber(entity.getLocation().clone().setDirection(
                getLookAtVector(entity.getLocation(), to)).getYaw() + 90,90));
    }

    public static void setPitchSideAngle(Entity entity, Location to){
        setPitch(entity, (float) MathUtils.roundToNearestNumber(entity.getLocation().clone().setDirection(
                getLookAtVector(entity.getLocation(), to)).getPitch(),90));
    }

    public static void setRotationSideAngle(Entity entity, Location to){
        setYawSideAngle(entity, to);
        setPitchSideAngle(entity, to);
    }

    public static void setYaw(Entity entity, float yaw){
        Location location = entity.getLocation();
        location.setYaw(yaw);
        entity.teleport(location);
    }

    public static void setPitch(Entity entity, float pitch){
        Location location = entity.getLocation();
        location.setPitch(pitch);
        entity.teleport(location);
    }

    public static void setRotation(Entity entity, float yaw, float pitch){
        Location location = entity.getLocation();
        location.setYaw(yaw);
        location.setPitch(pitch);
        entity.teleport(location);
    }

}
