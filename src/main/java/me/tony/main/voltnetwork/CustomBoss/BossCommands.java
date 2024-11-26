package me.tony.main.voltnetwork.CustomBoss;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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

                if (subCommand.equalsIgnoreCase("setwarp")) {
                    if (XBossWarp.isEmpty() && YBossWarp.isEmpty() && ZBossWarp.isEmpty() && WorldBossWarp.isEmpty() && WarpYaw.isEmpty() && WarpPitch.isEmpty()) {
                        XBossWarp.add(x);
                        YBossWarp.add(y);
                        ZBossWarp.add(z);
                        WorldBossWarp.add(w);
                        Location wLoc = new Location(Bukkit.getWorld(w), x, y, z);

                        WarpLoc.add(wLoc);

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You've set the Warp!"));
                    } else {
                        XBossWarp.clear();
                        YBossWarp.clear();
                        ZBossWarp.clear();
                        WorldBossWarp.clear();
                        WarpYaw.clear();
                        WarpPitch.clear();
                        WarpLoc.clear();

                        XBossWarp.add(x);
                        YBossWarp.add(y);
                        ZBossWarp.add(z);
                        WorldBossWarp.add(w);

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You've replaced the Warp!"));
                    }
                    BossFileManager.getInstance().SaveData();
                }

                if (subCommand.equalsIgnoreCase("setspawn")) {
                    if (XBossSpawn.isEmpty() && YBossSpawn.isEmpty() && ZBossSpawn.isEmpty() && WorldBossSpawn.isEmpty() && BossSpawn.isEmpty()) {
                        XBossSpawn.add(x);
                        YBossSpawn.add(y);
                        ZBossSpawn.add(z);
                        WorldBossSpawn.add(w);

                        Location bLoc = new Location(Bukkit.getWorld(w), x, y, z);
                        BossSpawn.add(bLoc);

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " Set Boss Spawn"));
                    } else {
                        XBossSpawn.remove(0);
                        XBossSpawn.add(x);
                        YBossSpawn.remove(0);
                        YBossSpawn.add(y);
                        ZBossSpawn.remove(0);
                        ZBossSpawn.add(z);
                        WorldBossSpawn.remove(0);
                        WorldBossSpawn.add(w);
                        Location bLoc = new Location(Bukkit.getWorld(w), x, y, z);

                        BossSpawn.clear();
                        BossSpawn.add(bLoc);

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " Replaced Boss Spawn"));

                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "X: " + x));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Y: " + y));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Z: " + z));
                    BossFileManager.getInstance().SaveData();
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
}
