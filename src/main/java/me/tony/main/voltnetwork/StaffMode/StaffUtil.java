package me.tony.main.voltnetwork.StaffMode;

import me.tony.main.voltnetwork.BonusFood.FoodUtil;
import me.tony.main.voltnetwork.DonatorPerks.NightVisionCommand;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaffUtil implements Listener {

    public static ArrayList<Player> Alerts = new ArrayList<>();
    public static ArrayList<Player> StaffMode = new ArrayList<>();
    public static HashMap<Player, ItemStack[]> SavedInv = new HashMap<>();
    public static ArrayList<Player> Frozen = new ArrayList<>();
    public static ArrayList<Player> Invis = new ArrayList<>();
    public static HashMap<Player, ItemStack[]> SurvivalINV = new HashMap<>();

    public static String perm = VoltNetwork.getInstance().getConfig().getString("staff_alert_permission");
    public static String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
    public static String exempt = VoltNetwork.getInstance().getConfig().getString("staff_alerts_exempt");


    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission(perm)) {
            if (!Alerts.contains(e.getPlayer())) {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYour Alerts are Enabled, to disable them do /sm alerts!"));
            } else {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour Alerts are Disabled, to enabled them do /sm alerts!"));
            }
        }
    }

    @EventHandler
    public static void DropItems(PlayerDropItemEvent e) {

        String exemptPerm = VoltNetwork.getInstance().getConfig().getString("gamemode_exempt");

        if (!e.getPlayer().hasPermission(exemptPerm)) {
            if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou cannot drop items while in Creative!"));
            }
        }

    }

    @EventHandler
    public static void PlaceBlock(BlockPlaceEvent e) {

        String exemptPerm = VoltNetwork.getInstance().getConfig().getString("gamemode_exempt");

        if (!e.getPlayer().hasPermission(exemptPerm)) {
            if (StaffMode.contains(e.getPlayer())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou cannot Place Blocks while in Staff Mode!"));
            }
        }

        if (e.getPlayer().getInventory().getItemInMainHand().equals(FreezePlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThat block cannot be placed!"));
        }

    }

    @EventHandler
    public static void BlockAccess(PlayerInteractEvent e) {

        String exemptPerm = VoltNetwork.getInstance().getConfig().getString("gamemode_exempt");
        List<String> checkBlocks = VoltNetwork.getInstance().getConfig().getStringList("Access_Blocks");

        if (e.getClickedBlock() == null || e.getClickedBlock().getType() == null || e.getClickedBlock().getType().equals(Material.AIR)) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_AIR)) return;

        if (!e.getPlayer().hasPermission(exemptPerm)) {
            if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    for (String b : checkBlocks) {
                        if (e.getClickedBlock().getType().equals(Material.valueOf(b))) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou cannot access this block while in Creative!"));
                        }
                    }
                }
                if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    for (String b : checkBlocks) {
                        if (e.getClickedBlock().getType().equals(Material.valueOf(b))) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou cannot break this block while in Creative!"));
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public static void GamemodeChange(PlayerGameModeChangeEvent e) {

        String exemptPerm = VoltNetwork.getInstance().getConfig().getString("gamemode_exempt");
        if (!e.getPlayer().hasPermission(exemptPerm)) {
            if (e.getNewGameMode().equals(GameMode.CREATIVE)) {
                SurvivalINV.put(e.getPlayer(), e.getPlayer().getInventory().getContents());
                e.getPlayer().getInventory().clear();
            } else if (e.getNewGameMode().equals(GameMode.SURVIVAL)) {
                e.getPlayer().getInventory().clear();
                if (SurvivalINV.containsKey(e.getPlayer())) {
                    for (ItemStack i : SurvivalINV.get(e.getPlayer())) {
                        if (i != null) {
                            e.getPlayer().getInventory().addItem(i);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public static void TeleportInteract(PlayerInteractEvent e) {
        Player staff = e.getPlayer();
        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;

        if (staff.getInventory().getItemInMainHand().equals(TeleportPlayer())) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                InventoryUtil.OnlinePlayers(staff);
            }
        }
        if (staff.getInventory().getItemInMainHand().equals(exitSM())) {
            e.setCancelled(true);
            InitStaffMode(e.getPlayer());
        }
    }

    @EventHandler
    public static void InitTeleport(InventoryClickEvent e) {
        Player staff = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&a&lOnline Players"))) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                ItemMeta meta = item.getItemMeta();
                String pName = meta.getDisplayName().strip();
                for (Player victim : Bukkit.getOnlinePlayers()) {
                    if (pName.equals(victim.getName())) {
                        if (victim.isOnline()) {
                            Location l = victim.getLocation();
                            staff.teleport(l);
                        } else {
                            staff.closeInventory();
                            staff.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is no longer online!"));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public static void StaffInteract(PlayerInteractEntityEvent e) {

        if (!(e.getRightClicked() instanceof Player)) return;
        Player victim = (Player) e.getRightClicked();
        Player staff = e.getPlayer();

        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (victim.hasPermission(exempt)) {
            staff.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You cannot punish that player!"));
            return;
        }

        if (staff.getInventory().getItemInMainHand().equals(PlayerInfo())) {
            InventoryUtil.PlayerInfo(staff, victim);
        }
        if (staff.getInventory().getItemInMainHand().equals(FreezePlayer())) {
            InitFreeze(victim, staff);
        }
    }

    @EventHandler
    public static void FreezePlayer(PlayerMoveEvent e) {

        if (Frozen.contains(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou're Frozen!"));
        }
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent e) {

        Boolean ban = VoltNetwork.getInstance().getConfig().getBoolean("frozen_ban_leave");
        String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");
        String defaultReason = VoltNetwork.getInstance().getConfig().getString("default_frozen_ban");
        String discordInv = VoltNetwork.getInstance().getConfig().getString("discord_inv");
        String staffPref = VoltNetwork.getInstance().getConfig().getString("staff_alerts");
        String staffPerm = VoltNetwork.getInstance().getConfig().getString("staff_alert_permission");

        if (ban) {
            if (Frozen.contains(e.getPlayer())) {
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission(staffPerm)) {
                        if (!Alerts.contains(staff)) {
                            staff.sendMessage(ChatColor.translateAlternateColorCodes('&', staffPref + " " + e.getPlayer().getDisplayName() + " has logged out while Frozen!"));
                        }
                    }
                }
                VoltNetwork.getInstance().getServer().getBanList(BanList.Type.NAME).addBan(e.getPlayer().getName(), ChatColor.translateAlternateColorCodes('&', "\n" + prefix + "\n" + defaultReason + "\n" + discordInv), null,
                         "Frozen Punishment");
            }
        }
    }

    public static void FailSafe(Player p) {
        if (SavedInv.containsKey(p)) {
            p.getInventory().clear();
            for (ItemStack i : SavedInv.get(p)) {
                if (i != null) {
                    p.getInventory().addItem(i);
                }
            }
            SavedInv.remove(p);
        }
        if (SurvivalINV.containsKey(p)) {
            p.getInventory().clear();
            for (ItemStack i : SurvivalINV.get(p)) {
                if (i != null) {
                    p.getInventory().addItem(i);
                }
            }
        }
        if (StaffMode.contains(p)) {
            InitStaffMode(p);
        }
        if (Invis.contains(p)) {
            InitInvis(p);
        }

    }


    public static void InitFreeze(Player p, Player Staff) {

        if (!Frozen.contains(p)) {
            Frozen.add(p);
            for (String i : VoltNetwork.getInstance().getConfig().getStringList("frozen_message")) {
                if (i.contains("%staff%")) {
                    String replace = i.replace("%staff%", Staff.getDisplayName());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', replace));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', i));
                }
            }
        } else {
            Frozen.remove(p);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You have been Unfrozen!"));
        }

    }

    public static void InitInvis(Player p) {

        String perm = VoltNetwork.getInstance().getConfig().getString("invis_from_all");

        if (p.hasPermission(perm)) {
            if (!Invis.contains(p)) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.hidePlayer(VoltNetwork.getInstance(), p);
                }
            } else {
                Invis.remove(p);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.showPlayer(VoltNetwork.getInstance(), p);
                }
            }
        }

    }

    public static void InitStaffMode(Player p) {

        Boolean modulePerms = VoltNetwork.getInstance().getConfig().getBoolean("permission_per_module");

        String tpPerms = VoltNetwork.getInstance().getConfig().getString("teleport_gui");
        String playerInfo = VoltNetwork.getInstance().getConfig().getString("player_info");
        String freezePlayer = VoltNetwork.getInstance().getConfig().getString("freeze_player");

        InitInvis(p);

        if (!StaffMode.contains(p)) {
            SavedInv.put(p, p.getInventory().getContents());
            StaffMode.add(p);
            p.getInventory().clear();
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have enabled Staff Mode!"));

            // Setting Players Hotbar


            p.getInventory().setItem(8, exitSM());

            if (!modulePerms) {
                p.getInventory().setItem(3, TeleportPlayer());
                p.getInventory().setItem(4, PlayerInfo());
                p.getInventory().setItem(5, FreezePlayer());
            } else {
                if (p.hasPermission(tpPerms)) {
                    p.getInventory().setItem(3, TeleportPlayer());
                }
                if (p.hasPermission(playerInfo)) {
                    p.getInventory().setItem(4, PlayerInfo());
                }
                if (p.hasPermission(freezePlayer)) {
                    p.getInventory().setItem(5, FreezePlayer());
                }
            }

        } else {
            StaffMode.remove(p);
            p.getInventory().clear();
            if (SavedInv.containsKey(p)) {
                for (ItemStack i : SavedInv.get(p)) {
                    if (i != null) {
                        p.getInventory().addItem(i);
                    }
                }
                SavedInv.remove(p);
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You have disabled Staff Mode!"));
        }
    }

    public static void AlertToggle(Player p) {
        if (p.hasPermission(perm)) {
            if (!Alerts.contains(p)) {
                Alerts.add(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've disabled Staff Alerts!"));
            } else {
                Alerts.remove(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've enabled Staff Alerts!"));
            }
        }
    }




    /*


    -------------- Staff Mode Items ---------------------


     */

    public static ItemStack ComingSoon() {
        ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lComing Soon!"));
        item.setItemMeta(meta);


        return item;
    }

    public static ItemStack exitSM() {

        ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cExit Staff Mode"));
        item.setItemMeta(meta);

        return item;

    }

    public static ItemStack victim(Player p) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
        meta.setOwningPlayer(p);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&l" + p.getName()));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(" ");
        meta.setLore(lore);
        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack PlayerInfo() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bPlayer Info"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click a Player to open the Player Information GUI!"));
        lore.add(" ");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }

    public static ItemStack TeleportPlayer() {
        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Teleport to any Online Player!"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to open Teleport menu!"));
        lore.add(" ");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }

    public static ItemStack FreezePlayer() {
        ItemStack item = new ItemStack(Material.PACKED_ICE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cFreeze Player"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click a Player to Freeze them!"));
        lore.add(" ");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }



}
