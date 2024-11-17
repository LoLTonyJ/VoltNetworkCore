package me.tony.main.voltnetwork.StaffChat;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatUtil implements Listener {



    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        String chatPerm = VoltNetwork.getInstance().getConfig().getString("staff_permission");
        String staffPrefix = VoltNetwork.getInstance().getConfig().getString("staff_prefix");

        if (StaffChatCommands.StaffChat.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission(chatPerm)) {
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', staffPrefix + " " + e.getPlayer().getDisplayName() + " &7> " + e.getMessage()));
                }
            }
        }
    }


}
