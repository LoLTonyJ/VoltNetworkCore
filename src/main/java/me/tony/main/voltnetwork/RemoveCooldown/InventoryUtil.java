package me.tony.main.voltnetwork.RemoveCooldown;

import me.tony.main.voltnetwork.BonusFood.FoodUtil;
import me.tony.main.voltnetwork.DonatorPerks.NightVisionCommand;
import me.tony.main.voltnetwork.StaffMode.StaffUtil;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryUtil implements Listener {


    public static void FillInventory(Material type, Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, new ItemStack(type));
            }
        }
    }

    protected static ItemStack createGUIItem(final Material material, final String title, final String lore, final String lore1, final String lore2, final String lore3) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore), ChatColor.translateAlternateColorCodes('&', lore1), ChatColor.translateAlternateColorCodes('&', lore2), ChatColor.translateAlternateColorCodes('&', lore3)));
        item.setItemMeta(meta);

        return item;
    }

    public static Inventory Cooldowns(Player p) {

        Inventory inv = Bukkit.createInventory(p, 27, ChatColor.translateAlternateColorCodes('&', "&7Buy Cooldowns"));

        Integer minCookie = VoltNetwork.getInstance().getConfig().getInt("special_cookie_cost");
        Integer clearCookie = VoltNetwork.getInstance().getConfig().getInt("special_cookie_remove");
        Integer minNv = VoltNetwork.getInstance().getConfig().getInt("night_vision_cost");
        Integer clearNV = VoltNetwork.getInstance().getConfig().getInt("night_vision_remove");

        inv.setItem(12, StaffUtil.ComingSoon());
        inv.setItem(13, StaffUtil.ComingSoon());
        inv.setItem(14, StaffUtil.ComingSoon());
        inv.setItem(15, StaffUtil.ComingSoon());
        inv.setItem(16, StaffUtil.ComingSoon());

        if (FoodUtil.SpecialCookie.containsKey(p)) {
            inv.setItem(10, createGUIItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&6&lSpecial Cookie"), " ", ChatColor.translateAlternateColorCodes('&', "&7Time Remaining: " + FoodUtil.SpecialCookie.get(p) + " minutes"),
                    ChatColor.translateAlternateColorCodes('&', "&7((Left-Click)) Remove 1 Minute - &a$" + minCookie), ChatColor.translateAlternateColorCodes('&', "&7((Right Click)) Remove Cooldown - &a$" + clearCookie)));
        } else {
            inv.setItem(10, createGUIItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&6&lSpecial Cookie"), " ", ChatColor.translateAlternateColorCodes('&', "&cNo Active Cooldown!"), " ", " "));
        }
        if (NightVisionCommand.Cooldown.containsKey(p)) {
            inv.setItem(11, createGUIItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&a&lNight Vision"), " ", ChatColor.translateAlternateColorCodes('&', "&7Time Remaining: " + NightVisionCommand.Cooldown.get(p) + " minutes"),
                    ChatColor.translateAlternateColorCodes('&', "&7((Left-Click)) Remove 1 Minute - &a$" + minNv), ChatColor.translateAlternateColorCodes('&', "&7((Right Click)) Remove Cooldown - &a$" + clearNV)));
        } else {
            inv.setItem(11, createGUIItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&a&lNight Vision"), " ", ChatColor.translateAlternateColorCodes('&', "&cNo Active Cooldown!"), " ", " "));
        }

        FillInventory(Material.BLACK_STAINED_GLASS_PANE, inv);
        p.openInventory(inv);
        return inv;
    }

    @EventHandler
    public static void InventoryClick(InventoryClickEvent e) {

        // Left-Click
        Integer minCookie = VoltNetwork.getInstance().getConfig().getInt("special_cookie_cost");
        Integer minNv = VoltNetwork.getInstance().getConfig().getInt("night_vision_cost");

        // Right-Click
        Integer clearCookie = VoltNetwork.getInstance().getConfig().getInt("special_cookie_remove");
        Integer clearNV = VoltNetwork.getInstance().getConfig().getInt("night_vision_remove");

        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&7Buy Cooldowns"))) {
            e.setCancelled(true);
            // Left Click
            if (e.getClick().isLeftClick()) {
                if (e.getSlot() == 10) {
                    if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
                        // Cookie Cooldown
                        if (FoodUtil.SpecialCookie.get(p) != 0) {
                            int time = FoodUtil.SpecialCookie.get(p);
                            if (VoltNetwork.getEconomy().getBalance(p) >= minCookie) {
                                VoltNetwork.getEconomy().withdrawPlayer(p, minCookie);
                                FoodUtil.SpecialCookie.replace(p, time, time - 1);
                                Cooldowns(p);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aNew Balance: $" + VoltNetwork.getEconomy().getBalance(p)));
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR > &cYou do not have enough money!"));
                            }
                        } else {
                            p.closeInventory();
                            FoodUtil.SpecialCookie.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR > &cYou don't have a cooldown to remove!"));
                        }
                    }
                }
                if (e.getSlot() == 11) {
                    if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
                        // Cookie Cooldown
                        if (NightVisionCommand.Cooldown.get(p) != 0) {
                            int time = NightVisionCommand.Cooldown.get(p);
                            if (VoltNetwork.getEconomy().getBalance(p) >= minNv) {
                                VoltNetwork.getEconomy().withdrawPlayer(p, minNv);
                                NightVisionCommand.Cooldown.replace(p, time, time - 1);
                                Cooldowns(p);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aNew Balance: $" + VoltNetwork.getEconomy().getBalance(p)));
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR > &cYou do not have enough money!"));
                            }
                        } else {
                            p.closeInventory();
                            NightVisionCommand.Cooldown.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR > &cYou don't have a cooldown to remove!"));
                        }
                    }
                }

            }
            // Right Click
            if (e.getClick().isRightClick()) {
                if (e.getSlot() == 10) {
                    if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
                        // Cookie Cooldown
                        if (FoodUtil.SpecialCookie.get(p) > 0) {
                            if (VoltNetwork.getEconomy().getBalance(p) >= clearCookie) {
                                VoltNetwork.getEconomy().withdrawPlayer(p, clearCookie);
                                FoodUtil.SpecialCookie.remove(p);
                                Cooldowns(p);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aNew Balance: $" + VoltNetwork.getEconomy().getBalance(p)));
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR > &cYou do not have enough money!"));
                            }
                        } else {
                            p.closeInventory();
                            FoodUtil.SpecialCookie.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR > &cYou don't have a cooldown to remove!"));
                        }
                    }
                }
                if (e.getSlot() == 11) {
                    if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
                        // Cookie Cooldown
                        if (NightVisionCommand.Cooldown.get(p) > 0) {
                            if (VoltNetwork.getEconomy().getBalance(p) >= clearNV) {
                                VoltNetwork.getEconomy().withdrawPlayer(p, clearNV);
                                NightVisionCommand.Cooldown.remove(p);
                                Cooldowns(p);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aNew Balance: $" + VoltNetwork.getEconomy().getBalance(p)));
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR > &cYou do not have enough money!"));
                            }
                        } else {
                            p.closeInventory();
                            NightVisionCommand.Cooldown.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR > &cYou don't have a cooldown to remove!"));
                        }
                    }
                }
            }
        }


    }

}
