package com.kingfrozo.inv.events;

import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.Redis.RLORetrieval;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractTestEvent implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String username = player.getName();

        if(player.getInventory().getItemInMainHand().getType() != Material.STICK){
            return;
        }

        RPlayer rplayer = RLORetrieval.getLivePlayerProfile(uuid, username);
        System.out.println("Last Seen: " + rplayer.getLastSeen());
        System.out.println("Playtime: " + rplayer.getPlaytime());

    }
}
