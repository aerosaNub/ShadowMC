package com.shadowmc.plugin.handlers.files.tags;

import com.shadowmc.plugin.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public class TagDatabaseFile {

    private File file;
    private YamlConfiguration configuration;

    public TagDatabaseFile() {
        this.file = new File(Main.getPlugin().getDataFolder(), "database-tags.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);

        createFile();
    }

    private void createFile() {
        final File folder = Main.getPlugin().getDataFolder();
        try {
            final File file = new File(folder, "database-tags.yml");
            if (!file.exists()) {
                if (Main.getPlugin().getResource("database-tags.yml") != null) {
                    Main.getPlugin().saveResource("database-tags.yml", false);
                }
                else {
                    this.configuration.save(file);
                }
            }
            else {
                this.configuration.load(file);
                this.configuration.save(file);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
