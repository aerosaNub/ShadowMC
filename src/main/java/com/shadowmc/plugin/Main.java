package com.shadowmc.plugin;

import com.shadowmc.plugin.commands.*;
import com.shadowmc.plugin.handlers.files.Profile;
import com.shadowmc.plugin.handlers.files.Config;
import com.shadowmc.plugin.handlers.files.WordsFile;
import com.shadowmc.plugin.handlers.files.mobcoins.MobCoins;
import com.shadowmc.plugin.handlers.files.tags.TagDatabaseFile;
import com.shadowmc.plugin.handlers.files.tags.TagFile;
import com.shadowmc.plugin.handlers.managers.TagManager;
import com.shadowmc.plugin.handlers.objects.Pickup;
import com.shadowmc.plugin.listeners.*;
import com.shadowmc.plugin.tabcompleters.StopPluginShow;
import com.shadowmc.plugin.tasks.MobStackerTask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin
{

    private static Main plugin;
    private Profile profile;
    private Config regFile;
    private Economy economy = null;
    private WordsFile wfile;
    private Pickup pickup;
    private MobStackerTask mobStackerTask;
    private TagManager tagManager;
    private TagFile tagFile;
    private TagDatabaseFile tagDatabaseFile;
    private MobCoins mc;
    private Logger log = Logger.getLogger("Minecraft");

    public void onEnable() {

        plugin = this;

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!this.getDataFolder().exists()) {
            try {
                this.getDataFolder().mkdir();
                System.out.println("Since the server couldn't find the folder for -PlayerReferral-, the system has created one!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        StopPluginShow.stopShowPlugins();

        this.tagFile = new TagFile();
        this.tagDatabaseFile = new TagDatabaseFile();
        this.mobStackerTask = new MobStackerTask(this);
        this.pickup=  new Pickup();
        this.tagManager = new TagManager(this);
        this.profile = new Profile();
        this.regFile = new Config();
        this.wfile = new WordsFile();
        this.mc = new MobCoins();
        Profile.setFolderPath("plugins/ShadowMC/players");
        PluginManager pm = Bukkit.getPluginManager();

       // new MobStackerRunnable().runTaskLaterAsynchronously(this, 3 * 20);

        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new ServerListener(), this);
        pm.registerEvents(new MobStackerListener(), this);
        pm.registerEvents(new TagInventoryListener(this), this);

        getCommand("tags").setExecutor(new TagCommand(this));
        getCommand("spawnershop").setExecutor(new SpawnerShopCommand());
        getCommand("referral").setExecutor(new ReferralCommand());
        getCommand("protection").setExecutor(new ProtectionCommand());
        getCommand("wotd").setExecutor(new WOTDCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand());
        getCommand("blockfilter").setExecutor(new DisablePickupCommand());
        getCommand("mobcoins").setExecutor(new MobCoinsCommand());
        getCommand("season").setExecutor(new SeasonCommand());
    }

    public void onDisable() {

        try {
            this.tagFile.save();
            this.tagDatabaseFile.save();
        } catch (Exception e) {
            System.out.println("Unable to save tag files.");
        }

        plugin = null;

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }


    public static Main getPlugin() {
        return plugin;
    }

    public Profile getProfile() {
        return profile;
    }

    public Config getRegFile() {
        return regFile;
    }

    public WordsFile getWfile() {
        return wfile;
    }

    public Economy getEconomy() {
        return economy;
    }

    public Pickup getPickup() {
        return pickup;
    }

    public MobStackerTask getMST() {
        return mobStackerTask;
    }

    public TagManager getTagManager() {
        return tagManager;
    }

    public TagDatabaseFile getTagDatabaseFile() {
        return tagDatabaseFile;
    }

    public TagFile getTagFile() {
        return tagFile;
    }

    public MobCoins getMc() {
        return mc;
    }
}
