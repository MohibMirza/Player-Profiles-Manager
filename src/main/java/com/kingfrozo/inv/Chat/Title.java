package com.kingfrozo.inv.Chat;

import com.kingfrozo.inv.Main;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Title {

    public static Map<String, Title> titles = new HashMap<String, Title>();

    public static Main plugin = Main.getPlugin();

    private String key;
    private String title;
    private List<String> desc; // unadded so far
    private char icon;
    private int length;
    private int customModelData;

    public Title(String key, String title, char icon, int length, int customModelData) {
        this.key = key;
        this.title = title;
        this.icon = icon;
        this.length = length;
        this.customModelData = customModelData;

        titles.put(key, this);
    }

    public static void readTitles() { // STATIC: WILL READ IN ALL THE TITLES FROM CONFIG.YML AND MAP THEM
        System.out.println("Reading titles...");
        ConfigurationSection config = plugin.getConfig().getConfigurationSection("titles");
        System.out.println("Acquired configuration section " + (config == null));
        Set<String> keys = config.getKeys(false);

        System.out.println(keys.size());

        for(String titleName : keys) {
            // System.out.println(titleName);
            String title = config.getString(titleName + ".Title");
            char icon = ' ';
            int length = config.getInt(titleName + ".Characters");
            int customModelData = config.getInt(titleName + ".CustomModelData");

            Title t = new Title(titleName, title, icon, length, customModelData);

            titles.put(titleName, t);

            System.out.println(titles.get(titleName).toString());
        }
    }

    public static void syncTitle(String playerName, String title_key) {
        Title title = titles.get(title_key);



    }

    public String getTitle(String color) { // color updates to whatever color is
        return color + title.substring(0, 2) + "§f" + title.charAt(2) + color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public char getIcon() {
        return icon;
    }

    public void setIcon(char icon) {
        this.icon = icon;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public String toString() {
        String info = "Key: " + key;
        info += "\n     title: " + title;
        info += "\n     icon: " + icon;
        info += "\n     length: " + length;
        info += "\n     CustomModelData: " + customModelData;

        return info;

    }
}