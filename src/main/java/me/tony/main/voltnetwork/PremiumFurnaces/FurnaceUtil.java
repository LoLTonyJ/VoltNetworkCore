package me.tony.main.voltnetwork.PremiumFurnaces;

import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.GeneralUtil.PlaceHolderUtil;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.*;

public class FurnaceUtil {

   public static HashMap<String, List<Location>> FurnaceLocations = new HashMap<>();
   public static HashMap<UUID, List<Location>> MultipleFurnace = new HashMap<>();
   public static HashMap<UUID, List<String>> PlayerLogs = new HashMap<>();

    public static boolean mutliplePerPlayer = VoltNetwork.getInstance().getConfig().getBoolean("multiple_furnace");

    public static Integer defaultMultiple = VoltNetwork.getInstance().getConfig().getInt("default_furnace_limit");
    public static Integer premiumMultiple = VoltNetwork.getInstance().getConfig().getInt("premium_furnace_limit");
    public static String premiumPermission = VoltNetwork.getInstance().getConfig().getString("permission_multiple_furnace");

    public static String prefix = VoltNetwork.getInstance().getConfig().getString("prefix");


    public static void furnacePlaceLogic(Player p, Block b) {
        Location loc = b.getLocation();
        UUID pUUID = p.getUniqueId();

        if (b.getType() == Material.FURNACE && p.getInventory().getItemInMainHand().hasItemMeta()) {
            String displayName = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (MultipleFurnace.containsKey(pUUID)) {
                if (!multipleCheck(p)) {
                    for (List<Location> furnaceLoc : FurnaceLocations.values()) {
                        if (loc.equals(furnaceLoc)) {
                            p.sendMessage(Chat.format("&c&l&oERROR >> &cPlease contact an Administrator with a Screenshot of this Message."));
                            p.sendMessage(Chat.format("&c&l&oERROR >> &cFURNACE_LOCATION_ERROR"));
                            return;
                        }
                    }
                } else {
                    b.setType(Material.AIR);
                    return;
                }
                MultipleFurnace.get(pUUID).add(loc);
                p.sendMessage(Chat.format(prefix + " &bYou have placed a Tier " + PlaceHolderUtil.numberExtractor(displayName) + " Furnace!"));
            } else {
                List<Location> locList = new ArrayList<>();
                locList.add(loc);
                MultipleFurnace.put(pUUID, locList);
                p.sendMessage(Chat.format(prefix + " &bYou have placed a Tier " + PlaceHolderUtil.numberExtractor(displayName) + " Furnace!"));
            }
            registerFurnace(b, PlaceHolderUtil.numberExtractor(displayName));
            logInformation(Chat.getTime(), "break", p, String.valueOf(getFurnaceTier(p, b)));
        }
    }

    public static boolean multipleCheck(Player p) {
        if (mutliplePerPlayer) {
            UUID pUUID = p.getUniqueId();
            if (MultipleFurnace.containsKey(pUUID)) {
                int amount = MultipleFurnace.get(pUUID).size();
                if (amount >= defaultMultiple && !p.hasPermission(premiumPermission)) {
                    p.sendMessage(Chat.format(prefix + " &cYou have reached the limit of Premium Furnaces! &b(" + amount + "/" + defaultMultiple + ")"));
                    return true;
                }
                if (amount >= premiumMultiple && p.hasPermission(premiumPermission)) {
                    p.sendMessage(Chat.format(prefix + " &cYou have reached the limit of Premium Furnaces! &b(" + amount + "/" + premiumMultiple + ")"));
                    return true;
                }
            }
        } else {
            p.sendMessage(Chat.format(prefix + " &cYou have placed the Maximum Amount of Premium Furnaces allowed."));
            return true;
        }
        return false;
    }

    public static void logInformation(String date, String action, Player p, String tier) {

        if (PlayerLogs.containsKey(p.getUniqueId())) {
            List<String> logList = PlayerLogs.get(p.getUniqueId());
            logList.add("&b&lPF LOG > &7" + date + " " + p.getDisplayName() + " " + action + " " + "&6[Tier " + tier + "]&7" +  " Premium Furnace");
            logList.add(" ");
            PlayerLogs.put(p.getUniqueId(), logList);
        } else {
            List<String> newList = new ArrayList<>();
            newList.add("&b&lPF LOG > &7 " + date + " " + p.getDisplayName() + " " + action + " " + "&6[Tier " + tier + "]&7" +  " Premium Furnace");
            newList.add(" ");
            PlayerLogs.put(p.getUniqueId(), newList);
        }
    }

