package me.tony.main.voltnetwork.GravestoneUtil;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Gravestones implements Listener {

    Boolean sendTitle = VoltNetwork.getInstance().getConfig().getBoolean("sendtitle_gravestone_coords");
    String titleHeader = VoltNetwork.getInstance().getConfig().getString("sendtitle_header");
    String titleFooter = VoltNetwork.getInstance().getConfig().getString("sendtitle_footer");
    Boolean sendMessage = VoltNetwork.getInstance().getConfig().getBoolean("sendmessage_gravestone_coords");
    String message = VoltNetwork.getInstance().getConfig().getString("gravestone_coords_message");
    Boolean pvpGraveStone = VoltNetwork.getInstance().getConfig().getBoolean("drop_gravestone_inpvp");

    public static HashMap<Player, Double> BlockLoc = new HashMap<>();
    public static HashMap<Player, Location> Gravestone = new HashMap<>();

    public static ArrayList<ItemStack> itemSave = new ArrayList<>();

    public static HashMap<Player, ArrayList<ItemStack>> SaveGrave = new HashMap<>();


    public static HashMap<Player, Integer> XCoord = new HashMap<>();
    public static HashMap<Player, Integer> YCoord = new HashMap<>();
    public static HashMap<Player, Integer> ZCoord = new HashMap<>();


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Player p = e.getEntity().getPlayer();
        Location pLoc = p.getLocation();
        String GraveBlock = VoltNetwork.getInstance().getConfig().getString("block_type");
        Boolean graveEnable = VoltNetwork.getInstance().getConfig().getBoolean("gravestone_enable");

        // World Guard Coolness
        LocalPlayer lp = WorldGuardPlugin.inst().wrapPlayer(p);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        if (!graveEnable) return;

        // Checks if the Region they died in has the flag "PVP" enabled.
        if (query.testState(lp.getLocation(), lp, Flags.PVP)) {
            // Checks if "drop_gravestone_inpvp" is set to false
            if (!pvpGraveStone) {
                return;
            }
        }


        if (p.getInventory().getContents() != null) {

            int x = (int) p.getLocation().getX();
            int y = (int) p.getLocation().getY();
            int z = (int) p.getLocation().getZ();

            XCoord.put(p, x);
            YCoord.put(p, y);
            ZCoord.put(p, z);

            // Clears Drops.
            e.getDrops().clear();

            // Storing Location of Player Death
            if (Gravestone.containsKey(p)) {
                Location oldLoc = Gravestone.get(p);
                oldLoc.getBlock().setType(Material.AIR);
                Gravestone.remove(p);
                Gravestone.put(p, pLoc);
            }
            Gravestone.put(p, pLoc);

            // Storing Players Inventory on Death.
            StoreItems.StoreOnDeath(p);

            // Getting the block at the location the player died at.
            pLoc.getBlock().setType(Material.valueOf(GraveBlock));

            // Storing the block location into a map.
            if (BlockLoc.containsKey(p)) {
                BlockLoc.remove(p);
                BlockLoc.put(p, pLoc.getBlock().getLocation().getX());
            }
            BlockLoc.put(p, pLoc.getBlock().getLocation().getX());

            // Spawning a Hologram.
            if (p.getName().contains(".")) {
                String replaceName = p.getName().replace(".", "");
                if (DHAPI.getHologram(replaceName) == null) {
                    StoreItems.Hologram(p, Gravestone.get(p).add(0, 3, 0));
                } else {
                    DHAPI.getHologram(replaceName).setLocation(pLoc.add(0, 3, 0));
                }
            } else {
            if (DHAPI.getHologram(p.getName()) == null) {
                StoreItems.Hologram(p, Gravestone.get(p).add(0, 3, 0));
            } else {
                DHAPI.getHologram(p.getName()).setLocation(pLoc.add(0, 3, 0));
            }
        }
            }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Inventory i = e.getInventory();
        Player p = (Player) e.getPlayer();

        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getDisplayName() + "'s Gravestone"))) {
            // Removes the created Hologram
            if (p.getName().contains(".")) {
                String nameReplace = p.getName().replace(".", " ");
                if (DHAPI.getHologram(nameReplace) != null) {
                    DHAPI.removeHologram(nameReplace);
                } else {
                    System.out.println("Null Replace");
                }
            } else {
                if (DHAPI.getHologram(p.getName()) != null) {
                    DHAPI.removeHologram(p.getName());
                } else {
                    System.out.println("Null Name");
                }
            }
            // Removes player from Gravestone Map
            StoreItems.SaveInv.remove(p);
            Gravestone.remove(p);
            BlockLoc.remove(p);
            itemSave.clear();
        }
    }

    @EventHandler
    public void GravestoneNotify(PlayerRespawnEvent e) {

        Player p = e.getPlayer();
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        if (Gravestone.containsKey(p)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You can teleport to your gravestone using"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7/gravestone tp"));
        }

    }

    @EventHandler
    public void GravestoneClaim(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        String GraveBlock = VoltNetwork.getInstance().getConfig().getString("block_type");

        // Basic Checks to reduce errors in console.
        if (!BlockLoc.containsKey(p)) return;
        if (!StoreItems.SaveInv.containsKey(p)) return;
        if (b == null) return;

        // Checks for the block type supplied in config.
        if (b.getType().equals(Material.valueOf(GraveBlock))) {
            if (b.getLocation().getX() == BlockLoc.get(p)) {
                e.setCancelled(true);
                StoreItems.DeathInv(p);
                b.setType(Material.AIR);
            } else {
                if (!sendTitle && !sendMessage) return;
                if (!BlockLoc.containsValue(b.getLocation().getX())) return;

                // If sendtitle_gravestone_coords is true in config, send the player the title.
                if (sendTitle) {
                    // Checking the String for %coords%, if the String contains keyword, replace with X,Y,Z
                    if (titleFooter.contains("%coords%")) {
                        String placeholder = titleFooter.replace("%coords%", "X " + XCoord.get(p) + " Z " + ZCoord.get(p) + " Y " + YCoord.get(p));
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', titleHeader), ChatColor.translateAlternateColorCodes('&', placeholder),
                                15, 15, 15);
                    } else {
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', titleHeader), ChatColor.translateAlternateColorCodes('&', titleFooter),
                                15, 15, 15);
                    }
                    // ELSE IF sendmesssage_gravestone_coords is true send message.
                } else if (sendMessage) {
                    if (message.contains("%coords%")) {
                        String replacement = message.replace("%coords%", "X " + XCoord.get(p) + " Z " + ZCoord.get(p) + " Y " + YCoord.get(p));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', replacement));
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }
            }
        }
    }
}

