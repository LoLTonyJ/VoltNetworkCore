package me.tony.main.voltnetwork.KothUtil;

import me.tony.main.voltnetwork.Koth.Commands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class KothCap implements Listener {

    @EventHandler
    public void KothEnter(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (Commands.kothActive.size() == 1) {
            if (WGUtil.inRegion(p.getLocation())) {
                KothUtil.KothCap(p, Commands.kothActive.get(0));
            } else {
                KothUtil.KothLeave(p, Commands.kothActive.get(0));
            }
        }
    }
}
