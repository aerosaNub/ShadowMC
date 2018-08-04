package com.shadowmc.plugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ServerListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.getWorld().setWeatherDuration(0);
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityKilled(EntityDeathEvent e) {
        Entity entity = e.getEntity();

        if (entity instanceof Endermite) {
            Endermite endermite = (Endermite) entity;
            if (!(endermite.getKiller() instanceof Player)) return;
            Random random = new Random();
            int chances = random.nextInt(100);

            if (chances >= 0 && chances <= 50) {
                endermite.getWorld().dropItemNaturally(endermite.getLocation(), new ItemStack(Material.GOLD_INGOT));

                if (endermite.getKiller() instanceof Player) {
                    endermite.getKiller().sendMessage(ChatColor.YELLOW + "The endermite has dropped a " + ChatColor.GOLD.toString() + ChatColor.BOLD + "GOLD_INGOT " + ChatColor.YELLOW + "randomly!");
                }
            }
        }

        if (entity instanceof Silverfish) {

            Silverfish silverfish = (Silverfish) entity;
            if (!(silverfish.getKiller() instanceof Player)) return;
            Random random = new Random();
            int chances = random.nextInt(100);

            if (chances >= 0 && chances <= 50) {
                silverfish.getWorld().dropItemNaturally(silverfish.getLocation(), new ItemStack(Material.DIAMOND));

                if (silverfish.getKiller() instanceof Player) {
                    silverfish.getKiller().sendMessage(ChatColor.YELLOW + "The silverfish has dropped a " + ChatColor.AQUA.toString() + ChatColor.BOLD + "DIAMOND " + ChatColor.YELLOW + "randomly!");
                }
            }
        }
    }
}
