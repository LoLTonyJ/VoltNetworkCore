package me.tony.main.voltnetwork.KothUtil;


import me.tony.main.voltnetwork.Koth.Commands;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class KothFileManager {

    private final static KothFileManager instance = new KothFileManager();

    private File file;
    private YamlConfiguration config;

    String Remove;
    String KothName;
    String RegionID;
    double x;
    double y;
    double z;
    String WorldName;

    public void LoadData() throws IOException, InvalidConfigurationException {

        config = new YamlConfiguration();
        config.load(file);

        if (config.getList("Koth_Rewards") != null) {
            for (Object obj : config.getList("Koth_Rewards")) {
                if (!(obj instanceof ItemStack)) continue;
                RewardEdit.rewards.add((ItemStack) obj);
            }
        }

        for (String KothNames : config.getStringList("Koth_Names")) {
            if (config.getConfigurationSection(KothNames) != null) {


                // Basic Checks so nothing gets mad.
                if (config.getConfigurationSection(KothNames).get("RegionID") == null) return;
                if (config.getConfigurationSection(KothNames).get("X") == null) return;
                if (config.getConfigurationSection(KothNames).get("Y") == null) return;
                if (config.getConfigurationSection(KothNames).get("Z") == null) return;
                if (config.getConfigurationSection(KothNames).get("World") == null) return;


                // Getting Data from "KothData.yml", and adding data to appropriate lists.
                RegionID = config.getConfigurationSection(KothNames).get("RegionID").toString();
                x = (double) config.getConfigurationSection(KothNames).get("X");
                y = (double) config.getConfigurationSection(KothNames).get("Y");
                z = (double) config.getConfigurationSection(KothNames).get("Z");
                WorldName = (String) config.getConfigurationSection(KothNames).get("World");
                Location l = new Location(Bukkit.getWorld(WorldName), x, y, z);
                Commands.kothLocation.put(KothNames, l);
                KothCreate.KothRegionX.put(KothNames, x);
                KothCreate.KothRegionY.put(KothNames, y);
                KothCreate.KothRegionZ.put(KothNames, z);
                KothCreate.KothRegionWorld.put(KothNames, WorldName);
                KothCreate.kothRegionConnect.put(RegionID, KothNames);
            }
            String Name = KothNames;
            Commands.kothNames.add(Name);

        }

        System.out.println("\n Loaded Koth Data \n");

    }

    public void StoreData() {
        file = new File(VoltNetwork.getInstance().getDataFolder(), "KothData.yml");
        config = new YamlConfiguration();

        ArrayList<String> SaveList = new ArrayList<>();
        ArrayList<ItemStack> RewardList = new ArrayList<>();

        for (ItemStack is: RewardEdit.rewards) {
            if (is != null) {
                RewardList.add(is);
            }
        }

        for (String i : Commands.kothNames) {
            if (i != null) {
                SaveList.add(i);
                KothName = i;
            }
        }
        for (String i : KothCreate.kothRegionConnect.keySet()) {
            if (i != null) {
                if (KothCreate.kothRegionConnect.get(i).equals(KothName)) {
                    RegionID = i;
                }
            }
        }
        config.createSection("Koth");
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Koth Name Data
        config.set("Koth_Names", SaveList);
        Save();

        // Seperate Koth Data
        ConfigurationSection sect = config.getConfigurationSection("Koth");


        sect.set("KothName", KothName);
        sect.set("RegionID", RegionID);
        sect.set("X", KothCreate.KothRegionX.get(KothName));
        sect.set("Y", KothCreate.KothRegionY.get(KothName));
        sect.set("Z", KothCreate.KothRegionZ.get(KothName));
        sect.set("World", KothCreate.KothRegionWorld.get(KothName));
        Save();



        // Koth Reward Data
        config.set("Koth_Rewards", RewardList);
        Save();

        System.out.println("\n Stored Koth Data \n VoltNetwork v1.0.2");
        
    }

    public void Load() throws IOException, InvalidConfigurationException {
        file = new File(VoltNetwork.getInstance().getDataFolder(), "KothData.yml");
        config = new YamlConfiguration();
        config.options().copyDefaults(true);
        if (!file.exists()) VoltNetwork.getInstance().saveResource("KothData.yml", false);
        config.load(file);

    }

    public void Save() {

        file = new File(VoltNetwork.getInstance().getDataFolder(), "KothData.yml");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static KothFileManager getInstance() {
        return instance;
    }

}
