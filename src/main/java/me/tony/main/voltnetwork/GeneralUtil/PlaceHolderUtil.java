package me.tony.main.voltnetwork.GeneralUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceHolderUtil {


    public static String stringReplace(String msg, String toReplace, String replace) {
        return msg.replace(toReplace, replace);
    }

    public static String playerReplace(String msg, String placeHolder, Player p) {
        return msg.replace(placeHolder, p.getName());
    }

    public static String numberExtractor(String toFind) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(toFind);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return "0";
        }
    }

}
