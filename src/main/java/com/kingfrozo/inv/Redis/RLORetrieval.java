package com.kingfrozo.inv.Redis;

import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.config.Values;
import org.bukkit.Bukkit;
import org.redisson.api.RLiveObjectService;

public class RLORetrieval {

    // IF CANT FIND PLAYER PROFILE KICK THEM FROM THE SERVER

    // private static com.kingfrozo.inv.Main plugin = com.kingfrozo.inv.Main.getPlugin();
    // private static RLiveObjectService rlo = com.kingfrozo.inv.Main.getRLO();
    public static RLiveObjectService rlo;


    public static RPlayer getLivePlayerProfileOnLogin(String uuid, String name) {
        RPlayer rPlayer = retrieveProfile(uuid);

        if(rPlayer == null) {
            rPlayer = new RPlayer(uuid, name);

            rlo.persist(rPlayer);
            rPlayer = retrieveProfile(uuid);
            if(Values.debug) System.out.println(name + " has joined network for first time. Creating new player profile...");
        }
        rPlayer.setLastSeen(System.currentTimeMillis());
        return rPlayer; // ALWAYS RETURNS AN RPLAYER
    }

    public static RPlayer getLivePlayerProfile(String uuid, String name) {
        RPlayer rPlayer = retrieveProfile(uuid);

        if(rPlayer == null) {
//            Bukkit.getScheduler().runTask(plugin, () -> {
//                Bukkit.getPlayer(name).kickPlayer("[Memory Cache Retrieval Error] Please log back in again");
//            });

            if(Values.debug) System.out.println("Player not found! Kicking " + name + "for relog");
        }

        return rPlayer; // MAY RETURN NULL
    }

    public static RPlayer retrieveProfile(String uuid) {
        if(Values.debug) System.out.println("Retrieving profile...");
        if(Values.debug) System.out.println("RLO Service Available: " + (rlo != null));
        return rlo.get(RPlayer.class, uuid);
    }
}
