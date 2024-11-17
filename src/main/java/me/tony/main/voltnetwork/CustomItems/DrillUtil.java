package me.tony.main.voltnetwork.CustomItems;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class DrillUtil implements Listener {

    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMine(BlockBreakEvent e) {

        Player p = e.getPlayer();
        Block brokenBlock = e.getBlock();
        Location blockLoc = brokenBlock.getLocation();

        if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        if (e.getBlock().getType().equals(Material.AIR)) return;
        if (p.getInventory().getItemInMainHand().getItemMeta() == null || p.getInventory().getItemInMainHand().getType() == null) return;

        if (!e.isCancelled()) {
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&b&lDrill"))
                    && p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                int radius = 1;
                for (int xMod = -radius; xMod <= radius; xMod++) {
                    for (int zMod = -radius; zMod <= radius; zMod++) {
                        for (int yMod = -radius; yMod <= radius; yMod++) {
                            Block broken = blockLoc.getBlock().getRelative(xMod, yMod, zMod);
                            if (broken.getType().equals(Material.BEDROCK) ||
                            broken.getType().equals(Material.WATER) ||
                            broken.getType().equals(Material.LAVA)) {
                                return;
                            }
                            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                            item.setDurability((short) (e.getPlayer().getInventory().getItemInMainHand().getDurability() + 18));
                            broken.breakNaturally();
                        }
                    }
                }
            }
        }
    }

    public static ItemStack DrillItem() {

        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lDrill"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Mines out a 3x3x2 hole"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Makes a Miners job, that much easier!"));
        meta.setLore(lore);
        item.setItemMeta(meta);


        return item;
    }


}
