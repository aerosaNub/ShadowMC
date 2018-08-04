package com.shadowmc.plugin.handlers.objects;

import org.bukkit.inventory.ItemStack;

public class MobCoinsOBJ {

    /*
        Object class for MobCoins.java
     */

    private int inventorySize;
    private String inventoryName;
    private int itemId;
    private int slot;
    private String itemName;
    private int price;

    public MobCoinsOBJ(int inventorySize, String inventoryName, int itemId, int slot, String itemName, int price) {
        this.inventorySize = inventorySize;
        this.inventoryName = inventoryName;
        this.itemId = itemId;
        this.slot = slot;
        this.itemName = itemName;
        this.price = price;
    }

    public int getSlot() {
        return slot;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public int getPrice() {
        return price;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemId() {
        return itemId;
    }
}
