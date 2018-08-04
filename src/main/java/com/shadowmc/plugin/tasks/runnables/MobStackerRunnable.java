package com.shadowmc.plugin.tasks.runnables;

import com.shadowmc.plugin.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class MobStackerRunnable extends BukkitRunnable {

    @Override
    public void run() {
        Main.getPlugin().getMST().stackMobs();
    }
}
