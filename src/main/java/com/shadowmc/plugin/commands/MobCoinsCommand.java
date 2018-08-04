package com.shadowmc.plugin.commands;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.guis.MobCoinsGUI;
import com.shadowmc.plugin.utils.C;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobCoinsCommand implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {


        if (!(sender instanceof Player)) {
            return true;
        }

        if (args.length == 0) {
                Player p = (Player) sender;
                sender.sendMessage(C.c("&c/" + lable + " checkbal"));
                MobCoinsGUI.openGUI(p);

            if (sender.hasPermission("command.mobcoins.staff")) {
                sender.sendMessage(C.c("&a&lMOB COINS HELP USAGE:"));
                sender.sendMessage(C.c("&7/" + lable + " <set|take|add> <user> <amount>"));
                return true;
            }
            return true;
        }


        if (args[0].equalsIgnoreCase("check")) {

                Player p = (Player) sender;
                if (args.length == 1) {
                    sender.sendMessage(C.c("&eYour current balance " + Main.getPlugin().getProfile().getMobCoins(p)));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    return true;
                }

                int userBal = Main.getPlugin().getProfile().getMobCoins(target);

                p.sendMessage(C.c("&e" +target.getName() + "'s balance is " + userBal + "!"));
                return true;



        }

        if (args[0].equalsIgnoreCase("set") && (sender.hasPermission("command.mobcoins.staff"))) {

            if (args.length <= 2) {
                sender.sendMessage(C.c("&7/" + lable + " <set|take|add> <user> <amount>"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                return true;
            }

            Integer amount = Integer.parseInt(args[2]);

            if (amount < 0) {
                sender.sendMessage(C.c("&cThe value specify is smaller than 0!"));
                return true;
            }

            Main.getPlugin().getProfile().setMobCoins(target, amount);
            sender.sendMessage(C.c("&eYou have set " + target.getName() + "'s mobcoins balance to " + amount + "!"));
            return true;

        }

        if (args[0].equalsIgnoreCase("add") && (sender.hasPermission("command.mobcoins.staff"))) {


            if (args.length <= 2) {
                sender.sendMessage(C.c("&7/" + lable + " <set|take|add> <user> <amount>"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                return true;
            }

            int userBal = Main.getPlugin().getProfile().getMobCoins(target);
            Integer amount = Integer.parseInt(args[2]);

            if (amount < 0) {
                sender.sendMessage(C.c("&cThe value specify is smaller than 0!"));
                return true;
            }

            int newAmount = userBal + amount;

            Main.getPlugin().getProfile().setMobCoins(target, newAmount);
            sender.sendMessage(C.c("&eYou have added" + amount + " mobcoins to " + target.getName() + "'s balance!"));
            return true;
        }

        if (args[0].equalsIgnoreCase("take") && (sender.hasPermission("command.mobcoins.staff"))) {


            if (args.length <= 2) {
                sender.sendMessage(C.c("&7/" + lable + " <set|take|add> <user> <amount>"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                return true;
            }

            int userBal = Main.getPlugin().getProfile().getMobCoins(target);
            Integer amount = Integer.parseInt(args[2]);

            if (amount < 0) {
                sender.sendMessage(C.c("&cThe value specify is smaller than 0!"));
                return true;
            }

            if (amount > userBal) {
                sender.sendMessage(C.c("&cThe value specify is smaller than what the user has!"));
                return true;
            }

            int newAmount = userBal - amount;

            Main.getPlugin().getProfile().setMobCoins(target, newAmount);
            sender.sendMessage(C.c("&eYou have taken " + amount + " mobcoins from " + target.getName() + "'s balance!"));
            return true;
        }

        return true;
    }
}
