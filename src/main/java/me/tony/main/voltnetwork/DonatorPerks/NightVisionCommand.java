package me.tony.main.voltnetwork.DonatorPerks;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class NightVisionCommand implements CommandExecutor {

    public static ArrayList<Player> NightVision = new ArrayList<>();
    public static HashMap<Player, Integer> Cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Boolean nvUnlimited = VoltNetwork.getInstance().getConfig().getBoolean("night_vision_unlimited");
        Boolean cdEnabled = VoltNetwork.getInstance().getConfig().getBoolean("donator_cooldown");
        String prefix = VoltNetwork.getInstance().getConfig().getString("donator_prefix");
        String noPerm = VoltNetwork.getInstance().getConfig().getString("night_vision_no_access");
        int cd = VoltNetwork.getInstance().getConfig().getInt("night_vision_cooldown");

        Player p = (Player) sender;
        if (DonatorCommands.NVPlayers.contains(p.getName())) {
            if (!NightVision.contains(p)) {
                if (!Cooldown.containsKey(p)) {
                    if (nvUnlimited) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 5));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've enabled Night-Vision!"));
                        NightVision.add(p);
                        if (cdEnabled) {
                            Cooldown.put(p, cd);
                        }
                    } else {
                        int duration = VoltNetwork.getInstance().getConfig().getInt("night_vision_duration");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, duration, 5));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've enabled Night-Vision!"));
                        NightVision.add(p);
                        if (cdEnabled) {
                            Cooldown.put(p, cd);
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7This Command is on Cooldown for " + Cooldown.get(p) + " minutes!"));
                }
            } else {
                NightVision.remove(p);
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &7You've disabled Night-Vision!"));
            }
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + noPerm));
        }

        return true;
    }
}
