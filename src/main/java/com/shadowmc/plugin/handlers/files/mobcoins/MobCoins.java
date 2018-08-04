package com.shadowmc.plugin.handlers.files.mobcoins;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.handlers.objects.MobCoinsOBJ;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;

public class MobCoins {

    /*
        File Creation
     */

    private File file;
    private YamlConfiguration configuration;
    private ArrayList<MobCoinsOBJ> mobCoins = new ArrayList<>(); // Not Needed, Remove later also remove Object class.
    private String path = "MobCoins-Shop";

    public MobCoins() {
        this.file = new File(Main.getPlugin().getDataFolder(), "mobcoins.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);

        doesFileExist();
    }

    /*
        Checking if file exists
     */

    public void doesFileExist() {
        File file = new File(Main.getPlugin().getDataFolder(), "mobcoins.yml");
        if (!file.exists()) {
            /*
                Generating new files
             */

            try {
                /*
                    Creating new file and writing in it.
                 */

                file.createNewFile();

                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                configuration.set("MobCoins-Shop.Inventory.size", 32);
                configuration.set("MobCoins-Shop.Inventory.name", "&cMC Shop");

                configuration.set("MobCoins-Shop.Items.Stone.itemid", 1);
                configuration.set("MobCoins-Shop.Items.Stone.displayName", "&7Stone");
                configuration.set("MobCoins-Shop.Items.Stone.slot", 4);
                configuration.set("MobCoins-Shop.Items.Stone.amount", 32);
                configuration.set("MobCoins-Shop.Items.Stone.price", 100);



                /*
                    Saving new config with data stored inside itself.
                 */
                configuration.save(file);
            }catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            /*
                If file already exists, the server will load all the items and data needed to add to the inventory. Using an object class for this. (MobCoinsOBJ.java)
             */

            for (String keys : getConfiguration().getConfigurationSection("MobCoins-Shop.Items").getKeys(false)) {
                mobCoins.add(new MobCoinsOBJ(getConfiguration().getInt(path + ".Inventory.size"),
                        getConfiguration().getString(path + ".Inventory.name"),
                        getConfiguration().getInt(path + ".Items." + keys + ".itemid"),
                                getConfiguration().getInt(path + ".Items." + keys + ".slot"),
                        getConfiguration().getString(path + ".Items." + keys + ".displayName"),
                                        getConfiguration().getInt(path + ".Items." + keys + ".price")));
            }
        }
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

}
