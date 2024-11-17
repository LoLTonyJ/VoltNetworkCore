package me.tony.main.voltnetwork.GravestoneUtil;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.clip.placeholderapi.PlaceholderAPI;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StoreItems {

    public static HashMap<Player, ItemStack[]> SaveInv = new HashMap<>();
    public static HashMap<Player, ItemStack[]> OldInv = new HashMap<>();

    public static Inventory DeathInv(Player p) {

        Inventory pInv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getDisplayName() + "'s Gravestone"));
        for (ItemStack item : SaveInv.get(p)) {
            if (item != null) {
                pInv.addItem(item);
            }
        }

        p.openInventory(pInv);

        return pInv;

    }

    public static void Hologram(Player p, Location loc) {

        List<String> GraveStoneList = VoltNetwork.getInstance().getConfig().getStringList("gravestone_message");


        if (p.getName().contains(".")) {
            String replaceName = p.getName().replace(".", " ");
            DHAPI.createHologram(replaceName, loc, Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&7" + p.getName() + "'s Gravestone")));
            if (DHAPI.getHologram(replaceName) != null) {
                for (String i : GraveStoneList) {
                    DHAPI.addHologramLine(Hologram.getCachedHologram(replaceName), i);
                }
            }
        } else {
            DHAPI.createHologram(p.getName(), loc, Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&7" + p.getName() + "'s Gravestone")));
            if (DHAPI.getHologram(p.getName()) != null) {
                for (String i : GraveStoneList) {
                    DHAPI.addHologramLine(Hologram.getCachedHologram(p.getName()), i);
                }
            }
        }
    }
    public static void StoreOnDeath(Player p) {
        if (SaveInv.containsKey(p)) {
            OldInv.put(p, SaveInv.get(p));
            SaveInv.remove(p);
            SaveInv.put(p, OldInv.get(p));
            OldInv.remove(p);
        }
        SaveInv.put(p, p.getInventory().getContents());
    }
}
