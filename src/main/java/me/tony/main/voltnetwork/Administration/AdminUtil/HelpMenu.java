package me.tony.main.voltnetwork.Administration.AdminUtil;

import me.tony.main.voltnetwork.GeneralUtil.ChatUtil;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpMenu {

    /*
     This class is mainly for Help Menus to make the command classes look cleaner.
     Also helps me stay organized, its mainly strings for Help Menus.
     */

    public static void DonoCommands(Player p) {
        p.sendMessage(ChatUtil.format("&b/dono give <player> <voucher> &7| &bGive someone a voucher."));
        p.sendMessage(ChatUtil.format("&b/dono remove <player> <voucher> &7| &bRemove access."));
        p.sendMessage(ChatUtil.format("&b/dono check <player> &7| &bCheck players Donator Perks."));
    }

    public static void KothCommands(Player p) {

        p.sendMessage(ChatUtil.format("&cKoth Admin Help Menu"));
        p.sendMessage(ChatUtil.format("&c<> - required param | () - optional param"));
        p.sendMessage(ChatUtil.format("&b/koth cleanup &7| &bRemove Bugged Koth Holograms"));
        p.sendMessage(ChatUtil.format("&b/koth edit <params> &7| &bEdit Koth settings"));
        p.sendMessage(ChatUtil.format("&b/koth create/remove <name> <region_name> (duration) &7| &bCreate / Remove Koth(s)"));
        p.sendMessage(ChatUtil.format("&b/koth start/stop <name> &7| &bStart / Stop koth"));
        p.sendMessage(ChatUtil.format("&b/koth schedule <name> <min> &7| &bSchedule a Koth to start."));

    }

    public static void HelpMenu(Player p) {

        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String BackEndPermission = VoltNetwork.getInstance().getConfig().getString("backend_permission");


        p.sendMessage(ChatUtil.format("&cPlugin Information"));
        p.sendMessage(ChatUtil.format("&cCurrent Prefix -> &b" + prefix));
        p.sendMessage(ChatUtil.format("&cAdministration Permission -> &b" + adminPerm));
        p.sendMessage(ChatUtil.format("&cBackend Permission -> &b" + BackEndPermission));
        p.sendMessage(" ");
        p.sendMessage(ChatUtil.format("&cAdministration Help Menu"));
        p.sendMessage(ChatUtil.format("&b/cfg help &7| &bConfiguration Commands"));
        p.sendMessage(ChatUtil.format("&b/vce help &7| &bCustom Enchantment Commands"));
        p.sendMessage(ChatUtil.format("&b/cfg reload/rl &7| &bReload the configuration"));
        p.sendMessage(ChatUtil.format("&b/cfg edit <arg> &7| &bEdit the config from in-game"));
        p.sendMessage(ChatUtil.format("&b/vce give <player> <enchantment/item> <level> &7| &bGive a custom enchant to a player"));
        p.sendMessage(ChatUtil.format("&b/vce remove <player> <enchantment> &7| &bRemove a custom enchantment from a player"));
        p.sendMessage(ChatUtil.format("&b/koth &7| &bGeneral Koth Command"));
        p.sendMessage(ChatUtil.format("&b/sc &7| &bStaff Chat Command"));

    }

    public static void ConfigModules(Player p) {

        p.sendMessage(ChatUtil.format("&7Current Configuration Modules Supported."));
        p.sendMessage(ChatUtil.format("&7/cfg edit prefix <prefix> &b| &7Edits the Plugins prefix."));
        p.sendMessage(ChatUtil.format("&7/cfg edit bePerm <Example.Permission> &b| &7Edits the permissions to edit Configuration"));
        p.sendMessage(ChatUtil.format("&7/cfg edit aPerm <Example.Permission> &b| &7Edits the Administration Permission"));

    }

    public static void ConfigSyntaxError(Player p) {
        p.sendMessage(ChatUtil.format("&c&l&o!! PLEASE READ CAREFULLY !!"));
        p.sendMessage(ChatUtil.format("&cEditing a Value from In-Game to the config is &c&l!! RISKY !! &cplease do this at your own risk! \n I have tried" +
                " my best to make it as safe as possible, theres no data leaks within testing, please be &c&l!! CAREFUL !! &cContinue at your own risk!"));
        p.sendMessage(" ");
        p.sendMessage(ChatUtil.format("&c&lSyntax Error!"));
        p.sendMessage(ChatUtil.format("&7Please specify which module you'd like to edit in the Configuration!"));
        p.sendMessage(ChatUtil.format("&7Example -> &c/cfg edit prefix &7[&cCoolPlugin&7]"));
        p.sendMessage(ChatUtil.format("&7Example -> &c/cfg reload"));
    }
}