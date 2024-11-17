package me.tony.main.voltnetwork;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.tony.main.voltnetwork.Administration.AdminUtil.ConfigCommands;
import me.tony.main.voltnetwork.Administration.AdminUtil.ConfigReloadConfirm;
import me.tony.main.voltnetwork.Administration.Commands;
import me.tony.main.voltnetwork.BonusFood.CooldownUtil;
import me.tony.main.voltnetwork.BonusFood.CraftingUtil;
import me.tony.main.voltnetwork.BonusFood.FoodUtil;
import me.tony.main.voltnetwork.ChatUtil.DisplayItem;
import me.tony.main.voltnetwork.CustomItems.DrillUtil;
import me.tony.main.voltnetwork.CustomItems.HarvestUtil;
import me.tony.main.voltnetwork.DonatorPerks.DonatorCommands;
import me.tony.main.voltnetwork.DonatorPerks.DonatorFileManagement;
import me.tony.main.voltnetwork.DonatorPerks.DonatorUtil;
import me.tony.main.voltnetwork.DonatorPerks.NightVisionCommand;
import me.tony.main.voltnetwork.EnchantmentUtil.EnchantmentAdd;
import me.tony.main.voltnetwork.EnchantmentUtil.HarvestListener;
import me.tony.main.voltnetwork.Enchantments.Harvest;
import me.tony.main.voltnetwork.GravestoneUtil.Gravestones;
import me.tony.main.voltnetwork.KothUtil.KothCap;
import me.tony.main.voltnetwork.KothUtil.KothFileManager;
import me.tony.main.voltnetwork.KothUtil.RewardEdit;
import me.tony.main.voltnetwork.RemoveCooldown.InventoryUtil;
import me.tony.main.voltnetwork.StaffChat.StaffChatCommands;
import me.tony.main.voltnetwork.StaffChat.StaffChatUtil;
import me.tony.main.voltnetwork.StaffMode.BlockCheckUtil;
import me.tony.main.voltnetwork.StaffMode.StaffModeCommands;
import me.tony.main.voltnetwork.StaffMode.StaffUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.logging.Level;

public final class VoltNetwork extends JavaPlugin {

    static VoltNetwork instance;
    public WorldGuardPlugin worldGuardPlugin;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {

        instance = this;
        worldGuardPlugin = getWorldGuard();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        setupPermissions();
        setupChat();

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (this.getConfig().getBoolean("donator_perks_active")) {

            try {
                DonatorFileManagement.getInstance().Load();
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
            DonatorFileManagement.getInstance().LoadData();

            // Bukkit Runnable
            DonatorUtil.Cooldown();
            CooldownUtil.Cooldown();

            getServer().getPluginManager().registerEvents(new DonatorUtil(), this);
            getCommand("dono").setExecutor(new DonatorCommands());
            getCommand("nv").setExecutor(new NightVisionCommand());

            // Load Donator Perks.
        } else {
            getLogger().log(Level.WARNING, "\nVoltNetwork\n Donator Perks are Disabled.");
        }

        getServer().getPluginManager().registerEvents(new Harvest(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentAdd(), this);
        getServer().getPluginManager().registerEvents(new HarvestListener(), this);

        getServer().getPluginManager().registerEvents(new ConfigReloadConfirm(), this);

        getServer().getPluginManager().registerEvents(new RewardEdit(), this);
        getServer().getPluginManager().registerEvents(new KothCap(), this);
        getServer().getPluginManager().registerEvents(new StaffChatUtil(), this);
        getServer().getPluginManager().registerEvents(new DrillUtil(), this);
        getServer().getPluginManager().registerEvents(new HarvestUtil(), this);
        getServer().getPluginManager().registerEvents(new InventoryUtil(), this);

        getServer().getPluginManager().registerEvents(new BlockCheckUtil(), this);
        getServer().getPluginManager().registerEvents(new StaffUtil(), this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            getServer().getPluginManager().registerEvents(new Gravestones(), this);

            getCommand("vce").setExecutor(new Commands());
            getCommand("cfg").setExecutor(new ConfigCommands());
            getCommand("gravestone").setExecutor(new me.tony.main.voltnetwork.GravestoneUtil.Commands());
            getCommand("koth").setExecutor(new me.tony.main.voltnetwork.Koth.Commands());
            getCommand("sc").setExecutor(new StaffChatCommands());
            getCommand("sm").setExecutor(new StaffModeCommands());
            getCommand("item").setExecutor(new DisplayItem());

            System.out.println("\n VoltNetwork v1.1.3 has been loaded Successfully \n If there is something wrong, please contact Ghostinq on Discord. \n");


        } else {
            getLogger().log(Level.SEVERE, "Could not find PlaceHolderAPI, please install the plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        try {
            KothFileManager.getInstance().Load();
            KothFileManager.getInstance().LoadData();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }


        // Registering Custom Food Events.
        getServer().getPluginManager().registerEvents(new FoodUtil(), this);

        // Loading Custom Recipes.
        CraftingUtil.SpeedySteak();
        CraftingUtil.SuperStew();
        CraftingUtil.SpecialCookie();



    }

    @Override
    public void onDisable() {

        KothFileManager.getInstance().StoreData();
        DonatorFileManagement.getInstance().StoreData();


        for (Player p : Bukkit.getOnlinePlayers()) {
            StaffUtil.FailSafe(p);
        }

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Economy getEconomy() {
        return econ;
    }
    public static Chat getChat() {
        return chat;
    }


    public static WorldGuardPlugin getWorldGuard() {
        Plugin wg = VoltNetwork.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");

        if (wg == null || (!(wg instanceof WorldGuardPlugin))) {
            return null;
        }
        return (WorldGuardPlugin) wg;
    }

    public static VoltNetwork getInstance() {
        return instance;
    }

}