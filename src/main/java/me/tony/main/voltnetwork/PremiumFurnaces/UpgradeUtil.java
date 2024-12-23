package me.tony.main.voltnetwork.PremiumFurnaces;

import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.GeneralUtil.InventoryMethods;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class UpgradeUtil implements Listener {

    public static Inventory FurnacePurchase(Player p) {

        Integer cost = VoltNetwork.getInstance().getConfig().getInt("cost_per_tier");
        String currency = VoltNetwork.getInstance().getConfig().getString("currency");

        String symbol = "";
        if (currency.equalsIgnoreCase("economy")) {
            symbol = "$";
        }
        if (currency.equalsIgnoreCase("experience")) {
            symbol = "XP";
        }

        Inventory inv = Bukkit.createInventory(p, 27, Chat.format("&bFurnace Shop"));
        inv.setItem(11, InventoryMethods.createGUIItem(Material.FURNACE, "&cTier 1 Furnace", "", "&bClick to Purchase", "&aCost: " + symbol + " " + cost, " ", "&9 50% Faster"));
        inv.setItem(12, InventoryMethods.createGUIItem(Material.FURNACE, "&cTier 2 Furnace", "", "&bClick to Purchase", "&aCost: " + symbol + " " + cost * 2, " ", "&970% Faster"));
        inv.setItem(13, InventoryMethods.createGUIItem(Material.FURNACE, "&cTier 3 Furnace", "", "&bClick to Purchase", "&aCost: " + symbol + " " + cost * 3, " ", "&990% Faster"));
        inv.setItem(14, InventoryMethods.createGUIItem(Material.FURNACE, "&cTier 4 Furnace", "", "&bClick to Purchase", "&aCost: " + symbol + " " + cost * 4, " ", "&9110% Faster"));
        inv.setItem(15, InventoryMethods.createGUIItem(Material.FURNACE, "&cTier 5 Furnace", "", "&bClick to Purchase", "&aCost: " + symbol + " " + cost * 5, " ", "&9200% Faster"));

        InventoryMethods.FillInventory(Material.BLACK_STAINED_GLASS_PANE, inv);

        p.openInventory(inv);
        return inv;
    }
}
