package com.kingfrozo.inv.RClasses.sync;

import com.kingfrozo.inv.InventorySync.InventorySerialization;
import com.kingfrozo.inv.Main;
import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.Redis.RLORetrieval;
import com.kingfrozo.inv.util.Callback;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Inventory {

    // SYNCHRONOUS SYNCING

    public static void sync(Player player) {
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        RPlayer rplayer = RLORetrieval.getLivePlayerProfile(uuid, name);
        if(rplayer == null) { // TODO: IMPLEMENT BETTER HANDLING
            player.sendMessage("Error Occurred: Please relogin");
        }
        rplayer.setInv(InventorySerialization.serialize(player.getInventory()));

    }

    // ASYNCHRONOUS SYNCING

    public static void aSync(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            sync(player);
        });
    }

    public static void aSync(Player player, Callback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            sync(player);
            callback.onFinish();
        });
    }

}
