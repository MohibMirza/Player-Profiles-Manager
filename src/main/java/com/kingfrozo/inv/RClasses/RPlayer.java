package com.kingfrozo.inv.RClasses;

import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@REntity
public class RPlayer {

    @RId
    private String uuid; // PRIMARY KEY

    private String name;
    private String title;
    private int money;
    private String inv;

    public RPlayer(String uuid, String name, String title, int money, String inv) {
        this.uuid = uuid;
        this.name = name;
        this.title = title;
        this.money = money;
        this.inv = inv;
    }

    // FIRST LOGIN CONSTRUCTOR
    public RPlayer(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        title = "default";
        money = 0;
        inv = ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
    }

    public RPlayer() { } // FOR USE BY REDISSON RLO MODULE

    public String getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getMoney() {
        return money;
    }

    public String getInv() {
        return inv;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setInv(String inv) {
        this.inv = inv;
    }

}
