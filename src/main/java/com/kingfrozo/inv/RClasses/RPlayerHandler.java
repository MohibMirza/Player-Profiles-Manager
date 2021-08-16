package com.kingfrozo.inv.RClasses;

import com.kingfrozo.inv.InventorySync.InventorySerialization;
import com.kingfrozo.inv.Redis.RLORetrieval;
import org.bukkit.entity.Player;

public class RPlayerHandler {

    public static void updateInventory(Player player) {
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        RPlayer rplayer = RLORetrieval.getLivePlayerProfile(uuid, name);
        if(rplayer == null) { // TODO: IMPLEMENT BETTER HANDLING
            player.sendMessage("Error Occurred: Please relogin");
        }
        rplayer.setInv(InventorySerialization.serialize(player.getInventory()));
    }

}
