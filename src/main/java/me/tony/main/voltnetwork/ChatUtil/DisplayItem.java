package me.tony.main.voltnetwork.ChatUtil;

import io.dynamicstudios.json.DynamicJText;
import io.dynamicstudios.json.data.component.IComponent;
import io.dynamicstudios.json.data.util.CColor;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class DisplayItem implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String permission = VoltNetwork.getInstance().getConfig().getString("display_item_permission");
        Boolean requirePerm = VoltNetwork.getInstance().getConfig().getBoolean("display_item_require_permission");

        String output = null;

        Player p = (Player) sender;

        if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return true;

        if (requirePerm) {
            if (p.hasPermission(permission)) {
                if (p.getInventory().getItemInMainHand().hasItemMeta()) {

                    ItemStack item = p.getInventory().getItemInMainHand();
                    ItemMeta m = item.getItemMeta();
                    if (m.getDisplayName() != null) {
                        output = m.getDisplayName();
                    }

                    DynamicJText text = new DynamicJText(p.getDisplayName());
                    text.add(": ");
                    text.add(output);
                    if (m.getLore() != null) {
                        text.hover(m.getLore().toArray(new String[m.getLore().size()]));
                    } else {
                        text.hover("No Lore");
                    }
                    text.add(" ");
                    text.color(CColor.fromName("gray"));
                    if (EnchantmentUtil.getEnchantments(p) != null) {
                        text.add("[Enchantments]");
                        text.hover(EnchantmentUtil.getEnchantments(p));
                    }

                    for (Player online : Bukkit.getOnlinePlayers()) {
                        text.send(online);
                    }

                }

            }
        } else {
            if (p.getInventory().getItemInMainHand().hasItemMeta()) {

                ItemStack item = p.getInventory().getItemInMainHand();
                ItemMeta m = item.getItemMeta();
                if (m.getDisplayName() != null) {
                    output = m.getDisplayName();
                }

                DynamicJText text = new DynamicJText(p.getDisplayName());
                text.add(": ");
                text.add(output);
                text.hover(m.getLore().toArray(new String[m.getLore().size()]));
                text.add(" ");
                text.color(CColor.fromName("gray"));
                if (EnchantmentUtil.getEnchantments(p) != null) {
                    text.add("[Enchantments]");
                    text.hover(EnchantmentUtil.getEnchantments(p));
                }

                for (Player online : Bukkit.getOnlinePlayers()) {
                    text.send(online);
                }

            }
        }


        return true;
    }
}
