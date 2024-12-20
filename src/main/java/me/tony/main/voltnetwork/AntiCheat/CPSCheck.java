package me.tony.main.voltnetwork.AntiCheat;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class CPSCheck implements Listener {

    private static ArrayList<UUID> ClickCheck = new ArrayList<>();
    private static HashMap<Player, Queue<Long>> playerClickIntervals = new HashMap<>();


    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();

        if (!ClickCheck.contains(p.getUniqueId())) return;
        if (!a.equals(Action.LEFT_CLICK_AIR)) return;
        if (!ACUtil.isExempt(p)) return;

        double max = VoltNetwork.getInstance().getConfig().getDouble("click_max");
        double cps = clickSpeed(p);
        if (cps > max) {
            ACUtil.inputPlayerLog(p, "AUTO CLICKER", (int) cps, "SEVERE");
            ClickCheck.remove(p.getUniqueId());
        }
    }


    private static Double clickSpeed(Player p) {

        double max = VoltNetwork.getInstance().getConfig().getDouble("click_max");

        Long currentTime = System.currentTimeMillis();
        Queue<Long> clickIntervals = playerClickIntervals.getOrDefault(p, new LinkedList<>());
        Long lastClick = clickIntervals.isEmpty() ? currentTime : currentTime - clickIntervals.peek();

        if (clickIntervals.size() >= max) {
            clickIntervals.poll();
        }
        clickIntervals.add(currentTime);
        playerClickIntervals.put(p, clickIntervals);
        if (clickIntervals.size() < 2) {
            return 0.0; // not enough data
        }

        long totalInterval = 0;
        Long previousClick = null;
        for (Long click : clickIntervals) {
            if (previousClick != null) {
                totalInterval += (click - previousClick);
            }
            previousClick = click;
        }
        double averageInterval = (double) totalInterval / (clickIntervals.size() - 1);
        double clickSpeed = 1000.0 / averageInterval;
        return clickSpeed;

    }

    public static void initCPSCheck(Player p) {
        if (!ClickCheck.contains(p.getUniqueId())) {
            ClickCheck.add(p.getUniqueId());
        }
    }

}
