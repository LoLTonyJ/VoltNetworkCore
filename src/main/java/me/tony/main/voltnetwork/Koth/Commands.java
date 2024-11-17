package me.tony.main.voltnetwork.Koth;

import com.google.gson.Gson;
import eu.decentsoftware.holograms.api.DHAPI;
import me.tony.main.voltnetwork.Administration.AdminUtil.HelpMenu;
import me.tony.main.voltnetwork.KothUtil.*;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;


public class Commands implements CommandExecutor {

    private static final Gson gson = new Gson();

    String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
    String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

    public static HashMap<String, Location> kothLocation = new HashMap<>();
    public static ArrayList<String> kothActive = new ArrayList<>();
    public static ArrayList<String> kothNames = new ArrayList<>();
    public static HashMap<String, Integer> kothDuration = new HashMap<>();

    public Integer duration = VoltNetwork.getInstance().getConfig().getInt("koth_default_duration");
    public String kothPrefix = VoltNetwork.getInstance().getConfig().getString("koth_prefix");
    public boolean enabHolo = VoltNetwork.getInstance().getConfig().getBoolean("koth_hologram");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;

        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("rewards")) {
                if (KothUtil.kothReward.containsKey(p)) {
                    KothUtil.KothRewards(p);
                } else {
                    System.out.println("false");
                }
            }
        }

        if (p.hasPermission(adminPerm)) {
            if (args.length == 0) {
                HelpMenu.KothCommands(p);
            }
            if (args.length == 1) {
                String subCommand = args[0];
                if (subCommand.equalsIgnoreCase("list")) {
                    if (kothNames.isEmpty()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cNo Koths available to list!"));
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/koth info <koth>"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Here is a list of Koths"));
                        for (String kothList : kothNames) {
                            String jointList = String.join(",", kothList);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- " + jointList));
                        }
                    }
                }
                if (subCommand.equalsIgnoreCase("cleanup")) {
                    for (String i : kothNames) {
                        if (DHAPI.getHologram(i) != null) {
                            DHAPI.removeHologram(i);
                        }
                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Cleaned up bugged Holograms!"));
                }
            }
            if (args.length == 2) {
                String subCommand = args[0];
                String editing = args[1];
                if (subCommand.equalsIgnoreCase("edit")) {
                    if (editing.equalsIgnoreCase("rewards")) {
                        RewardEdit.RewardEditInv(p);
                    }
                }

                if (subCommand.equalsIgnoreCase("info")) {
                    if (kothLocation.containsKey(editing) && kothNames.contains(editing)) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + editing + " koth information"));
                        String s;
                        if (kothActive.contains(editing)) {
                            s = "true";
                        } else {
                            s = "false";
                        }
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aActive -> " + s));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bLocation -> " + kothLocation.get(editing)));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bDuration -> " + kothDuration.getOrDefault(editing, 0)));
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cInvalid Koth Name / Koth Doesn't Exist"));
                    }
                }

                if (subCommand.equalsIgnoreCase("start")) {
                    if (kothActive.size() == 1) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Only 1 koth can be active"));
                    }
                    KothUtil.KothStart(editing, duration);
                }

                if (subCommand.equalsIgnoreCase("stop")) {
                    if (kothActive.isEmpty()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " Koth is not active!"));
                    }
                    KothAutoStart.toStart.remove(editing);
                    if (kothActive.contains(editing)) {
                        if (enabHolo) {
                            if (DHAPI.getHologram(editing) != null) {
                                DHAPI.removeHologram(editing);
                            }
                        }
                        KothUtil.kothCap.clear();
                        kothActive.remove(editing);
                        kothDuration.remove(editing);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Set " + editing + " to deactivated"));
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + editing + " has been stopped!"));
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " Koth is not active!"));
                    }
                }

                if (subCommand.equalsIgnoreCase("remove") || subCommand.equalsIgnoreCase("delete")) {

                    if (kothActive.contains(editing) || KothAutoStart.toStart.containsKey(editing)) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Please wait until the Koth has Ended before removing!"));
                    } else {
                        if (kothNames.contains(editing)) {
                            kothLocation.remove(editing);
                            KothCreate.kothRegionConnect.remove(editing);
                            kothNames.remove(editing);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have removed " + editing + " from list of koths!"));
                        }
                    }
                }
            }
            if (args.length == 3) {
                String subCommand = args[0];
                String KothName = args[1];
                String regionName = args[2];
                if (subCommand.equalsIgnoreCase("create")) {
                    if (!kothNames.contains(KothName) || !kothLocation.containsKey(KothName)) {
                        KothCreate.KothRegion(p, KothName, regionName);
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7That Koth Region already exist!"));
                    }
                }
                if (subCommand.equalsIgnoreCase("schedule")) {
                    int duration = Integer.parseInt(args[2]);
                    if (duration > 60) {
                        // Will add a method where it'll convert to hours if duration > 60
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Max Duration - 60 Minutes."));
                    }
                    if (duration == 1) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Duration must be greater than 1"));
                    }

                    if (kothNames.contains(KothName)) {
                        KothAutoStart.toStart.put(KothName, duration);
                        KothAutoStart.AutoStart(KothName);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have scheduled " + KothName + " to start in " + duration + " minutes"));
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7That Koth Name doesn't exist!"));
                    }
                }
            }
        }
        return true;
    }
}