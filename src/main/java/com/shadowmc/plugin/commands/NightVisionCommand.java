package com.shadowmc.plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players!");
            return true;
        }

        if (sender.hasPermission("nightvision.use")) {

            Player p = (Player) sender;

            if (!(p.hasPotionEffect(PotionEffectType.NIGHT_VISION))) {

                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));

                p.sendMessage(ChatColor.YELLOW + "Night Vision has been " + ChatColor.GREEN + "enabled!");
                return true;

            } else {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                p.sendMessage(ChatColor.YELLOW + "Night Vision has been " + ChatColor.RED + "disabled!");
                return true;
            }


        }

        return false;
    }
}
