package com.shadowmc.plugin.listeners;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.customevents.TagsUpdateEvent;
import com.shadowmc.plugin.handlers.managers.TagManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TagInventoryListener implements Listener {

    private final Main plugin;

    public TagInventoryListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getName() == null) return;

        if(e.getWhoClicked() instanceof Player){
            Player player = (Player) e.getWhoClicked();
            if(e.getClickedInventory().getName().equals(TagManager.INVENTORYNAME)) {
                int slot = e.getSlot();
                if (slot < plugin.getTagManager().getTags().size()) {
                    String tag = plugin.getTagManager().getTags().get(slot);
                    String selectedtag = plugin.getTagManager().getUserTag(player);
                    if (player.hasPermission(TagManager.TAGPERMISSION + tag)) {
                        if(selectedtag != null && selectedtag.equals(tag)){
                            plugin.getTagManager().removeCurrentTag(player);
                            player.sendMessage(ChatColor.YELLOW + "You unselected the tag " + ChatColor.DARK_GRAY + "[" + ChatColor.RESET + plugin.getTagManager().getTagDisplay(tag) + ChatColor.DARK_GRAY + "]");
                        }
                        else{
                            plugin.getTagManager().setTag(player, tag);
                            player.sendMessage(ChatColor.YELLOW + "You selected the tag " + ChatColor.DARK_GRAY + "[" + ChatColor.RESET + plugin.getTagManager().getTagDisplay(tag)+ ChatColor.DARK_GRAY + "]");
                        }
                        Inventory inventory = plugin.getTagManager().createTagInventory(player);
                        Inventory existinginventory = e.getClickedInventory();
                        if(inventory.getSize() != existinginventory.getSize()){
                            player.openInventory(inventory);
                        }
                        else{
                            existinginventory.setContents(inventory.getContents());
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.YELLOW + "You do not have permission to use " + ChatColor.DARK_GRAY + "[" + ChatColor.RESET + plugin.getTagManager().getTagDisplay(tag) + ChatColor.DARK_GRAY + "]");
                    }
                }
                e.setCancelled(true);
            }
            else if(e.getView().getTopInventory() != null && e.getView().getTopInventory().getName().equals(TagManager.INVENTORYNAME)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTagChange(TagsUpdateEvent e){
        if(e.getType() == TagsUpdateEvent.Type.REMOVE){
            plugin.getTagManager().cleanTags(e.getTag());
        }
        for(Player p: Bukkit.getOnlinePlayers()){
            if(p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getName().equals(TagManager.INVENTORYNAME)){
                Inventory inventory = plugin.getTagManager().createTagInventory(p);
                Inventory existinginventory = p.getOpenInventory().getTopInventory();
                if(inventory.getSize() != existinginventory.getSize()){
                    p.openInventory(inventory);
                }
                else{
                    existinginventory.setContents(inventory.getContents());
                }
            }
        }
    }
}
