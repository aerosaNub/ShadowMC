package com.shadowmc.plugin.tasks.runnables;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.utils.C;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class BCRunnable extends BukkitRunnable {

    private int broadcastNumber = 0;
    private List<String> value;

    public void run() {

        Set<String> keys =  Main.getPlugin().getBcFile().getConfiguration().getConfigurationSection("Broadcast").getKeys(false);

        for (String key : keys) {

            if (broadcastNumber == 0) {
                int broadcasts = keys.size();
                broadcastNumber = broadcasts;
            }

            value = Main.getPlugin().getBcFile().getMessages().get(broadcastNumber - 1).getFormats();

        }

        for (String msg : value) {
            Bukkit.broadcastMessage(C.c(msg));
        }

        broadcastNumber = broadcastNumber - 1;

    }
}
