package me.tony.main.voltnetwork.DonatorPerks;

import me.tony.main.voltnetwork.KothUtil.KothFileManager;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DonatorFileManagement {

    private final static DonatorFileManagement instance = new DonatorFileManagement();

    private File file;
    private YamlConfiguration config;

    public void LoadData() {

        file = new File(VoltNetwork.getInstance().getDataFolder(), "DonatorData.yml");
        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        for (String Player : config.getStringList("Night-Vision")) {
            if (Player != null) {
                DonatorCommands.NVPlayers.add(Player);
            }
        }

        System.out.println("\n [VoltNetwork] Loaded Donator Player Info \n");

    }

    public void StoreData() {

        file = new File(VoltNetwork.getInstance().getDataFolder(), "DonatorData.yml");
        config = new YamlConfiguration();

        config.set("Night-Vision", DonatorCommands.NVPlayers);
        Save();

        System.out.println("\n Saved Donator Player Information \n");
    }

    public void Save() {

        file = new File(VoltNetwork.getInstance().getDataFolder(), "DonatorData.yml");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Load() throws IOException, InvalidConfigurationException {
        file = new File(VoltNetwork.getInstance().getDataFolder(), "DonatorData.yml");
        config = new YamlConfiguration();
        config.options().copyDefaults(true);
        if (!file.exists()) VoltNetwork.getInstance().saveResource("DonatorData.yml", false);
        config.load(file);

    }

    public static DonatorFileManagement getInstance() {
        return instance;
    }
}
