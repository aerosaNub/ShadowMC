package com.shadowmc.plugin.handlers.objects;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Pickup {

    private HashMap<UUID, List<Material>> pickups = new HashMap<>();

    public HashMap<UUID, List<Material>> getPickups() {
        return pickups;
    }

    public void addPickups(Player player , Material material) {

        if (inBlockedList(player)) {
            List<Material> items = getPickups().get(player.getUniqueId());

            items.add(material);

            this.pickups.remove(player.getUniqueId());

            this.pickups.put(player.getUniqueId(), items);
        } else {
            List<Material> items = new ArrayList<>();

            items.add(material);

            this.pickups.put(player.getUniqueId(), items);
        }
    }

    public List<Material> blockedList(Player p) {
        return this.pickups.get(p.getUniqueId());
    }

    public boolean inBlockedList(Player p) {
        return this.pickups.containsKey(p.getUniqueId());
    }

    public void removeFromList(Player p) {
        this.pickups.remove(p.getUniqueId());
    }
}
