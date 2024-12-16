package me.tony.main.voltnetwork.AntiCheat;

import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ACUtil {

    public static ArrayList<UUID> AlertToggle = new ArrayList<>();
    public static ArrayList<UUID> ExemptToggle = new ArrayList<>();

    private static HashMap<UUID, List<String>> PlayerLogs = new HashMap<>();

    private static String prefix = VoltNetwork.getInstance().getConfig().getString("ac_prefix");
    private static String perm = VoltNetwork.getInstance().getConfig().getString("ac_permission");

    public static void inputPlayerLog(Player target, String cheatType, Integer triggeredAmount, String severeType) {
        if (target == null) {
            Bukkit.getLogger().log(Level.SEVERE, "PLAYER_NULL_CHECK");
        }
        if (PlayerLogs.containsKey(target.getUniqueId())) {
            List<String> logList = PlayerLogs.get(target.getUniqueId());
            logList.add(Chat.getTime() + " " + target.getName() + " &c&lTriggered > &7" + cheatType + " [+ " + triggeredAmount + "] &c&l&oLEVEL > " + severeType);
            PlayerLogs.put(target.getUniqueId(), logList);
        } else {
            List<String> newList = new ArrayList<>();
            newList.add(Chat.getTime() + " " + target.getName() + " &c&lTriggered > &7" + cheatType + " [+ " + triggeredAmount + "] &c&l&oLEVEL > " + severeType);
            PlayerLogs.put(target.getUniqueId(), newList);
        }

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (!AlertToggle.contains(staff) && staff.hasPermission(perm)) {
                staff.sendMessage(Chat.format(Chat.getTime() + " " + target.getName() + " &c&lTriggered > &7" + cheatType));
            } else if (AlertToggle.contains(staff) && staff.hasPermission(perm)) {
                staff.sendMessage(Chat.format("&cVNA Tried to Inform you about Player Behavior! You have Alerts off!"));
            }
        }

    }

    public static void clearPlayerLogs(Player target, Player staff) {

        if (target == null) {
            staff.sendMessage(Chat.format(prefix + " &CNULL_PLAYER"));
            return;
        }
        if (!PlayerLogs.containsKey(target.getUniqueId())) {
            staff.sendMessage(Chat.format(prefix + " &cNo history for player!"));
            return;
        }
        PlayerLogs.remove(target.getUniqueId());
        staff.sendMessage(Chat.format(prefix + " &cYou have cleared this Players log."));

    }

    public static void getPlayerLogs(Player target, Player staff) {
        if (target == null) {
            staff.sendMessage(Chat.format(prefix + " &cNULL_PLAYER"));
            return;
        }
        if (!PlayerLogs.containsKey(target.getUniqueId())) {
            staff.sendMessage(Chat.format(prefix + " &cNo history for player!"));
            return;
        }
        List<String> logList = PlayerLogs.get(target.getUniqueId());
        for (String logs : logList) {
            staff.sendMessage(Chat.format(logs));
        }
    }

    public static void exemptPlayer(Player staff, Player p) {
        if (p == null || !p.isOnline()) {
            staff.sendMessage(Chat.format(prefix + " &cNULL_PLAYER"));
        }
        if (ExemptToggle.contains(p.getUniqueId())) {
            ExemptToggle.remove(p.getUniqueId());
            staff.sendMessage(Chat.format(prefix + " &cPlayer will now trigger AC Alerts!"));
        } else {
            ExemptToggle.add(p.getUniqueId());
            staff.sendMessage(Chat.format(prefix + " &aPlayer will no longer Trigger AC Alerts"));
        }
    }

    public static void toggleAlerts(Player p) {

        if (AlertToggle.contains(p.getUniqueId())) {
            AlertToggle.remove(p.getUniqueId());
            p.sendMessage(Chat.format(prefix + " &aYou have Enabled AC Alerts"));
        } else {
            AlertToggle.add(p.getUniqueId());
            p.sendMessage(Chat.format(prefix + " &cYou have Disabled AC Alerts"));
        }
    }

}
