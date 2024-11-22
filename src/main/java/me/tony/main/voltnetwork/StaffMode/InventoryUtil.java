package me.tony.main.voltnetwork.StaffMode;

import me.tony.main.voltnetwork.BonusFood.CooldownUtil;
import me.tony.main.voltnetwork.BonusFood.FoodUtil;
import me.tony.main.voltnetwork.DonatorPerks.NightVisionCommand;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryUtil {

    public static void FillInventory(Material type, Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, new ItemStack(type));
            }
        }
    }

    protected static ItemStack createGUIItem(final Material material, final String title, final String lore, final String lore1, final String lore2, final String lore3) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore), ChatColor.translateAlternateColorCodes('&', lore1), ChatColor.translateAlternateColorCodes('&', lore2), ChatColor.translateAlternateColorCodes('&', lore3)));
        item.setItemMeta(meta);

        return item;
    }

    public static Inventory PlayerInfo(Player staff, Player p) {
        Inventory inv = Bukkit.createInventory(staff, 27, ChatColor.translateAlternateColorCodes('&', p.getName() + " &b's Information"));

        String worldName = p.getWorld().getName();
        Integer x = (int) p.getLocation().getX();
        Integer y = (int) p.getLocation().getY();
        Integer z = (int) p.getLocation().getZ();

        inv.setItem(0, createGUIItem(Material.COMPASS, ChatColor.translateAlternateColorCodes('&', "&7Teleport to Player"), " ", " ", " ", " "));
        inv.setItem(4, StaffUtil.victim(p));
        inv.setItem(9, createGUIItem(Material.STRING, ChatColor.translateAlternateColorCodes('&', "&7Spectate Player"), " ", " ", " ", " "));
        inv.setItem(13, createGUIItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&bRank "), " ", VoltNetwork.getChat().getPrimaryGroup(p),  ChatColor.translateAlternateColorCodes('&', VoltNetwork.getChat().getPlayerPrefix(p)), " "));
        inv.setItem(21, createGUIItem(Material.EMERALD, ChatColor.translateAlternateColorCodes('&', "&a&LBalance "), " ", ChatColor.translateAlternateColorCodes('&', "&a&l$ " + VoltNetwork.getEconomy().getBalance(p)), " ", " "));
        inv.setItem(22, createGUIItem(Material.OAK_SIGN, ChatColor.translateAlternateColorCodes('&', "&7Display NAME"), " ", ChatColor.translateAlternateColorCodes('&', p.getDisplayName()), " ", " "));
        inv.setItem(23, createGUIItem(Material.MAP, ChatColor.translateAlternateColorCodes('&', "&7Location"), "World > " + worldName, "X: " + x, "Y: " + y, "Z: " + z));

        FillInventory(Material.BLACK_STAINED_GLASS_PANE, inv);

        staff.openInventory(inv);

        return inv;
    }


    public static Inventory OnlinePlayers(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&a&lOnline Players"));
        int slot = 0;

        for (Player online : Bukkit.getOnlinePlayers()) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', online.getName()));
            meta.setOwningPlayer(online);
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Player Rank > " + VoltNetwork.getChat().getPlayerPrefix(online)));
            meta.setLore(lore);
            skull.setItemMeta(meta);
            inv.setItem(slot, skull);
            slot++;
        }

        p.openInventory(inv);


        return inv;
    }





}
