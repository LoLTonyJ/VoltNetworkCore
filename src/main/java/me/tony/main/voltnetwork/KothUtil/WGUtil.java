package me.tony.main.voltnetwork.KothUtil;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.tony.main.voltnetwork.Koth.Commands;
import org.bukkit.Location;

public class WGUtil {



    // Some magical stuffz for loc logic
    public static ApplicableRegionSet getApplicableRegions(Location loc) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager;
        if (container == null || (manager = container.get(BukkitAdapter.adapt(loc.getWorld()))) == null) {
            return null;
        }
        return manager.getApplicableRegions(
                BukkitAdapter.asBlockVector(loc));
    }

    // Checks to see if the player is in a region
    public static boolean inRegion(Location loc) {
        ApplicableRegionSet regions = getApplicableRegions(loc);
        if (regions == null || regions.getRegions().isEmpty()) return false;
        for (ProtectedRegion rg : regions) {
            if (KothCreate.kothRegionConnect.containsKey(rg.getId())) {
                if (Commands.kothActive.contains(KothCreate.kothRegionConnect.get(rg.getId()))) {
                        return true;
                    }
                }
            }
        return false;
    }
}
