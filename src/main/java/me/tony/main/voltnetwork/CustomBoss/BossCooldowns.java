package me.tony.main.voltnetwork.CustomBoss;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BossCooldowns {

    public static HashMap<Entity, Integer> BossRespawn = new HashMap<>();
    public static ArrayList<Integer> MinionCount = new ArrayList<>();

    public static int rndm(int min, int max) {
        Random DropAmount = new Random();
        return DropAmount.nextInt((max - min) + 1) + min;
    }

    public static void SpawnMinions() {

        Integer MinionCD = VoltNetwork.getInstance().getConfig().getInt("minion_cooldown");
        Integer MinionMax = VoltNetwork.getInstance().getConfig().getInt("minions_amount_max");
        Boolean MinionEnabled = VoltNetwork.getInstance().getConfig().getBoolean("minions");
        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");
        List<String> MinionDialogue = VoltNetwork.getInstance().getConfig().getStringList("minion_spawn_dialogue");


        Bukkit.getScheduler().runTaskTimer(VoltNetwork.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (BossCommands.Alive.isEmpty()) return;
                if (BossUtil.boss.isEmpty()) return;
                Entity e = BossUtil.boss.get(0);

                if (MinionEnabled) {
                    if (MinionMax > MinionCount.size()) {

                        if (Bukkit.getOnlinePlayers().isEmpty()) return;
                        if (Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers().isEmpty()) return;

                        int rndmPlayer;
                        int choice;

                        choice = rndm(0, MinionDialogue.size());
                        if (choice == MinionDialogue.size()) {
                            choice = choice - 1;
                        }

                        rndmPlayer = rndm(0, Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers().size());
                        if (rndmPlayer == Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers().size()) {
                            rndmPlayer = rndmPlayer - 1;
                        }

                        if (MinionDialogue.get(choice).contains("%player%")) {
                            String replace = MinionDialogue.get(choice).replace("%player%", Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers().get(rndmPlayer).getName());
                            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + ": " + replace));
                            return;
                        }
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + ": " + MinionDialogue.get(choice)));

                        for (int i = MinionCount.size() - 1; i <= MinionMax; i++) {
                            Location loc = e.getLocation();
                            BossUtil.BossMinion(e.getWorld(), loc);
                            MinionCount.add(i);
                        }
                    }
                }

            }
        }, 20, MinionCD * 20);
    }

    public static void SpawnWatchers() {

        ProtectedRegion rg;
        String wrld = VoltNetwork.getInstance().getConfig().getString("boss_world");
        World w = BukkitAdapter.adapt(Bukkit.getWorld(wrld));

        if (!BossCommands.WorldBossSpawn.isEmpty()) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(w);
            if (regions != null) {
                rg = regions.getRegion("bossarena");

                Bukkit.getScheduler().runTaskTimer(VoltNetwork.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        if (!BossCommands.Alive.isEmpty()) return;

                        if (EndMobs.MobCount.size() >= 25) {
                            return;
                        }
                        if (!EndMobs.MiddleRegion(rg.getId(), BossCommands.WorldBossSpawn.get(0)).add(rndm(-50, 50), 0, rndm(-50, 50)).getBlock().getType().equals(Material.AIR))
                            return;

                        EndMobs.Mob(EndMobs.MiddleRegion(rg.getId(), BossCommands.WorldBossSpawn.get(0)).add(rndm(-50, 50), 0, rndm(-50, 50)));
                        EndMobs.MobCount.add(1);
                    }
                }, 20, 200);

            }
        }
    }

    public static void AbilityUse() {

        Integer abilCD = VoltNetwork.getInstance().getConfig().getInt("ability_cooldowns");

        Bukkit.getScheduler().runTaskTimer(VoltNetwork.getInstance(), new Runnable() {
            @Override
            public void run() {

                if (BossCommands.Alive.isEmpty()) return;

                int rndm = rndm(1, 4);

                if (rndm == 1) {
                    BossAbilities.PoisonCircle();
                }
                if (rndm == 2) {
                    BossAbilities.NegateDamage();
                }
                if (rndm == 3) {
                    BossAbilities.SpookPlayer();
                }
                if (rndm == 4) {
                    BossAbilities.FlingPlayer();
                }


            }
        }, 20, abilCD * 20);

    }

    public static void DialogueQueue() {

        Integer Cooldown = VoltNetwork.getInstance().getConfig().getInt("dialogue_occurrence");
        List<String> DioOptions = VoltNetwork.getInstance().getConfig().getStringList("dialogue");
        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!BossCommands.Alive.isEmpty()) {

                    if (Bukkit.getOnlinePlayers().isEmpty()) return;
                    if (Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers().isEmpty()) return;

                    int rndmPlayer;
                    int choice;

                    choice = rndm(0, DioOptions.size());
                    if (choice == DioOptions.size()) {
                        choice = choice - 1;
                    }

                    rndmPlayer = rndm(0, Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers().size());
                    if (rndmPlayer == Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers().size()) {
                        rndmPlayer = rndmPlayer - 1;
                    }

                    if (DioOptions.get(choice).contains("%player%")) {
                        String replace = DioOptions.get(choice).replace("%player%", Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)).getPlayers().get(rndmPlayer).getName());
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + ": " + replace));
                        return;
                    }

                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + ": " + DioOptions.get(choice)));
                }

            }
        }.runTaskTimerAsynchronously(VoltNetwork.getInstance(), 20, Cooldown * 20);
    }


    public static void RespawnCooldown(Entity e) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!BossRespawn.isEmpty()) {
                    int timeLeft = BossRespawn.get(e);

                    if (timeLeft == 5) {
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7Congratulations on a victorious fight!"));
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + " &7Will spawn again in " + timeLeft + " days"));
                        return;
                    }

                    if (timeLeft > 0) {
                        BossRespawn.replace(e, timeLeft, timeLeft - 1);
                        int newTime = BossRespawn.get(e);
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + " &7is spawning in " + newTime + " days!"));
                    }
                    if (timeLeft == 0) {
                        BossRespawn.remove(e);
                        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', bossName + " &cSo you think you can challenge me?"));
                        BossUtil.Boss(Bukkit.getWorld(BossCommands.WorldBossSpawn.get(0)), BossCommands.BossSpawn.get(0));
                        BossCommands.Alive.add("1");
                    }
                }

            }
        }.runTaskTimerAsynchronously(VoltNetwork.getInstance(), 20, 1728000);

    }
}
