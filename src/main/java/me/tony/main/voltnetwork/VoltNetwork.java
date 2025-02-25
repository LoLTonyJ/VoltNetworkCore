package me.tony.main.voltnetwork;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.tony.main.voltnetwork.Administration.AdminUtil.ConfigCommands;
import me.tony.main.voltnetwork.Administration.AdminUtil.ConfigReloadConfirm;
import me.tony.main.voltnetwork.Administration.Commands;
import me.tony.main.voltnetwork.AntiCheat.*;
import me.tony.main.voltnetwork.BonusFood.CooldownUtil;
import me.tony.main.voltnetwork.BonusFood.CraftingUtil;
import me.tony.main.voltnetwork.BonusFood.FoodUtil;
import me.tony.main.voltnetwork.CaseItem.CaseCommands;
import me.tony.main.voltnetwork.ChatUtil.DisplayItem;
import me.tony.main.voltnetwork.CustomBoss.*;
import me.tony.main.voltnetwork.CustomItems.DrillUtil;
import me.tony.main.voltnetwork.CustomItems.HarvestUtil;
import me.tony.main.voltnetwork.CustomItems.TPBowUtil;
import me.tony.main.voltnetwork.CustomItemsUtil.SuperBonemeal;
import me.tony.main.voltnetwork.DonatorPerks.DonatorCommands;
import me.tony.main.voltnetwork.DonatorPerks.DonatorFileManagement;
import me.tony.main.voltnetwork.DonatorPerks.DonatorUtil;
import me.tony.main.voltnetwork.DonatorPerks.NightVisionCommand;
import me.tony.main.voltnetwork.EnchantmentUtil.EnchantmentAdd;
import me.tony.main.voltnetwork.EnchantmentUtil.HarvestListener;
import me.tony.main.voltnetwork.Enchantments.Harvest;
import me.tony.main.voltnetwork.Experience.ExperienceCommands;
import me.tony.main.voltnetwork.Experience.ExperienceGUIEvents;
import me.tony.main.voltnetwork.GravestoneUtil.Gravestones;
import me.tony.main.voltnetwork.KothUtil.KothCap;
import me.tony.main.voltnetwork.KothUtil.KothFileManager;
import me.tony.main.voltnetwork.KothUtil.RewardEdit;
import me.tony.main.voltnetwork.PremiumFurnaces.FurnaceCommand;
import me.tony.main.voltnetwork.PremiumFurnaces.PlayerUtil;
import me.tony.main.voltnetwork.RemoveCooldown.InventoryUtil;
import me.tony.main.voltnetwork.StaffChat.StaffChatCommands;
import me.tony.main.voltnetwork.StaffChat.StaffChatUtil;
import me.tony.main.voltnetwork.StaffMode.StaffModeCommands;
import me.tony.main.voltnetwork.StaffMode.StaffUtil;
import me.tony.main.voltnetwork.ViewCraftingRecipe.RecipeCommand;
import me.tony.main.voltnetwork.Vouchers.VoucherCommand;
import me.tony.main.voltnetwork.Vouchers.VoucherUtil;
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

        String version = this.getConfig().getString("version");
        instance = this;
        worldGuardPlugin = getWorldGuard();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        setupPermissions();
        setupChat();

        for (Player p : Bukkit.getOnlinePlayers()) {
            LagCheck.nearbyEntities(p);
        }

        try {
            // Data File Util.
            KothFileManager.getInstance().Load();
            KothFileManager.getInstance().LoadData();
            BossFileManager.getInstance().Load();
            BossFileManager.getInstance().LoadData();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (this.getConfig().getBoolean("donator_perks_active")) {
            // Load Donator Perks.
            try {
                DonatorFileManagement.getInstance().Load();
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
            DonatorFileManagement.getInstance().LoadData();

            getServer().getPluginManager().registerEvents(new DonatorUtil(), this);
            getCommand("dono").setExecutor(new DonatorCommands());
            getCommand("nv").setExecutor(new NightVisionCommand());

        } else {
            getLogger().log(Level.WARNING, "\n" + version + "\n Donator Perks are Disabled.");
        }

        // Custom Enchantments
        getServer().getPluginManager().registerEvents(new Harvest(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentAdd(), this);
        getServer().getPluginManager().registerEvents(new HarvestListener(), this);

        // Configuration Chat Util
        getServer().getPluginManager().registerEvents(new ConfigReloadConfirm(), this);

        // Koth Listeners
        getServer().getPluginManager().registerEvents(new RewardEdit(), this);
        getServer().getPluginManager().registerEvents(new KothCap(), this);

        // Custom Items Listeners
        getServer().getPluginManager().registerEvents(new DrillUtil(), this);
        getServer().getPluginManager().registerEvents(new HarvestUtil(), this);
        getServer().getPluginManager().registerEvents(new TPBowUtil(), this);
        getServer().getPluginManager().registerEvents(new SuperBonemeal(), this);

        // Staff Mode Listeners / Util.
        getServer().getPluginManager().registerEvents(new StaffChatUtil(), this);
        getServer().getPluginManager().registerEvents(new StaffUtil(), this);

        // Registering Custom Food Events.
        getServer().getPluginManager().registerEvents(new InventoryUtil(), this);
        getServer().getPluginManager().registerEvents(new FoodUtil(), this);

        // Boss Listeners / Util.
        getServer().getPluginManager().registerEvents(new BossUtil(), this);
        getServer().getPluginManager().registerEvents(new BossInventoryUtil(), this);

        // Experience GUI Listeners / Util.
        getServer().getPluginManager().registerEvents(new ExperienceGUIEvents(), this);

        // Vouchers
        getServer().getPluginManager().registerEvents(new VoucherUtil(), this);

        // Recipe Command
        getServer().getPluginManager().registerEvents(new RecipeCommand(), this);

        // Premium Furnaces
        getServer().getPluginManager().registerEvents(new PlayerUtil(), this);

        // Anti Cheat
        getServer().getPluginManager().registerEvents(new BlockCheck(), this);
        getServer().getPluginManager().registerEvents(new ReachCheck(), this);
       // getServer().getPluginManager().registerEvents(new FlightCheck(), this);
        getServer().getPluginManager().registerEvents(new CPSCheck(), this);
        getServer().getPluginManager().registerEvents(new AuraCheckUtil(), this);


        // Commands
        getCommand("koth").setExecutor(new me.tony.main.voltnetwork.Koth.Commands());
        getCommand("staffchat").setExecutor(new StaffChatCommands());
        getCommand("staffmode").setExecutor(new StaffModeCommands());
        getCommand("cooldown").setExecutor(new me.tony.main.voltnetwork.RemoveCooldown.Commands());
        getCommand("item").setExecutor(new DisplayItem());
        getCommand("customboss").setExecutor(new BossCommands());
        getCommand("experience").setExecutor(new ExperienceCommands());
        getCommand("displaycase").setExecutor(new CaseCommands());
        getCommand("voltcustomenchant").setExecutor(new Commands());
        getCommand("config").setExecutor(new ConfigCommands());
        getCommand("voucher").setExecutor(new VoucherCommand());
        getCommand("recipe").setExecutor(new RecipeCommand());
        getCommand("premiumfurnace").setExecutor(new FurnaceCommand());
        getCommand("voltnetworkanticheat").setExecutor(new ACMain());
        getCommand("report").setExecutor(new ReportCommand());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            getServer().getPluginManager().registerEvents(new Gravestones(), this);

            getCommand("gravestone").setExecutor(new me.tony.main.voltnetwork.GravestoneUtil.Commands());


        } else {
            getLogger().log(Level.SEVERE, "Could not find PlaceHolderAPI, please install the plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // Bukkit Runnable


        if (this.getConfig().getBoolean("donator_cooldown") && this.getConfig().getBoolean("donator_perks_active")) {
            DonatorUtil.Cooldown();
        }

        // Custom Food Runnables.
        CooldownUtil.Cooldown();

        // Custom Boss Runnables.
        BossCooldowns.DialogueQueue();
        BossCooldowns.AbilityUse();
        //BossCooldowns.SpawnWatchers();
        BossCooldowns.SpawnMinions();

        // Loading Custom Recipes.
        CraftingUtil.SpeedySteak();
        CraftingUtil.SuperStew();
        CraftingUtil.SpecialCookie();
        SuperBonemeal.SuperBone();

        System.out.println("\n" + version + " has been loaded Successfully \n If there is something wrong, please contact Ghostinq on Discord. \n");

    }

    @Override
    public void onDisable() {

        KothFileManager.getInstance().StoreData();
        DonatorFileManagement.getInstance().StoreData();
        BossFileManager.getInstance().SaveData();


        for (Player p : Bukkit.getOnlinePlayers()) {
            // Restores Inventory before Plugin Restart.
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

    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
        getConfig().options().copyDefaults(true);
    }


    public static VoltNetwork getInstance() {
        return instance;
    }

}