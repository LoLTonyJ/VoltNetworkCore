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
        if (item.containsEnchantment(Enchantment.DURABILITY)) {
            int value = item.getEnchantmentLevel(Enchantment.DURABILITY);
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
        if (item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int value = item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            enchantmentType.add("Protection " + value);
        }
        // Aqua Affinity
        if (item.containsEnchantment(Enchantment.WATER_WORKER)) {
            int value = item.getEnchantmentLevel(Enchantment.WATER_WORKER);
            enchantmentType.add("Aqua Affinity " + value);
        }

        // Blast Protection
        if (item.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS)) {
            int value = item.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS);
            enchantmentType.add("Blast Protection " + value);
        }

        // Fire Protection
        if (item.containsEnchantment(Enchantment.PROTECTION_FIRE)) {
            int value = item.getEnchantmentLevel(Enchantment.PROTECTION_FIRE);
            enchantmentType.add("Fire Protection " + value);
        }

        // Projectile Protection
        if (item.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
            int value = item.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE);
            enchantmentType.add("Projectile Protection " + value);
        }

        // Depth Strider
        if (item.containsEnchantment(Enchantment.DEPTH_STRIDER)) {
            int value = item.getEnchantmentLevel(Enchantment.DEPTH_STRIDER);
            enchantmentType.add("Depth Strider " + value);
        }

        // Respiration
        if (item.containsEnchantment(Enchantment.OXYGEN)) {
            int value = item.getEnchantmentLevel(Enchantment.OXYGEN);
            enchantmentType.add("Respiration " + value);
        }

        // Frost Walker
        if (item.containsEnchantment(Enchantment.FROST_WALKER)) {
            int value = item.getEnchantmentLevel(Enchantment.FROST_WALKER);
            enchantmentType.add("Frost Walker " + value);
        }

        // Feather Falling
        if (item.containsEnchantment(Enchantment.PROTECTION_FALL)) {
            int value = item.getEnchantmentLevel(Enchantment.PROTECTION_FALL);
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
        if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
            int value = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            enchantmentType.add("Sharpness " + value);
        }
        // Bane of Arthropods
        if (item.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS)) {
            int value = item.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
            enchantmentType.add("Bane of Arthropods " + value);
        }

        // Smite
        if (item.containsEnchantment(Enchantment.DAMAGE_UNDEAD)) {
            int value = item.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
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
        if (item.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
            int value = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
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
        if (item.containsEnchantment(Enchantment.LUCK)) {
            int value = item.getEnchantmentLevel(Enchantment.LUCK);
            enchantmentType.add("Luck of the Sea " + value);
        }

        //

        /*
        Bow Enchantments
         */

        // Power
        if (item.containsEnchantment(Enchantment.ARROW_DAMAGE)) {
            int value = item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
            enchantmentType.add("Power " + value);
        }

        // Flame
        if (item.containsEnchantment(Enchantment.ARROW_FIRE)) {
            int value = item.getEnchantmentLevel(Enchantment.ARROW_FIRE);
            enchantmentType.add("Flame " + value);
        }

        // Punch
        if (item.containsEnchantment(Enchantment.ARROW_KNOCKBACK)) {
            int value = item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
            enchantmentType.add("Punch " + value);
        }

        // Infinity
        if (item.containsEnchantment(Enchantment.ARROW_INFINITE)) {
            int value = item.getEnchantmentLevel(Enchantment.ARROW_INFINITE);
            enchantmentType.add("Infinity " + value);
        }




        /*
        Pickaxe Enchantments
         */

        // Efficiency
        if (item.containsEnchantment(Enchantment.DIG_SPEED)) {
            int value = item.getEnchantmentLevel(Enchantment.DIG_SPEED);
            enchantmentType.add("Efficiency " + value);
        }

        // Fortune
        if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            int value = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
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
