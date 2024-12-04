package me.tony.main.voltnetwork.CustomBoss;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class BossFileManager {

    private final static BossFileManager instance = new BossFileManager();

    private File file;
    private YamlConfiguration config;

    public void LoadData() throws IOException, InvalidConfigurationException {

        config = new YamlConfiguration();
        config.load(file);

        if (config.getList("1st_Place") != null) {
            for (Object obj : config.getList("1st_Place")) {
                if (!(obj instanceof ItemStack)) continue;
                BossInventoryUtil.TopRewards.add((ItemStack) obj);
            }
        }
        if (config.getList("2nd_Place") != null) {
            for (Object obj : config.getList("2nd_Place")) {
                if (!(obj instanceof ItemStack)) continue;
                BossInventoryUtil.SecondRewards.add((ItemStack) obj);
            }
        }
        if (config.getList("3rd_Place") != null) {
            for (Object obj : config.getList("3rd_Place")) {
                if (!(obj instanceof ItemStack)) continue;
                BossInventoryUtil.ThirdRewards.add((ItemStack) obj);
            }
        }

        if (config.getConfigurationSection("Boss_Respawn") != null) {
            Entity e = null;
            int timeLeft = 0;

            e = (Entity) config.getConfigurationSection("Boss_Respawn").get("Boss");
            if (e == null) return;
            timeLeft = (int) config.getConfigurationSection("Boss_Respawn").get("Time Left");
            BossCooldowns.BossRespawn.put(e, timeLeft);
        }

        if (config.getConfigurationSection("Boss_Warp") != null) {

            // Getting Data
            if (config.getConfigurationSection("Boss_Warp").get("X") != null || config.getConfigurationSection("Boss_Warp").get("Y") != null || config.getConfigurationSection("Boss_Warp").get("Z") != null) {
                int x = (int) config.getConfigurationSection("Boss_Warp").get("X");
                int y = (int) config.getConfigurationSection("Boss_Warp").get("Y");
                int z = (int) config.getConfigurationSection("Boss_Warp").get("Z");
                String w = (String) config.getConfigurationSection("Boss_Warp").get("World");
                BossCommands.XBossWarp.add(x);
                BossCommands.WorldBossWarp.add(w);
                BossCommands.YBossWarp.add(y);
                BossCommands.ZBossWarp.add(z);
                Location wLoc = new Location(Bukkit.getWorld(w), x, y, z);
                BossCommands.WarpLoc.add(wLoc);
            } else {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Boss Warp is not set! Continuing....");
            }
        }

        if (config.getConfigurationSection("Boss_Spawn") != null) {

            if (config.getConfigurationSection("Boss_Spawn").get("X") != null || config.getConfigurationSection("Boss_Spawn").get("Y") != null || config.getConfigurationSection("Boss_Spawn").get("Z") != null) {
                // Getting Location Data from Data File
                int x = (int) config.getConfigurationSection("Boss_Spawn").get("X");
                int y = (int) config.getConfigurationSection("Boss_Spawn").get("Y");
                int z = (int) config.getConfigurationSection("Boss_Spawn").get("Z");
                String w = (String) config.getConfigurationSection("Boss_Spawn").get("World");

                BossCommands.WorldBossSpawn.add(w);
                BossCommands.XBossSpawn.add(x);
                BossCommands.YBossSpawn.add(y);
                BossCommands.ZBossSpawn.add(z);
                Location l = new Location(Bukkit.getWorld(w), x, y, z);
                BossCommands.BossSpawn.add(l);

            } else {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Boss Spawn is not set! Continuing....");
            }
        }
        else {
            System.out.println("Boss Spawn Null");
        }

        System.out.println("\n [VoltNetwork] Loaded Boss Data \n");

    }

    public void SaveData() {
        file = new File(VoltNetwork.getInstance().getDataFolder(), "BossData.yml");
        config = new YamlConfiguration();


        config.createSection("1st_Place");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!BossInventoryUtil.TopRewards.isEmpty()) {
            config.set("1st_Place", BossInventoryUtil.TopRewards);
            Save();
        }
        config.createSection("2nd_Place");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!BossInventoryUtil.SecondRewards.isEmpty()) {
            config.set("2nd_Place", BossInventoryUtil.SecondRewards);
            Save();
        }
        config.createSection("3rd_Place");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!BossInventoryUtil.SecondRewards.isEmpty()) {
            config.set("3rd_Place", BossInventoryUtil.ThirdRewards);
            Save();
        }

        if (!BossCooldowns.BossRespawn.isEmpty()) {

            config.createSection("Boss_Respawn");

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Entity ent : BossCooldowns.BossRespawn.keySet()) {
                int timeLeft = BossCooldowns.BossRespawn.get(ent);
                ConfigurationSection sect = config.getConfigurationSection("Boss_Respawn");
                sect.set("Boss", ent);
                sect.set("Time Left", timeLeft);
                Save();
            }
        }

        config.createSection("Boss_Spawn");
        config.createSection("Boss_Warp");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!BossCommands.WorldBossWarp.isEmpty() || !BossCommands.XBossWarp.isEmpty() || !BossCommands.YBossWarp.isEmpty() || !BossCommands.ZBossWarp.isEmpty()) {
            ConfigurationSection warp = config.getConfigurationSection("Boss_Warp");
            warp.set("World", BossCommands.WorldBossWarp.get(0));
            warp.set("X", BossCommands.XBossWarp.get(0));
            warp.set("Y", BossCommands.YBossWarp.get(0));
            warp.set("Z", BossCommands.ZBossWarp.get(0));
            Save();
        }

        if (!BossCommands.WorldBossSpawn.isEmpty() || !BossCommands.XBossSpawn.isEmpty() || !BossCommands.YBossSpawn.isEmpty() || !BossCommands.ZBossSpawn.isEmpty()) {
            ConfigurationSection sect = config.getConfigurationSection("Boss_Spawn");
            sect.set("World", BossCommands.WorldBossSpawn.get(0));
            sect.set("X", BossCommands.XBossSpawn.get(0));
            sect.set("Y", BossCommands.YBossSpawn.get(0));
            sect.set("Z", BossCommands.ZBossSpawn.get(0));

            Save();

            System.out.println("Saved Boss Data");
        }
    }


    public void Load() throws IOException, InvalidConfigurationException {
        file = new File(VoltNetwork.getInstance().getDataFolder(), "BossData.yml");
        config = new YamlConfiguration();
        config.options().copyDefaults(true);
        if (!file.exists()) VoltNetwork.getInstance().saveResource("BossData.yml", false);
        config.load(file);

    }

    public void Save() {

        file = new File(VoltNetwork.getInstance().getDataFolder(), "BossData.yml");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BossFileManager getInstance() {
        return instance;
    }
}