package me.tony.main.voltnetwork.AntiCheat;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReachCheck implements Listener {

    private static int max = VoltNetwork.getInstance().getConfig().getInt("max_distance");

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();

        if (e.getDamager() instanceof Projectile) return;
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player)) return;
        if (ACUtil.ExemptToggle.contains(attacker.getUniqueId())) return;

        if (gettingLocationDifference(attacker, victim) > max) {
            ACUtil.inputPlayerLog(attacker, "REACH", gettingLocationDifference(attacker, victim) ,"SEVERE");
            e.setCancelled(true);
        } else {
            if (gettingLocationDifference(attacker, victim) > max) {
                ACUtil.inputPlayerLog(attacker, "REACH", gettingLocationDifference(attacker, victim), "POSSIBLE");
            }
        }
    }


     private static Integer gettingLocationDifference(Player attacker, Player victim) {
        Location attackLoc = attacker.getLocation();
        Location vicLoc = victim.getLocation();
        return (int) attackLoc.distance(vicLoc);
     }

}
