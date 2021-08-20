package com.kingfrozo.inv.RClasses.sync;

import com.kingfrozo.inv.Main;
import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.Redis.RLORetrieval;
import com.kingfrozo.inv.util.Callback;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Playtime {

    // SYNCHRONOUS SYNCING

    public static void sync(Player player) {
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        RPlayer rPlayer = RLORetrieval.getLivePlayerProfile(uuid, name);

        if(rPlayer == null) {
            System.out.println("Null RPLAYER! Cannot perform Playtime Sync...");
        }
        int playtime = rPlayer.getPlaytime();
        long lastTimePlaytimeSynced = rPlayer.getLastTimePlaytimeSynced();
        long currTime = System.currentTimeMillis();
        int sessionTime = (int) (currTime - lastTimePlaytimeSynced);

        rPlayer.setPlaytime(playtime + sessionTime);
        rPlayer.setLastTimePlaytimeSynced(currTime);
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
