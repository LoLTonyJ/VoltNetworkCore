package me.tony.main.voltnetwork.Experience;

import me.tony.main.voltnetwork.VoltNetwork;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ExperienceGUIEvents implements Listener {

    public static ArrayList<Player> chooseXP = new ArrayList<>();
    public static ArrayList<Player> sellXp = new ArrayList<>();

    @EventHandler
    public static void onChat(AsyncPlayerChatEvent e) {

        String msg = e.getMessage();
        Player p = e.getPlayer();
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        int price = VoltNetwork.getInstance().getConfig().getInt("price_per_experience");
        int sell = VoltNetwork.getInstance().getConfig().getInt("price_per_sell");
        Economy balance = VoltNetwork.getEconomy();

        if (sellXp.contains(p)) {
            e.setCancelled(true);
            if (msg.equalsIgnoreCase("cancel")) {
                sellXp.remove(p);
                ExperienceGUIUtil.SellGUI(p);
            }
            try {
                int amt = Integer.parseInt(msg);
                if (p.getExpToLevel() >= amt) {
                    p.giveExpLevels(-amt);
                    balance.depositPlayer(p, amt * sell);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have sold " + amt + " levels of experience! &a+$" + sell * amt));
                    sellXp.remove(p);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have &a" + amt + " of experience levels to sell"));
                    sellXp.remove(p);
                }
            } catch (NumberFormatException n) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7" + msg + " &7is not a number! If you'd like to cancel, please type &c&lCancel!"));
            }

        }


        if (chooseXP.contains(p)) {
            e.setCancelled(true);
            if (msg.equalsIgnoreCase("cancel")) {
                chooseXP.remove(p);
                ExperienceGUIUtil.MainGUI(p);
            }
            try {
                int amt = Integer.parseInt(msg);
                if (balance.getBalance(p) >= amt * price) {
                    p.giveExpLevels(amt);
                    balance.withdrawPlayer(p, amt * price);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &aYou have bought " + amt + " levels of experience! &c-$" + price * amt));
                    chooseXP.remove(p);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have &a$" + amt * price + "!"));
                    chooseXP.remove(p);
                }
            } catch (NumberFormatException n) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7" + msg + " &7is not a number! If you'd like to cancel, please type &c&lCancel!"));
            }
        }
    }

    @EventHandler
    public static void onClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        Economy balance = VoltNetwork.getEconomy();
        int cost = VoltNetwork.getInstance().getConfig().getInt("price_per_experience");
        int sell = VoltNetwork.getInstance().getConfig().getInt("price_per_sell");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&a&lExperience Shop"))) {

            e.setCancelled(true);

            // Buy Shop
            // 25
            if (e.getSlot() == 10) {
                if (balance.getBalance(p) >= cost * 25) {
                    p.giveExpLevels(25);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've bought 25 levels of experience! &c-$" + cost * 25));
                    balance.withdrawPlayer(p, cost * 25);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have &a$" + cost * 25 + "!"));
                    p.closeInventory();
                }
            }
            // 50
            if (e.getSlot() == 11) {
                if (balance.getBalance(p) >= cost * 50) {
                    p.giveExpLevels(50);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've bought 50 levels of experience! &c-$" + cost * 50));
                    balance.withdrawPlayer(p, cost * 50);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have &a$" + cost * 50 + "!"));
                    p.closeInventory();
                }
            }
            // 75
            if (e.getSlot() == 12) {
                if (balance.getBalance(p) >= cost * 75) {
                    p.giveExpLevels(75);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've bought 75 levels of experience! &c-$" + cost * 75));
                    balance.withdrawPlayer(p, cost * 75);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have &a$" + cost * 75 + "!"));
                    p.closeInventory();
                }
            }
            // 100
            if (e.getSlot() == 13) {
                if (balance.getBalance(p) >= cost * 100) {
                    p.giveExpLevels(100);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've bought 100 levels of experience! &c-$" + cost * 100));
                    balance.withdrawPlayer(p, cost * 100);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have &a$" + cost * 100 + "!"));
                    p.closeInventory();
                }
            }
            // Custom Amount
            if (e.getSlot() == 16) {
                chooseXP.add(p);
                p.closeInventory();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Please type the amount you'd like to buy!"));
            }
            // Close Inventory
            if (e.getSlot() == 18) {
                p.closeInventory();
            }
            // Switch Shop Mode.
            if (e.getSlot() == 26) {
                ExperienceGUIUtil.SellGUI(p);
            }

        }
        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&a&lExperience Sell"))) {

            e.setCancelled(true);

            // Sell Shop
            // 25
            if (e.getSlot() == 10) {
                if (p.getExpToLevel() >= 25) {
                    p.giveExpLevels(-25);
                    balance.depositPlayer(p, sell * 25);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've sold 25 levels of experience! &a+$" + sell * 25));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have 25 experience levels to sell!"));
                }
            }
            if (e.getSlot() == 11) {
                if (p.getExpToLevel() >= 50) {
                    p.giveExpLevels(-50);
                    balance.depositPlayer(p, sell * 50);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've sold 25 levels of experience! &a+$" + sell * 50));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have 50 experience levels to sell!"));
                }
            }
            if (e.getSlot() == 12) {
                if (p.getExpToLevel() >= 75) {
                    p.giveExpLevels(-75);
                    balance.depositPlayer(p, sell * 75);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've sold 75 levels of experience! &a+$" + sell * 75));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have 75 experience levels to sell!"));
                }
            }
            if (e.getSlot() == 13) {
                if (p.getExpToLevel() >= 100) {
                    p.giveExpLevels(-100);
                    balance.depositPlayer(p, sell * 100);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've sold 100 levels of experience! &a+$" + sell * 100));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cYou do not have 100 experience levels to sell!"));
                }
            }

            // Custom Amount
            if (e.getSlot() == 16) {
                sellXp.add(p);
                p.closeInventory();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Please type the amount you'd like to sell!"));
            }
            // Close Inventory
            if (e.getSlot() == 18) {
                p.closeInventory();
            }
            // Switch Shop Mode.
            if (e.getSlot() == 26) {
                ExperienceGUIUtil.MainGUI(p);
            }
        }
    }
}