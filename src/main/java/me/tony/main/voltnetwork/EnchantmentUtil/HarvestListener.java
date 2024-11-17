package me.tony.main.voltnetwork.EnchantmentUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class HarvestListener implements Listener {

    public static int DropAmount(int min, int max) {
        Random DropAmount = new Random();
        return DropAmount.nextInt((max - min) + 1) + min;
    }

    Integer Lv1Min = VoltNetwork.getInstance().getConfig().getInt("harvest_yield_lv1_min");
    Integer Lv1Max = VoltNetwork.getInstance().getConfig().getInt("harvest_yield_lv1_max");

    Integer Lv2Min = VoltNetwork.getInstance().getConfig().getInt("harvest_yield_lv2_min");
    Integer Lv2Max = VoltNetwork.getInstance().getConfig().getInt("harvest_yield_lv2_max");

    @EventHandler
    public void onHarvest(BlockBreakEvent e) {
        Block b = e.getBlock();
        BlockData bData = b.getBlockData();
        Player p = e.getPlayer();

        if (!p.getInventory().getItemInMainHand().hasItemMeta()) return;
        if (!p.getInventory().getItemInMainHand().getItemMeta().hasLore()) return;

        // Checks to see if the wheat is fully grown.
        if (bData instanceof Ageable) {
            Ageable age = (Ageable) bData;
            if (age.getAge() == age.getMaximumAge()) {
                // Checks for existing lore.
                if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                    // Checks Lore to see if Custom Enchant is present.
                    if (p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Harvest Yield I")) {

                        int num = DropAmount(Lv1Min, Lv1Max);

                        b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.valueOf(b.getType().toString()), num));
                    }

                    if (p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Harvest Yield II")) {

                        int num = DropAmount(Lv2Min, Lv2Max);

                        b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.valueOf(b.getType().toString()), num));
                    }
                }
            }
        }
    }
}
