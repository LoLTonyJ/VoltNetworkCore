package me.tony.main.voltnetwork.StaffMode;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffModeCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String permission = VoltNetwork.getInstance().getConfig().getString("staff_mode_permission");
        Player p = (Player) sender;

        if (p.hasPermission(permission)) {
            if (args.length == 0) {
                StaffUtil.InitStaffMode(p);
            }
            if (args.length == 1) {
                String subCommand = args[0];
                if (subCommand.equals("alerts")) {
                    StaffUtil.AlertToggle(p);
                }
            }
        }

        return true;
    }
}
