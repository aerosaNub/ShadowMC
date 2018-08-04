package com.shadowmc.plugin.commands;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.customevents.TagsUpdateEvent;
import com.shadowmc.plugin.handlers.managers.TagManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCommand implements CommandExecutor {

    private final Main plugin;

    public TagCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            p.openInventory(plugin.getTagManager().createTagInventory(p));
            sender.sendMessage(ChatColor.YELLOW + "Opening tag inventory...");
            if (sender.hasPermission("command.tags.admin")) {
                p.sendMessage(ChatColor.RED + "/tags create <name> <tag>");
                p.sendMessage(ChatColor.RED + "/tags setdescription <name> <description>");
                p.sendMessage(ChatColor.RED + "/tags setdisplay <name> <newTag>");
                p.sendMessage(ChatColor.RED + "/tags remove <name>");
                return true;
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 3)
            {
                sender.sendMessage(ChatColor.RED + "Usage: /tags create <name> <tag>");
                return true;
            }
            if(Main.getPlugin().getTagManager().getTags().contains(args[1].toLowerCase())){
                sender.sendMessage(ChatColor.RED + "A tag with that name already exists");
                return true;
            }
            String s = "";
            for(int i = 2; i < args.length; i++){
                s += args[i];
            }
            plugin.getTagManager().setTagDisplay(args[1], s);
            plugin.getTagManager().setTagDescription(args[1], new ArrayList<>());
            sender.sendMessage(ChatColor.YELLOW + "The tag " + ChatColor.DARK_GRAY + "[" + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', s) + ChatColor.DARK_GRAY + "]" +  ChatColor.YELLOW + " was created");
            sender.sendMessage(ChatColor.YELLOW + "Give players the permission: " + ChatColor.RED + ChatColor.UNDERLINE.toString() + TagManager.TAGPERMISSION + args[1].toLowerCase());
            Bukkit.getPluginManager().callEvent(new TagsUpdateEvent(TagsUpdateEvent.Type.CREATE, args[1].toLowerCase()));
           // Main.getPlugin().getTagManager().save();
            return true;
        }

        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length < 2)
            {
                sender.sendMessage(ChatColor.RED + "Usage: /tags remove <name>");
                return true;
            }
            boolean b = plugin.getTagManager().removeTag(args[1]);
            String message = b ? ChatColor.YELLOW + "Tag " + ChatColor.RED + args[1].toLowerCase() + ChatColor.YELLOW + " deleted successfully" : ChatColor.RED + "Tag does not exist";
            sender.sendMessage(message);
            Bukkit.getPluginManager().callEvent(new TagsUpdateEvent(TagsUpdateEvent.Type.REMOVE, args[1].toLowerCase()));
           // Main.getPlugin().getTagManager().save();
            return true;

        }

        if (args[0].equalsIgnoreCase("setdescription")) {
            if(args.length < 3){
                sender.sendMessage(ChatColor.RED + "Usage: /tags setdescription <name> <description>");
            }
            else{
                if(plugin.getTagManager().getTags().contains(args[1].toLowerCase())){
                    String s = "";
                    for(int i = 2; i < args.length; i++){
                        s += args[i] + " ";
                    }
                    List<String> description = new ArrayList<>(Arrays.asList(s.split("<n>")));
                    plugin.getTagManager().setTagDescription(args[1], description);
                    sender.sendMessage(ChatColor.YELLOW + "Set the description name of the tag " + ChatColor.RED + args[1].toLowerCase() + ChatColor.YELLOW + " to: ");
                   // Main.getPlugin().getTagManager().save();
                    for(String descriptionline: plugin.getTagManager().getTagDescription(args[1])){
                        sender.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + descriptionline);
                    }
                    Bukkit.getPluginManager().callEvent(new TagsUpdateEvent(TagsUpdateEvent.Type.EDITED, args[1].toLowerCase()));
                }
                else{
                    sender.sendMessage(ChatColor.YELLOW + "A tag with the name " + ChatColor.RED + args[1].toLowerCase() + ChatColor.YELLOW + " does not exist");
                }
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("setdisplay")) {
            if(args.length < 3){
                sender.sendMessage(ChatColor.RED + "Usage: /tags setdisplay <name> <newTag>");
            }
            else{
                if(plugin.getTagManager().getTags().contains(args[1].toLowerCase())){
                    String s = "";
                    for(int i = 2; i < args.length; i++){
                        s += args[i];
                        if(i < args.length - 1){
                            s += " ";
                        }
                    }
                    plugin.getTagManager().setTagDisplay(args[1], s);
                    sender.sendMessage(ChatColor.YELLOW + "Set the display name of the tag " + ChatColor.RED + args[1].toLowerCase() + ChatColor.YELLOW + " to " + ChatColor.DARK_GRAY + "[" + ChatColor.RESET + plugin.getTagManager().getTagDisplay(args[1]) + ChatColor.DARK_GRAY + "]");
                   // Main.getPlugin().getTagManager().save();
                    Bukkit.getPluginManager().callEvent(new TagsUpdateEvent(TagsUpdateEvent.Type.EDITED, args[1].toLowerCase()));
                }
                else{
                    sender.sendMessage(ChatColor.YELLOW + "A tag with the name " + ChatColor.RED + args[1].toLowerCase() + ChatColor.YELLOW + " does not exist");
                }
            }
            return true;
        }

        return false;
    }
}
