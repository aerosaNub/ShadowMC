package com.shadowmc.plugin.commands;

import com.shadowmc.plugin.guis.SpawnerShopGUI;
import com.shadowmc.plugin.utils.C;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnerShopCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("spawnershop")) {

            if (sender.hasPermission("command.spawnershop")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "This command is player usage only!");
                    return true;
                }

                Player p = (Player) sender;

                SpawnerShopGUI.spawnerMenu(p);

                return true;
            } else {
                sender.sendMessage(C.c("&cNot enough permissions!"));
                return true;
            }

        }
        return false;
    }
}
