package me.tony.main.voltnetwork.BonusFood;

import me.tony.main.voltnetwork.GeneralUtil.Chat;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class FoodUtil implements Listener {

    public static HashMap<Player, Integer> SpecialCookie = new HashMap<>();

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (SpecialCookie.containsKey(p)) {
            VoltNetwork.getEconomy().depositPlayer(p, 5);
            p.sendMessage(Chat.format("&6&lCOOKIE BUFF! &a&l+$5"));
        }

    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {

        if (e.getItem().equals(CraftingUtil.Cookie())) {
            if (SpecialCookie.containsKey(e.getPlayer())) {
                int timeLeft = SpecialCookie.get(e.getPlayer());
                e.getPlayer().sendMessage(Chat.format("&6This item is on Cooldown for " + timeLeft + " minutes."));
                e.setCancelled(true);
                return;
            }
            SpecialCookie.put(e.getPlayer(), 5);
            e.getPlayer().sendMessage(Chat.format("&7Have fun Mining!"));
        }
        if (e.getItem().equals(CraftingUtil.Stew())) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 3000, 1));
        }
        if (e.getItem().equals(CraftingUtil.SpeedSteak())) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 1));
        }

    }


}
