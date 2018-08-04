package com.shadowmc.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WOTDCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label ,String[] args) {

        if (sender.hasPermission("command.wotd")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "/wotd <word>");
                return true;
            }

            String word = String.valueOf(args[0]);

            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&eWord of the day&7: &a" + word));

            return true;

        }

        return false;
    }
}
