package com.kingfrozo.inv;

import com.kingfrozo.inv.Redis.RLORetrieval;
import com.kingfrozo.inv.Redis.RedisClient;
import com.kingfrozo.inv.events.PlayerJoinProfileSync;
import com.kingfrozo.inv.events.PlayerLeaveProfileSync;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RLiveObjectService;

import java.io.IOException;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static RedisClient redis;
    private static RLiveObjectService rlo;


    @Override
    public void onEnable() {
        plugin = this;
        saveConfig();
        saveDefaultConfig();
        connectToRedis();
        getServer().getPluginManager().registerEvents(new PlayerJoinProfileSync(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveProfileSync(), this);
    }

    @Override
    public void onDisable() {
        disconnectRedis();
    }

    private void registerEvents(Server server) {
        server.getPluginManager().registerEvents(new PlayerJoinProfileSync(), this);
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static RLiveObjectService getRLO() {
        return rlo;
    }

    private static void connectToRedis() {
        redis = new RedisClient("redis://127.0.0.1:6379");

//        try {
//            redis.loadConfig("config.yml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        redis.start();

        rlo = redis.redisson.getLiveObjectService();
        RLORetrieval.rlo = rlo;

//        if(!rlo.isClassRegistered(RPlayer.class)) {
//            System.out.println("RPlayer.class not registered to Redisson RLO!");
//            rlo.registerClass(RPlayer.class);
//            System.out.println("Registered RPlayer.class on RLO.");
//        }
    }

    private static void disconnectRedis(){
        redis.shutdown();
    }

}
