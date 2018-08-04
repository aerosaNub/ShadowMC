package com.shadowmc.plugin.commands;

import com.shadowmc.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class ReferralCommand implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {

            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Referral Commands:");
            player.sendMessage(ChatColor.YELLOW + "/" + label + " code <referral-code> - Insert your friend's referral-code here!");
            player.sendMessage(ChatColor.YELLOW +"/" + label + " setcode <referral-code> - Don't like your current code? Change it here.");
            player.sendMessage(ChatColor.YELLOW + "");
            player.sendMessage(ChatColor.YELLOW  + "Your Referral Code: " + ChatColor.LIGHT_PURPLE + Main.getPlugin().getProfile().getCode(player.getUniqueId()));

            return true;

        }

        if (args[0].equalsIgnoreCase("code")) {
            if (args.length == 1) {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Not Enough Arguments!");
                player.sendMessage(ChatColor.YELLOW + "/" + label + " code <referral-code> - Insert your friend's referral-code here!");
                player.sendMessage(ChatColor.YELLOW + "");
                player.sendMessage(ChatColor.YELLOW  + "Your Referral Code: " + ChatColor.LIGHT_PURPLE + Main.getPlugin().getProfile().getCode(player.getUniqueId()));
                return true;
            }

            String code = String.valueOf(args[1]);

            if (!Main.getPlugin().getProfile().alreadyReferred(player.getUniqueId())) {

                for (File files : new File(Main.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "players").listFiles()) {
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(files);

                    if (config.getString("code") != null) {
                        if (config.getString("code").equalsIgnoreCase(code)) {
                            String referredPlayer = config.getString("name");
                            player.sendMessage(ChatColor.GREEN + "You have referred " + ChatColor.LIGHT_PURPLE + referredPlayer + ChatColor.YELLOW + "!");
                            Main.getPlugin().getProfile().setReferred(player.getUniqueId());


                            Player user = Bukkit.getPlayer(referredPlayer);
                            for (String cmds : Main.getPlugin().getRegFile().getConfig().getStringList("Rewards.commands")) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds.replaceAll("%user%", user.getName()));
                            }
                            Main.getPlugin().getProfile().addAmountRef(user.getUniqueId());
                            if (user.isOnline()) {
                                user.sendMessage(ChatColor.YELLOW + "Your referral code has been used by " + player.getName() + "!");
                                return true;
                            }

                            return true;
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are only able to refer 1 person only, 0/1 usage left!");
                return true;
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("setcode")) {
            if (args.length == 1) {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Not Enough Arguments!");
                player.sendMessage(ChatColor.YELLOW + "/" + label + " setcode <referral-code> - Don't like your current code? Change it here.");
                player.sendMessage(ChatColor.YELLOW + "");
                player.sendMessage(ChatColor.YELLOW + "Your Referral Code: " + ChatColor.LIGHT_PURPLE + Main.getPlugin().getProfile().getCode(player.getUniqueId()));
                return true;
            }
            String oldCode = Main.getPlugin().getProfile().getCode(player.getUniqueId());
            String newCode = String.valueOf(args[1]);

            player.sendMessage(ChatColor.GREEN + "You code has been changed from " + ChatColor.LIGHT_PURPLE + oldCode + ChatColor.GREEN + " to " + ChatColor.LIGHT_PURPLE + newCode + ChatColor.YELLOW + "!");
            Main.getPlugin().getProfile().setNewCode(player.getUniqueId(), newCode);

            return true;
        }
        return false;
    }
}
