package me.tony.main.voltnetwork.CustomBoss;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TopDamager {
    protected String name;
    protected int dmg;

    public TopDamager(String name, int dmg) {
        this.name = name;
        this.dmg = dmg;
    }

    public String getName() {
        return name;
    }

    public int getDmg() {
        return dmg;
    }
    public static List<TopDamager> getLeaderboard() {

        List<TopDamager> damager = new ArrayList<>();
        Set<Player> Players = BossUtil.DamageDealt.keySet();

        for (Player p : Players) {
            String name = p.getName();
            int dmg = BossUtil.DamageDealt.get(p);
            damager.add(new TopDamager(name, dmg));
        }

        Collections.reverse(damager);
        return damager;
    }
}
