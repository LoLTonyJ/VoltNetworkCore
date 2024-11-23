package me.tony.main.voltnetwork.CustomBoss;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class EndMobs {


    public static ArrayList<Integer> MobCount = new ArrayList<>();

    public static Location MiddleRegion(String RegionName, String w) {

        World wld = BukkitAdapter.adapt(Bukkit.getWorld(w));

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(wld);

        if (regions != null) {
            ProtectedRegion region = regions.getRegion(RegionName);
            if (region != null) {
                Location top = new Location(Bukkit.getWorld(w), 0, 0, 0);
                top.setX(region.getMaximumPoint().getX());
                top.setY(region.getMaximumPoint().getY());
                top.setZ(region.getMaximumPoint().getZ());

                Location bottom = new Location(Bukkit.getWorld(w), 0, 0, 0);
                bottom.setX(region.getMinimumPoint().getX());
                bottom.setY(region.getMinimumPoint().getY());
                bottom.setZ(region.getMinimumPoint().getZ());

                double X = ((top.getX() - bottom.getX()) / 2) + bottom.getX();
                double Y = ((top.getY() - bottom.getY()) / 2) + bottom.getY();
                double Z = ((top.getZ() - bottom.getZ()) / 2) + bottom.getZ();

                return new Location(Bukkit.getWorld(w), X, Y, Z);
            }
        }
        return new Location(Bukkit.getWorld(w), 0, 0, 0);
    }


    public static Entity Mob(Location l) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        org.bukkit.World w = Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0));

        Chicken end = (Chicken) w.spawnEntity(l, EntityType.CHICKEN);
        Enderman pas = (Enderman) w.spawnEntity(l, EntityType.ENDERMAN);
        end.addPassenger(pas);
        pas.setCustomName(ChatColor.translateAlternateColorCodes('&', bossName + " Watcher"));
        pas.setCustomNameVisible(true);
        end.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(500.0);
        end.setHealth(500.0);


        if (end.getTarget() == null) {
            if (!end.isDead()) {
                Bukkit.getScheduler().runTaskTimer(VoltNetwork.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        for (Entity ent : end.getNearbyEntities(10, 10, 10)) {
                            if (ent instanceof Player) {
                                pas.setTarget((Player) ent);
                                end.setTarget((Player) ent);
                                end.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, 2));
                            }
                        }
                    }
                }, 20, 20);
            }
        }
        return end;
    }
}
