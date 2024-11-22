package me.tony.main.voltnetwork.CustomBoss;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class BossInventoryUtil implements Listener {

    public static ArrayList<ItemStack> TopRewards = new ArrayList<>();
    public static ArrayList<ItemStack> SecondRewards = new ArrayList<>();
    public static ArrayList<ItemStack> ThirdRewards = new ArrayList<>();

    public static void FillInventory(Material type, Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, new ItemStack(type));
            }
        }
    }

    protected static ItemStack createGUIItem(final Material material, final String title, final String lore, final String lore1) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore), ChatColor.translateAlternateColorCodes('&', lore1)));
        item.setItemMeta(meta);

        return item;
    }


    public static Inventory RewardEdit(Player p) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        Inventory inv = Bukkit.createInventory(p, 27, ChatColor.translateAlternateColorCodes('&', bossName + " &7RewardHub GUI"));

        inv.setItem(11, createGUIItem(Material.GOLD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&a&l1st Place Rewards"), " ", ChatColor.translateAlternateColorCodes('&', "&7(Left-Click) to edit rewards!")));
        inv.setItem(13, createGUIItem(Material.IRON_BLOCK, ChatColor.translateAlternateColorCodes('&', "&7&l2nd Place Rewards"), " ", ChatColor.translateAlternateColorCodes('&', "&7(Left-Click) to edit rewards!")));
        inv.setItem(15, createGUIItem(Material.BROWN_CONCRETE, ChatColor.translateAlternateColorCodes('&', "&6&l3rd Place Rewards"), " ", ChatColor.translateAlternateColorCodes('&', "&7(Left-Click) to edit rewards!")));

        FillInventory(Material.BLACK_STAINED_GLASS_PANE, inv);

        p.openInventory(inv);
        return inv;
    }

    public static Inventory ThirdPlace(Player p) {

        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&6&l3rd Place Rewards"));

        if (ThirdRewards.isEmpty()) {
            p.openInventory(inv);
        } else {
            for (ItemStack i : ThirdRewards) {
                if (i != null) {
                    inv.addItem(i);
                }
            }
            p.openInventory(inv);
        }

        return inv;
    }

    public static Inventory SecondPlace(Player p) {

        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&7&l2nd Place Rewards"));

        if (SecondRewards.isEmpty()) {
            p.openInventory(inv);
        } else {
            for (ItemStack i : SecondRewards) {
                if (i != null) {
                    inv.addItem(i);
                }
            }
            p.openInventory(inv);
        }

        return inv;
    }

    public static Inventory FirstPlace(Player p) {

        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&a&l1st Place Rewards"));

        if (TopRewards.isEmpty()) {
            System.out.println("Empty List");
            p.openInventory(inv);
        } else {
            for (ItemStack i : TopRewards) {
                if (i != null) {
                    inv.addItem(i);
                }
            }
            p.openInventory(inv);
        }

        return inv;
    }

    @EventHandler
    public static void onClose(InventoryCloseEvent e) {

        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&a&l1st Place Rewards"))) {
            for (ItemStack i : e.getInventory().getContents()) {
                if (i != null) {
                    if (TopRewards.contains(i)) return;
                    TopRewards.add(i);
                }
            }
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Saved First Place Rewards."));
            BossFileManager.getInstance().SaveData();
        }
        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&7&l2nd Place Rewards"))) {
            for (ItemStack i : e.getInventory().getContents()) {
                if (i != null) {
                    if (SecondRewards.contains(i)) return;
                    SecondRewards.add(i);
                }
            }
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Saved Second Place Rewards."));
            BossFileManager.getInstance().SaveData();
        }
        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&6&l3rd Place Rewards"))) {
            for (ItemStack i : e.getInventory().getContents()) {
                if (i != null) {
                    if (ThirdRewards.contains(i)) return;
                    ThirdRewards.add(i);
                }
            }
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Saved Third Place Rewards."));
            BossFileManager.getInstance().SaveData();
        }

    }

    @EventHandler
    public static void onEdit(InventoryClickEvent e) {

        String bossName = VoltNetwork.getInstance().getConfig().getString("boss_name");

        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', bossName + " &7RewardHub GUI"))) {
            e.setCancelled(true);
            if (e.getSlot() == 11) {
                FirstPlace((Player) e.getWhoClicked());
            }
            if (e.getSlot() == 13) {
                SecondPlace((Player) e.getWhoClicked());
            }
            if (e.getSlot() == 15) {
                ThirdPlace((Player) e.getWhoClicked());
            }

        }
    }
}
