package me.tony.main.voltnetwork.StaffChat;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class StaffChatCommands implements CommandExecutor {

    public static ArrayList<UUID> StaffChat = new ArrayList<>();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        String chatPerm = VoltNetwork.getInstance().getConfig().getString("staff_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        Player p = (Player) sender;


        if (p.hasPermission(chatPerm)) {
            if (args.length == 0) {
                if (StaffChat.contains(p.getUniqueId())) {
                    StaffChat.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have disabled Staff Chat! You will now send messages globally!"));
                    return true;
                }
                StaffChat.add(p.getUniqueId());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You're have enabled Staff Chat, you will now send messages to other Staff Members Online!"));
            }
        }


        return true;
    }
}
