package me.tony.main.voltnetwork.ViewCraftingRecipe;

import me.tony.main.voltnetwork.BonusFood.CraftingUtil;
import me.tony.main.voltnetwork.BonusFood.FoodUtil;
import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class RecipeCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + " Only players can use this command!");
            return true;
        }

        Player p = (Player) sender;

        if (args.length < 1) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bUsage; /recipe <item>"));
            return true;
        }

        Material mat;
        try {
            mat = Material.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            p.sendMessage(ChatColor.RED + " Invalid Item Type");
            return true;
        }

        showRecipe(p, mat);


        return true;
    }

    @EventHandler
    public void onTake(InventoryClickEvent e) {
        if (!e.getInventory().getType().equals(InventoryType.WORKBENCH)) return;
        if (!e.getView().getTitle().startsWith("Recipe for ")) return;
        e.setCancelled(true);
    }

    private void showRecipe(Player p, Material material) {

        Iterator<Recipe> recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            Recipe recipe = recipes.next();
            if (recipe != null && recipe.getResult().getType() == material) {
                if (recipe instanceof ShapedRecipe) {
                    ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                     Inventory inv  = Bukkit.createInventory(null, InventoryType.WORKBENCH, "Recipe for " + material);


                       int[] slots = {20, 21, 22, 29, 30, 31, 38, 39, 40}; // Set the Crafting Slots.
                       String[] shape = shapedRecipe.getShape();
                       for (int row = 0; row < shape.length; row++) {
                           for (int col = 0; col < shape.length; col++) {
                               char c = shape[row].charAt(col);
                               ItemStack item = shapedRecipe.getIngredientMap().get(c);
                               if (item != null) {
                                   inv.setItem(row * 3 + col + 1 , item);
                               }

                           }
                       }

                       new BukkitRunnable() {
                           @Override
                           public void run() {
                               inv.setItem(0, recipe.getResult());
                           }
                       }.runTaskLater(VoltNetwork.getInstance(), 1L);
                       p.openInventory(inv);
                       return;
                }
            }
        }
        p.sendMessage(ChatColor.RED + "No Recipe found for " + material.name() + ".");
    }


}
