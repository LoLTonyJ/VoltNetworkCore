package me.tony.main.voltnetwork.DonatorPerks;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class VouchItems {

    public static ItemStack NightVisionVouch() {

        String vouchName = VoltNetwork.getInstance().getConfig().getString("night_vision_item_name");
        List<String> vouchLore = VoltNetwork.getInstance().getConfig().getStringList("night_vision_item_lore");
        String vouchType = VoltNetwork.getInstance().getConfig().getString("night_vision_item_type");

        ItemStack item = new ItemStack(Material.valueOf(vouchType));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', vouchName));
        ArrayList<String> lore = new ArrayList<>();
        for (String list : vouchLore) {
            if (list != null) {
                lore.add(ChatColor.translateAlternateColorCodes('&', list));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);


        return item;

    }


}
