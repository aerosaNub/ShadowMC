package com.shadowmc.plugin.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SpawnerShopGUI {

    public static void spawnerMenu(Player p ) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, ChatColor.GREEN + "Spawner Shop");

        ItemStack item = new ItemStack(Material.MOB_SPAWNER);;
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "Passive Mobs");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Pigs, Cows, Chickens, and etc."));
        item.setItemMeta(meta);
        inv.setItem(11, item);

        meta.setDisplayName(ChatColor.YELLOW + "Aggressive Mobs");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Zombies, Skeleton, Creepers, and etc."));
        item.setItemMeta(meta);
        inv.setItem(13, item);

        meta.setDisplayName(ChatColor.YELLOW + "Others");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Other types of spawners available."));
        item.setItemMeta(meta);
        inv.setItem(15, item);

        p.openInventory(inv);

    }

    public static void passiveMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, ChatColor.GREEN + "Spawner Shop");

        ItemStack item = new ItemStack(Material.MOB_SPAWNER);;
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "COW");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$75,000"));
        item.setItemMeta(meta);
        inv.setItem(0, item);

        meta.setDisplayName(ChatColor.YELLOW + "CHICKEN");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$20,000"));
        item.setItemMeta(meta);
        inv.setItem(1, item);

        meta.setDisplayName(ChatColor.YELLOW + "PIG");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$50,000"));
        item.setItemMeta(meta);
        inv.setItem(2, item);

        meta.setDisplayName(ChatColor.YELLOW + "BAT");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$150,000"));
        item.setItemMeta(meta);
        inv.setItem(3, item);

        meta.setDisplayName(ChatColor.YELLOW + "MOOSHROOM");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$250,000"));
        item.setItemMeta(meta);
        inv.setItem(4, item);

        meta.setDisplayName(ChatColor.YELLOW + "SHEEP");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$150,000"));
        item.setItemMeta(meta);
        inv.setItem(5, item);

        meta.setDisplayName(ChatColor.YELLOW + "SQUID");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$150,000"));
        item.setItemMeta(meta);
        inv.setItem(6, item);

        meta.setDisplayName(ChatColor.YELLOW + "VILLAGER" +
                "");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$300,000"));
        item.setItemMeta(meta);
        inv.setItem(7, item);

        meta.setDisplayName(ChatColor.YELLOW + "OCELOT");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$150,000"));
        item.setItemMeta(meta);
        inv.setItem(8, item);

        meta.setDisplayName(ChatColor.YELLOW + "WOLF");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$150,000"));
        item.setItemMeta(meta);
        inv.setItem(9, item);



        p.openInventory(inv);
    }

    public static void aggressiveMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, ChatColor.GREEN + "Spawner Shop");

        ItemStack item = new ItemStack(Material.MOB_SPAWNER);;
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "ZOMBIE");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$300,000"));
        item.setItemMeta(meta);
        inv.setItem(0, item);

        meta.setDisplayName(ChatColor.YELLOW + "SKELETON");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$300,000"));
        item.setItemMeta(meta);
        inv.setItem(1, item);

        meta.setDisplayName(ChatColor.YELLOW + "SPIDER");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$300,000"));
        item.setItemMeta(meta);
        inv.setItem(2, item);

        meta.setDisplayName(ChatColor.YELLOW + "CAVE_SPIDER");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$750,000"));
        item.setItemMeta(meta);
        inv.setItem(3, item);

        meta.setDisplayName(ChatColor.YELLOW + "PIGMEN");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$300,000"));
        item.setItemMeta(meta);
        inv.setItem(4, item);

        meta.setDisplayName(ChatColor.YELLOW + "CREEPER");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$750,000"));
        item.setItemMeta(meta);
        inv.setItem(5, item);

        meta.setDisplayName(ChatColor.YELLOW + "BLAZE");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$1,000,000"));
        item.setItemMeta(meta);
        inv.setItem(6, item);

        meta.setDisplayName(ChatColor.YELLOW + "WITCH");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$500,000"));
        item.setItemMeta(meta);
        inv.setItem(7, item);

        meta.setDisplayName(ChatColor.YELLOW + "MAGMA_CUBE");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$500,000"));
        item.setItemMeta(meta);
        inv.setItem(8, item);

        meta.setDisplayName(ChatColor.YELLOW + "ENDERMAN");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$1,000,000"));
        item.setItemMeta(meta);
        inv.setItem(9, item);

        meta.setDisplayName(ChatColor.YELLOW + "IRON_GOLEM");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$5,000,000"));
        item.setItemMeta(meta);
        inv.setItem(10, item);


        p.openInventory(inv);
    }

    public static void othersMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, ChatColor.GREEN + "Spawner Shop");

        ItemStack item = new ItemStack(Material.MOB_SPAWNER);;
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "MYSTERY");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "$400,000"));
        item.setItemMeta(meta);
        inv.setItem(0, item);


        p.openInventory(inv);
    }
}
