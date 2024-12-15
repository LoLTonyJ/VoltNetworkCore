package me.tony.main.voltnetwork.AntiCheat;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class FlightCheck implements Listener {


    private static double max = VoltNetwork.getInstance().getConfig().getDouble("alert_after");
    private static boolean tp = VoltNetwork.getInstance().getConfig().getBoolean("cancel_move_tp_back");


    @EventHandler
    public void onFlightCheck(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) return;
        if (p.isOp() || p.getAllowFlight()) return;

        if (ACUtil.ExemptToggle.contains(p.getUniqueId())) return;

        double pY = e.getTo().getY() - e.getFrom().getY();
        double pX = e.getTo().getX() - e.getFrom().getX();
        double pZ = e.getTo().getX() - e.getFrom().getZ();
        HashMap<Player, Location> validLocation = new HashMap<>();
        validLocation.put(p, p.getLocation());
        if (pY == max || pX == max || pZ == max) {
            ACUtil.inputPlayerLog(p, "FLIGHT", 1, "POSSIBLE");
        }
        if (pY > max || pX > max || pZ > max) {
            ACUtil.inputPlayerLog(p, "FLIGHT", 1, "SEVERE");
            if (tp) {
                if (validLocation.get(p) != null) {
                    p.teleport(validLocation.get(p));
                }
            }
        }
    }








}
