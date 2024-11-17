package me.tony.main.voltnetwork.Administration.AdminUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ConfigCommands implements CommandExecutor {

    public static ArrayList<Player> CFGReload = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String BackEndPermission = VoltNetwork.getInstance().getConfig().getString("backend_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        Player p = (Player) sender;
        if (p.hasPermission(BackEndPermission)) {
            if (args.length == 1) {
                String subCommand = args[0];
                if (subCommand.equalsIgnoreCase("rl") || subCommand.equalsIgnoreCase("reload")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n" + prefix + " &7Reloading the config file is &c&l!! Risky !!&7 Some values may return to default, do you want to proceed?"));
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cType &a&LYes &cIf you'd like to proceed \n&cType &c&LNo &cIf you'd like to cancel."));
                    CFGReload.add(p);
                }
                if (subCommand.equalsIgnoreCase("modules")) {
                    HelpMenu.ConfigModules(p);
                }
            }
            if (args.length == 3) {
                String subCommand = args[0];
                String cfgEdit = args[1];
                String toEdit = args[2];
                if (subCommand.equalsIgnoreCase("edit")) {
                    if (cfgEdit.equalsIgnoreCase("prefix")) {
                        VoltNetwork.getInstance().getConfig().set("prefix", ChatColor.translateAlternateColorCodes('&', toEdit));
                        VoltNetwork.getInstance().saveDefaultConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &aThe Plugin prefix has been changed!"));
                    }
                    if (cfgEdit.equalsIgnoreCase("bePerm")) {
                        VoltNetwork.getInstance().getConfig().set("backend_permission", toEdit);
                        VoltNetwork.getInstance().saveDefaultConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &aThe Backend Permission has been changed!"));
                    }
                    if (cfgEdit.equalsIgnoreCase("aPerm")) {
                        VoltNetwork.getInstance().getConfig().set("administration_permission", toEdit);
                        VoltNetwork.getInstance().saveDefaultConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &aThe Administration Permission has been changed!"));
                    }
                }
            } else if (args.length > 3 || args.length == 2) {
                HelpMenu.ConfigSyntaxError(p);
            }
        }
        return true;
    }
}