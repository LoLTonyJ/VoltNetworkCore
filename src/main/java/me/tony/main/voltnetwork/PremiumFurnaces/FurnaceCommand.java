package me.tony.main.voltnetwork.PremiumFurnaces;

import eu.decentsoftware.holograms.api.utils.scheduler.S;
import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FurnaceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        if (!(sender instanceof Player)) {
            sender.sendMessage(Chat.format("&cPlayers can use this command only!"));
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("buy")) {
                UpgradeUtil.FurnacePurchase(p);
            }
        }

        if (p.hasPermission(adminPerm)) {
            // premiumfurnace <give/fix> <player> <tier>
            if (args.length == 0) {
                p.sendMessage(Chat.format("&bUsage /pf <give/fix> <player> <tier>"));
            }
            if (args.length == 2) {
                String subCommand = args[0];
                Player target = Bukkit.getPlayer(args[1]);

                if (target == null || !target.isOnline()) {
                    p.sendMessage(Chat.format(prefix + " &cPLAYER_NULL"));
                }

                if (subCommand.equalsIgnoreCase("fix")) {
                    FurnaceUtil.FixFurnaces(p, target);
                }
                if (subCommand.equalsIgnoreCase("log")) {
                    FurnaceUtil.getPlayerLogs(p, target);
                }
            }
            if (args.length == 3) {
                String subCommand = args[0];
                Player target = Bukkit.getPlayer(args[1]);
                String tier = args[2];
                if (target == null || !target.isOnline()) {
                    p.sendMessage(Chat.format(prefix + " &cPLAYER_NULL"));
                }

                if (subCommand.equalsIgnoreCase("give")) {
                    target.getInventory().addItem(FurnaceUtil.Furnace(tier));
                }
            }
        }

        return true;
    }
}
