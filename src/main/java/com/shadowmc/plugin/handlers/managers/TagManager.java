package com.shadowmc.plugin.handlers.managers;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.handlers.files.tags.TagDatabaseFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagManager {

    public static final String TAGPERMISSION = "tags.", INVENTORYNAME = ChatColor.YELLOW + "Tags";

    private final ItemStack SIGNITEM = new ItemStack(Material.SIGN);

    {
        ItemMeta meta = SIGNITEM.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Your current tag: ");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Click a tag to select or deselect it!", ChatColor.RED + "You are allow to purchase tags at our buycraft!"));
        SIGNITEM.setItemMeta(meta);
    }

    private ItemStack getSign(Player p) {
        ItemMeta meta = SIGNITEM.getItemMeta();
        String currenttag = getDisplayTag(p);
        if(currenttag == null){
            currenttag = ChatColor.RED + "None";
        }
        meta.setDisplayName(meta.getDisplayName() + currenttag);
        ItemStack clone = SIGNITEM.clone();
        clone.setItemMeta(meta);
        return clone;
    }

    private final Main plugin;

    public TagManager(Main plugin) {
        this.plugin = plugin;


    }

    public boolean removeTag(String tag){
        tag = tag.toLowerCase();
        boolean b = getTags().contains(tag);
        this.plugin.getTagFile().getConfig().set(tag, null);
        return b;
    }

    public String getDisplayTag(Player p){
        if(hasUserTag(p)){
            return getTagDisplay(getUserTag(p));
        }
        return null;
    }

    public String getUserTag(Player p){
        return this.plugin.getTagDatabaseFile().getConfig().getString(p.getUniqueId().toString());
    }

    public boolean hasUserTag(Player p){
        return this.plugin.getTagDatabaseFile().getConfig().contains(p.getUniqueId().toString());
    }

    public String getTagDisplay(String tagname){
        return ChatColor.translateAlternateColorCodes('&', this.plugin.getTagFile().getConfig().getString(tagname.toLowerCase() + ".name"));
    }

    public List<String> getTagDescription(String tagname){
        List<String> description = new ArrayList<>();
        Object got = this.plugin.getTagFile().getConfig().get(tagname.toLowerCase() + ".description");
        if(got instanceof Iterable) {
            for (String s : (Iterable<String>) got) {
                description.add(ChatColor.translateAlternateColorCodes('&', s));
            }
        }
        else if(got instanceof String[]){
            for (String s : (String[]) got) {
                description.add(ChatColor.translateAlternateColorCodes('&', s));
            }
        }
        return description;
    }

    public void setTagDisplay(String tagName, String name){
        this.plugin.getTagFile().getConfig().set(tagName.toLowerCase() + ".name", name);
    }

    public void setTagDescription(String tag, List<String> description){
        this.plugin.getTagFile().getConfig().set(tag.toLowerCase() + ".description", description.toArray(new String[description.size()]));
    }

    public List<String> getTags(){
        return new ArrayList<>(this.plugin.getTagFile().getConfig().getKeys(false));
    }

    public Inventory createTagInventory(Permissible permissible){
        int amtoftags = getTags().size();
        Inventory inventory = Bukkit.createInventory(null, amtoftags + 18 - (amtoftags % 9), INVENTORYNAME);
        String equipt = permissible instanceof Player ? getUserTag((Player)permissible) : null;
        int i = 0;
        for(String tag: getTags()){
            boolean permission = permissible.hasPermission(TAGPERMISSION + tag);
            ItemStack stack;
            if(permission){
                stack = new ItemStack(Material.NAME_TAG);
            }
            else{
                stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData());
            }
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(org.bukkit.ChatColor.DARK_GRAY + "[" + getTagDisplay(tag) + org.bukkit.ChatColor.DARK_GRAY  + "]");
            List<String> lore = new ArrayList<>();
            for(String s: getTagDescription(tag)){
                lore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + s);
            }
            lore.add("");
            lore.add(permission ? ChatColor.GREEN + "You have permission for this tag" : ChatColor.DARK_RED + "You do not have permission for this tag");
            lore.add("");
            if(equipt != null && tag.equals(equipt)){
                lore.add(ChatColor.GOLD + "You have this tag enabled");
                lore.add("");
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);
            inventory.setItem(i, stack);
            i ++;
        }
        int startslot = inventory.getSize()-10;
        if(permissible instanceof Player) {
            inventory.setItem(startslot + 5, getSign((Player)permissible));
        }
        return inventory;
    }

    public void setTag(Player player, @Nullable String tag){
        this.plugin.getTagDatabaseFile().getConfig().set(player.getUniqueId().toString(), tag);
    }

    public void removeCurrentTag(Player player){
        setTag(player, null);
    }

    public void cleanTags(String tag){
        for(String s: this.plugin.getTagDatabaseFile().getConfig().getKeys(false)){
            if(this.plugin.getTagDatabaseFile().getConfig().getString(s).equals(tag)){
                this.plugin.getTagDatabaseFile().getConfig().set(s, null);
            }
        }
    }

}
