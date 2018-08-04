package com.shadowmc.plugin.commands;

import com.shadowmc.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ProtectionCommand implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (sender.hasPermission("protection.admin")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "/protection cmdadd (command) - Adds command into the confirming command list in config.yml! Add it with the '/(s)'");
                sender.sendMessage(ChatColor.RED + "/protection banword (word) - Adds the word into the blocked-words list in words.yml!");
                return true;
            }

            if (args[0].equalsIgnoreCase("cmdadd")) {


                StringBuilder b = new StringBuilder();
                for (int i = 1; i<args.length; i++) {
                    b.append(args[i] + "");
                }

                List<String> allCMDS = Main.getPlugin().getRegFile().getCommands();

                allCMDS.add(b.toString());

                Main.getPlugin().getRegFile().addCommands(allCMDS);
                sender.sendMessage(ChatColor.GREEN + "Command added to the confirmation list!");
                return true;
            }

            if (args[0].equalsIgnoreCase("banword")) {
                String word = String.valueOf(args[1]);

                List<String> allWords = Main.getPlugin().getWfile().getConfig().getStringList("Blocked-Words");

                allWords.add(word);

                Main.getPlugin().getWfile().addNewWord(allWords);
                sender.sendMessage(ChatColor.GREEN + "Word added to the blocked-words list!");
                return true;
            }
        }
        return false;
    }
}
