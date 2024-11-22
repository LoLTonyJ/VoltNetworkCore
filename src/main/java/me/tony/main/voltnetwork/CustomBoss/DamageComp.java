package me.tony.main.voltnetwork.CustomBoss;

import java.util.Comparator;

public class DamageComp implements Comparator<TopDamager> {

    @Override
    public int compare(TopDamager o1, TopDamager o2) {
        int dmgComparator = Integer.valueOf(o1.dmg).compareTo(o2.dmg);
        return dmgComparator == 0 ? Integer.valueOf(o1.dmg).compareTo(o2.dmg) : dmgComparator;
    }
}