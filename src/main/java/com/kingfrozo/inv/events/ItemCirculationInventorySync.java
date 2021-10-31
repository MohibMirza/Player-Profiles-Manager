package com.kingfrozo.inv.events;

import com.kingfrozo.inv.RClasses.sync.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;

public class ItemCirculationInventorySync implements Listener {

    // TODO: MAKE A BOOLEAN THAT TRANSFORMS THIS CLASS INTO AN IMMUTABLE INVENTORY FOR MINIGAMES
    // TODO: HANDLE ALL TODOS BELOW (TODO TODO)

    public static boolean dropsAllowed = true;
    public static String errorMessage = ChatColor.RED + "Item drops/usage not allowed right now!";

    @EventHandler
    public void itemDrop(PlayerDropItemEvent event) {
        if(!dropsAllowed) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(errorMessage);
            return;
        }

        aSync(event.getPlayer());
    }

    @EventHandler
    public void itemPickup(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player)) { return; }

        if(!dropsAllowed) {
            event.setCancelled(true);
            event.getEntity().sendMessage(errorMessage);
            return;
        }

        Player player = (Player) event.getEntity();
        aSync(player);

        // PlayerItemConsumeEvent
        // PlayerChangedMainHandEvent
        // PlayerSwapHandItemsEvent
        // PlayerItemBreakEvent

        // InventoryMoveItemEvent
        // InventoryPickupItemEvent
    }

    @EventHandler
    public void swapHands(PlayerSwapHandItemsEvent event) {
        if(!dropsAllowed) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(errorMessage);
            return;
        }

        aSync(event.getPlayer());


    }

    // TODO: CHECK THIS OUT
    @EventHandler
    public void changedMainHand(PlayerChangedMainHandEvent event) {
        if(!dropsAllowed) {
            System.out.println("DEBUG: PlayerChangedMainHandEvent activated. Not properly handled yet. Seems to be a weird edge case of player changing" +
                    "some settings." );
            return;
        }
        aSync(event.getPlayer());


    }

    @EventHandler
    public void itemConsume(PlayerItemConsumeEvent event){
        if(!dropsAllowed) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(errorMessage);
            return;
        }
        aSync(event.getPlayer());


    }

    // TODO: CANCEL THIS EVENT OR HANDLE IT DIFFERENTLY
    @EventHandler
    public void itemBreak(PlayerItemBreakEvent event) {
        aSync(event.getPlayer());


    }

    // TODO: ALERT THE PLAYER
    @EventHandler
    public void invMove(InventoryMoveItemEvent event) {
        if(!dropsAllowed) {
            event.setCancelled(true);
            return;
        }

        System.out.println("Inventory Move Event : " + event.getDestination().getType().toString());
        if(event.getDestination().getType() != InventoryType.PLAYER) return;
        System.out.println("Inv Viewers: " + event.getDestination().getViewers().size());
        event.getDestination().getViewers().forEach(humanEntity -> {
            String name = humanEntity.getName();
            Player player = Bukkit.getPlayer(name);
            aSync(player);
        });
    }

    // TODO: HANDLE THIS PROPER, getWhoClicked() might send unneeded message
    @EventHandler
    public void invClick(InventoryClickEvent event) {
        if(!dropsAllowed) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(errorMessage);
            return;
        }
        Player player = (Player) event.getWhoClicked();
        aSync(player);
    }

    public static void aSync(Player player){
        Inventory.aSync(player, () -> {
            player.sendMessage("Inventory Updated!");
        });
    }
}
