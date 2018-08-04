package com.shadowmc.plugin.listeners;

import com.shadowmc.plugin.Main;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ChatListener implements Listener {

    public HashMap<UUID, String> confirming = new HashMap<>();

    @EventHandler
    public void onCommandUsage(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage().toLowerCase();

        if (p.hasPermission("protection.confirm.commands")) {
            if (confirming.containsKey(p.getUniqueId())) {

                if (msg.equalsIgnoreCase("/confirm")) {
                    e.setCancelled(true);
                    p.performCommand(this.confirming.get(p.getUniqueId()).replaceAll("/", ""));
                    this.confirming.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.YELLOW + "Your command has been executed safely due to your confirmation!");
                    return;
                }

                if (msg.equalsIgnoreCase("/deny")) {
                    e.setCancelled(true);
                    this.confirming.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.YELLOW + "Your command has been discard safely due to denying the command!");
                    return;
                }

                e.setCancelled(true);

                p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Please confirm your last action by using /confirm or /deny before anything else can be executed!");
                p.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + this.confirming.get(p.getUniqueId()));
                return;
            }

            for (String confirmCMDS : Main.getPlugin().getRegFile().getConfig().getStringList("Commands-To-Confirm")) {
                if (msg.startsWith(confirmCMDS)) {
                    this.confirming.put(p.getUniqueId(), msg);
                    p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Please confirm this action by using /confirm or /deny!");
                    p.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + this.confirming.get(p.getUniqueId()));
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onTryingCrash(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String lc = e.getMessage().toLowerCase();
        if (!p.hasPermission("worledit.use")) {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if (online.hasPermission("crashblock.notify")) {
                if (lc.startsWith("/worldedit:/calc")
                        || lc.startsWith("/worldedit:/eval")
                        || lc.startsWith("/worldedit:/solve")
                        || lc.startsWith("//calc")
                        || lc.startsWith("//eval")
                        || lc.startsWith("//solve")) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.WHITE + "Unknown command for you!");

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " Your attempt to crash the server has failed! Better luck next time.");

                    for (Player staffs : Bukkit.getServer().getOnlinePlayers()) {
                        if (staffs.hasPermission("crashing.attempt")) {
                            staffs.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + p.getName() + " is trying to run a possible crash command!");
                        }
                    }
                }
            }
            }
        }
    }

    @EventHandler
    public void onPluginCheck(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String lc = e.getMessage().toLowerCase();

        if (!p.isOp()) {
            if (lc.startsWith("/pl") || lc.startsWith("/bukkit:pl") || lc.startsWith("/bukkit:plugins") || lc.startsWith("/bukkit:?") || lc.startsWith("/?") || lc.startsWith("/plugins") || lc.contains("/bukkit:me")
                    || lc.contains("/bukkit:?")
                    || lc.contains("/bukkit:about")
                    || lc.contains("/minecraft:me")
                    || lc.contains("/minecraft:")
                    || lc.contains("/me")
                    || lc.contains("/MINECRAFT:")
                    || lc.contains("/bukkit:")
                    || lc.contains("/bukkit")
                    || lc.contains("/ver")) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Your request to use this command has been denied!");
            }
        }
    }

    @EventHandler
    public void onUserChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String realMsg = e.getMessage();
        String msg = e.getMessage().toLowerCase();
        String rank = ChatColor.translateAlternateColorCodes((char)'&', (String)("&e" + PermissionsEx.getUser(p).getPrefix())).replace("_", " ");
        String displayName = p.getDisplayName();


        if (Main.getPlugin().getTagManager().hasUserTag(p)) {
            e.setFormat(rank + displayName + ChatColor.DARK_GRAY + "[" + Main.getPlugin().getTagManager().getDisplayTag(p) + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + ": " + realMsg);
        } else {
            e.setFormat(rank + displayName + ChatColor.WHITE + ": " + realMsg);
        }
        for (String bW : Main.getPlugin().getWfile().getConfig().getStringList("Blocked-Words")) {
            if (msg.contains(bW.toLowerCase())) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Looks like a word in your sentence is a swear word, please remove this before re-sending!");
            }
        }
    }
}
