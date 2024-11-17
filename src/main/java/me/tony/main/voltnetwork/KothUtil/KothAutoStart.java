package me.tony.main.voltnetwork.KothUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class KothAutoStart {

    public static HashMap<String, Integer> toStart = new HashMap<>();

    public static void AutoStart(String koth) {

        new BukkitRunnable() {

            @Override
            public void run() {
                String kPrefix = VoltNetwork.getInstance().getConfig().getString("koth_prefix");

                System.out.println("running auto start");

                if (!toStart.isEmpty()) {
                    int i = toStart.get(koth);
                    if (i <= 0) {
                        KothUtil.KothStart(koth, VoltNetwork.getInstance().getConfig().getInt("koth_default_duration"));
                        toStart.remove(koth);
                        this.cancel();
                    }
                    if (i > 0) {
                        toStart.replace(koth, i, i - 1);
                    }
                    if (i <= 5) {
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', kPrefix + " &7" + koth + " will be starting in " + i + " minutes!"));
                    }
                }

            }
        }.runTaskTimerAsynchronously(VoltNetwork.getInstance(), 20, 1200);
    }
}
