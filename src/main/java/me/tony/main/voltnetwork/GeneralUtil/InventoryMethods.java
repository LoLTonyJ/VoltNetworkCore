package me.tony.main.voltnetwork.GeneralUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryMethods {


    public static void FillInventory(Material type, Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, new ItemStack(type));
            }
        }
    }

    public static ItemStack createGUIItem(final Material material, final String title, final String lore, final String lore1, final String lore2, final String lore3, final String lore4) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore), ChatColor.translateAlternateColorCodes('&', lore1), ChatColor.translateAlternateColorCodes('&', lore2), ChatColor.translateAlternateColorCodes('&', lore3), ChatColor.translateAlternateColorCodes('&', lore4)));
        item.setItemMeta(meta);

        return item;
    }




}
