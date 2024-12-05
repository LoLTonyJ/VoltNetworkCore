package me.tony.main.voltnetwork.EnchantmentUtil;

import me.tony.main.voltnetwork.Enchantments.Harvest;
import me.tony.main.voltnetwork.GeneralUtil.ChatUtil;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentAdd implements Listener {

    public static ArrayList<Player> HarvestI = new ArrayList<>();
    public static ArrayList<Player> HarvestII = new ArrayList<>();

    @EventHandler
    public void ToolClick(PlayerInteractEvent e) {

        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        String cantEnchant = VoltNetwork.getInstance().getConfig().getString("cannot_enchant");

        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        Action a = e.getAction();


        // Check if the player clicks a block, or air. In this case it doesn't matter.
        if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)) {
            // Checks to see if the player is in the list for enchanting.
            if (HarvestI.contains(p)) {
                // Loops through the List "Harvest" in Configuration File to check allowed Tools.
                for (String canEnchant : VoltNetwork.getInstance().getConfig().getStringList("Harvest")) {
                    if (p.getInventory().getItemInMainHand().getType().equals(Material.valueOf(canEnchant))) {
                        // Checks the item in the players hand for existing Lore.
                        if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                            // Checks for Harvest Enchantment.
                            if (p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Harvest Yield I") || p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Harvest Yield II")) {
                                p.sendMessage(ChatUtil.format(prefix + " &7You cannot enchant this item!"));
                                return;
                            }
                            // If the tool already has an existing Lore, this runs.
                            // Gets the tools lore, and adds onto it.
                            HarvestI.remove(p);
                            ItemStack i = p.getInventory().getItemInMainHand();
                            ItemMeta m = i.getItemMeta();
                            List<String> lore = i.getItemMeta().getLore();
                            lore.add(ChatColor.GRAY + "Harvest Yield I");
                            m.setLore(lore);
                            i.setItemMeta(m);
                            p.sendMessage(ChatUtil.format(prefix + " &7Enchanted your tool!"));
                        } else {
                            // If the tool doesn't have an existing Lore, it'll create a template.
                            HarvestI.remove(p);
                            AddLore.LoreTemplate(p, "Harvest Yield I");
                            p.sendMessage(ChatUtil.format(prefix + " &7Enchanted your tool!"));
                        }
                        for (ItemStack book : p.getInventory().getContents()) {
                            if (book == null) return;
                            if (book.equals(Harvest.item(1))) {
                                p.getInventory().remove(book);
                            }
                        }
                    }
                }
            }
            if (HarvestII.contains(p)) {
                for (String canEnchant : VoltNetwork.getInstance().getConfig().getStringList("Harvest")) {
                    if (p.getInventory().getItemInMainHand().getType().equals(Material.valueOf(canEnchant))) {
                        if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                            if (p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Harvest Yield I") || p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Harvest Yield II")) {
                                p.sendMessage(ChatUtil.format(prefix + " &7You cannot enchant this item!"));
                                return;
                            }
                            HarvestII.remove(p);
                            ItemStack i = p.getInventory().getItemInMainHand();
                            ItemMeta m = i.getItemMeta();
                            List<String> lore = i.getItemMeta().getLore();
                            lore.add(ChatColor.GRAY + "Harvest Yield II");
                            m.setLore(lore);
                            i.setItemMeta(m);
                            p.sendMessage(ChatUtil.format(prefix + " &7Enchanted your tool!"));
                        } else {
                            HarvestII.remove(p);
                            AddLore.LoreTemplate(p, "Harvest Yield II");
                            p.sendMessage(ChatUtil.format(prefix + " &7Enchanted your tool!"));
                        }
                        for (ItemStack book : p.getInventory().getContents()) {
                            if (book == null) return;
                            if (book.getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', "&7Harvest Yield 2"))) {
                                p.getInventory().remove(book);
                            }
                        }
                    }
                }
            }
        }

    }


    @EventHandler
    public void onClick(PlayerInteractEvent e) {


        // Identifiers.
        Player p = e.getPlayer();

        // Config Stuff
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        String applyEnchant = VoltNetwork.getInstance().getConfig().getString("apply_enchantment");
        String undoEnchant = VoltNetwork.getInstance().getConfig().getString("undo_enchantment");
        String errorEnchant = VoltNetwork.getInstance().getConfig().getString("error_enchant");


        // Basic checks to reduce errors in console.
        if (p.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
        if (!p.getInventory().getItemInMainHand().getType().equals(Material.ENCHANTED_BOOK)) return;


        // Checks to see if the player is clicking the custom book (Level 1)
        if (p.getInventory().getItemInMainHand().getType().equals(Material.ENCHANTED_BOOK)
                && p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Harvest Yield 1")) {



            // If the List contains the player, it will add them to a list for enchanting.
            if (!HarvestI.contains(p)) {
                HarvestI.add(p);
                p.sendMessage(ChatUtil.format(prefix + " " + applyEnchant));
            } else {
                // If the list ALREADY contains the player, it will remove the player from list for enchanting.
                HarvestI.remove(p);
                p.sendMessage(ChatUtil.format(prefix + " " + undoEnchant));
            }
            // Debugging.
            System.out.println("Clicked Lv 1");
        }

        if (p.getInventory().getItemInMainHand().getType().equals(Material.ENCHANTED_BOOK)
                && p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Harvest Yield 2")) {


            // Line 93
            if (!HarvestII.contains(p)) {
                HarvestII.add(p);
                p.sendMessage(ChatUtil.format(prefix + " " + applyEnchant));
            } else {
                // Line 99
                HarvestII.remove(p);
                p.sendMessage(ChatUtil.format(prefix + " " + undoEnchant));
            }
            System.out.println("Clicked Lv 2");
        }
    }
}
