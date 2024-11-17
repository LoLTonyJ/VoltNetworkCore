package me.tony.main.voltnetwork.DonatorPerks;

import me.tony.main.voltnetwork.Administration.AdminUtil.HelpMenu;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class DonatorCommands implements CommandExecutor {

    public static ArrayList<String> NVPlayers = new ArrayList<>();

    public boolean availSlot(Player p) {
        Inventory inv = p.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        /*
        General Command Usage: /dono <param> <player> <voucher>
         */

        String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        // Console Area
        if (args.length == 3) {
            if (sender instanceof ConsoleCommandSender) {
                String subCommand = args[0];
                Player target = Bukkit.getPlayer(args[1]);
                String voucherName = args[2];
                if (target != null) {
                    if (subCommand.equalsIgnoreCase("give")) {
                        if (voucherName.equalsIgnoreCase("nightvision") ||
                                voucherName.equalsIgnoreCase("nv")) {
                            if (availSlot(target)) {
                                target.getInventory().addItem(VouchItems.NightVisionVouch());
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You have been given a NightVision Voucher!"));
                            } else if (!availSlot(target)) {
                                VoltNetwork.getInstance().getLogger().log(Level.SEVERE, target.getDisplayName() + " does not have an open inventory slot!");
                            }
                        }
                    }
                }
                return true;
            }
        }


        if (sender.hasPermission(adminPerm)) {
            if (args.length == 0) {
                HelpMenu.DonoCommands((Player) sender);
            }
            if (args.length == 3) {
                Player p = (Player) sender;
                String subCommand = args[0];
                Player target = Bukkit.getPlayer(args[1]);
                String voucherName = args[2];
                if (target != null) {
                    if (subCommand.equalsIgnoreCase("give")) {
                        if (voucherName.equalsIgnoreCase("nightvision") ||
                                voucherName.equalsIgnoreCase("nv")) {
                            if (availSlot(target)) {
                                target.getInventory().addItem(VouchItems.NightVisionVouch());
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You have been given a NightVision Voucher!"));
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Given " + target.getDisplayName() + " NightVision Voucher!"));
                            } else if (!availSlot(target)) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + target.getDisplayName() + " Does not have an available slot in their inventory!"));
                            }
                        }
                    }
                    if (subCommand.equalsIgnoreCase("remove")) {
                        if (voucherName.equalsIgnoreCase("nightvision") ||
                                voucherName.equalsIgnoreCase("nv")) {
                            if (DonatorCommands.NVPlayers.contains(target.getName())) {
                                DonatorCommands.NVPlayers.remove(target.getName());
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Removed Night Vision Perms from " + target.getDisplayName()));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
