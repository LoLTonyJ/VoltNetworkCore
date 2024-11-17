package me.tony.main.voltnetwork.Enchantments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Harvest implements Listener {


    public static ItemStack item(int level) {

        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Harvest Yield " + level));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
