package me.tony.main.voltnetwork.CustomBoss;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static me.tony.main.voltnetwork.StaffMode.StaffUtil.prefix;

public class BossCommands implements CommandExecutor {

    public static ArrayList<Integer> XBossWarp = new ArrayList<>();
    public static ArrayList<Integer> YBossWarp = new ArrayList<>();
    public static ArrayList<Integer> ZBossWarp = new ArrayList<>();
    public static ArrayList<String> WorldBossWarp = new ArrayList<>();
    public static ArrayList<Double> WarpPitch = new ArrayList<>();
    public static ArrayList<Double> WarpYaw = new ArrayList<>();
    public static ArrayList<Location> WarpLoc = new ArrayList<>();

    public static ArrayList<Integer> XBossSpawn = new ArrayList<>();
    public static ArrayList<Integer> YBossSpawn = new ArrayList<>();
    public static ArrayList<Integer> ZBossSpawn = new ArrayList<>();
    public static ArrayList<String> WorldBossSpawn = new ArrayList<>();
    public static ArrayList<Location> BossSpawn = new ArrayList<>();
    public static ArrayList<String> Alive = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        Player p = (Player) sender;

        if (p.hasPermission(adminPerm)) {
            if (args.length == 1) {
                String subCommand = args[0];

                String w = p.getWorld().getName();
                int x = (int) p.getLocation().getX();
                int y = (int) p.getLocation().getY();
                int z = (int) p.getLocation().getZ();

                if (subCommand.equalsIgnoreCase("warp")) {
                    if (WarpLoc.isEmpty()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Boss warp is not setup!"));
                    } else {
                        p.teleport(WarpLoc.get(0));
                    }
                }

                if (subCommand.equalsIgnoreCase("setwarp")) {
                    registerBossWarp(p);
                }

                if (subCommand.equalsIgnoreCase("setspawn")) {
                    registerBossSpawn(p);
                    BossCommands.BossSpawn.add(registerBossSpawn(p));
                }
                if (subCommand.equalsIgnoreCase("create")) {
                    BossUtil.SignCreate(p);
                }
                if (subCommand.equalsIgnoreCase("spawn")) {
                    BossUtil.Boss(Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)), BossCommands.BossSpawn.get(0));
                    Alive.add("true");
                }
                if (subCommand.equalsIgnoreCase("rewards")) {
                    BossInventoryUtil.RewardEdit(p);
                }
                if (subCommand.equalsIgnoreCase("test")) {
                    EndMobs.Mob(p.getLocation());
                }
            }
        }
        return true;
    }

    public Location registerBossSpawn(Player p) {
        Location loc = p.getLocation();
        int x = (int) loc.getX();
        int y = (int) loc.getY();
        int z = (int) loc.getZ();
        String worldName = loc.getWorld().getName();

        if (XBossSpawn.isEmpty() || YBossSpawn.isEmpty() || ZBossSpawn.isEmpty() || WorldBossSpawn.isEmpty()) {
            XBossSpawn.add(x);
            YBossSpawn.add(y);
            ZBossSpawn.add(z);
            WorldBossSpawn.add(worldName);

            BossFileManager.getInstance().SaveData();

            return new Location(Bukkit.getWorld(worldName), x, y, z);
        } else {

            XBossSpawn.clear();
            YBossSpawn.clear();
            ZBossSpawn.clear();
            WorldBossSpawn.clear();

            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've replaced the Boss Spawn Point!"));

            XBossSpawn.add(x);
            YBossSpawn.add(y);
            ZBossSpawn.add(z);
            WorldBossSpawn.add(worldName);

            BossFileManager.getInstance().SaveData();

            return new Location(Bukkit.getWorld(worldName), x, y, z);

        }
    }

    public Location registerBossWarp(Player p) {
        Location loc = p.getLocation();
        int x = (int) loc.getX();
        int y = (int) loc.getY();
        int z = (int) loc.getZ();
        String worldName = loc.getWorld().getName();

        if (XBossWarp.isEmpty() || YBossWarp.isEmpty() || ZBossWarp.isEmpty() || WorldBossWarp.isEmpty()) {
            XBossWarp.add(x);
            YBossWarp.add(y);
            ZBossWarp.add(z);
            WorldBossWarp.add(worldName);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've set the Boss Warp!"));

            BossFileManager.getInstance().SaveData();

        } else {
            XBossWarp.clear();;
            ZBossWarp.clear();
            YBossWarp.clear();
            WorldBossWarp.clear();

            XBossWarp.add(x);
            YBossWarp.add(y);
            ZBossWarp.add(z);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've replaced the Boss Warp"));

            BossFileManager.getInstance().SaveData();
        }

        return loc;
    }
}
