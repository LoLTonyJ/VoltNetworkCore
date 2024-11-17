package me.tony.main.voltnetwork.RemoveCooldown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p = (Player) sender;

        InventoryUtil.Cooldowns(p);

        return true;
    }
}
