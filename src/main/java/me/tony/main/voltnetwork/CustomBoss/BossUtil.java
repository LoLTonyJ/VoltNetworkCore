package me.tony.main.voltnetwork.CustomBoss;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static me.tony.main.voltnetwork.CustomBoss.TopDamager.getLeaderboard;

public class BossUtil implements Listener {


    public static ArrayList<Entity> boss = new ArrayList<>();
    public static HashMap<Player, Integer> DamageDealt = new HashMap<>();
    public static ArrayList<Player> Player1 = new ArrayList<>();
    public static ArrayList<Player> Player2 = new ArrayList<>();
    public static ArrayList<Player> Player3 = new ArrayList<>();
    public static HashMap<Location, BlockData> Frames = new HashMap<>();

    public static ArrayList<Player> PlacedShards = new ArrayList<>();

    public static int rndm(int min, int max) {
        Random DropAmount = new Random();
        return DropAmount.nextInt((max - min) + 1) + min;
    }

    @EventHandler
    public void BossSpawnAlter(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = e.getPlayer().getInventory().getItemInMainHand();
        Block b = e.getClickedBlock();
        String w = VoltNetwork.getInstance().getConfig().getString("boss_world");

        if (b == null) return;
        if (!b.getType().equals(Material.END_PORTAL_FRAME) || b.getType().equals(Material.AIR)) return;
        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (!p.getWorld().equals(Bukkit.getWorld(w))) return;



        EndPortalFrame frame = (EndPortalFrame) b.getBlockData();

        // If a player is clicking an End Portal frame that has an eye, with their hand. This code will run.
        if (i.getType().equals(Material.AIR)) {
            if (frame.hasEye()) {
                if (PlacedShards.contains(p)) {
                    PlacedShards.remove(p);
                    Frames.remove(b.getLocation());
                    frame.setEye(false);
                    b.setBlockData(frame);
                    int count = PlacedShards.size();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&l&oEND ALTER >> &7" + p.getName() + " has removed a Heart Shard! (" + count + "/8)"));
                    p.getInventory().addItem(BossItems.BossShard());
                }
            }
        }A

        // If a player is clicking an End Portal Frame with the Boss Item. This code will run.
        if (i.getType().equals(Material.GHAST_TEAR) && i.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&5Boss Heart Shard"))) {

            e.setCancelled(true);

            if (BossCommands.WorldBossSpawn.isEmpty()) return;
            if (BossCommands.BossSpawn.isEmpty()) return;

            if (PlacedShards.size() == 8) {
                BossCommands.Alive.add("true");
                PlacedShards.clear();
                for (Location l : Frames.keySet()) {
                    if (l.getBlock().getType().equals(Material.END_PORTAL_FRAME)) {
                        EndPortalFrame bData = (EndPortalFrame) l.getBlock().getBlockData();
                        if (bData.hasEye()) {
                            bData.setEye(false);
                            l.getBlock().setBlockData(bData);
                        }
                    }
                }

                BossUtil.Boss(Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)), BossCommands.BossSpawn.get(0));
            }

            if (!BossCommands.Alive.isEmpty()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7The Boss has already Awoken! Beware!"));
                return;
            }

