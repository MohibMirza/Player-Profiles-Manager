package com.kingfrozo.inv.events;

import com.kingfrozo.inv.InventorySync.InventorySerialization;
import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.RClasses.RPlayerHandler;
import com.kingfrozo.inv.Redis.RLORetrieval;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;

public class ItemCirculationInventorySync implements Listener {

    // TODO: MAKE A BOOLEAN THAT TRANSFORMS THIS CLASS INTO AN IMMUTABLE INVENTORY FOR MINIGAMES

    @EventHandler
    public void itemDrop(PlayerDropItemEvent event) {
        RPlayerHandler.updateInventory(event.getPlayer());
        event.getPlayer().sendMessage("Inventory Updated!");
    }

    @EventHandler
    public void itemPickup(EntityPickupItemEvent event) { ;
        if(!(event.getEntity() instanceof Player)) { return; }

        Player player = (Player) event.getEntity();
        RPlayerHandler.updateInventory(player);
        player.sendMessage("Inventory Updated!");

        // PlayerItemConsumeEvent
        // PlayerChangedMainHandEvent
        // PlayerSwapHandItemsEvent
        // PlayerItemBreakEvent

        // InventoryMoveItemEvent
        // InventoryPickupItemEvent
    }

    @EventHandler
    public void swapHands(PlayerSwapHandItemsEvent event) {
        RPlayerHandler.updateInventory(event.getPlayer());
        event.getPlayer().sendMessage("Inventory Updated!");

    }

    @EventHandler
    public void changedMainHand(PlayerChangedMainHandEvent event) {
        RPlayerHandler.updateInventory(event.getPlayer());
        event.getPlayer().sendMessage("Inventory Updated!");

    }

    @EventHandler
    public void itemConsume(PlayerItemConsumeEvent event){
        RPlayerHandler.updateInventory(event.getPlayer());
        event.getPlayer().sendMessage("Inventory Updated!");

    }

    @EventHandler
    public void itemBreak(PlayerItemBreakEvent event) {
        RPlayerHandler.updateInventory(event.getPlayer());
        event.getPlayer().sendMessage("Inventory Updated!");

    }

    @EventHandler
    public void invMove(InventoryMoveItemEvent event) {
        System.out.println("Inventory Move Event : " + event.getDestination().getType().toString());
        if(event.getDestination().getType() != InventoryType.PLAYER) return;
        System.out.println("Inv Viewers: " + event.getDestination().getViewers().size());
        event.getDestination().getViewers().forEach(humanEntity -> {
            String name = humanEntity.getName();
            Player player = Bukkit.getPlayer(name);
            RPlayerHandler.updateInventory(player);
            player.sendMessage("Inventory Updated!!");
        });
    }

    @EventHandler
    public void invClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        RPlayerHandler.updateInventory(player);
        player.sendMessage("Inventory Updated!");
    }
}
