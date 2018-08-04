package com.shadowmc.plugin.commands;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.utils.C;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SeasonCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        for (String msgs : Main.getPlugin().getRegFile().getConfig().getStringList("Messages.season.messages")) {
            sender.sendMessage(C.c(msgs.toLowerCase()));
        }

        return true;
    }
}
