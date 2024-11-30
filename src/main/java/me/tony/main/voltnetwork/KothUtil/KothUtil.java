package me.tony.main.voltnetwork.KothUtil;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.tony.main.voltnetwork.Koth.Commands;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static me.tony.main.voltnetwork.Koth.Commands.kothDuration;
import static me.tony.main.voltnetwork.Koth.Commands.kothLocation;

public class KothUtil {

    public static HashMap<Player, String> kothCap = new HashMap<>();
    public static ArrayList<Player> kothVictor = new ArrayList<>();
    public static HashMap<Player, ItemStack> kothReward = new HashMap<>();
    public static ArrayList<ItemStack> rewardList = new ArrayList<>();
    public static ArrayList<String> kothContested = new ArrayList<>();

    public static int rndm(int min, int max) {
        Random DropAmount = new Random();
        return DropAmount.nextInt((max - min) + 1) + min;
    }

    public static void Hologram(String kothName, Integer duration, Location loc) {

        Boolean holoEnab = VoltNetwork.getInstance().getConfig().getBoolean("koth_hologram");

        if (!holoEnab) return;

        if (DHAPI.getHologram(kothName) == null) {
            DHAPI.createHologram(kothName, loc, Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&c&l>> " + kothName + " &c&l<<")));
            DHAPI.addHologramLine(Hologram.getCachedHologram(kothName), ChatColor.translateAlternateColorCodes('&', "&cCurrent King -> " + "Nobody!"));
            DHAPI.addHologramLine(Hologram.getCachedHologram(kothName), ChatColor.translateAlternateColorCodes('&', "&cTime Left -> " + duration + " seconds!"));
        } else {
            if (!kothCap.isEmpty()) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (kothCap.containsKey(p)) {
                        DHAPI.setHologramLine(Hologram.getCachedHologram(kothName), 1, ChatColor.translateAlternateColorCodes('&', "&cCurrent King -> " + p.getDisplayName()));
                    }
                }
            } else {
                DHAPI.setHologramLine(Hologram.getCachedHologram(kothName), 1, ChatColor.translateAlternateColorCodes('&', "&cCurrent King -> " + "Nobody!"));
            }
            DHAPI.setHologramLine(Hologram.getCachedHologram(kothName), 2, ChatColor.translateAlternateColorCodes('&', "&cTime Left -> " + duration + " seconds!"));
        }
    }

    public static Inventory KothRewards(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&7Koth rewards"));
        for (ItemStack i : RewardEdit.rewards) {
            if (i != null && !rewardList.contains(i)) {

                rewardList.add(i);
            }
        }
        for (ItemStack i : rewardList) {
            if (i != null) {
                RewardEdit.rewards.add(i);
                inv.addItem(i);
            }
        }
        p.openInventory(inv);

        System.out.println(rewardList.size());

        return inv;
    }

    public static void KothStart(String koth, Integer duration) {

        String broadcastMessage = VoltNetwork.getInstance().getConfig().getString("koth_start");
        String kothPrefix = VoltNetwork.getInstance().getConfig().getString("koth_prefix");

        if (!Commands.kothActive.contains(koth)) {
            if (Commands.kothNames.contains(koth) && kothLocation.containsKey(koth)) {
                Commands.kothActive.add(koth);
                kothDuration.put(koth, duration);
                KothUtil.KothCountDown(koth);


                // Placeholders Replacement.
                if (broadcastMessage.contains("%koth%") && broadcastMessage.contains("%duration%")) {
                    String replaceChars = broadcastMessage.replace("%koth%", koth);
                    String result = replaceChars.replace("%duration%", String.valueOf(kothDuration.get(koth)));
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " " + result));
                } else if (broadcastMessage.contains("%koth%")) {
                    String bc = broadcastMessage.replace("%koth%", koth);
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " " + bc));
                }
            }
        }
    }

    public static void KothCountDown(String kothName) {

        String kothPrefix = VoltNetwork.getInstance().getConfig().getString("koth_prefix");


        new BukkitRunnable() {

            @Override
            public void run() {
                int i = kothDuration.get(kothName);
                if (i <= 0) {
                    kothDuration.remove(kothName);
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + kothName + " has ended!"));
                    this.cancel();
                    KothEnd(kothName);
                }
                if (i <= 10) {
                    if (i == 0) return;
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + kothName + " is ending in " + i + " seconds"));
                }
                if (i > 0) {
                    kothDuration.put(kothName, i - 1);
                }
               KothUtil.Hologram(kothName, kothDuration.get(kothName), kothLocation.get(kothName));
            }
        }.runTaskTimerAsynchronously(VoltNetwork.getInstance(), 20, 20);
    }

    public static void KothEnd(String kothName) {

        String vMsg = VoltNetwork.getInstance().getConfig().getString("victor_message");
        String kPrefix = VoltNetwork.getInstance().getConfig().getString("koth_prefix");
        String bcEnd = VoltNetwork.getInstance().getConfig().getString("koth_end_no_capper");

        String contBC = VoltNetwork.getInstance().getConfig().getString("koth_remained_contested");


        if (!kothContested.contains(kothName)) {
            if (!kothCap.isEmpty()) {
                // Gets all online players
                for (Player p : Bukkit.getOnlinePlayers())
                    // Checks to see if any of the players are victorious
                    if (kothCap.containsKey(p)) {
                        int num = rndm(0, RewardEdit.rewards.size());
                        if (num == RewardEdit.rewards.size()) num = num -1;
                        p.getInventory().addItem(RewardEdit.rewards.get(num));
                        kothVictor.add(p);
                        if (vMsg.contains("%koth%")) {
                            String replace = vMsg.replace("%koth%", kothName);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', kPrefix + " &7" + replace));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', kPrefix + " &7" + vMsg));
                        }
                    }
            } else {
                if (bcEnd.contains("%koth%")) {
                    String replace = bcEnd.replace("%koth%", kothName);
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kPrefix + " &7" + replace));
                } else {
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kPrefix + " &7" + bcEnd));
                }
            }
        } else {
            if (contBC.contains("%koth%")) {
                String replace = contBC.replace("%koth%", kothName);
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kPrefix + " &7" + replace));
            } else {
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kPrefix + " &7" + contBC));
            }
        }
        // Resetting all Lists
        kothCap.clear();
        Commands.kothActive.remove(kothName);

        if (DHAPI.getHologram(kothName) != null) {
            DHAPI.removeHologram(kothName);
        }

    }


    public static void KothLeave(Player p, String regionID) {

        String kothPrefix = VoltNetwork.getInstance().getConfig().getString("koth_prefix");
        String leaveBc = VoltNetwork.getInstance().getConfig().getString("koth_cap_leave");

        if (kothCap.size() == 1) {
            kothContested.remove(regionID);
        }

        if (kothCap.containsKey(p)) {
            kothCap.remove(p);
            if (kothCap.isEmpty()) {
                if (leaveBc.contains("%koth%")) {
                    String replace = leaveBc.replace("%koth%", regionID);
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + replace));
                } else {
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + leaveBc));
                }
            }
        }
    }

    public static void KothCont(Player p, String kothName) {
        String kothCont = VoltNetwork.getInstance().getConfig().getString("koth_contest");
        String kothPrefix = VoltNetwork.getInstance().getConfig().getString("koth_prefix");
        int i = 5;


        if (kothCap.containsKey(p)) return;
        if (kothContested.contains(kothName)) return;

        kothCap.put(p, kothName);

        if (kothCont.contains("%koth%")) {
            String replace = kothCont.replace("%koth%", kothName);
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + replace));
        } else {
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + kothCont));
        }

        kothContested.add(kothName);

    }

    public static void KothCap(Player p, String regionID) {

        String kothPrefix = VoltNetwork.getInstance().getConfig().getString("koth_prefix");
        String kothCapBc = VoltNetwork.getInstance().getConfig().getString("koth_cap");


        if (kothCap.isEmpty()) {
            kothCap.put(p, regionID);
            if (kothCapBc.contains("%player_displayname%")) {
                String replace = kothCapBc.replace("%player_displayname%", p.getName());
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + replace));
            } else {
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kothPrefix + " &7" + kothCapBc));
            }
        } else {
            KothCont(p, regionID);
        }
    }
}
