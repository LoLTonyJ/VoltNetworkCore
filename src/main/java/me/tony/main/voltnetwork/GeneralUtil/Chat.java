package me.tony.main.voltnetwork.GeneralUtil;

import org.bukkit.ChatColor;

public class Chat {

    public static String format(String msg) {
        return (ChatColor.translateAlternateColorCodes('&', msg));
    }
}
