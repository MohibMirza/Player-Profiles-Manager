package com.kingfrozo.inv;

import com.kingfrozo.inv.Chat.ChatFormat;
import com.kingfrozo.inv.Chat.ChatMarkdownEvent;
import com.kingfrozo.inv.Redis.RLORetrieval;
import com.kingfrozo.inv.Redis.RedisClient;
import com.kingfrozo.inv.Chat.Title;
import com.kingfrozo.inv.events.*;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RLiveObjectService;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static RedisClient redis;
    private static RLiveObjectService rlo;


    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        connectToRedis();
        getServer().getPluginManager().registerEvents(new PlayerJoinProfileSync(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveProfileSync(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractTestEvent(), this);
        getServer().getPluginManager().registerEvents(new ItemCirculationInventorySync(), this);
        getServer().getPluginManager().registerEvents(new ChatMarkdownEvent(), this);
        Title.readTitles();
        Title.titles.forEach((s, title) -> {
            System.out.println(title.toString());
        });
        System.out.println("Titles loaded: " + Title.titles.size());

        getServer().getPluginManager().registerEvents(new ChatFormat(), this);

    }

    @Override
    public void onDisable() {
        //disconnectRedis();
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

    public static void disconnectRedis(){
        redis.shutdown();
    }

}
