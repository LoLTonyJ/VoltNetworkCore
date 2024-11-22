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
import java.util.HashMap;

public class BossCommands implements CommandExecutor {

    public static ArrayList<Integer> XSpawn = new ArrayList<>();
    public static ArrayList<Integer> YSpawn = new ArrayList<>();
    public static ArrayList<Integer> ZSpawn = new ArrayList<>();
    public static ArrayList<String> World = new ArrayList<>();
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
                if (subCommand.equalsIgnoreCase("setspawn")) {
                    String w = p.getWorld().getName();
                    int x = (int) p.getLocation().getX();
                    int y = (int) p.getLocation().getY();
                    int z = (int) p.getLocation().getZ();
                    if (XSpawn.isEmpty() && YSpawn.isEmpty() && ZSpawn.isEmpty() && World.isEmpty() && BossSpawn.isEmpty()) {
                        XSpawn.add(x);
                        YSpawn.add(y);
                        ZSpawn.add(z);
                        World.add(w);

                        Location bLoc = new Location(Bukkit.getWorld(w), x, y, z);
                        BossSpawn.add(bLoc);

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " Set Boss Spawn"));
                    } else {
                        XSpawn.remove(0);
                        XSpawn.add(x);
                        YSpawn.remove(0);
                        YSpawn.add(y);
                        ZSpawn.remove(0);
                        ZSpawn.add(z);
                        World.remove(0);
                        World.add(w);
                        Location bLoc = new Location(Bukkit.getWorld(w), x, y, z);

                        BossSpawn.clear();
                        BossSpawn.add(bLoc);

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " Replaced Boss Spawn"));

                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',  "X: " + x));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',  "Y: " + y));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',  "Z: " + z));
                    BossFileManager.getInstance().SaveData();
                }
                if (subCommand.equalsIgnoreCase("create")) {
                    BossUtil.SignCreate(p);
                }
                if (subCommand.equalsIgnoreCase("spawn")) {
                    BossUtil.Boss(Bukkit.getWorld(BossCommands.World.get(0)), BossCommands.BossSpawn.get(0));
                    Alive.add("true");
                }
                if (subCommand.equalsIgnoreCase("rewards")) {
                    BossInventoryUtil.RewardEdit(p);
                }
            }
        }
        return true;
    }
}
