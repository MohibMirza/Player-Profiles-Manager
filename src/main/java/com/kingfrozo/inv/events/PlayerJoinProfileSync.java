package com.kingfrozo.inv.events;

import com.kingfrozo.inv.InventorySync.InventorySerialization;
import com.kingfrozo.inv.Main;
import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.Redis.RLORetrieval;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.redisson.api.RLiveObjectService;

public class PlayerJoinProfileSync implements Listener {

    private Main plugin = Main.getPlugin();
    private RLiveObjectService rlo = Main.getRLO();

//    @EventHandler
//    public void onJoin(PlayerJoinEvent event) {
//        System.out.println("test");
//        Player player = event.getPlayer();
//        String uuid = player.getUniqueId().toString();
//        String name = player.getName();
//
//        player.getInventory().clear();
//
//        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
//            RPlayer rPlayer = RLORetrieval.getLivePlayerProfileOnLogin(uuid, name);
//            if(Values.debug) System.out.println(name + " profile synced.");
//
//        });
//
//    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        InventorySerialization.serialize(player.getInventory()); // not sure why this is here??
        // if(player.hasPermission(Permissions.dontSyncInventory)){ return; } // ANNOYING TO DEBUG

        // if(player.getName().equals("KingFrozo") || player.getName().equals("flareman99")) return;

        player.getInventory().clear();
        System.out.println("Inventory cleared!");

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            RPlayer rPlayer = RLORetrieval.getLivePlayerProfileOnLogin(uuid, name);
            String title = rPlayer.getTitle();
            String inv = rPlayer.getInv();

            Bukkit.getScheduler().runTask(plugin, () -> {
                InventorySerialization.setInventory(player, inv);
                System.out.println("TODO: SET PREFIX TO THAT OF " + title);

            });
        });
    }

}
