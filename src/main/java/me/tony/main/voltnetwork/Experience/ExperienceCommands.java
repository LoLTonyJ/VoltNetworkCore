package me.tony.main.voltnetwork.Experience;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ExperienceCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        Player p = (Player) sender;
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        if (args.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Correct usage /xp buy/sell"));
        }
        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("buy")) {
                ExperienceGUIUtil.MainGUI(p);
            }
            if (subCommand.equalsIgnoreCase("sell")) {
                ExperienceGUIUtil.SellGUI(p);
            }
        }

        return true;
    }
}
