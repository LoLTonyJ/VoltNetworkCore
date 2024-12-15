package me.tony.main.voltnetwork.AntiCheat;

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

public class ReportCommand implements CommandExecutor {


    private static ArrayList<UUID> blacklisted = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        Player p = (Player) sender;
        String perm = VoltNetwork.getInstance().getConfig().getString("ac_permission");

        if (blacklisted.contains(p.getUniqueId())) {
            p.sendMessage(Chat.format("&c&lBLACKLISTED >> &cYou have been blocked from making player reports"));
            p.sendMessage(Chat.format("&cIf you believe this is a mistake, create a Ticket in our Discord!"));
        }

        if (args.length == 0) {
            p.sendMessage(Chat.format("&bUsage /report <player> <reason>"));
        }

        if (args.length > 2) {
            p.sendMessage(Chat.format("&bUsage /report <player> <reason>"));
        }

        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            String reason = args[1];
            if (target == null || !target.isOnline()) return true;
            if (reason.equalsIgnoreCase("blacklist") || reason.equalsIgnoreCase("block")) {
                if (p.hasPermission(perm)) {
                    if (blacklisted.contains(target.getUniqueId())) {
                        p.sendMessage(Chat.format("&c" + target.getName() + " has already been blocked from using this command!"));
                    } else {
                        blacklisted.add(target.getUniqueId());
                    }
                }
            }
            PlayerReport.reportPlayer(target, p, reason);
            p.sendMessage(Chat.format("&bReported " + target.getName()));
        }
        return true;
    }
}
