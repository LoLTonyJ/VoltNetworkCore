package me.tony.main.voltnetwork.StaffMode;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class BlockCheckUtil implements Listener {


    @EventHandler
    public void blockCheck(BlockBreakEvent e) {

        String alertPerm = VoltNetwork.getInstance().getConfig().getString("staff_alert_permission");
        String alertPrefix = VoltNetwork.getInstance().getConfig().getString("staff_alerts");
        String exempt = VoltNetwork.getInstance().getConfig().getString("staff_alerts_exempt");

        if (e.getPlayer().hasPermission(exempt)) return;

        for (String block : VoltNetwork.getInstance().getConfig().getStringList("Check_Blocks")) {
            if (e.getBlock().getType().equals(Material.valueOf(block))) {
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission(alertPerm)) {
                        if (!StaffUtil.Alerts.contains(staff)) {
                            staff.sendMessage(ChatColor.translateAlternateColorCodes('&', alertPrefix + " &7" + e.getPlayer().getDisplayName() + " has broken " + block));
                        }
                    }
                }
            }
        }
    }


}
