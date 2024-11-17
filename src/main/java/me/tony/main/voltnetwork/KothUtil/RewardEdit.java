package me.tony.main.voltnetwork.KothUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class RewardEdit implements Listener {

    public static ArrayList<ItemStack> rewards = new ArrayList<ItemStack>();

    String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

    public static Inventory RewardEditInv(Player p) {

        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&4Koth Rewards"));
        if (!rewards.isEmpty()) {
            for (ItemStack item : rewards) {
                if (item != null) {
                    inv.addItem(item);
                }
            }
        }
        p.openInventory(inv);

        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClick().isLeftClick()) {
            if (rewards.contains(e.getCurrentItem())) {
                rewards.remove(e.getCurrentItem());
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {

        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&4Koth Rewards"))) {
            if (e.getInventory().getContents() != null) {
                for (ItemStack invItems : e.getInventory().getContents()) {
                    if (!rewards.contains(invItems)) {
                        rewards.add(invItems);
                    }
                }
            } else {
                System.out.println(Arrays.toString(e.getInventory().getContents()));
            }
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Updated Koth Rewards"));
            KothFileManager.getInstance().StoreData();
        }
    }
}
