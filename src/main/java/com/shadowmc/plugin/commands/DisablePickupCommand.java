package com.shadowmc.plugin.commands;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.handlers.objects.Pickup;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisablePickupCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED  + "/blockfilter <item> - Blocks pickup for any items added!");
            return true;
        }

        String itemName = String.valueOf(args[0]);

        Material material;

        try {
            material = Material.valueOf(itemName.toUpperCase());
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Material cannot be matched with anything!");
            return true;
        }

        Player p = (Player) sender;

        Main.getPlugin().getPickup().addPickups(p, material);


        p.sendMessage(ChatColor.YELLOW+  "You've added " + ChatColor.GRAY + itemName + " " + ChatColor.YELLOW + "to your blockfilter list.");

        return true;
    }
}
