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


    // TODO: REMOVE SYNCING OF INVENTORIES WHEN LEAVING SERVER
    // DATA SHOULD BE SYNCED DURING GAMEPLAY ONLY, NOT WHEN LEAVING
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        String inv = InventorySerialization.serialize(player.getInventory());

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            RPlayer rplayer = RLORetrieval.getLivePlayerProfile(uuid, name);
            rplayer.setInv(inv);
        });

    }
}
