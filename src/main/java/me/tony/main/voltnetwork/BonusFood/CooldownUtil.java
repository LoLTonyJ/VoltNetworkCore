package me.tony.main.voltnetwork.BonusFood;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CooldownUtil {

    public static void Cooldown() {



        new BukkitRunnable() {

            @Override
            public void run() {

                if (FoodUtil.SpecialCookie.isEmpty()) return;

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (FoodUtil.SpecialCookie.containsKey(p)) {
                        int timeLeft = FoodUtil.SpecialCookie.get(p);

                        if (timeLeft > 0) {
                            FoodUtil.SpecialCookie.replace(p, timeLeft, timeLeft - 1);
                        }
                        if (timeLeft == 0) {
                            FoodUtil.SpecialCookie.remove(p);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(VoltNetwork.getInstance(), 20, 1200);


    }



}
