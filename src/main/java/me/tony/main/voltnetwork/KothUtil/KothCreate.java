package me.tony.main.voltnetwork.KothUtil;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.tony.main.voltnetwork.Koth.Commands;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;

public class KothCreate {

    // Key > Region ID || Value -> Koth Name
    public static HashMap<String, String> kothRegionConnect = new HashMap<>();
    public static HashMap<String, Double> KothRegionX = new HashMap<>();
    public static HashMap<String, Double> KothRegionY = new HashMap<>();
    public static HashMap<String, Double> KothRegionZ = new HashMap<>();
    public static HashMap<String, String> KothRegionWorld = new HashMap<>();


    public static void KothRegion(Player p, String KothName, String regionName) {

        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        Integer offsetX = VoltNetwork.getInstance().getConfig().getInt("X_offset_hologram");
        Integer offsetY = VoltNetwork.getInstance().getConfig().getInt("Y_offset_hologram");
        Integer offsetZ = VoltNetwork.getInstance().getConfig().getInt("Z_offset_hologram");

        LocalPlayer lp = WorldGuardPlugin.inst().wrapPlayer(p);
        World wld = lp.getWorld();

        org.bukkit.World w = p.getWorld();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(wld);


        if (regions != null) {
            ProtectedRegion region = regions.getRegion(regionName);
            if (region != null) {

                // Getting middle of the Region Defined.
                Location top = new Location(w, 0, 0, 0);
                top.setX(region.getMaximumPoint().getX());
                top.setY(region.getMaximumPoint().getY());
                top.setZ(region.getMaximumPoint().getZ());

                Location bottom = new Location(w, 0, 0, 0);
                bottom.setX(region.getMinimumPoint().getX());
                bottom.setY(region.getMinimumPoint().getY());
                bottom.setZ(region.getMinimumPoint().getZ());

                double X = ((top.getX() - bottom.getX()) / 2) + bottom.getX() + offsetX;
                double Y = ((top.getY() - bottom.getY()) / 2) + bottom.getY() + offsetY;
                double Z = ((top.getZ() - bottom.getZ()) / 2) + bottom.getZ() + offsetZ;

                // Config Vars
                KothRegionX.put(KothName, X);
                KothRegionY.put(KothName, Y);
                KothRegionZ.put(KothName, Z);
                KothRegionWorld.put(KothName, p.getWorld().getName());

                // Setup new location
                Location l = new Location(w, X, Y, Z);

                // Storing that Location
                // Also storing the Koth Name.
                Commands.kothLocation.put(KothName, l);
                kothRegionConnect.put(region.getId(), KothName);
                Commands.kothNames.add(KothName);
                KothFileManager.getInstance().StoreData();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have created " + KothName));

            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7That region doesn't exist!"));
            }
        }
    }
}
