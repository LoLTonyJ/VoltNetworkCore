package me.tony.main.voltnetwork.Administration;

import me.tony.main.voltnetwork.Administration.AdminUtil.HelpMenu;
import me.tony.main.voltnetwork.BonusFood.CraftingUtil;
import me.tony.main.voltnetwork.BonusFood.FoodUtil;
import me.tony.main.voltnetwork.CustomItems.DrillUtil;
import me.tony.main.voltnetwork.CustomItems.HarvestUtil;
import me.tony.main.voltnetwork.CustomItems.TPBowUtil;
import me.tony.main.voltnetwork.CustomItemsUtil.SuperBonemeal;
import me.tony.main.voltnetwork.EnchantmentUtil.RemoveEnchant;
import me.tony.main.voltnetwork.Enchantments.Harvest;
import me.tony.main.voltnetwork.VoltNetwork;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.tony.main.voltnetwork.StaffMode.StaffUtil.prefix;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        int amount = VoltNetwork.getInstance().getConfig().getInt("crate_key_amount");
        String command = VoltNetwork.getInstance().getConfig().getString("crate_key_command");
        Boolean announce = VoltNetwork.getInstance().getConfig().getBoolean("announce_key_purchase");
        String announceMsg = VoltNetwork.getInstance().getConfig().getString("announce_key_message");

        Player p = (Player) sender;

        if (args.length == 2) {
            String subCommand = args[0];
            int keyAmount = Integer.parseInt(args[1]);
            if (subCommand.equalsIgnoreCase("key")) {
                keyGive(p, keyAmount);
            }
        } else if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("key")) {
                keyGive(p, 1);
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
                            itemGive(target, DrillUtil.DrillItem());
                        }
                        if (itemType.equalsIgnoreCase("hoe")) {
                            itemGive(target, HarvestUtil.HarvestItem());
                        }
                        if (itemType.equalsIgnoreCase("tpbow")) {
                            itemGive(target, TPBowUtil.tpBow());
                        }
                    }
                }
                if (subCommand.equalsIgnoreCase("customfood") || subCommand.equalsIgnoreCase("food")) {
                    if (target != null && target.isOnline()) {
                        if (itemType.equalsIgnoreCase("cookie")) {
                            itemGive(target, CraftingUtil.Cookie());
                        }
                        if (itemType.equalsIgnoreCase("steak")) {
                            itemGive(target, CraftingUtil.SpeedSteak());
                        }
                        if (itemType.equalsIgnoreCase("stew")) {
                            itemGive(target, CraftingUtil.Stew());
                        }
                    }
                }
                if (subCommand.equalsIgnoreCase("customitem") || subCommand.equalsIgnoreCase("item")) {
                    if (itemType.equalsIgnoreCase("bonemeal") || itemType.equalsIgnoreCase("bone")) {
                        itemGive(target, SuperBonemeal.SuperMeal());
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

    public static void itemGive(Player p, ItemStack item) {
        p.getInventory().addItem(item);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You have given " + item.getItemMeta().getDisplayName() + " &7to " + p.getName()));
    }

    public static void keyGive(Player p, Integer amount) {

        int price = VoltNetwork.getInstance().getConfig().getInt("crate_key_amount");
        String command = VoltNetwork.getInstance().getConfig().getString("crate_key_command");
        boolean announce = VoltNetwork.getInstance().getConfig().getBoolean("announce_key_purchase");
        String announceMsg = VoltNetwork.getInstance().getConfig().getString("announce_key_message");

        if (amount != null || amount != 0) {
            Economy balance = VoltNetwork.getEconomy();
            if (balance.getBalance(p) >= price * amount) {
                String replaceCMD = command.replace("%player%", p.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaceCMD + " " + amount);
                balance.withdrawPlayer(p, price * amount);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have bought the Common Crate key"));
                if (announce) {
                    String replaceBC = announceMsg.replace("%player%", p.getName());
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', replaceBC));
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have enough money to buy that many keys!"));
            }
        }
    }



}
