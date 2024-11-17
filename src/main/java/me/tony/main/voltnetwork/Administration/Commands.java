package me.tony.main.voltnetwork.Administration;

import me.tony.main.voltnetwork.Administration.AdminUtil.HelpMenu;
import me.tony.main.voltnetwork.CustomItems.DrillUtil;
import me.tony.main.voltnetwork.CustomItems.HarvestUtil;
import me.tony.main.voltnetwork.EnchantmentUtil.RemoveEnchant;
import me.tony.main.voltnetwork.Enchantments.Harvest;
import me.tony.main.voltnetwork.RemoveCooldown.InventoryUtil;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        Player p = (Player) sender;

        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("cooldown") || subCommand.equalsIgnoreCase("cooldowns") || subCommand.equalsIgnoreCase("cd")) {
                InventoryUtil.Cooldowns(p);
            }
        }

        // Checks players permission.
        if (p.hasPermission(adminPerm)) {
            if (args.length == 0) {
                HelpMenu.HelpMenu(p);
            }
            if (args.length == 2) {
                // /vce remove <int>
                String subCommand = args[0];
                Integer i = Integer.valueOf(args[1]);
                if (subCommand.equalsIgnoreCase("remove")) {
                    RemoveEnchant.LoreRemove(p, i);
                }
            }
            if (args.length == 3) {
                String subCommand = args[0];
                Player target = Bukkit.getPlayer(args[1]);
                String itemType = args[2];
                if (subCommand.equalsIgnoreCase("toolgive") || subCommand.equalsIgnoreCase("tool")) {
                    if (target != null && target.isOnline()) {
                        if (itemType.equalsIgnoreCase("drill")) {
                            target.getInventory().addItem(DrillUtil.DrillItem());
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You've given the Drill to " + target.getDisplayName()));
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You have been gifted the Drill!"));
                        }
                        if (itemType.equalsIgnoreCase("hoe")) {
                            target.getInventory().addItem(HarvestUtil.HarvestItem());
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You've given the Harvest Hoe to " + target.getDisplayName()));
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You have been gifted the Harvesting Hoe!"));
                        }
                    }
                }
            }
            if (args.length == 4) {
                String subCommand = args[0];
                Player target = Bukkit.getPlayer(args[1]);
                String enchType = args[2];
                Integer enchLvl = Integer.valueOf(args[3]);
                if (subCommand.equalsIgnoreCase("give")) {
                    if (target != null && target.isOnline()) {
                        if (enchType.equalsIgnoreCase("harvest")) {
                            if (enchLvl > 2) p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7The maximum level for this enchantment is 2"));
                            if (enchLvl.equals(1)) {
                                target.getInventory().addItem(Harvest.item(1));
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've given " + target.getName() + " an Harvest I book!"));
                            } else if (enchLvl.equals(2)) {
                                target.getInventory().addItem(Harvest.item(2));
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've given " + target.getName() + " an Harvest II book!"));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
