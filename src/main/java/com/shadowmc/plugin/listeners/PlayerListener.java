package com.shadowmc.plugin.listeners;

import com.shadowmc.plugin.Main;
import com.shadowmc.plugin.guis.SpawnerShopGUI;
import com.shadowmc.plugin.handlers.files.Profile;
import com.shadowmc.plugin.utils.C;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;
import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if (!p.hasPlayedBefore()) {
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getRegFile().getConfig().getString("Messages.newUser").replaceAll("%user%", e.getPlayer().getName())));
            Profile.createYaml(uuid);
            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.ITALIC + "Your referral code has been created! " + ChatColor.LIGHT_PURPLE.toString() + Main.getPlugin().getProfile().getCode(uuid));
            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.ITALIC + "Give this to your friends so you can receive money in game for referring you! /referral");
        }

        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getRegFile().getConfig().getString("Messages.oldUser").replaceAll("%user%", e.getPlayer().getName())));

        if (!Profile.hasYaml(uuid)) {
            Profile.createYaml(uuid);
            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.ITALIC + "Your referral code has been created! " + ChatColor.LIGHT_PURPLE.toString() + Main.getPlugin().getProfile().getCode(uuid));
            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.ITALIC + "Give this to your friends so you can receive money in game for referring you! /referral");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getRegFile().getConfig().getString("Messages.userLeave").replaceAll("%user%", e.getPlayer().getName())));

        if (Main.getPlugin().getPickup().inBlockedList(e.getPlayer())) {
            Main.getPlugin().getPickup().removeFromList(e.getPlayer());
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();

        if (Main.getPlugin().getPickup().inBlockedList(p)) {

            for (Material materials : Main.getPlugin().getPickup().blockedList(p)) {

                if (e.getItem().getItemStack().getType().equals(materials)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (p instanceof Player) {

            if (e.getInventory() == null) return;

            if (e.getInventory().getName().equalsIgnoreCase(ChatColor.GREEN + "Spawner Shop")) {

                ItemStack item = e.getCurrentItem();

                if (item == null) return;

                if (item.getType() == Material.MOB_SPAWNER) {

                    if (!item.hasItemMeta()) return;

                    ItemMeta meta = item.getItemMeta();

                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Passive Mobs")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        SpawnerShopGUI.passiveMenu(p);
                    }

                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Aggressive Mobs")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        SpawnerShopGUI.aggressiveMenu(p);
                    }

                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Others")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        SpawnerShopGUI.othersMenu(p);
                    }

                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "COW")) {
                        e.setCancelled(true);
                        p.closeInventory();

                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 75000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }

                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 75000);
                        giveSpawnerType(p, "COW");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "CHICKEN")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 20000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 20000);
                        giveSpawnerType(p, "CHICKEN");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "PIG")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 50000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 50000);
                        giveSpawnerType(p, "PIG");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "BAT")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 150000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 150000);
                        giveSpawnerType(p, "BAT");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "MOOSHROOM")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 250000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 250000);
                        giveSpawnerType(p, "MOOSHROOM");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "SHEEP")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 75000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 75000);
                        giveSpawnerType(p, "SHEEP");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "SQUID")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 150000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 150000);
                        giveSpawnerType(p, "SQUID");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "VILLAGER")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 300000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 300000);
                        giveSpawnerType(p, "VILLAGER");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "OCELOT")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 150000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 150000);
                        giveSpawnerType(p, "OCELOT");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "WOLF")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 150000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 150000);
                        giveSpawnerType(p, "WOLF");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }

                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "ZOMBIE")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 300000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 300000);
                        giveSpawnerType(p, "ZOMBIE");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "SKELETON")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 300000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 300000);
                        giveSpawnerType(p, "SKELETON");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "SPIDER")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 300000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 300000);
                        giveSpawnerType(p, "SPIDER");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "CAVE_SPIDER")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 750000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 750000);
                        giveSpawnerType(p, "CAVE_SPIDER");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "BLAZE")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 1000000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 1000000);
                        giveSpawnerType(p, "BLAZE");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "ENDERMAN")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 1000000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 1000000);
                        giveSpawnerType(p, "ENDERMAN");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "PIGMEN")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 300000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 300000);
                        giveSpawnerType(p, "PIGMEN");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "WITCH")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 500000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 500000);
                        giveSpawnerType(p, "WITCH");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "IRON_GOLEM")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 5000000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 5000000);
                        giveSpawnerType(p, "IRON_GOLEM");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "MAGMA_CUBE")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 300000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 300000);
                        giveSpawnerType(p, "MAGMA_CUBE");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "CREEPER")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 500000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 500000);
                        giveSpawnerType(p, "CREEPER");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }
                    if (meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "MYSTERY")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (Main.getPlugin().getEconomy().getBalance(p.getName()) < 400000) {
                            p.sendMessage(ChatColor.RED + "You do not have enough money to buy this!");
                            return;
                        }
                        Main.getPlugin().getEconomy().withdrawPlayer(p.getName(), 400000);
                        giveSpawnerType(p, "MYSTERY");
                        p.sendMessage(ChatColor.YELLOW + "You just bought the spawner of your choice! "+  ChatColor.GREEN + "New Balance: " + ChatColor.GRAY + Main.getPlugin().getEconomy().getBalance(p.getName()));
                    }

                }

            }
            if (e.getClickedInventory().getName().equalsIgnoreCase(C.c(Main.getPlugin().getMc().getConfiguration().getString("MobCoins-Shop.Inventory.name")))) {

                e.setCancelled(true);

                ItemStack item = e.getCurrentItem();

                if (item == null) return;

                for (String keys : Main.getPlugin().getMc().getConfiguration().getConfigurationSection("MobCoins-Shop.Items.").getKeys(false)) {

                    if (item.getTypeId() == Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Items." + keys +".itemid")) {

                        if (!item.hasItemMeta()) return;

                        ItemMeta meta = item.getItemMeta();

                        int price = Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Items." + keys + ".price");
                        int userBal = Main.getPlugin().getProfile().getMobCoins(p);

                        if (meta.getDisplayName().equalsIgnoreCase(C.c(Main.getPlugin().getMc().getConfiguration().getString("MobCoins-Shop.Items." + keys + ".displayName")))) {

                            if (Main.getPlugin().getMc().getConfiguration().getBoolean("MobCoins-Shop.Items." + keys + ".newMenu")) {

                                return;
                            }

                            if (Main.getPlugin().getProfile().getMobCoins(p) <= price) {
                                p.sendMessage(C.c("&cYou don't have enough mobcoins for this item!"));
                                return;
                            }

                            Main.getPlugin().getProfile().setMobCoins(p, userBal - price);

                            int newBal = userBal - price;

                            p.getInventory().addItem(new ItemStack(Material.getMaterial(Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Items." + keys +".itemid")), Main.getPlugin().getMc().getConfiguration().getInt("MobCoins-Shop.Items." + keys +".amount")));

                            p.sendMessage(C.c("&ePurchased! Your new mobcoins balance is " + newBal));

                        }

                    }
                }
            }
        }
    }

    @EventHandler
    public void onspawnerPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();

        if (block == null) return;

        if (block.getType() != Material.MOB_SPAWNER) return;

        BlockState state = block.getState();

        ItemStack item = p.getItemInHand();

        ItemMeta meta = item.getItemMeta();

        if (state == null) return;

        if (item.hasItemMeta()) {
            if (meta.getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "MYSTERY")) {
                p.sendMessage(ChatColor.YELLOW + "Your mystery spawner is calculating a mob of it's own choice.....");

                int entityTypes = new Random().nextInt(Main.getPlugin().getMST().getStackMobs().size());

                EntityType type = Main.getPlugin().getMST().getStackMobs().get(entityTypes);

                if (type.equals(EntityType.BLAZE) || type.equals(EntityType.IRON_GOLEM) || type.equals(EntityType.VILLAGER)) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "Your spawner has been returned to your inventory due to the mob chosen was blacklisted from MYSTERY Spawners!");
                    return;
                }

                CreatureSpawner creatureSpawner = (CreatureSpawner) state;

                creatureSpawner.setSpawnedType(type);

                String entityName = type.getName().toUpperCase();

                p.sendMessage(ChatColor.YELLOW + "Mystery Spawner has chosen the entity type " + ChatColor.GREEN.toString() + ChatColor.BOLD + entityName + ChatColor.YELLOW+ "!");

            }
            if (!meta.getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "MYSTERY")) {
                EntityType type;


                try {
                    type = EntityType.valueOf(ChatColor.stripColor(meta.getDisplayName()));
                }
                catch (Exception ev) {
                    p.sendMessage(ChatColor.RED + "That is not valid input!");
                    return;
                }


                CreatureSpawner creatureSpawner = (CreatureSpawner) state;

                creatureSpawner.setSpawnedType(type);
            }
        }

    }

    public void giveSpawnerType(Player p, String name) {

        ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
        ItemMeta spawnerMeta = spawner.getItemMeta();

        spawnerMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a" + name));
        spawner.setItemMeta(spawnerMeta);

        p.getInventory().addItem(spawner);
    }

}
