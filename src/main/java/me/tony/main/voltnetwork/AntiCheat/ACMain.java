package me.tony.main.voltnetwork.AntiCheat;

import me.tony.main.voltnetwork.Administration.AdminUtil.HelpMenu;
import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class ACMain implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(Chat.format("&cThis Command is Player Use Only!"));
        }
        Player p = (Player) sender;

        if (!p.hasPermission("VoltNetwork.AntiCheat.Staff")) {
            return true;
        }

        if (args.length == 0) {
            HelpMenu.AntiCheatCommands(p);
        }
        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("alerts")) {
                ACUtil.toggleAlerts(p);
            }
        }
        if (args.length == 2) {
            String subCommand = args[0];
            Player target = Bukkit.getPlayer(args[1]);
            if (subCommand.equalsIgnoreCase("exempt")) {
                ACUtil.exemptPlayer(p, target);
            }
        }
        if (args.length == 3) {
            // vna (0) check (1) p (2) param
            String subCommand = args[0];
            Player target = Bukkit.getPlayer(args[1]);
            String param = args[2];
            if (subCommand.equalsIgnoreCase("check")) {
                String cheatCheck = args[1];
                if (cheatCheck.equalsIgnoreCase("killaura") || cheatCheck.equalsIgnoreCase("ka")) {
                    Player toCheck = Bukkit.getPlayer(args[2]);
                    if (toCheck != null && toCheck.isOnline()) {
                        AuraCheckUtil.AuraCheck(toCheck);
                    }
                }
                if (cheatCheck.equalsIgnoreCase("cps") || cheatCheck.equalsIgnoreCase("macro")) {
                    Player toCheck = Bukkit.getPlayer(args[2]);
                    if (toCheck != null && toCheck.isOnline()) {
                        CPSCheck.initCPSCheck(toCheck);
                        p.sendMessage(Chat.format("&cChecking...."));
                    }
                }
            }
            if (subCommand.equalsIgnoreCase("log")) {
                String subParam = args[1];
                if (subParam.equalsIgnoreCase("check")) {
                    ACUtil.getPlayerLogs(Bukkit.getPlayer(args[2]), p);
                }
                if (subParam.equalsIgnoreCase("clear")) {
                    ACUtil.clearPlayerLogs(target, p);
                }
            }
            if (subCommand.equalsIgnoreCase("report")) {
                String subParam = args[1];
                if (subParam.equalsIgnoreCase("check") || param.equalsIgnoreCase("view")) {
                    Player toView = Bukkit.getPlayer(args[2]);
                    PlayerReport.reportView(p, toView);
                }
            }
        }

        return true;
    }
}
