package com.shadowmc.plugin.listeners;

import com.shadowmc.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobStackerListener implements Listener {

    @EventHandler
    public void onStackingAttempt(EntityDeathEvent e) {
        if (Main.getPlugin().getMST().getStackMobs().contains(e.getEntity().getType())) {

            LivingEntity livingEntity = (LivingEntity) e.getEntity();

            if (livingEntity.getCustomName() != null) {

                if (Main.getPlugin().getMST().getNewEntities().contains(livingEntity)) {
                    Main.getPlugin().getMST().getNewEntities().remove(livingEntity);
                }

                int oldNUM = Integer.valueOf(livingEntity.getCustomName().substring(3, 4));
                String name = livingEntity.getCustomName().substring(5);


                int updated = oldNUM;
                updated--;
                if (updated == 0) {
                    return;
                }

                LivingEntity le = (LivingEntity) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), e.getEntityType());
                le.setCustomName(ChatColor.translateAlternateColorCodes('&', "&ax" + updated + " " + name));
                Main.getPlugin().getMST().getNewEntities().add(le);
            }
        }
    }
}
