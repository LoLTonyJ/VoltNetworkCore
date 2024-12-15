package me.tony.main.voltnetwork.AntiCheat;


import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerReport {

    private static HashMap<UUID, List<String>> ReportStorage = new HashMap<>();

    private static String perm = VoltNetwork.getInstance().getConfig().getString("ac_permission");


    public static Inventory reportView(Player p, Player target) {

        Inventory inv = Bukkit.createInventory(p, 9, Chat.format("&cReports"));
        getReports(target, inv, p);

        p.openInventory(inv);
        return inv;
    }

    public static void getReports(Player target, Inventory inv, Player staff) {

        if (ReportStorage.containsKey(target.getUniqueId())) {

            List<String> reportList = ReportStorage.get(target.getUniqueId());

            for (int i = 0; i < 8 ; i++) {
                if (reportList.size() <= i) {
                    break;
                }
                ItemStack item = new ItemStack(Material.RED_WOOL);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Chat.format("&c" + target.getName()));
                ArrayList<String> lore = new ArrayList<>();
                lore.add(Chat.format("&b" + reportList.get(i)));
                meta.setLore(lore);
                item.setItemMeta(meta);
                inv.addItem(item);
            }
        }

    }

    public static void reportPlayer(Player target, Player victim, String reason) {
        if (target.getName().equals(victim.getName())) {
            victim.sendMessage(Chat.format("&cYou cannot report yourself!"));
        }
        if (!target.isOnline() || target == null) {
            victim.sendMessage(Chat.format("&cThat Player is not Online, or doesn't exist!"));
        }
        if (ReportStorage.containsKey(target.getUniqueId())) {
            List<String> stringList = ReportStorage.get(target.getUniqueId());
            stringList.add(Chat.getTime() + " " + reason);
            ReportStorage.put(target.getUniqueId(), stringList);
        } else {
            List<String> list = new ArrayList<>();
            list.add(Chat.getTime() + " " + reason);
            ReportStorage.put(target.getUniqueId(), list);
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(perm) && !ACUtil.AlertToggle.contains(p.getUniqueId())) {
                p.sendMessage(Chat.format("&c&l&oREPORT > &7" + victim.getName() + " has reported " + target.getName() + " Reason: " + reason));
            } else if (p.hasPermission(perm) && ACUtil.AlertToggle.contains(p.getUniqueId())) {
                p.sendMessage(Chat.format("&cVNA Tried to Inform you about Player Behavior! You have Alerts off!"));
            }
        }

    }
}
