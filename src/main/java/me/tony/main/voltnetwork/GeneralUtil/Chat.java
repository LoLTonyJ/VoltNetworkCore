package me.tony.main.voltnetwork.GeneralUtil;

import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {

    public static String format(String msg) {
        return (ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static String getTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM HH:mm");
        return format.format(now);

    }
}
