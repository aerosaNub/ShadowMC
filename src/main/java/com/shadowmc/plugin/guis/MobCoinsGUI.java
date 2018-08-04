package com.shadowmc.plugin.guis;

import com.shadowmc.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MobCoinsGUI {

    /*
        Get rid of static abuse later
     */

    public static void openGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Inventory.size"), ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getMc().getConfiguration().getString("MobCoins-Shop.Inventory.name")));

        for (String keys : Main.getPlugin().getMc().getConfiguration().getConfigurationSection("MobCoins-Shop.Items").getKeys(false)) {

            try {
                ItemStack items = new ItemStack(Material.getMaterial(Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Items." + keys + ".itemid")));
                ItemMeta meta = items.getItemMeta();

                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getMc().getConfiguration().getString("MobCoins-Shop.Items." + keys + ".displayName")));
                meta.setLore(Arrays.asList(ChatColor.GREEN + "Purchase x" + Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Items." + keys + ".amount")  + " for " + Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Items." + keys + ".price") + " mobcoins!"));

                items.setItemMeta(meta);

                inv.setItem(Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Items." + keys + ".slot"),items);

            } catch (Exception e) {
                System.out.println("Something went wrong with building the inventory. (Item Part)");
            }
        }


        p.openInventory(inv);
    }
}
