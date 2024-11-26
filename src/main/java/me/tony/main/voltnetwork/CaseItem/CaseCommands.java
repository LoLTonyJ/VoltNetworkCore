package me.tony.main.voltnetwork.CaseItem;

import me.tony.main.voltnetwork.CustomItems.DrillUtil;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class CaseCommands implements CommandExecutor {

    public static HashMap<ItemDisplay, Location> ItemDisplayList = new HashMap<>();
    public static ArrayList<Boolean> bHolder = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        String perm = VoltNetwork.getInstance().getConfig().getString("administration_permission");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        Player p = (Player) sender;

        if (p.hasPermission(perm)) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/display create <hand/itemstack> <rotation> <size>"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/display remove"));
            }
            if (args.length == 1) {
                String subCommand = args[0];
                if (subCommand.equalsIgnoreCase("remove") || subCommand.equalsIgnoreCase("delete")) {
                    Block b = p.getTargetBlock(null, 5);
                    Location bLoc = b.getLocation().add(0.5, 0.5, 0.5);

                    if (!b.getType().equals(Material.GLASS)) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Thats not a compatible Display Item Block!"));
                        return true;
                    }

                    if (ItemDisplayList.isEmpty()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7There are no ItemDisplays"));
                        return true;
                    }
                    for (Entity ent : ItemDisplayList.keySet()) {
                        if (ItemDisplayList.get(ent).equals(bLoc)) {
                            ent.remove();
                            ItemDisplayList.remove(ent);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Successfully removed " + ent));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7Thats not a removable ItemDisplay"));
                            break;
                        }
                    }

                }
            }

            if (args.length == 4) {
                String subCommand = args[0];
                String item = args[1];
                float rotation = Float.parseFloat(args[2]);
                float size = Float.parseFloat(args[3]);

                if (subCommand.equalsIgnoreCase("create")) {

                    if (size > 1F || rotation > 1F) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " Max Rotation; 1, Max Size; 1"));
                        return true;
                    }
                    if (size <= 0F || rotation <= 0F) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Min Rotation; 0, Min Size; 0"));
                        return true;
                    }

                    if (item.equalsIgnoreCase("hand")) {
                        Block b = p.getTargetBlock(null, 5);

                        for (Location l : ItemDisplayList.values()) {
                            if (b.getLocation() == l) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7That has already been defined as a Display Case."));
                            }
                        }

                        if (b.getType().equals(Material.AIR)) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You cannot set AIR as a Display Case!"));
                            return true;
                        }
                        Location bLoc = b.getLocation().add(0.5, 0.5, 0.5);
                        ItemStack i = p.getInventory().getItemInMainHand();
                        ItemDisplay dItem = p.getWorld().spawn(bLoc, ItemDisplay.class);
                        Transformation dItemTrans = dItem.getTransformation();
                        dItem.setItemStack(i);
                        if (i.hasItemMeta()) {
                            String dName = ChatColor.translateAlternateColorCodes('&', i.getItemMeta().getDisplayName());
                            dItem.setCustomName(dName);
                            dItem.setCustomNameVisible(true);
                        } else {
                            dItem.setCustomName(i.getType().toString().toLowerCase());
                            dItem.setCustomNameVisible(true);
                        }
                        dItemTrans.getLeftRotation().y = rotation;
                        dItemTrans.getScale().set(size);
                        dItem.setTransformation(dItemTrans);

                        ItemDisplayList.put(dItem, bLoc);

                    // Arg is not hand
                    } else {
                        ItemStack i = new ItemStack(Material.valueOf(item.toUpperCase()));
                        Block b = p.getTargetBlock(null, 5);

                        for (Location l : ItemDisplayList.values()) {
                            if (b.getLocation() == l) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7That has already been defined as a Display Case."));
                            }
                        }

                        if (b.getType().equals(Material.AIR) || !b.getType().equals(Material.GLASS)) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7That is not a compatible block!"));
                            return true;
                        }
                        Location bLoc = b.getLocation().add(0.5, 0.5, 0.5);
                        ItemDisplay dItem = p.getWorld().spawn(bLoc, ItemDisplay.class);
                        Transformation dItemTrans = dItem.getTransformation();
                        dItem.setItemStack(i);
                        if (i.hasItemMeta()) {
                            String dName = ChatColor.translateAlternateColorCodes('&', i.getItemMeta().getDisplayName());
                            dItem.setCustomName(dName);
                            dItem.setCustomNameVisible(true);
                        } else {
                            dItem.setCustomName(i.getType().toString().toLowerCase());
                            dItem.setCustomNameVisible(true);
                        }
                        dItemTrans.getLeftRotation().y = rotation;
                        dItemTrans.getScale().set(size);
                        dItem.setTransformation(dItemTrans);

                        ItemDisplayList.put(dItem, bLoc);
                    }
                }
            }
        }

        return true;
    }
}