    public static void getPlayerLogs(Player staff, Player p) {

        if (p == null) {
            staff.sendMessage(Chat.format(prefix + " &cPLAYER_NULL"));
        }

        if (PlayerLogs.containsKey(p.getUniqueId())) {
            List<String> logList = PlayerLogs.get(p.getUniqueId());
            staff.sendMessage(Chat.format("&b&lPF LOG Params: &c<Date> &b| &c<Player> &B| &c<Action> &B| &c<Tier>"));
            for (String logs : logList) {
                staff.sendMessage(Chat.format(logs));
            }
        } else {
            staff.sendMessage(Chat.format(prefix + " &cNo History for this Player."));
        }

    }

    public static Integer getFurnaceTier(Player p, Block b) {
        for (String tier : FurnaceLocations.keySet()) {
            if (FurnaceLocations.containsKey(tier)) {
                List<Location> locList = FurnaceLocations.get(tier);
                if (locList.contains(b.getLocation())) {
                    return Integer.parseInt(tier);
                }
            } else {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (item.hasItemMeta() && item.getType().equals(Material.FURNACE)) {
                    String displayName = item.getItemMeta().getDisplayName();
                    String strippedName = displayName.strip();
                    return Integer.parseInt(PlaceHolderUtil.numberExtractor(strippedName));
                }
            }
        }
        return 1;
    }

    public static void registerFurnace(Block b, String tier) {
        if (FurnaceLocations.containsKey(tier)) {
            List<Location> locList = FurnaceLocations.get(tier);
            locList.add(b.getLocation());
            FurnaceLocations.put(tier, locList);
        } else {
            List<Location> newList = new ArrayList<>();
            newList.add(b.getLocation());
            FurnaceLocations.put(tier, newList);
        }
    }

    public static boolean isFurnace(Block b) {
        Location bLoc = b.getLocation();
        if (b.getType() != Material.FURNACE) return false;
        for (String furnace : FurnaceLocations.keySet()) {
            List<Location> locList = FurnaceLocations.get(furnace);
            if (locList.contains(bLoc)) {
                return true;
            }
        }
        return false;
    }

    public static void BreakFurnace(Player p, Block b) {
        List<Location> dataList = FurnaceLocations.get(String.valueOf(getFurnaceTier(p, b)));
        List<Location> locList = MultipleFurnace.get(p.getUniqueId());

        if (dataList == null) {
            p.sendMessage(Chat.format("&c&l&oERROR >> &cPlease contact an Administrator with a Screen Shot of this Message."));
            p.sendMessage(Chat.format("&c&L&oERROR >> &cFURNACE_LIST_NULL"));
            return;
        }

        if (dataList.contains(b.getLocation())) {
            dataList.remove(b.getLocation());
            if (dataList.isEmpty()) {
                FurnaceLocations.remove(String.valueOf(getFurnaceTier(p, b)));
            } else {
                FurnaceLocations.put(String.valueOf(getFurnaceTier(p, b)), dataList);
            }
        }

        if (locList.contains(b.getLocation())) {
            locList.remove(b.getLocation());
            if (!locList.isEmpty()) {
                MultipleFurnace.put(p.getUniqueId(), locList);
            } else {
                MultipleFurnace.remove(p.getUniqueId());
            }
        }
    }

    public static void FixFurnaces(Player staff, Player toFix) {
        int count = 0;
        if (FurnaceLocations.isEmpty() && MultipleFurnace.isEmpty()) {
            staff.sendMessage(Chat.format("&cThere are No Furnaces that need fixing!"));
            return;
        }
        UUID tUUID = toFix.getUniqueId();
        List<Location> locList = MultipleFurnace.get(tUUID);
        if (locList == null) {
            staff.sendMessage(Chat.format("&cNo Furnaces Found for " + toFix.getName()));
            return;
        }
        Iterator<Location> iterator = locList.iterator();
        while(iterator.hasNext()) {
            Location loc = iterator.next();
            if (!loc.getBlock().getType().equals(Material.FURNACE)) {
                iterator.remove();
                count++;
            }
        }
        if (locList.isEmpty()) {
            MultipleFurnace.remove(tUUID);
        } else {
            MultipleFurnace.put(tUUID, locList);
        }
        if (count == 0) {
            staff.sendMessage(Chat.format("&cNo Furnaces were Removed"));
        } else {
            staff.sendMessage(Chat.format("&cRemoved " + count + " corrupted furnace(s)!"));
        }
    }

    public static ItemStack Furnace(String tier) {

        ItemStack item = new ItemStack(Material.FURNACE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Chat.format("&cFurnace Tier " + tier));
        item.setItemMeta(meta);

        return item;
    }
}
