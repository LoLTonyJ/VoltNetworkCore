package me.tony.main.voltnetwork.AntiCheat;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class BlockCheck implements Listener {

    private static HashMap<Player, Integer> xrayTrigger = new HashMap<>();
    private static Integer checkAlert = VoltNetwork.getInstance().getConfig().getInt("block_check_alert_after");
    private static Integer softAlert = VoltNetwork.getInstance().getConfig().getInt("soft_block_check_alert_after");
    private static Integer resetInterval = VoltNetwork.getInstance().getConfig().getInt("reset_check_interval");
    private static String perm = VoltNetwork.getInstance().getConfig().getString("ac_permission");

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();


        if (getBlock(e.getBlock())) {

            if (e.getPlayer().hasPermission(perm) || e.getPlayer().isOp()) return;

            int blockCheckTrigger = blockCheckAmount(p);

            if (blockCheckTrigger >= softAlert) {
                ACUtil.inputPlayerLog(e.getPlayer(), "XRAY", blockCheckTrigger, "POSSIBLE");
                return;
            }
            if (blockCheckTrigger >= checkAlert) {
                ACUtil.inputPlayerLog(e.getPlayer(), "XRAY", blockCheckTrigger, "SEVERE");
                return;
            }
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                if (xrayTrigger.containsKey(p)) {
                    xrayTrigger.remove(p);
                } else {
                    this.cancel();
                }
                System.out.println("reset");
            }
        }.runTaskTimer(VoltNetwork.getInstance(), resetInterval * 20, 20);

    }



    public static Integer blockCheckAmount(Player p) {
        if (xrayTrigger.containsKey(p)) {
            int old = xrayTrigger.get(p);
            xrayTrigger.replace(p, old, old + 1);
        } else {
            xrayTrigger.put(p, 1);
        }
        return xrayTrigger.get(p);
    }

    public static Boolean getBlock(Block b) {
        List<String> blockCheck = VoltNetwork.getInstance().getConfig().getStringList("Check_Blocks");
        if (blockCheck.contains(String.valueOf(b.getType()))) {
            return true;
        }

        return false;
    }

}
