package com.kingfrozo.inv.RClasses.sync;

import com.kingfrozo.inv.Main;
import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.Redis.RLORetrieval;
import com.kingfrozo.inv.util.Callback;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FullScaleSync {

    // TODO: MAKE LESS CALLS TO RLOS BY PASSING RPLAYER AS PARAMETER

    // SYNCHRONOUS SYNCING

    public static void sync(Player player) {
            Inventory.sync(player);
            Playtime.sync(player);
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
