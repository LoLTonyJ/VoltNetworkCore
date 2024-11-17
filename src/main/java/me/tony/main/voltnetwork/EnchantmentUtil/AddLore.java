package me.tony.main.voltnetwork.EnchantmentUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddLore {

    public static void LoreTemplate(Player p, String ench) {

        String enchantSeperate = VoltNetwork.getInstance().getConfig().getString("custom_enchant_separator");


        ItemStack i = p.getInventory().getItemInMainHand();
        ItemMeta m = i.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', enchantSeperate));
        lore.add(ChatColor.GRAY + ench);
        m.setLore(lore);
        i.setItemMeta(m);
    }
}
