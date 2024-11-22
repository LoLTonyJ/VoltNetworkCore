package me.tony.main.voltnetwork.ChatUtil;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EnchantmentUtil {

    public static String getEnchantments(Player p) {

        ItemStack item = p.getInventory().getItemInMainHand();

        ArrayList<String> enchantmentType = new ArrayList<>();

        /*
        Flexible Enchantments
         */

        // Curse of Binding
        if (item.containsEnchantment(Enchantment.BINDING_CURSE)) {
            int value = item.getEnchantmentLevel(Enchantment.BINDING_CURSE);
            enchantmentType.add("Curse of Binding " + value);
        }

        // Curse of Vanish
        if (item.containsEnchantment(Enchantment.VANISHING_CURSE)) {
            int value = item.getEnchantmentLevel(Enchantment.VANISHING_CURSE);
            enchantmentType.add("Curse of Vanishing " + value);
        }

        // Unbreaking
        if (item.containsEnchantment(Enchantment.UNBREAKING)) {
            int value = item.getEnchantmentLevel(Enchantment.UNBREAKING);
            enchantmentType.add("Unbreaking " + value);
        }

        // Mending
        if (item.containsEnchantment(Enchantment.MENDING)) {
            int value = item.getEnchantmentLevel(Enchantment.MENDING);
            enchantmentType.add("Mending " + value);
        }

        /*
        Armor Enchantments
         */

        // Protection
        if (item.containsEnchantment(Enchantment.PROTECTION)) {
            int value = item.getEnchantmentLevel(Enchantment.PROTECTION);
            enchantmentType.add("Protection " + value);
        }
        // Aqua Affinity
        if (item.containsEnchantment(Enchantment.AQUA_AFFINITY)) {
            int value = item.getEnchantmentLevel(Enchantment.AQUA_AFFINITY);
            enchantmentType.add("Aqua Affinity " + value);
        }

        // Blast Protection
        if (item.containsEnchantment(Enchantment.BLAST_PROTECTION)) {
            int value = item.getEnchantmentLevel(Enchantment.BLAST_PROTECTION);
            enchantmentType.add("Blast Protection " + value);
        }

        // Fire Protection
        if (item.containsEnchantment(Enchantment.FIRE_PROTECTION)) {
            int value = item.getEnchantmentLevel(Enchantment.FIRE_PROTECTION);
            enchantmentType.add("Fire Protection " + value);
        }

        // Projectile Protection
        if (item.containsEnchantment(Enchantment.PROJECTILE_PROTECTION)) {
            int value = item.getEnchantmentLevel(Enchantment.PROJECTILE_PROTECTION);
            enchantmentType.add("Projectile Protection " + value);
        }

        // Depth Strider
        if (item.containsEnchantment(Enchantment.DEPTH_STRIDER)) {
            int value = item.getEnchantmentLevel(Enchantment.DEPTH_STRIDER);
            enchantmentType.add("Depth Strider " + value);
        }

        // Respiration
        if (item.containsEnchantment(Enchantment.RESPIRATION)) {
            int value = item.getEnchantmentLevel(Enchantment.RESPIRATION);
            enchantmentType.add("Respiration " + value);
        }

        // Frost Walker
        if (item.containsEnchantment(Enchantment.FROST_WALKER)) {
            int value = item.getEnchantmentLevel(Enchantment.FROST_WALKER);
            enchantmentType.add("Frost Walker " + value);
        }

        // Feather Falling
        if (item.containsEnchantment(Enchantment.FEATHER_FALLING)) {
            int value = item.getEnchantmentLevel(Enchantment.FEATHER_FALLING);
            enchantmentType.add("Feather Falling " + value);
        }

        // Thorns
        if (item.containsEnchantment(Enchantment.THORNS)) {
            int value = item.getEnchantmentLevel(Enchantment.THORNS);
            enchantmentType.add("Thorns " + value);
        }

        /*
        Sword Enchantments
         */

        // Sharpness
        if (item.containsEnchantment(Enchantment.SHARPNESS)) {
            int value = item.getEnchantmentLevel(Enchantment.SHARPNESS);
            enchantmentType.add("Sharpness " + value);
        }
        // Bane of Arthropods
        if (item.containsEnchantment(Enchantment.BANE_OF_ARTHROPODS)) {
            int value = item.getEnchantmentLevel(Enchantment.BANE_OF_ARTHROPODS);
            enchantmentType.add("Bane of Arthropods " + value);
        }

        // Smite
        if (item.containsEnchantment(Enchantment.SMITE)) {
            int value = item.getEnchantmentLevel(Enchantment.SMITE);
            enchantmentType.add("Smite " + value);
        }

        // Knockback
        if (item.containsEnchantment(Enchantment.KNOCKBACK)) {
            int value = item.getEnchantmentLevel(Enchantment.KNOCKBACK);
            enchantmentType.add("Knockback " + value);
        }

        // Fire-Aspect
        if (item.containsEnchantment(Enchantment.FIRE_ASPECT)) {
            int value = item.getEnchantmentLevel(Enchantment.FIRE_ASPECT);
            enchantmentType.add("Fire Aspect " + value);
        }

        // Looting
        if (item.containsEnchantment(Enchantment.LOOTING)) {
            int value = item.getEnchantmentLevel(Enchantment.LOOTING);
            enchantmentType.add("Looting " + value);
        }

        // Sweeping Edge
        if (item.containsEnchantment(Enchantment.SWEEPING_EDGE)) {
            int value = item.getEnchantmentLevel(Enchantment.SWEEPING_EDGE);
            enchantmentType.add("Sweeping Edge " + value);
        }

        /*
        Trident Enchantments
         */

        // Impaling
        if (item.containsEnchantment(Enchantment.IMPALING)) {
            int value = item.getEnchantmentLevel(Enchantment.IMPALING);
            enchantmentType.add("Impaling " + value);
        }

        // Channeling
        if (item.containsEnchantment(Enchantment.CHANNELING)) {
            int value = item.getEnchantmentLevel(Enchantment.CHANNELING);
            enchantmentType.add("Channeling " + value);
        }

        // Loyalty
        if (item.containsEnchantment(Enchantment.LOYALTY)) {
            int value = item.getEnchantmentLevel(Enchantment.LOYALTY);
            enchantmentType.add("Loyalty " + value);
        }

        // Riptide
        if (item.containsEnchantment(Enchantment.RIPTIDE)) {
            int value = item.getEnchantmentLevel(Enchantment.RIPTIDE);
            enchantmentType.add("Riptide " + value);
        }

        //
        if (item.containsEnchantment(Enchantment.LURE)) {
            int value = item.getEnchantmentLevel(Enchantment.LURE);
            enchantmentType.add("Lure " + value);
        }

        /*
        Fishing Rod Enchantments
         */

        // Lure
        if (item.containsEnchantment(Enchantment.LURE)) {
            int value = item.getEnchantmentLevel(Enchantment.LURE);
            enchantmentType.add("Lure " + value);
        }

        // Luck of the Sea
        if (item.containsEnchantment(Enchantment.LUCK_OF_THE_SEA)) {
            int value = item.getEnchantmentLevel(Enchantment.LUCK_OF_THE_SEA);
            enchantmentType.add("Luck of the Sea " + value);
        }

        //

        /*
        Bow Enchantments
         */

        // Power
        if (item.containsEnchantment(Enchantment.POWER)) {
            int value = item.getEnchantmentLevel(Enchantment.POWER);
            enchantmentType.add("Power " + value);
        }

        // Flame
        if (item.containsEnchantment(Enchantment.FLAME)) {
            int value = item.getEnchantmentLevel(Enchantment.FLAME);
            enchantmentType.add("Flame " + value);
        }

        // Punch
        if (item.containsEnchantment(Enchantment.PUNCH)) {
            int value = item.getEnchantmentLevel(Enchantment.PUNCH);
            enchantmentType.add("Punch " + value);
        }

        // Infinity
        if (item.containsEnchantment(Enchantment.INFINITY)) {
            int value = item.getEnchantmentLevel(Enchantment.INFINITY);
            enchantmentType.add("Infinity " + value);
        }




        /*
        Pickaxe Enchantments
         */

        // Efficiency
        if (item.containsEnchantment(Enchantment.EFFICIENCY)) {
            int value = item.getEnchantmentLevel(Enchantment.EFFICIENCY);
            enchantmentType.add("Efficiency " + value);
        }

        // Fortune
        if (item.containsEnchantment(Enchantment.FORTUNE)) {
            int value = item.getEnchantmentLevel(Enchantment.FORTUNE);
            enchantmentType.add("Fortune " + value);
        }

        // Silk Touch
        if (item.containsEnchantment(Enchantment.SILK_TOUCH)) {
            int value = item.getEnchantmentLevel(Enchantment.SILK_TOUCH);
            enchantmentType.add("Silk Touch " + value);
        }



        if (enchantmentType.isEmpty()) {
            return null;
        }

        return enchantmentType.toString().replace("[", " ").replace("]", "");

    }



}
