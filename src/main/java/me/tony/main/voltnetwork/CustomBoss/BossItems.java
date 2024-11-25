package me.tony.main.voltnetwork.CustomBoss;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BossItems {

    public static ItemStack bossPearl() {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        ItemStack i = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5Boss Heart Shard"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Used to spawn the " + bossName));
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Place 8 Boss Heart Shards in the Alter to instantly spawn the boss!"));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        i.setItemMeta(meta);
        return i;


    }





}
