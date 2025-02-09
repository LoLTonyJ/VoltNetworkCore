package me.tony.main.voltnetwork.AntiCheat;

import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LagCheck {




    public static void nearbyEntities(Player p) {

        HashMap<Player, List<Entity>> listCheck = new HashMap<>();

        List<String> entList = VoltNetwork.getInstance().getConfig().getStringList("Lag_Check");
        Integer entAmount = VoltNetwork.getInstance().getConfig().getInt("max_amount_entities");
        String staffPermission = VoltNetwork.getInstance().getConfig().getString("ac_permission");

        new BukkitRunnable() {

            @Override
            public void run() {

                System.out.println("Running Lag Check...");

                for (Entity entity : p.getNearbyEntities(25, 25 ,25)) {
                    for (String s : entList) {
                        if (entity.getType().equals(EntityType.valueOf(s))) {
                            HashMap<Player, List<Entity>> checkList = new HashMap<>();
                            List<Entity> entityList = new ArrayList<>();
                            entityList.add(entity);
                            checkList.put(p, entityList);
                            if (entityList.size() >= entAmount) {
                                for (Player staff : Bukkit.getOnlinePlayers()) {
                                    if (staff.hasPermission(staffPermission)) {
                                        staff.sendMessage(Chat.format("&c&lLAG CHECK >> &7" + p.getName() + " has potentially built a lag machine!"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(VoltNetwork.getInstance(), 1, 300);
     }




}
