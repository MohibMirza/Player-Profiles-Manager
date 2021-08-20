package com.kingfrozo.inv.events;

import com.kingfrozo.inv.RClasses.sync.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;

public class ItemCirculationInventorySync implements Listener {

    // TODO: MAKE A BOOLEAN THAT TRANSFORMS THIS CLASS INTO AN IMMUTABLE INVENTORY FOR MINIGAMES

    @EventHandler
    public void itemDrop(PlayerDropItemEvent event) {
        aSync(event.getPlayer());
    }

    @EventHandler
    public void itemPickup(EntityPickupItemEvent event) { ;
        if(!(event.getEntity() instanceof Player)) { return; }

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
        aSync(event.getPlayer());


    }

    @EventHandler
    public void changedMainHand(PlayerChangedMainHandEvent event) {
        aSync(event.getPlayer());


    }

    @EventHandler
    public void itemConsume(PlayerItemConsumeEvent event){
        aSync(event.getPlayer());


    }

    @EventHandler
    public void itemBreak(PlayerItemBreakEvent event) {
        aSync(event.getPlayer());


    }

    @EventHandler
    public void invMove(InventoryMoveItemEvent event) {
        System.out.println("Inventory Move Event : " + event.getDestination().getType().toString());
        if(event.getDestination().getType() != InventoryType.PLAYER) return;
        System.out.println("Inv Viewers: " + event.getDestination().getViewers().size());
        event.getDestination().getViewers().forEach(humanEntity -> {
            String name = humanEntity.getName();
            Player player = Bukkit.getPlayer(name);
            aSync(player);
        });
    }

    @EventHandler
    public void invClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        aSync(player);
    }

    public static void aSync(Player player){
        Inventory.aSync(player, () -> {
            player.sendMessage("Inventory Updated!");
        });
    }
}
