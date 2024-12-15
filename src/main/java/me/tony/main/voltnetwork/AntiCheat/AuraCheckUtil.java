package me.tony.main.voltnetwork.AntiCheat;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class AuraCheckUtil implements Listener {

    private static ArrayList<UUID> entUUID = new ArrayList<>();

    public static int rndm(int min, int max) {
        Random DropAmount = new Random();
        return DropAmount.nextInt((max - min) + 1) + min;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Player attacker = (Player) e.getDamager();

        if (e.getDamager() instanceof Projectile) return;

        if (entUUID.contains(victim.getUniqueId())) {
            ACUtil.inputPlayerLog(attacker, "KILL AURA", 1, "SEVERE");
        }
    }


    public static void AuraCheck(Player p) {
        Location loc = p.getLocation();
        World w = p.getWorld();

        int x = (int) loc.getX() + rndm(1,4);
        int y = (int) loc.getY() + rndm(1,4);
        int z = (int) loc.getZ() + rndm(1,4);

        Location newLoc = new Location(w, x, y, z);

        checkerEnt(newLoc, w);
    }


    public static Entity checkerEnt(Location loc, World w) {
        Entity ent = w.spawnEntity(loc, EntityType.ZOMBIE);
        ent.setGravity(false);
        ent.setInvulnerable(false);

        entUUID.add(ent.getUniqueId());

        new BukkitRunnable() {
            @Override
            public void run() {
                ent.remove();
                entUUID.remove(ent.getUniqueId());
                System.out.println("removed");
                this.cancel();
            }
        }.runTaskTimer(VoltNetwork.getInstance(), 100, 1L);

        return ent;
    }

}
