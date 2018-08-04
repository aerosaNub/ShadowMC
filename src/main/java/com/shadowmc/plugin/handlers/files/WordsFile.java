package com.shadowmc.plugin.handlers.files;

import com.shadowmc.plugin.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WordsFile {

    private File file;
    private YamlConfiguration configuration;
    private List<String> bannedWords = new ArrayList<>();

    public WordsFile() {
        this.file = new File(Main.getPlugin().getDataFolder(), "words.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);

        this.fileSetup();
    }

    public void fileSetup() {
        if (!(this.file.exists())) {
            try {
                YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
                this.file.createNewFile();
                c.set("Blocked-Words", bannedWords);
                c.save(file);
            } catch (Exception e) {
                System.out.println("words.yml had problems creating itself!");
            }
        } else {
            loadWords();
        }
    }

    public void loadWords() {
        YamlConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
        if (fileConfig.contains("Blocked-Words")) {
            for (String cmd : getConfig().getStringList("Blocked-Words")) {
                getBannedWords().add(cmd);
            }
        }
    }

    public void addNewWord(List<String> word) {
        try {
            File file = new File(Main.getPlugin().getDataFolder(), "words.yml");
            YamlConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
            fileConfig.set("Blocked-Words", word);
            fileConfig.save(file);
            loadWords();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getBannedWords() {
        return bannedWords;
    }

    public FileConfiguration getConfig() {
        return this.configuration;
    }
}
