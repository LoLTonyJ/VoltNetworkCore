package me.tony.main.voltnetwork.CustomBoss;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.tony.main.voltnetwork.CustomBoss.TopDamager.getLeaderboard;

public class BossUtil implements Listener {


    public static ArrayList<Entity> boss = new ArrayList<>();
    public static HashMap<Player, Integer> DamageDealt = new HashMap<>();
    public static ArrayList<Player> Player1 = new ArrayList<>();
    public static ArrayList<Player> Player2 = new ArrayList<>();
    public static ArrayList<Player> Player3 = new ArrayList<>();

    @EventHandler
    public void BossDmg(EntityDamageByEntityEvent e) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        Entity ent = e.getEntity();
        if (ent instanceof Enderman) {
            if (ent.getCustomName() == null) return;
            if (ent.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', bossName))) {
                if (ent.isDead()) return;
                Player p = (Player) e.getDamager();
                Integer dmg = (int) e.getFinalDamage();
                if (!DamageDealt.containsKey(p)) {
                    DamageDealt.put(p, dmg);
                } else {
                    Integer old = DamageDealt.get(p);
                    DamageDealt.replace(p, old, dmg + old);
                }

            }
        }

    }

    @EventHandler
    public static void EndPortal(PlayerPortalEvent e) {
        Player p = e.getPlayer();

        if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            e.setCancelled(true);
            if (BossCommands.WarpLoc.isEmpty()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lComing Soon!"));
                return;
            }
                p.teleport(BossCommands.WarpLoc.get(0));
        }
    }

    @EventHandler
    public static void MobKill(EntityDeathEvent e) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        Entity ent = e.getEntity();

        if (ent.getType().equals(EntityType.ZOMBIE)) {
            if (ent.getCustomName() != null) {
                if (ent.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', bossName + "'s Minion"))) {
                    if (BossCooldowns.MinionCount.size() > 1) {
                        BossCooldowns.MinionCount.remove(0);
                        System.out.println(BossCooldowns.MinionCount.size());
                    }
                }
            }
        }

        if (!ent.getType().equals(EntityType.ENDERMAN)) return;
        if (ent.getCustomName() == null || !ent.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', bossName + " Watcher"))) return;

        if (EndMobs.MobCount.size() > 1) {
            EndMobs.MobCount.remove(0);
        }
        for (Entity em : ent.getNearbyEntities(0, 1, 0)) {
            if (em.getType().equals(EntityType.CHICKEN)) {
                em.remove();
            }
        }

    }

    @EventHandler
    public static void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();
        Location pLoc = p.getLocation().add(0, -1, 0);
        Block b = pLoc.getBlock();

        if (BossCommands.Alive.isEmpty()) return;

        if (p.getWorld().equals(Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)))) {
            if (b.getType().equals(Material.MAGENTA_WOOL)) {
                p.damage(0.5);
            }
        }


    }

    @EventHandler
    public static void EnderTP(EntityTeleportEvent e) {

        Entity ent = e.getEntity();

        World w = Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0));

        if (ent instanceof Enderman) {
            if (ent.getCustomName() == null) return;
            if (ent.getWorld() != w) return;

            e.setCancelled(true);
        }
    }

    @EventHandler
    public static void EggSpawn(ItemSpawnEvent e) {

        // Disable The Watchers Chickens from Laying Eggs.
        World w = Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0));
        ItemStack i = e.getEntity().getItemStack();
        Entity ent = e.getEntity();
        if (w == null || BossCommands.WorldBossSpawn.isEmpty()) return;
        if (!(ent instanceof Chicken)) return;
        if (!i.getType().equals(Material.EGG)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public static void BossDead(EntityDeathEvent e) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");
        int minPlayers = VoltNetwork.getInstance().getConfig().getInt("min_players");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        Entity ent = e.getEntity();
        EntityType entType = ent.getType();

        if (entType.equals(EntityType.ENDERMAN)) {
            if (ent.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', bossName))) {
                if (!BossCommands.Alive.isEmpty()) {
                    BossCommands.Alive.clear();
                }
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + " &7Has been killed!"));
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7Most Damage:"));

                List<TopDamager> leaderboard = getLeaderboard();

                if (minPlayers != 0 && minPlayers > leaderboard.size()) {

                    for (int i = 0; i < 3 || i == leaderboard.size(); i++) {
                        TopDamager top = leaderboard.get(i);
                        if (Player1.isEmpty() && !Player1.contains(Bukkit.getPlayer(top.getName()))) {
                            Player1.add(Bukkit.getPlayer(top.getName()));
                        }
                        if (Player2.isEmpty() && !Player1.contains(Bukkit.getPlayer(top.getName()))) {
                            Player2.add(Bukkit.getPlayer(top.getName()));
                        }
                        if (Player3.isEmpty() && !Player2.contains(Bukkit.getPlayer(top.getName())) || !Player1.contains(Bukkit.getPlayer(top.getName()))) {
                            Player3.add(Bukkit.getPlayer(top.getName()));
                        }
                        int placement = i + 1;
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + placement + ". " + Bukkit.getPlayer(top.getName()).getDisplayName() + " &b> &c❤ " + top.getDmg()));
                        KillerRewards.getPlacements();
                    }

                    BossCooldowns.BossRespawn.put(ent, 5);
                    BossCooldowns.RespawnCooldown(ent);
                    BossFileManager.getInstance().SaveData();
                }
            } else {
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Not enough Players participated in this fight! Rewards will not be given!"));
            }
        }

    }

    public static void SignCreate(Player p) {

        List<String> signList = VoltNetwork.getInstance().getConfig().getStringList("sign_lines");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        Block b = p.getTargetBlock(null, 5);
        if (b.getType().equals(Material.OAK_SIGN)) {
            Sign s = (Sign) b.getState();
            for (int i = 0 ; i <= 3; i++) {
                String st = signList.get(i);
                s.setLine(i, ChatColor.translateAlternateColorCodes('&', st));
                s.update();
                System.out.println(st);
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Successfuly created Boss Arena sign."));
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError > &cThat is not a sign!"));
        }

    }


    public static Entity BossMinion(World w, Location l) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        Zombie zomb = (Zombie) w.spawnEntity(l, EntityType.ZOMBIE);
        zomb.setCustomName(ChatColor.translateAlternateColorCodes('&', bossName + "'s Minion"));
        zomb.setBaby(true);
        zomb.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
        zomb.setHealth(20.0);
        zomb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));

        zomb.setCanPickupItems(false);
        zomb.setCustomNameVisible(true);

        new BukkitRunnable() {


            @Override
            public void run() {
                if (!zomb.isDead()) {
                    if (zomb.getTarget() == null) {
                        for (Player p : Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers()) {
                            if (p != null) {
                                zomb.setTarget(p);
                                return;
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(VoltNetwork.getInstance(), 100L, 100L);

        return zomb;
    }


    public static Entity Boss(World w, Location l) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");
        double bossHealth = VoltNetwork.getInstance().getConfig().getDouble("boss_health");
        double bossSpeed = VoltNetwork.getInstance().getConfig().getDouble("boss_speed");
        double bossArmor = VoltNetwork.getInstance().getConfig().getDouble("boss_armor");
        double bossDmg = VoltNetwork.getInstance().getConfig().getDouble("boss_damage");

        Enderman end = (Enderman) w.spawnEntity(l, EntityType.ENDERMAN);
        end.setCustomName(ChatColor.translateAlternateColorCodes('&', bossName));
        end.setCustomNameVisible(true);

        end.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(bossHealth);
        end.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(bossSpeed);
        end.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(bossArmor);
        end.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(bossDmg);

        end.setHealth(bossHealth);
        end.setCanPickupItems(false);

        boss.add(end);


        new BukkitRunnable() {


            @Override
            public void run() {

                if (!end.isDead()) {
                    if (end.getTarget() == null) {
                        for (Player p : Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers()) {
                            if (p != null) {
                                end.setTarget(p);
                                return;
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(VoltNetwork.getInstance(), 100L, 100L);
        return end;
    }
}
