package me.tony.main.voltnetwork.Experience;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class ExperienceGUIUtil {

    public static HashMap<Player, Integer> xpChosen = new HashMap<>();

    public static void FillInventory(Material type, Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, new ItemStack(type));
            }
        }
    }

    protected static ItemStack createGUIItem(final Material material, final String title, final String lore, final String lore1, String lore2) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore), ChatColor.translateAlternateColorCodes('&', lore1), ChatColor.translateAlternateColorCodes('&', lore1)));
        item.setItemMeta(meta);

        return item;
    }


    public static Inventory MainGUI(Player p) {

        Integer costPerXP = VoltNetwork.getInstance().getConfig().getInt("price_per_experience");

        Inventory inv = Bukkit.createInventory(p, 27, ChatColor.translateAlternateColorCodes('&', "&a&lExperience Shop"));
        inv.setItem(10, createGUIItem(Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&a25 Experience Levels"), ChatColor.translateAlternateColorCodes('&', "&a$" + costPerXP * 25),  ChatColor.translateAlternateColorCodes('&', "&7(Right-Click) To purchase"), " "));
        inv.setItem(11, createGUIItem(Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&a50 Experience Levels"), ChatColor.translateAlternateColorCodes('&', "&a$" + costPerXP * 50),  ChatColor.translateAlternateColorCodes('&', "&7(Right-Click) To purchase"), " "));
        inv.setItem(12, createGUIItem(Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&a75 Experience Levels"), ChatColor.translateAlternateColorCodes('&', "&a$" + costPerXP * 75),  ChatColor.translateAlternateColorCodes('&', "&7(Right-Click) To purchase"), " "));
        inv.setItem(13, createGUIItem(Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&a100 Experience Levels"), ChatColor.translateAlternateColorCodes('&', "&a$" + costPerXP * 100),  ChatColor.translateAlternateColorCodes('&', "&7(Right-Click) To purchase"), " "));
        inv.setItem(16, createGUIItem(Material.OAK_SIGN, ChatColor.translateAlternateColorCodes('&', "&aCustom Amount"), "&7Click to Set!", " ", " "));

        inv.setItem(18, createGUIItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&c&lClose Menu"), " ", " ", " "));
        inv.setItem(26, createGUIItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&a&lSell Mode"), " ", " ", " "));

        FillInventory(Material.BLACK_STAINED_GLASS_PANE, inv);

        p.openInventory(inv);

        return inv;
    }

    public static Inventory SellGUI(Player p) {

        Integer costPerSell = VoltNetwork.getInstance().getConfig().getInt("price_per_sell");

        Inventory inv = Bukkit.createInventory(p, 27, ChatColor.translateAlternateColorCodes('&', "&a&lExperience Sell"));
        inv.setItem(10, createGUIItem(Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&a25 Experience Levels"), ChatColor.translateAlternateColorCodes('&', "&a$" + costPerSell * 25),  ChatColor.translateAlternateColorCodes('&', "&7(Right-Click) To sell"), " "));
        inv.setItem(11, createGUIItem(Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&a50 Experience Levels"), ChatColor.translateAlternateColorCodes('&', "&a$" + costPerSell * 50),  ChatColor.translateAlternateColorCodes('&', "&7(Right-Click) To sell"), " "));
        inv.setItem(12, createGUIItem(Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&a75 Experience Levels"), ChatColor.translateAlternateColorCodes('&', "&a$" + costPerSell * 75),  ChatColor.translateAlternateColorCodes('&', "&7(Right-Click) To sell"), " "));
        inv.setItem(13, createGUIItem(Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&a100 Experience Levels"), ChatColor.translateAlternateColorCodes('&', "&a$" + costPerSell * 100),  ChatColor.translateAlternateColorCodes('&', "&7(Right-Click) To sell"), " "));
        inv.setItem(16, createGUIItem(Material.OAK_SIGN, ChatColor.translateAlternateColorCodes('&', "&aCustom Amount"), "&7Click to Set!", " ", " "));

        inv.setItem(18, createGUIItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&c&lClose Menu"), " ", " ", " "));
        inv.setItem(26, createGUIItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&a&lBuy Mode"), " ", " ", " "));

        FillInventory(Material.BLACK_STAINED_GLASS_PANE, inv);

        p.openInventory(inv);
        return inv;
    }


}
