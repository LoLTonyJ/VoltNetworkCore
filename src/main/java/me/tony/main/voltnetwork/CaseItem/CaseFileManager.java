package me.tony.main.voltnetwork.CaseItem;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemDisplay;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class CaseFileManager {

    File file;
    YamlConfiguration config;


    public void LoadData() {


        file = new File(VoltNetwork.getInstance().getDataFolder(), "DisplayCases.yml");
        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        if (config.getStringList("Names").isEmpty() || config.getConfigurationSection("Names") == null) return;
        if (config.getConfigurationSection("Locations") == null) return;

        for (String name : config.getStringList("Names")) {
            ConfigurationSection sect = config.getConfigurationSection("Locations");
            if (sect.getConfigurationSection(name) != null) {
                int x = (int) sect.getConfigurationSection(name).get("X");
                int y = (int) sect.getConfigurationSection(name).get("X");
                int z = (int) sect.getConfigurationSection(name).get("X");
                String world = (String) sect.getConfigurationSection(name).get("World");

                Location loc = new Location(Bukkit.getWorld(world), x, y, z);
                CaseCommands.ItemDisplayList.put(name, loc);
            }
        }

        System.out.println("\n [VoltNetwork] Loaded Case Data");

    }


    public void SaveData() {

        file = new File(VoltNetwork.getInstance().getDataFolder(), "DisplayCases.yml");
        config = new YamlConfiguration();


        config.createSection("Names");
        config.createSection("Locations");

        if (CaseCommands.ItemDisplayList.isEmpty()){
            System.out.println("List is Empty");
            return;
        }

        for (String name : CaseCommands.ItemDisplayList.keySet()) {
            int x = CaseCommands.DisplayX.get(name);
            int y = CaseCommands.DisplayY.get(name);
            int z = CaseCommands.DisplayZ.get(name);
            String world = CaseCommands.DisplayWorld.get(name);
            ConfigurationSection sect = config.getConfigurationSection("Locations");
            sect.createSection(name).set("X", x);
            sect.getConfigurationSection(name).set("Y", y);
            sect.getConfigurationSection(name).set("Z", z);
            sect.getConfigurationSection(name).set("World", world);
        }

        config.set("Names", CaseCommands.ItemDisplayList.keySet());


        Save();
    }


    public void Load() throws IOException, InvalidConfigurationException {
        file = new File(VoltNetwork.getInstance().getDataFolder(), "DisplayCases.yml");
        config = new YamlConfiguration();
        config.options().copyDefaults(true);
        if (!file.exists()) VoltNetwork.getInstance().saveResource("DisplayCases.yml", false);
        config.load(file);

    }

    public void Save() {

        file = new File(VoltNetwork.getInstance().getDataFolder(), "DisplayCases.yml");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
