package me.tony.main.voltnetwork.DonatorPerks;

import me.tony.main.voltnetwork.GeneralUtil.ChatUtil;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


public class DonatorUtil implements Listener {

    public static void Cooldown() {

        String prefix = VoltNetwork.getInstance().getConfig().getString("donator_prefix");

        new BukkitRunnable() {
            @Override
            public void run() {

                if (NightVisionCommand.Cooldown.isEmpty()) return;

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (NightVisionCommand.Cooldown.containsKey(p)) {
                        int cd = NightVisionCommand.Cooldown.get(p);

                        if (cd == 0) {
                            NightVisionCommand.Cooldown.remove(p);
                            p.sendMessage(ChatUtil.format(prefix + " &7Night Vision is off Cooldown!"));
                        }
                        if (cd > 0) {
                            NightVisionCommand.Cooldown.remove(p);
                            int replace = cd - 1;
                            NightVisionCommand.Cooldown.put(p, replace);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(VoltNetwork.getInstance(), 20, 1200);

    }

    @EventHandler
    public void ClaimVoucher(PlayerInteractEvent e) {

        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        String nvType = VoltNetwork.getInstance().getConfig().getString("night_vision_item_type");
        String nvDisplay = VoltNetwork.getInstance().getConfig().getString("night_vision_item_name");
        String nvPerm = VoltNetwork.getInstance().getConfig().getString("night_vision_permission");

        Player p = e.getPlayer();
        ItemStack heldItem = p.getInventory().getItemInMainHand();
        Action a = e.getAction();

        if (heldItem == null || (heldItem.getType().equals(Material.AIR))) return;
        if (a.equals(Action.LEFT_CLICK_AIR) || a.equals(Action.LEFT_CLICK_BLOCK)) return;


        // Night Vision Listener
        if (heldItem.getType().equals(Material.valueOf(nvType)) && heldItem.getItemMeta() != null) {
            if (heldItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', nvDisplay))) {
                if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)) {
                    if (!DonatorCommands.NVPlayers.contains(p.getName())) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user" + p + " permission set " + nvPerm + " true");
                        p.getInventory().remove(e.getItem());
                        DonatorCommands.NVPlayers.add(p.getName());
                        DonatorFileManagement.getInstance().StoreData();
                        p.sendMessage(ChatUtil.format(prefix + " &7You have been granted permission for Night Vision!"));
                        p.sendMessage(ChatUtil.format("&7To Enable Night Vision, please do /nv"));
                    } else {
                        p.sendMessage(ChatUtil.format(prefix + " &cYou already claimed this Voucher!"));
                    }
                }
            }
        }


    }


}
