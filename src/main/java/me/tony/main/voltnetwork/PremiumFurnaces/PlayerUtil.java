package me.tony.main.voltnetwork.PremiumFurnaces;

import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.List;

public class PlayerUtil implements Listener {

    public static HashMap<Player, Block> TempData = new HashMap<>();

    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void BreakFurnace(BlockBreakEvent e) {

        Block b = e.getBlock();
        if (b.getType() != Material.FURNACE) return;
        Location bLoc = b.getLocation();
        if (FurnaceUtil.isFurnace(b)) {
            if (!e.getPlayer().isSneaking()) {
                Player p = e.getPlayer();
                p.sendMessage(Chat.format("&cTo break this block, you should be crouching!"));
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            b.setType(Material.AIR);
            Location loc = b.getLocation();
            loc.getWorld().dropItemNaturally(bLoc, FurnaceUtil.Furnace(String.valueOf(FurnaceUtil.getFurnaceTier(e.getPlayer(), b))));
            FurnaceUtil.logInformation(Chat.getTime(), "broken", e.getPlayer(), String.valueOf(FurnaceUtil.getFurnaceTier(e.getPlayer(), b)));
            FurnaceUtil.BreakFurnace(e.getPlayer(), b);
        }
    }

    @EventHandler
    public void FurnaceShopInventory(InventoryClickEvent e) {

        int cost = VoltNetwork.getInstance().getConfig().getInt("cost_per_upgrade");
        String currency = VoltNetwork.getInstance().getConfig().getString("currency");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");

        Economy balance = VoltNetwork.getEconomy();


        Player p = (Player) e.getWhoClicked();
        int xpLevel = p.getExpToLevel();
        String titleName = e.getView().getTitle().strip();
        if (!titleName.contains("Furnace Shop")) return;
        int slot = e.getSlot();
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getType().equals(Material.REDSTONE_BLOCK) || e.getCurrentItem().getType().equals(Material.AIR)) return;
        e.setCancelled(true);

        if (slot == 11) {
            if (currency.equalsIgnoreCase("economy")) {
                if (balance.getBalance(p) >= cost) {
                    balance.withdrawPlayer(p, cost);
                    p.getInventory().addItem(FurnaceUtil.Furnace("1"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "1");
                } else {
                    p.closeInventory();
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough money to purchase this!"));
                }
            } else if (currency.equalsIgnoreCase("experience")) {
                if (xpLevel >= cost) {
                    p.giveExpLevels(-cost);
                    p.getInventory().addItem(FurnaceUtil.Furnace("1"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "1");
                } else {
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough Experience Levels to purchase this!"));
                }
            }
        }
        if (slot == 12) {
            if (currency.equalsIgnoreCase("economy")) {
                if (balance.getBalance(p) >= cost *2) {
                    balance.withdrawPlayer(p, cost *2);
                    p.getInventory().addItem(FurnaceUtil.Furnace("2"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "2");
                } else {
                    p.closeInventory();
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough money to purchase this!"));
                }
            } else if (currency.equalsIgnoreCase("experience")) {
                if (xpLevel >= cost*2) {
                    p.giveExpLevels(-cost*2);
                    p.getInventory().addItem(FurnaceUtil.Furnace("2"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "2");
                } else {
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough Experience Levels to purchase this!"));
                }
            }
        }
        if (slot == 13) {
            if (currency.equalsIgnoreCase("economy")) {
                if (balance.getBalance(p) >= cost *3) {
                    balance.withdrawPlayer(p, cost *3);
                    p.getInventory().addItem(FurnaceUtil.Furnace("3"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "3");
                } else {
                    p.closeInventory();
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough money to purchase this!"));
                }
            } else if (currency.equalsIgnoreCase("experience")) {
                if (xpLevel >= cost*3) {
                    p.giveExpLevels(-cost*3);
                    p.getInventory().addItem(FurnaceUtil.Furnace("3"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "3");
                } else {
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough Experience Levels to purchase this!"));
                }
            }
        }
        if (slot == 14) {
            if (currency.equalsIgnoreCase("economy")) {
                if (balance.getBalance(p) >= cost *4) {
                    balance.withdrawPlayer(p, cost *4);
                    p.getInventory().addItem(FurnaceUtil.Furnace("4"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "4");
                } else {
                    p.closeInventory();
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough money to purchase this!"));
                }
            } else if (currency.equalsIgnoreCase("experience")) {
                if (xpLevel >= cost*4) {
                    p.giveExpLevels(-cost*4);
                    p.getInventory().addItem(FurnaceUtil.Furnace("4"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "4");
                } else {
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough Experience Levels to purchase this!"));
                }
            }
        }
        if (slot == 15) {
            if (currency.equalsIgnoreCase("economy")) {
                if (balance.getBalance(p) >= cost *5) {
                    balance.withdrawPlayer(p, cost *5);
                    p.getInventory().addItem(FurnaceUtil.Furnace("5"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "5");
                } else {
                    p.closeInventory();
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough money to purchase this!"));
                }
            } else if (currency.equalsIgnoreCase("experience")) {
                if (xpLevel >= cost*5) {
                    p.giveExpLevels(-cost*5);
                    p.getInventory().addItem(FurnaceUtil.Furnace("5"));
                    p.closeInventory();
                    FurnaceUtil.logInformation(Chat.getTime(), "bought", p, "5");
                } else {
                    p.sendMessage(Chat.format(prefix + " &cYou do not have enough Experience Levels to purchase this!"));
                }
            }
        }
    }

    @EventHandler
    public void furnaceBurn(FurnaceStartSmeltEvent e) {
        Block b = e.getBlock();
        Location bLoc = b.getLocation();

        int cookTimeTier1 = VoltNetwork.getInstance().getConfig().getInt("tier1");
        int cookTimeTier2 = VoltNetwork.getInstance().getConfig().getInt("tier2");
        int cookTimeTier3 = VoltNetwork.getInstance().getConfig().getInt("tier3");
        int cookTimeTier4 = VoltNetwork.getInstance().getConfig().getInt("tier4");
        int cookTimeTier5 = VoltNetwork.getInstance().getConfig().getInt("tier5");

        for (String tier : FurnaceUtil.FurnaceLocations.keySet()) {
            if (FurnaceUtil.FurnaceLocations.containsKey(tier)) {  // why the #@!# did I check this.
                List<Location> locList = FurnaceUtil.FurnaceLocations.get(tier);
                if (locList.contains(bLoc)) {
                    int tierSet = Integer.parseInt(tier);
                    if (tierSet == 1) {
                        e.setTotalCookTime(cookTimeTier1);
                    }
                    if (tierSet == 2) {
                        e.setTotalCookTime(cookTimeTier2);
                    }
                    if (tierSet == 3) {
                        e.setTotalCookTime(cookTimeTier3);
                    }
                    if (tierSet == 4) {
                        e.setTotalCookTime(cookTimeTier4);
                    }
                    if (tierSet == 5) {
                        e.setTotalCookTime(cookTimeTier5);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (!b.getType().equals(Material.FURNACE));
        if (!p.getInventory().getItemInMainHand().hasItemMeta());
        String strippedName = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().strip();
        if (!strippedName.contains("Furnace Tier "));

        FurnaceUtil.furnacePlaceLogic(p, b);
    }


}
