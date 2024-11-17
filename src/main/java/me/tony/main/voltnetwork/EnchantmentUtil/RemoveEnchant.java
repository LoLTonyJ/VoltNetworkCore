package me.tony.main.voltnetwork.EnchantmentUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RemoveEnchant {

    public static void LoreRemove(Player p, Integer i) {

        ItemStack item = p.getInventory().getItemInMainHand();
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        String error = VoltNetwork.getInstance().getConfig().getString("error_remove_enchantment");
        String perm = VoltNetwork.getInstance().getConfig().getString("administration_permission");

        // Basic Checks to reduce erros in Console.
        if (item.getItemMeta().getLore() == null) return;
        if (!item.hasItemMeta()) return;
        if (i == null) return;
        if (!p.hasPermission(perm)) return;

        if (i.equals(0) || i.equals(1)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR &cNot a valid enchantment to remove!"));
            return;
        }

        if (p.getInventory().getItemInMainHand().hasItemMeta()) {

            ItemMeta meta = item.getItemMeta();
            // Getting the existing Lore
            List<String> lore = p.getInventory().getItemInMainHand().getItemMeta().getLore();

            // Getting total Lore Size, checking to see if x is bigger than, or equal to #size().
            // Reason for doing = is due to the way ArrayList work.
            /*
            If the Array List size is 4, that means there is only 3 entries.
            Array List count from 0, for an array list of 4 entries, it'll count 0, 1, 2, 3
             */

            if (i >= lore.size()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l&oERROR &cNot a valid enchantment to remove!"));
                return;
            }
            // Getting Lore Entry (Integer) i
            String toRemove = lore.get(i);
            // Removing specified entry from list of Strings.
            lore.remove(toRemove);
            // Updating ItemMeta.
            meta.setLore(lore);
            item.setItemMeta(meta);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " You have removed " + toRemove));
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + error));
        }
    }
}
