package me.tony.main.voltnetwork.CustomItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TPBowUtil implements Listener {

    public static HashMap<UUID, Player> ArrowLaunched = new HashMap<UUID, Player>();
    public static HashMap<UUID, ItemStack[]> BowStorage = new HashMap<>();

    public static ItemStack tpBow() {

        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bTeleport Bow"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Left-Click) to open storage!"));
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Teleports you to the arrows landing area!"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }

    @EventHandler
    public void ArrowLand(ProjectileHitEvent e) {

        Entity ent = e.getEntity();
        Block b = ent.getLocation().getBlock();
        Location arrowLoc = ent.getLocation();
        Location bLoc = b.getLocation().add(0, 1, 0);
        Location headLevel = b.getLocation().add(0, 2, 0);

        if (b.getType().equals(Material.WATER) || b.getType().equals(Material.LAVA)) {
            if (ArrowLaunched.containsValue((Player) e.getEntity().getShooter())) {
                ArrowLaunched.remove(ent.getUniqueId());
            }
        }
        if (bLoc.getBlock().getType().equals(Material.WATER) || bLoc.getBlock().getType().equals(Material.LAVA)) {
            if (ArrowLaunched.containsValue((Player) e.getEntity().getShooter())) {
                ArrowLaunched.remove(ent.getUniqueId());
            }
        }

        if (!(ent instanceof Arrow)) return;
        if (!headLevel.getBlock().getType().equals(Material.AIR)) return;
        if (!ArrowLaunched.containsKey(ent.getUniqueId())) return;

        Player p = ArrowLaunched.get(ent.getUniqueId());
        ent.remove();
        p.teleport(arrowLoc);
        arrowLoc.setYaw(ent.getLocation().getYaw());
        arrowLoc.setPitch(ent.getLocation().getPitch());
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Swoosh!"));
        p.getWorld().spawnParticle(org.bukkit.Particle.PORTAL, arrowLoc, 50);
        p.getWorld().playSound(arrowLoc, org.bukkit.Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        ArrowLaunched.remove(ent.getUniqueId());

        System.out.println("Landed");
    }

    @EventHandler public void ShootArrow(ProjectileLaunchEvent e) {
        Player p = (Player) e.getEntity().getShooter();
        ItemStack i = p.getInventory().getItemInMainHand();
        Entity ent = e.getEntity();
        if (!i.getType().equals(Material.BOW)) return;
        if (!i.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bTeleport Bow"))) return;
        if (!BowStorage.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have ammo!"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Left-Click your bow to view your storage"));
            return;
        }

        for (Player player : ArrowLaunched.values()) {
            if (player.equals(p)) return;
        }

        for (ItemStack ammo : BowStorage.get(p.getUniqueId())) {
            if (ammo != null && ammo.getType().equals(Material.ENDER_PEARL)) {
                if (ammo.getAmount() >= 1) {
                    ammo.setAmount(ammo.getAmount() -1);
                    ArrowLaunched.put(ent.getUniqueId(), p);
                    System.out.println("Launched");
                    break;
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lNot enough Ammo!"));
                }
            }
        }

    }


    @EventHandler
    public void StorageOpen(PlayerInteractEvent e) {
        ItemStack i = e.getPlayer().getInventory().getItemInMainHand();
        Action a = e.getAction();
        Player p = e.getPlayer();


        if (a.equals(Action.LEFT_CLICK_BLOCK) || a.equals(Action.LEFT_CLICK_AIR)) {
            if (!i.getType().equals(Material.BOW)) return;
            if (!e.getItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bTeleport Bow"))) return;
            TPBowStorage(p);
        }

    }

    @EventHandler
    public void InventoryEdit(InventoryCloseEvent e) {

        Player p = (Player) e.getPlayer();

        if (!e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&bTeleport Bow Storage"))) return;

        if (!BowStorage.containsKey(p.getUniqueId())) {
            for (ItemStack i : e.getInventory().getContents()) {
                if (i != null) {
                    if (!i.getType().equals(Material.ENDER_PEARL)) {
                        p.getInventory().addItem(i);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7" + i.getType().toString().toLowerCase() + " is not an enderpearl!"));
                    }
                    BowStorage.put(p.getUniqueId(), e.getInventory().getContents());
                }
            }
        }

        BowStorage.replace(p.getUniqueId(), BowStorage.get(p.getUniqueId()), e.getInventory().getContents());

    }

    public static Inventory TPBowStorage(Player p) {

        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&bTeleport Bow Storage"));

        if (BowStorage.containsKey(p.getUniqueId())) {
            for (ItemStack item : BowStorage.get(p.getUniqueId())) {
                if (item != null && item.getType().equals(Material.ENDER_PEARL)) {
                    inv.addItem(item);
                }
            }
        }

        p.openInventory(inv);

        return inv;
    }



}
