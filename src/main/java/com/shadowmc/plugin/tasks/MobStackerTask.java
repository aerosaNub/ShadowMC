package com.shadowmc.plugin.tasks;

import com.shadowmc.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class MobStackerTask {

    public Main plugin;
    List<EntityType> stackMobs = new ArrayList<>();
    List<Entity> newEntities = new ArrayList<>();
    int start = 3 * 20;
    int schedule = 20;
    int checkradius = 5;

    public MobStackerTask(Main plugin) {
        this.plugin = plugin;
        stackMobs.add(EntityType.ZOMBIE);
        stackMobs.add(EntityType.SKELETON);
        stackMobs.add(EntityType.BAT);
        stackMobs.add(EntityType.PIG);
        stackMobs.add(EntityType.PIG_ZOMBIE);
        stackMobs.add(EntityType.IRON_GOLEM);
        stackMobs.add(EntityType.CHICKEN);
        stackMobs.add(EntityType.CAVE_SPIDER);
        stackMobs.add(EntityType.COW);
        stackMobs.add(EntityType.CREEPER);
        stackMobs.add(EntityType.MAGMA_CUBE);
        stackMobs.add(EntityType.MUSHROOM_COW);
        stackMobs.add(EntityType.OCELOT);
        stackMobs.add(EntityType.WITCH);
        stackMobs.add(EntityType.SQUID);
        stackMobs.add(EntityType.BLAZE);
        stackMobs.add(EntityType.SHEEP);
        stackMobs.add(EntityType.SILVERFISH);
        stackMobs.add(EntityType.SLIME);
        stackMobs.add(EntityType.SPIDER);
        stackMobs.add(EntityType.SNOWMAN);
        stackMobs.add(EntityType.GHAST);
        stackMobs.add(EntityType.HORSE);




        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                stackMobs();
            }
        }, start, schedule);
    }

    public void stackMobs() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getLivingEntities()) {
                if (stackMobs.contains(entity.getType())) {
                    for (Entity nearbyEnt : entity.getNearbyEntities(checkradius, checkradius, checkradius)) {
                        if (stackMobs.contains(nearbyEnt.getType())) {
                            if (entity.getType().equals(nearbyEnt.getType())) {
                                LivingEntity livingEntity = (LivingEntity) nearbyEnt;

                                if (livingEntity.getCustomName() != null) {
                                    if (livingEntity.getCustomName().contains("x")) {
                                        if (!newEntities.contains(entity) && !entity.isDead()) {
                                            entity.remove();

                                            int oldNUM = Integer.valueOf(livingEntity.getCustomName().substring(3, 4));
                                            String name = livingEntity.getCustomName().substring(5);

                                            try {

                                                int updated = oldNUM;
                                                updated++;
                                                livingEntity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&ax" + updated + " " + name));
                                                newEntities.add(livingEntity);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                }
                            }
                        }

                    }

                    LivingEntity livingEntity = (LivingEntity) entity;

                    if (livingEntity.getCustomName() == null) {
                        livingEntity.setCustomName(ChatColor.GREEN + "x1" + " " +ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + livingEntity.getType().getName().toUpperCase());

                    }
                }
            }
        }
    }

    public List<EntityType> getStackMobs() {
        return stackMobs;
    }

    public List<Entity> getNewEntities() {
        return newEntities;
    }
}
