package com.shadowmc.plugin.handlers.files;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.handlers.objects.Rewards;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Config
{
    private File file;
    private YamlConfiguration configuration;
    private List<String> rewards = new ArrayList<>();
    private List<String> commands = new ArrayList<>();

    public Config() {
        this.file = new File(Main.getPlugin().getDataFolder(), "config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);
        loadRewards();

        setupCommandUsages();

        setupRegular();

    }

    public void loadRewards() {
        File rewardFile = new File(Main.getPlugin().getDataFolder(), "config.yml");

        if (!Main.getPlugin().getDataFolder().exists()) {
            try {
                Main.getPlugin().getDataFolder().createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (rewardFile.exists()) {
            YamlConfiguration fileConfig = YamlConfiguration.loadConfiguration(rewardFile);

            if (fileConfig.contains("Rewards")) {
               for (String test : getConfig().getStringList("Rewards.commands")) {
                   this.rewards.add(test);
               }
            }
        } else {
            try {
                rewardFile.createNewFile();
                YamlConfiguration rewardConfig = YamlConfiguration.loadConfiguration(rewardFile);
                rewardConfig.set("Rewards.commands", "eco give %user% 1000");
                rewardConfig.save(rewardFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setupCommandUsages() {
        File file = new File(Main.getPlugin().getDataFolder(), "config.yml");

        if (!Main.getPlugin().getDataFolder().exists()) {
            try {
                Main.getPlugin().getDataFolder().createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (file.exists()) {
        YamlConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
        if (fileConfig.contains("Commands-To-Confirm")) {
            for (String cmd : getConfig().getStringList("Commands-To-Confirm")) {
                getCommands().add(cmd);
            }
        }
        } else {
            try {
                file.createNewFile();
                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                configuration.set("Commands-To-Confirm", commands);
                configuration.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setupRegular() {
        File file = new File(Main.getPlugin().getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                configuration.set("Messages.newUser", "&eWelcome %user% to the server!");
                configuration.set("Messages.oldUser", "&eWelcome back to the server %user%!");
                configuration.set("Messages.userLeave", "&e%user% has left the server!");
                configuration.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            if (configuration.getString("Messages") == null) {
                configuration.set("Messages.newUser", "&eWelcome %user% to the server!");
                configuration.set("Messages.oldUser", "&eWelcome back to the server %user%!");
                configuration.set("Messages.userLeave", "&e%user% has left the server!");
                configuration.set("Messages.season.messages", new ArrayList<String>());
                try {
                    configuration.save(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public FileConfiguration getConfig() {
        return this.configuration;
    }


    public List<String> getCommands() {
        return commands;
    }

    public void addCommands(List<String> cmds) {
        try {
            File file = new File(Main.getPlugin().getDataFolder(), "config.yml");
            YamlConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
            fileConfig.set("Commands-To-Confirm", cmds);
            fileConfig.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
