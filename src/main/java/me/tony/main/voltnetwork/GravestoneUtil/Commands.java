package me.tony.main.voltnetwork.GravestoneUtil;

import eu.decentsoftware.holograms.api.DHAPI;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        Double CostToTP = VoltNetwork.getInstance().getConfig().getDouble("gravestone_tp_cost");
        Boolean GraveTP = VoltNetwork.getInstance().getConfig().getBoolean("gravestone_tp_enabled");

        if (GraveTP) {
            if (args.length == 1) {
                String subCommand = args[0];
                if (subCommand.equalsIgnoreCase("tp") || subCommand.equalsIgnoreCase("teleport")) {

                    int x;
                    int y;
                    int z;

                    if (Gravestones.XCoord.containsKey(p) &&
                            Gravestones.YCoord.containsKey(p) &&
                            Gravestones.ZCoord.containsKey(p)) {

                        x = Gravestones.XCoord.get(p);
                        y = Gravestones.YCoord.get(p);
                        z = Gravestones.ZCoord.get(p);

                        Location l = new Location(p.getWorld(), x, y, z);
                        if (VoltNetwork.getEconomy().getBalance(p) >= CostToTP) {
                            VoltNetwork.getEconomy().withdrawPlayer(p, CostToTP);
                            p.teleport(l);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have been teleported to your Gravestone."));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &c- $" + CostToTP));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You do not have enough Money to teleport to your Gravestone!"));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Here are your Gravestone Coords!"));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "X " + x));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Y " + y));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Z " + z));
                        }
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You do not have a Gravestone to teleport to!"));
                    }
                }
            }
        }


        if (p.hasPermission(VoltNetwork.getInstance().getConfig().getString("administration_permission"))) {

            // gravestone <remove> <player>

            if (args.length == 2) {
                String subCommand = args[0];
                Player target = Bukkit.getPlayer(args[1]);

                if (subCommand.equalsIgnoreCase("remove")) {
                    if (DHAPI.getHologram(target.getName()) != null) {
                        // Checks to see if any gravestone lists contain the player.
                        if (StoreItems.SaveInv.containsKey(target)) StoreItems.SaveInv.remove(target);
                        if (Gravestones.BlockLoc.containsKey(target)) Gravestones.BlockLoc.remove(target);
                        if (Gravestones.Gravestone.containsKey(target)) Gravestones.Gravestone.remove(target);

                        // Removes bugged Hologram
                        /*
                        Hologram could bug either because the Player didn't remove all the Items from the Gravestone then broke the block.
                        or Server lag.
                         */
                        DHAPI.removeHologram(target.getName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Removed Bugged Gravestone Hologram."));

                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7That Gravestone doesn't exist!"));
                    }
                }
            }
        }

        return true;
    }
}
