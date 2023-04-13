package wf.utils.bukkit.plugins.world_guard.region;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.World;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RegionUtils {

    public static ProtectedCuboidRegion createRegion(String name, World w, Location p1, Location p2){
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(w);
        ProtectedCuboidRegion rg = new ProtectedCuboidRegion(name,
                BlockVector3.at(p1.getX(), p1.getY(), p1.getZ()),
                BlockVector3.at(p2.getX(), p2.getY(), p2.getZ()));

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(world);
        regions.addRegion(rg);
        return rg;
    }

    public static boolean containRegion(World w, Location p1, Location p2){
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(w);
        ProtectedCuboidRegion rg = new ProtectedCuboidRegion("WF_UTILS_CHECK_REGION",
                BlockVector3.at(p1.getX(), p1.getY(), p1.getZ()),
                BlockVector3.at(p2.getX(), p2.getY(), p2.getZ()));
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(world);

        ApplicableRegionSet set = regionManager.getApplicableRegions(rg);

        return (set.size() != 0);
    }

    public static boolean containRegion(World w, String name){
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(w);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(world);

        return regionManager.getRegion(name) != null;
    }

    public static void deleteRegion(World w, String name){
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(w);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(world);

        regions.removeRegion(name);
    }

    public static void deleteRegions(World w){
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(w);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(world);
        for(ProtectedRegion region : regions.getRegions().values().toArray(new ProtectedRegion[0])){
            regions.removeRegion(region.getId());
        }
    }

    public static ProtectedRegion getRegion(World w, String name){
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(w);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(world);

        return regionManager.getRegion(name);
    }

    public static void addMember(ProtectedRegion rg, UUID player){
        rg.getMembers().addPlayer(player);
    }

    public static void removeMember(ProtectedRegion rg, UUID player){
        rg.getMembers().removePlayer(player);
    }

    public static boolean containMember(ProtectedRegion rg, UUID player){
        return rg.getMembers().contains(player);
    }

    public static List<ProtectedRegion> getRegions(Location location) {
        return new ArrayList<>(WorldGuard.getInstance().getPlatform().getRegionContainer()
                .get(new BukkitWorld(location.getWorld())).getApplicableRegions(BlockVector3.at(
                        location.getX(),location.getY(), location.getZ())).getRegions());
    }


}
