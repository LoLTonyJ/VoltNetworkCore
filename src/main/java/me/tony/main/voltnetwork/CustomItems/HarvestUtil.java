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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class HarvestUtil implements Listener {

    public static ItemStack HarvestItem() {

        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lHarvest Hoe"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Harvests a 3x3 square of Farmland"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Makes the Farmers job, so much more easier!"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Action a = e.getAction();
        Location blockLoc = e.getClickedBlock().getLocation();

        if (e.getClickedBlock().getType().equals(Material.AIR)) return;
        if (e.getHand().equals(EquipmentSlot.OFF_HAND) || e.getHand() == null) return;
        if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        if (p.getInventory().getItemInMainHand().getItemMeta() == null) return;

        if (!e.isCancelled()) {
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&b&lHarvest Hoe"))
                    && p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_HOE)) {
                if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (e.getClickedBlock().getType().equals(Material.GRASS_BLOCK) || e.getClickedBlock().getType().equals(Material.DIRT)) {
                        int radius = 1;
                        for (int xMod = -radius; xMod <= radius; xMod++) {
                            for (int zMod = -radius; zMod <= radius; zMod++) {
                                Block broken = blockLoc.getBlock().getRelative(xMod, 0, zMod);
                                if (broken.getType().equals(Material.GRASS_BLOCK)) {
                                    broken.setType(Material.FARMLAND);
                                }
                                if (broken.getType().equals(Material.DIRT)) {
                                    broken.setType(Material.FARMLAND);
                                }
                                if (broken.getType().equals(Material.GRASS_BLOCK) && broken.getType().equals(Material.DIRT)) {
                                    broken.setType(Material.FARMLAND);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
