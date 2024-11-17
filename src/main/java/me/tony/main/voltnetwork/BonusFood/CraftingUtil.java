package me.tony.main.voltnetwork.BonusFood;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CraftingUtil {

    public static ItemStack SpeedSteak() {

        ItemStack item = new ItemStack(Material.COOKED_BEEF);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lSpeedy Steak"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Grants Speed II for 5 minutes"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack Cookie() {

        ItemStack item = new ItemStack(Material.COOKIE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lSpecial Cookie"));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Grants Block Fortune for 1 minute"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Every block mined adds &a$5 &7to your Bank!"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }

    public static ItemStack Stew() {
        ItemStack item = new ItemStack(Material.MUSHROOM_STEW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lSuper Stew"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Grants Jump Boost II for 2 minutes"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static void SpecialCookie() {

        ShapedRecipe specialCookie = new ShapedRecipe(Cookie());
        specialCookie.shape("DID", "GCG", "ERE");

        /*
        Diamond_Block Diamond Diamond_Block
        Gold_Block Cookie Gold_Block
        Emerald_Block Emerald Emerald_Block
         */

        specialCookie.setIngredient('D', Material.DIAMOND_BLOCK, 2);
        specialCookie.setIngredient('I', Material.DIAMOND, 1);
        specialCookie.setIngredient('G', Material.GOLD_BLOCK, 2);
        specialCookie.setIngredient('C', Material.COOKIE, 2);
        specialCookie.setIngredient('E', Material.EMERALD_BLOCK, 2);
        specialCookie.setIngredient('R', Material.EMERALD, 2);

        VoltNetwork.getInstance().getServer().addRecipe(specialCookie);

    }

    public static void SuperStew() {

        ShapedRecipe superStew = new ShapedRecipe(Stew());
        superStew.shape("SBS", " F ", " M ");

        /*
        Slime_Block Slime_Ball Slime_Block
        /////////// Feather ////////
                 Mushroom_Stew
         */

        superStew.setIngredient('S', Material.SLIME_BLOCK, 2);
        superStew.setIngredient('B', Material.SLIME_BALL, 1);
        superStew.setIngredient('F', Material.FEATHER, 2);
        superStew.setIngredient('M', Material.MUSHROOM_STEW, 2);

        VoltNetwork.getInstance().getServer().addRecipe(superStew);

    }

    public static void SpeedySteak() {

        ShapedRecipe steakSpeed = new ShapedRecipe(SpeedSteak());
        steakSpeed.shape("S*S", "***", "C*C");

        /*
        Sugar Steak Sugar
        Steak Steak Steak
        Sugar_Cane Steak Sugar_Cane
         */

        steakSpeed.setIngredient('S', Material.SUGAR, 2);
        steakSpeed.setIngredient('*', Material.COOKED_BEEF, 1);
        steakSpeed.setIngredient('C', Material.SUGAR_CANE, 2);

        VoltNetwork.getInstance().getServer().addRecipe(steakSpeed);

    }

}
