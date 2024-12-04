package me.tony.main.voltnetwork.CustomBoss;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public class BossAbilities {

    static boolean active = false;

    public static int rndm(int min, int max) {
        Random DropAmount = new Random();
        return DropAmount.nextInt((max - min) + 1) + min;
    }

    public static void FlingPlayer() {

        Entity e = BossUtil.boss.get(0);
        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        for (Entity ent : e.getNearbyEntities(4, 4, 4)) {
            if (ent instanceof Player) {
                Player p = (Player) ent;
                p.setVelocity(new Vector(0, 1, 0));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', bossName + " Be gone!"));
                active = false;
            }
        }

    }

    public static void NegateDamage() {

        Entity e = BossUtil.boss.get(0);


        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        if (e.getLastDamageCause() != null) {
            int dmg = (int) e.getLastDamageCause().getFinalDamage() / 2;
            for (Entity p : e.getNearbyEntities(4, 4, 4)) {
                if (p instanceof Player) {
                    ((Player) p).damage(dmg);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', bossName + "&7: &cDealt ❤" + dmg + " to you!"));
                    active = false;
                }
            }
        }

    }

    public static void SpookPlayer() {
        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");
        Entity e = BossUtil.boss.get(0);


        for (Entity p : e.getNearbyEntities(5, 5, 5)) {
            if (p instanceof Player) {
                ((Player) p).playSound(p, Sound.ENTITY_ENDER_DRAGON_GROWL, 5, 5);
                ((Player) p).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 5));
                active = false;
            }
        }
    }

    public static void PoisonCircle() {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        Entity e = BossUtil.boss.get(0);


        if (e.getType().equals(EntityType.ENDERMAN)) {
            if (e.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', bossName))) {
                Location entLoc = e.getLocation();
                int locx = rndm(-15, 15);
                int locz = rndm(-15, 15);
                Location circLoc = entLoc.add(locx, 0, locz);
                int radius = 5;
                for (int xMod = -radius; xMod <= radius; xMod++) {
                    for (int zMod = -radius; zMod <= radius; zMod++) {
                        if ((xMod * xMod) + (zMod * zMod) <= (radius * radius)) {
                            Block b = circLoc.getBlock().getRelative(xMod, -1, zMod);
                            BlockData save = b.getBlockData();
                            b.setType(Material.MAGENTA_WOOL);
                            b.getWorld().spawnParticle(Particle.DRAGON_BREATH, circLoc, 5);


                            Bukkit.getScheduler().runTaskTimer(VoltNetwork.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    b.setBlockData(save);
                                }
                            }, 200, 20);
                        }
                    }
                }
            }
        }
    }
}
