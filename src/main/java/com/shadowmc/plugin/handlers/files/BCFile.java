package com.shadowmc.plugin.handlers.files;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.handlers.objects.BC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BCFile {

    private File file;
    private YamlConfiguration configuration;
    private List<BC> messages = new ArrayList<>();

    public BCFile() {
        this.file = new File(Main.getPlugin().getDataFolder(), "bc_messages.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);

        fileExist();
    }

    public void fileExist() {
        if (!file.exists()) {
            try {
                file.createNewFile();

                configuration.set("Broadcast.message1.format", new ArrayList<String>());
                configuration.set("Broadcast.message2.format", new ArrayList<String>());

                configuration.save(file);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                for (String keys : this.configuration.getConfigurationSection("Broadcast").getKeys(false)) {
                    List<String> fileMessages = this.configuration.getStringList("Broadcast." + keys + ".format");

                    this.messages.add(new BC(fileMessages));

                    System.out.println("Loaded: "+ fileMessages);

                }
            } catch (Exception e) {
                System.out.println("Unable to load Broadcast Formats!");
            }
        }
    }

    public List<BC> getMessages() {
        return messages;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
