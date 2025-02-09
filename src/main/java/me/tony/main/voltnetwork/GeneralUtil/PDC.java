package me.tony.main.voltnetwork.GeneralUtil;

import me.tony.main.voltnetwork.VoltNetwork;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class PDC {

    private static NamespacedKey itemID = new NamespacedKey(VoltNetwork.getInstance(), "ITEM_ID");

    public static boolean containsItemID(ItemStack itemStack, String ID) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(itemID, PersistentDataType.STRING).equalsIgnoreCase(ID);
    }

    public static String getItemID(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(itemID, PersistentDataType.STRING);
    }

    public static void setItemID(ItemStack itemStack, String ID) {
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        container.set(itemID, PersistentDataType.STRING, ID);
    }


}
