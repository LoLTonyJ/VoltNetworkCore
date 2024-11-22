package me.tony.main.voltnetwork.CustomBoss;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KillerRewards {


    public static int rndm(int min, int max) {
        Random DropAmount = new Random();
        return DropAmount.nextInt((max - min) + 1) + min;
    }

    public static void getPlacements() {

        int num;
        int num2;
        int num3;
        Player player = null;

        num = rndm(0, BossInventoryUtil.TopRewards.size());
        num2 = rndm(0, BossInventoryUtil.SecondRewards.size());
        num3 = rndm(0, BossInventoryUtil.ThirdRewards.size());

        if (!BossUtil.Player1.isEmpty()) {
            ArrayList<ItemStack> SelectRewards = new ArrayList<>();
            if (BossInventoryUtil.TopRewards.isEmpty() || BossInventoryUtil.SecondRewards.isEmpty() || BossInventoryUtil.ThirdRewards.isEmpty()) return;

            if (num == BossInventoryUtil.TopRewards.size()) {
                num = num - 1;
            }
            if (num2 == BossInventoryUtil.TopRewards.size()) {
                num2 = num2 - 1;
            }
            if (num3 == BossInventoryUtil.TopRewards.size()) {
                num3 = num3 - 1;
            }

            ItemStack fDrop = BossInventoryUtil.TopRewards.get(num);
            ItemStack sDrop = BossInventoryUtil.SecondRewards.get(num2);
            ItemStack tDrop = BossInventoryUtil.ThirdRewards.get(num3);
            SelectRewards.add(fDrop);
            SelectRewards.add(sDrop);
            SelectRewards.add(tDrop);
            if (fDrop != null && sDrop != null && tDrop != null) {
                for (Player p : BossUtil.Player1) {
                    player = p;
                }
                for (ItemStack i : SelectRewards) {
                    player.getInventory().addItem(i);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "- " + i.getType().toString()));
                }
            }
        }
        if (!BossUtil.Player2.isEmpty()) {
            ArrayList<ItemStack> SelectRewards = new ArrayList<>();
            if (BossInventoryUtil.TopRewards.isEmpty() || BossInventoryUtil.SecondRewards.isEmpty() || BossInventoryUtil.ThirdRewards.isEmpty()) return;

            if (num2 == BossInventoryUtil.TopRewards.size()) {
                num2 = num2 - 1;
            }
            if (num3 == BossInventoryUtil.TopRewards.size()) {
                num3 = num3 - 1;
            }

            ItemStack sDrop = BossInventoryUtil.SecondRewards.get(num2);
            ItemStack tDrop = BossInventoryUtil.ThirdRewards.get(num3);
            SelectRewards.add(sDrop);
            SelectRewards.add(tDrop);
            if (sDrop != null && tDrop != null) {
                for (Player p : BossUtil.Player1) {
                    player = p;
                }
                for (ItemStack i : SelectRewards) {
                    player.getInventory().addItem(i);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "- " + i.getType().toString()));
                }
            }
        }
        if (!BossUtil.Player3.isEmpty()) {
            ArrayList<ItemStack> SelectRewards = new ArrayList<>();
            if (BossInventoryUtil.TopRewards.isEmpty() || BossInventoryUtil.SecondRewards.isEmpty() || BossInventoryUtil.ThirdRewards.isEmpty()) return;
            if (num3 == BossInventoryUtil.TopRewards.size()) {
                num3 = num3 - 1;
            }
            ItemStack tDrop = BossInventoryUtil.ThirdRewards.get(num3);
            SelectRewards.add(tDrop);
            if (tDrop != null) {
                for (Player p : BossUtil.Player1) {
                    player = p;
                }
                for (ItemStack i : SelectRewards) {
                    player.getInventory().addItem(i);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "- " + i.getType().toString()));
                }
            }
        }

        BossUtil.Player1.clear();
        BossUtil.Player2.clear();
        BossUtil.Player3.clear();

    }
}
