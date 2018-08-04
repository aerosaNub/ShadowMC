package com.shadowmc.plugin.handlers.files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Profile
{

    private static File playerdata;
    private static YamlConfiguration configuration;

    public void setReferred(UUID uuid) {
        File file = new File(playerdata, uuid.toString() + ".yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("referred", true);
        saveYaml(uuid, configuration);
    }

    public boolean alreadyReferred(UUID uuid) {
        return getYaml(uuid).getBoolean("referred");
    }

    public String getCode(UUID uuid) {
        return getYaml(uuid).getString("code");
    }

    public int getAmountRef(UUID uuid) {
        return getYaml(uuid).getInt("amount-referred");
    }

    public void addAmountRef(UUID uuid) {
        File file = new File(playerdata, uuid.toString() + ".yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("amount-referred", getAmountRef(uuid) + 1);
        saveYaml(uuid, configuration);
    }

    public void setNewCode(UUID uuid, String newCode) {
        File file = new File(playerdata, uuid.toString() + ".yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("code", newCode);
        saveYaml(uuid, configuration);
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.YELLOW.toString() + ChatColor.ITALIC + "Give this to your friends so you can receive money in game for referring you! /referral");
    }

    public void addMobCoins(Player p, int amount) {
        File file = new File(playerdata, p.getUniqueId().toString() + ".yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("mobcoins", getMobCoins(p) + amount);
        saveYaml(p.getUniqueId(), configuration);
    }

    public void setMobCoins(Player p, int amount) {
        File file = new File(playerdata, p.getUniqueId().toString() + ".yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("mobcoins", amount);
        saveYaml(p.getUniqueId(), configuration);
    }

    public int getMobCoins(Player p) {
        return getYaml(p.getUniqueId()).getInt("mobcoins");
    }

    /**
     * @param path The directories path for folder to be created at.
     */
    public static void setFolderPath(String path)
    {
        playerdata = new File(path);

        if (!playerdata.exists())
        {
            playerdata.mkdirs();
        }
    }

    /**
     * @param dataFolder The plugin's data file - plugin.getDataFolder()
     * @param folderName Folder name to go into the data folder.
     */
    public static void setFolderPath(File dataFolder, String folderName)
    {
        playerdata = new File(dataFolder + File.separator + folderName);

        if (!playerdata.exists())
        {
            playerdata.mkdirs();
        }
    }


    /**
     * @param uuid The uuid of the player to be checked.
     *
     * @return true if they have a .yml file.
     */
    public static boolean hasYaml(UUID uuid)
    {
        return getYaml(uuid) != null;
    }

    /**
     * @param uuid The uuid of the player to get their Yaml.
     * @return YamlConfiguration of the player otherwise null if couldn't be found.
     */
    public static YamlConfiguration getYaml(UUID uuid)
    {
        File file = new File(playerdata, uuid.toString() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        return !file.exists() ? null : config;
    }

    /**
     * @param uuid The uuid of the player.
     * @return config The YamlConfiguration file created.
     */
    public static YamlConfiguration createYaml(UUID uuid)
    {
        File file = new File(playerdata, uuid.toString() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);



        config.set("name", Bukkit.getPlayer(uuid).getName());
        config.set("mobcoins", 0);
        config.set("amount-referred", 0);
        config.set("referred", false);
        String code = UUID.randomUUID().toString();
        String officialCode = code.substring(30);
        config.set("code", officialCode);



        saveYaml(uuid, config);

        return config;
    }

    /**
     * @exception IOException
     *
     * @param uuid The uuid of the owner's config.
     * @param config The YamlConfiguration to be saved.
     * @return true if successfully saved.
     */
    public static boolean saveYaml(UUID uuid, YamlConfiguration config)
    {
        File file = new File(playerdata, uuid.toString() + ".yml");

        try
        {
            config.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
