package com.kingfrozo.inv.Chat;

import com.kingfrozo.inv.Redis.RLORetrieval;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener {

     // TODO: MAKE LOCAL MAP OF PLAYERS' TITLE TO REDUCE REDIS CALLS

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        String rtitle = RLORetrieval.getLivePlayerProfile(uuid, name).getTitle();
        Title title = Title.titles.get(rtitle);
        String prefix = title.getTitle("§4");
        event.setFormat(prefix + " " + name + ": §f" + event.getMessage());




    }


}
