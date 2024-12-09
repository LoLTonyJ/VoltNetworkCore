package me.tony.main.voltnetwork.Administration.AdminUtil;

import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ConfigReloadConfirm implements Listener {

    @EventHandler
    public void OnChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        /*
        Line 21 -> Checks to see if the player is in a list.
        22 / 27 -> If the message contains yes, or no.
        25 -> Reloads the Configuration File.
         */

        if (ConfigCommands.CFGReload.contains(p)) {
            if (msg.equalsIgnoreCase("yes")) {
                VoltNetwork.getInstance().reloadConfig();
                e.setCancelled(true);
                p.sendMessage(Chat.format("&a&lReloaded Configuration File"));
            } else if (msg.equalsIgnoreCase("no")) {
                ConfigCommands.CFGReload.remove(p);
                e.setCancelled(true);
                p.sendMessage(Chat.format("&c&lCancelled Config Reload"));
            }
        }
    }
}
