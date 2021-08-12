package com.kingfrozo.inv.RClasses;

import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@REntity
public class RPlayer {

    @RId
    private String uuid; // PRIMARY KEY

    private String name;
    private String rank;
    private String title;
    private int money;
    private String inv;
    private long lastSeen;
    private int playtime;

    public RPlayer(String uuid, String name, String title, int money, String inv) {
        this.uuid = uuid;
        this.name = name;
        this.title = title;
        this.money = money;
        this.inv = inv;
        playtime = 0;
        lastSeen = System.currentTimeMillis();
    }

    // FIRST LOGIN CONSTRUCTOR
    public RPlayer(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        title = "default";
        money = 0;
        inv = ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
        playtime = 0;
        lastSeen = System.currentTimeMillis();
    }

    public RPlayer() { } // FOR USE BY REDISSON RLO MODULE

    public String getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getRank() { return rank; }

    public String getTitle() {
        return title;
    }

    public int getMoney() {
        return money;
    }

    public String getInv() {
        return inv;
    }

    public int getPlaytime() { return playtime; }

    public long getLastSeen() { return lastSeen; }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(String rank) { this.rank = rank; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void removeMoney(int money) { this.money -= money; }

    public void setInv(String inv) {
        this.inv = inv;
    }

    public void addPlaytime(int minutes) { playtime += minutes; }

    public void setLastSeen(long time) { lastSeen = time; }

    public void updatePlaytime() { playtime += (System.currentTimeMillis() - lastSeen) / 1000; }

}
