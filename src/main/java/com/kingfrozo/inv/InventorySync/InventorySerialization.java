package com.kingfrozo.inv.InventorySync;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySerialization {

    private static final int INV_SIZE = 41;

    public static String serialize(Inventory inv) {
        String strInv = "";

        for(int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);


            if(item != null && item.getType() != Material.AIR
                    && CustomStack.byItemStack(item) != null){
                CustomStack cStack = CustomStack.byItemStack(item);
                strInv += cStack.getNamespacedID();
            }
            strInv += ",";
        }
        return strInv;
    }

    public static Inventory deserialize(String s) {

        Inventory inv = Bukkit.createInventory(null, 45);

        int beginIndex = 0;
        for(int i = 0; i < INV_SIZE; i++){
            int endIndex = s.indexOf(',', beginIndex);
            String itemName = s.substring(beginIndex, endIndex);
            beginIndex = endIndex+1;
            inv.setItem(i, ItemsAdder.getCustomItem(itemName));
        }

        return inv;
    }

    public static void setInventory(Player player, String s) {
        if (s.equals("")) return;
        int beginIndex = 0;
        for(int i = 0; i < INV_SIZE; i++) {
            int endIndex = s.indexOf(',', beginIndex);
            String itemName = s.substring(beginIndex, endIndex);
            beginIndex = endIndex + 1;
            if(itemName.equals("")) continue;
            player.getInventory().setItem(i, CustomStack.getInstance(itemName).getItemStack());

        }
    }

}
