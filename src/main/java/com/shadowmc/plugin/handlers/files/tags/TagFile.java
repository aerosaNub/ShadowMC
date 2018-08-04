package com.shadowmc.plugin.handlers.files.tags;

import com.shadowmc.plugin.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TagFile {

    private File file;
    private YamlConfiguration configuration;

    public TagFile() {
        this.file = new File(Main.getPlugin().getDataFolder(), "tags.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try{
                this.file.createNewFile();
                this.configuration.save(file);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void save() {
        if ((configuration == null) || (this.file == null)) {
            return;
        }
        try {
            getConfig().save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.configuration;
    }
}
