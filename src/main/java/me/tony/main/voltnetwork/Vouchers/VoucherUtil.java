package me.tony.main.voltnetwork.Vouchers;

import me.tony.main.voltnetwork.VoltNetwork;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class VoucherUtil implements Listener {

    @EventHandler
    public void VoucherClick(PlayerInteractEvent e) {

        ItemStack item = e.getItem();
        Economy balance = VoltNetwork.getEconomy();
        if (item != null && item.getType() == Material.PAPER && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            String displayName = meta.getDisplayName();
            String colorlessName = ChatColor.stripColor(displayName);

            if (colorlessName.matches("\\$\\d+ - \\$\\d+")) {
                String[] parts = colorlessName.replace("$", "").split(" - ");
                int min = Integer.parseInt(parts[0]);
                int max = Integer.parseInt(parts[1]);

                Random rand = new Random();
                int randNum = rand.nextInt((max - min) + 1) + min;

                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l&oVOUCHER >> &a+ $" + randNum));
                balance.depositPlayer(e.getPlayer(), randNum);
                item.setAmount(item.getAmount() - 1);
                e.setCancelled(true);
            }

        }


    }



}