            if (!frame.hasEye()) {
                if (p.getInventory().getItemInMainHand().getAmount() > 1) p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                if (p.getInventory().getItemInMainHand().getAmount() == 1) p.getInventory().getItemInMainHand().setAmount(0);
                PlacedShards.add(p);
                int count = PlacedShards.size();
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&l&oEND ALTER >> &7" + p.getName() + " has placed a Heart Shard! (" + count + "/8)"));
                frame.setEye(true);
                b.setBlockData(frame);
                Frames.put(b.getLocation(), frame);
            }
        }
    }

    @EventHandler
    public void BossDmg(EntityDamageByEntityEvent e) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        // Damage Leaderboard Util.
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
    public static void onBreak(BlockBreakEvent e) {

        Block b = e.getBlock();
        Player p = e.getPlayer();
        if (BossCommands.WorldBossSpawn.isEmpty()) return;
        if (!p.getWorld().equals(Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)))) return;
        if (!b.getType().equals(Material.END_STONE) || !b.getType().equals(Material.OBSIDIAN)) return;

        BlockData bData = b.getBlockData();
        b.setType(Material.BEDROCK);

        Bukkit.getScheduler().runTaskTimer(VoltNetwork.getInstance(), new Runnable() {
            @Override
            public void run() {
                b.setBlockData(bData);
            }
        },20, 200);


    }

    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public static void EndPortal(PlayerPortalEvent e) {
        Player p = e.getPlayer();

        // Cancels the Original Teleport to the End
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            e.setCancelled(true);
            // If Warp Loc is not set, cancel.
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
        Player p = e.getEntity().getKiller();


        // Checks Entity Typpe
        if (ent.getType().equals(EntityType.ZOMBIE)) {
            if (ent.getCustomName() != null) {
                // Checks to see if the Entity has Custom Name of x
                if (ent.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', bossName + "'s Minion"))) {

                    // Reduce size of MinionCount.
                    if (BossCooldowns.MinionCount.size() > 1) {
                        BossCooldowns.MinionCount.remove(0);
                    }
                }
            }
        }

        if (!ent.getType().equals(EntityType.ENDERMAN)) return;
        if (ent.getCustomName() == null || !ent.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', bossName + " Watcher"))) return;

        if (p != null) {
            if (rndm(1, 10) > 7) { // %25 Drop
                e.getDrops().clear();
                ent.getWorld().dropItemNaturally(ent.getLocation(), BossItems.BossShard());
                p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&b&lRARE DROP!"), ChatColor.translateAlternateColorCodes('&', "&7You dropped a Boss Heart Shard!")
                        , 15, 15, 15);
            }
        }

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

        // Poison Square Damage.
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

        // Cancels the Teleportation of Endermen in Boss World.
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
                BossCommands.Alive.clear();
                BossCooldowns.BossRespawn.put(ent, 5);
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + " &7Has been killed!"));
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7Most Damage:"));

                List<TopDamager> leaderboard = getLeaderboard();

                // If the Amount of Players who fought the boss is > minPlayers (Refer to Config) then run code..
                if (minPlayers != 0 && minPlayers > leaderboard.size()) {
                    for (int i = 0; i < 3 || i == leaderboard.size(); i++) {
                        TopDamager top = leaderboard.get(i);
                        // Setting Players Placement.
                        if (Player1.isEmpty() && !Player1.contains(Bukkit.getPlayer(top.getName()))) {
                            Player1.add(Bukkit.getPlayer(top.getName()));
                        }
                        if (Player2.isEmpty() && !Player1.contains(Bukkit.getPlayer(top.getName()))) {
                            Player2.add(Bukkit.getPlayer(top.getName()));
                        }
                        if (Player3.isEmpty() && !Player2.contains(Bukkit.getPlayer(top.getName())) || !Player1.contains(Bukkit.getPlayer(top.getName()))) {
                            Player3.add(Bukkit.getPlayer(top.getName()));
                        }
                        // Broadcasting Placement to Server.
                        int placement = i + 1;
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + placement + ". " + Bukkit.getPlayer(top.getName()).getDisplayName() + " &b> &c❤ " + top.getDmg()));
                        KillerRewards.getPlacements();
                    }

                    // Edit Boss Respawn Counter.
                    if (BossCooldowns.BossRespawn.containsKey(ent)) {
                        BossCooldowns.BossRespawn.replace(ent, BossCooldowns.BossRespawn.get(ent), 5);
                    }
                    BossCooldowns.BossRespawn.put(ent, 5);
                    BossCooldowns.RespawnCooldown(ent);
                    BossFileManager.getInstance().SaveData();
                }  else {
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Not enough Players participated in this fight! Rewards will not be given!"));
                }
            }
        }

    }

    public static void SignCreate(Player p) {

        List<String> signList = VoltNetwork.getInstance().getConfig().getStringList("sign_lines");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        // Not being used.
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


            // Checks the Mobs Target, if its null set it to any player in Boss World.
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
