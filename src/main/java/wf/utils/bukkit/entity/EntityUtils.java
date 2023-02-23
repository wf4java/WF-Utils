package wf.utils.bukkit.entity;



import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;



public class EntityUtils {


    public static Collection<LivingEntity> entityInRadius(Player player, double x, double y, boolean removeThis){
        Collection<LivingEntity> entities = player.getWorld().getNearbyLivingEntities(player.getLocation(), x, y);
        if(removeThis) entities.remove((LivingEntity) player);
        return entities;
    }

    public static Collection<LivingEntity> entityInCube(Player player, double x, double y, double z, boolean removeThis){
        Collection<LivingEntity> entities = player.getWorld().getNearbyLivingEntities(player.getLocation(), x, y, z);
        if(removeThis) entities.remove((LivingEntity) player);
        return entities;
    }

    public static Collection<LivingEntity> entityInRadius(Location location, double x, double y){
        return location.getWorld().getNearbyLivingEntities(location, x, y);
    }

    public static Collection<LivingEntity> entityInCube(Location location, double x, double y, double z){
        return location.getWorld().getNearbyLivingEntities(location, x, y, z);
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

    public static void addEffect(Player player, LivingEntity entity, PotionEffect effect){
        entity.addPotionEffect(effect,true);
    }

    public static void addEffect(Player player, Collection<LivingEntity> entities, PotionEffect effect){
        entities.forEach(e -> addEffect(player, e, effect));
    }

    public static void damage(Player player, LivingEntity entity, double damage, boolean netDamage){
        if(netDamage){
            entity.damage(0);
            float health = (float) (entity.getHealth() - damage);
            entity.setHealth(health < 0 ? 0 : health);
        } else entity.damage(damage);
    }

    public static void damage(Player player, Collection<LivingEntity> entities, double damage, boolean netDamage){
        entities.forEach(e -> damage(player, e, damage, netDamage));
    }


    public static void fireTicks(Player player, LivingEntity entity, int fireTicks){
        entity.setFireTicks(fireTicks);
    }

    public static void fireTicks(Player player, Collection<LivingEntity> entities, int fireTicks){
        entities.forEach(e -> fireTicks(player, e, fireTicks));
    }


}
