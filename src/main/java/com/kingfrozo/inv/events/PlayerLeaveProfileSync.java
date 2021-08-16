package com.kingfrozo.inv.events;

import com.kingfrozo.inv.InventorySync.InventorySerialization;
import com.kingfrozo.inv.Main;
import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.Redis.RLORetrieval;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveProfileSync implements Listener {

    Main plugin = Main.getPlugin();


    // TODO: SYNC INVENTORIES UPON PICKUP/DROP EVENT
    // TODO: ADD MORE ACCURATE PLAYTIME
    // TODO: ADD PREFIXES AND RANKS THRU LUCKPERMS API

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        String inv = InventorySerialization.serialize(player.getInventory());

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            RPlayer rplayer = RLORetrieval.getLivePlayerProfile(uuid, name);
            rplayer.setInv(inv);
            int timeLoggedIn = (int) ((System.currentTimeMillis() - rplayer.getLastSeen()) / 1000 );
            rplayer.setPlaytime(rplayer.getPlaytime() + timeLoggedIn);

        });

    }
}
