package me.tony.main.voltnetwork.Vouchers;

import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VoucherCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        String adminPerm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");


        Player p = (Player) sender;

        if (p.hasPermission(adminPerm)) {
            // vouch <give/create> <min> <max>
            if (args.length == 3) {
                String subCommand = args[0];
                int min = 0;
                int max = 0;
                try {
                    min = Integer.parseInt(args[1]);
                    max = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    p.sendMessage(Chat.format(prefix + " &7Both Min and Max must be Integers"));
                    p.sendMessage(Chat.format(prefix + " &7Usage /vouch create <min> <max>"));
                }

                if (min > max || min == max) {
                    p.sendMessage(Chat.format(prefix + " Min must be lower than Max!"));
                    return true;
                }

                VoucherGive(p, min, max);
            }
        }
        return true;
    }


    public static void VoucherGive(Player p, Integer min, Integer max) {
        ItemStack voucher = new ItemStack(Material.PAPER);
        ItemMeta meta = voucher.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(Chat.format("&7(Right-Click) to claim your voucher!"));
        lore.add(" ");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l$" + min + " - $" + max));
        voucher.setItemMeta(meta);

        p.getInventory().addItem(voucher);
    }
}