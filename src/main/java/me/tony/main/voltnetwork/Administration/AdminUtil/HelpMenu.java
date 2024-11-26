package me.tony.main.voltnetwork.Administration.AdminUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpMenu {

    /*
     This class is mainly for Help Menus to make the command classes look cleaner.
     Also helps me stay organized, its mainly strings for Help Menus.
     */

    public static void DonoCommands(Player p) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/dono give <player> <voucher> &7| &bGive someone a voucher."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/dono remove <player> <voucher> &7| &bRemove access."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/dono check <player> &7| &bCheck players Donator Perks."));
    }

    public static void KothCommands(Player p) {

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cKoth Admin Help Menu"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c<> - required param | () - optional param"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/koth cleanup &7| &bRemove Bugged Koth Holograms"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/koth edit <params> &7| &bEdit Koth settings"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/koth create/remove <name> <region_name> (duration) &7| &bCreate / Remove Koth(s)"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/koth start/stop <name> &7| &bStart / Stop koth"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/koth schedule <name> <min> &7| &bSchedule a Koth to start."));

    }

    public static void HelpMenu(Player p) {

        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String BackEndPermission = VoltNetwork.getInstance().getConfig().getString("backend_permission");


        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlugin Information"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCurrent Prefix -> &b" + prefix));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAdministration Permission -> &b" + adminPerm));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBackend Permission -> &b" + BackEndPermission));
        p.sendMessage(" ");
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAdministration Help Menu"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/cfg help &7| &bConfiguration Commands"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/vce help &7| &bCustom Enchantment Commands"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/cfg reload/rl &7| &bReload the configuration"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/cfg edit <arg> &7| &bEdit the config from in-game"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/vce give <player> <enchantment/item> <level> &7| &bGive a custom enchant to a player"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/vce remove <player> <enchantment> &7| &bRemove a custom enchantment from a player"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/koth &7| &bGeneral Koth Command"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/sc &7| &bStaff Chat Command"));

    }

    public static void ConfigModules(Player p) {

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Current Configuration Modules Supported."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/cfg edit prefix <prefix> &b| &7Edits the Plugins prefix."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/cfg edit bePerm <Example.Permission> &b| &7Edits the permissions to edit Configuration"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/cfg edit aPerm <Example.Permission> &b| &7Edits the Administration Permission"));

    }

    public static void ConfigSyntaxError(Player p) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&o!! PLEASE READ CAREFULLY !!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEditing a Value from In-Game to the config is &c&l!! RISKY !! &cplease do this at your own risk! \n I have tried" +
                " my best to make it as safe as possible, theres no data leaks within testing, please be &c&l!! CAREFUL !! &cContinue at your own risk!"));
        p.sendMessage(" ");
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lSyntax Error!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Please specify which module you'd like to edit in the Configuration!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Example -> &c/cfg edit prefix &7[&cCoolPlugin&7]"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Example -> &c/cfg reload"));
    }
}