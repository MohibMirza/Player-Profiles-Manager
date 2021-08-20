package com.kingfrozo.inv.Chat;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatMarkdownEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage().trim();

        String urlRegex = "(.+)\\((https?://)?(www\\.)?([a-zA-Z0-9]{1,24}\\.[a-zA-Z]{2,4})\\)(.*)";

        if (message.matches(urlRegex)) {
            String url = message.replaceFirst(urlRegex, "$2$3$4");
            String name = message.replaceFirst(urlRegex, "$1$5");

            if (!url.startsWith("http"))
                url = "https://" + url;

            message = message.replaceFirst(urlRegex, name)
                    .replaceAll("\\*\\*(.+)\\*\\*", "§l$1§r")
                    .replaceAll("\\*(.+)\\*", "§o$1§r")
                    .replaceAll("_(.+)_", "§n$1§r");

            TextComponent component = new TextComponent(message);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));

            String prefix = event.getFormat().split(" ")[0].replaceFirst("%1\\$s", player.getName());

            for (Player all : Bukkit.getOnlinePlayers())
                all.spigot().sendMessage(new ComponentBuilder().append(prefix + " ").append(component).create());

            event.setCancelled(true);
            return;
        }

        message = message
                .replaceAll("\\*\\*([[^*]]+)\\*\\*", "§l$1§r")
                .replaceAll("\\*([[^*]]+)\\*", "§o$1§r")
                .replaceAll("_(.+)_", "§n$1§r");

        event.setMessage(message);

    }
}
