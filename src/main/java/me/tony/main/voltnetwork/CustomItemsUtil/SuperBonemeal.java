package me.tony.main.voltnetwork.CustomItemsUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SuperBonemeal implements Listener {

    public static void SuperBone() {

        ShapedRecipe superBone = new ShapedRecipe(SuperMeal());
        superBone.shape(" X ", "XDX", " X ");

        /*
        AIR BONE_MEAL AIR
        BONE_MEAL DIAMOND BONE_MEAL
        AIR BONE_MEAL AIR
         */

        superBone.setIngredient('X', Material.BONE_MEAL, 2);
        superBone.setIngredient('D', Material.DIAMOND, 1);

        VoltNetwork.getInstance().getServer().addRecipe(superBone);

    }


    public static ItemStack SuperMeal() {

        ItemStack i = new ItemStack(Material.BONE_MEAL);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bSuper Bone Meal"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Bonemeals a 4x4 area!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7The farmers dream!"));
        meta.setLore(lore);
        i.setItemMeta(meta);

        return i;

    }




    @EventHandler
    public void BonemealInteract(PlayerInteractEvent e) {

        if (e.getHand() != EquipmentSlot.HAND) return;
        ItemStack i = e.getItem();
        Block b = e.getClickedBlock();
        if (i == null || b == null) return;
        if (i.getType() == Material.BONE_MEAL && i.hasItemMeta() && i.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bSuper Bone Meal"))) {
            int radius = 4;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Location loc = b.getLocation().add(x, y, z);
                        Block block = loc.getBlock();

                        if (block.getBlockData() instanceof Ageable) {
                            Ageable age = (Ageable) block.getBlockData();
                            int currentAge = age.getAge();
                            int maxAge = age.getMaximumAge();

                            if (currentAge < maxAge) {
                                age.setAge(currentAge + 1);
                                block.setBlockData(age);
                            }
                        }

                    }
                }
            }
            i.setAmount(i.getAmount() - 1);
        }


    }




}
