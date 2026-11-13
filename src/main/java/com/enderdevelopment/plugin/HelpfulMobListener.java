package com.enderdevelopment.plugin;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class HelpfulMobListener implements Listener {
    private static final long VILLAGER_GIFT_COOLDOWN_MILLIS = 60_000L;
    private static final Set<EntityType> HOSTILE_TYPES = EnumSet.of(
            EntityType.BLAZE, EntityType.CREEPER, EntityType.DROWNED,
            EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.GHAST,
            EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM,
            EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.RAVAGER,
            EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME,
            EntityType.SPIDER, EntityType.STRAY, EntityType.VEX,
            EntityType.VINDICATOR, EntityType.WARDEN, EntityType.WITCH,
            EntityType.WITHER_SKELETON, EntityType.ZOGLIN, EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER);
    private final Map<UUID, Long> lastVillagerGift = new HashMap<>();

    @EventHandler
    public void onOwnerDamaged(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player) || event.isCancelled()
                || player.getHealth() - event.getFinalDamage() <= 0.0) {
            return;
        }
        for (Entity nearby : player.getNearbyEntities(12.0, 6.0, 12.0)) {
            if (!(nearby instanceof Wolf wolf) || !wolf.isTamed() || !(wolf.getOwner() instanceof Player owner)
                    || !owner.getUniqueId().equals(player.getUniqueId())) {
                continue;
            }
            double healedHealth = Math.min(player.getMaxHealth(), player.getHealth() + 2.0);
            if (healedHealth > player.getHealth()) {
                player.setHealth(healedHealth);
                player.sendActionBar(Component.text("Your wolf helps you recover.", NamedTextColor.GREEN));
                break;
            }
        }
    }

    @EventHandler
    public void onHostileSpawn(EntitySpawnEvent event) {
        if (!HOSTILE_TYPES.contains(event.getEntityType())) {
            return;
        }
        Set<UUID> warnedOwners = new HashSet<>();
        for (Entity nearby : event.getEntity().getNearbyEntities(16.0, 8.0, 16.0)) {
            if (!(nearby instanceof Cat cat) || !cat.isTamed() || !(cat.getOwner() instanceof Player owner)) {
                continue;
            }
            if (warnedOwners.add(owner.getUniqueId())) {
                owner.sendActionBar(Component.text("Your cat senses danger nearby.", NamedTextColor.YELLOW));
            }
        }
    }

    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager villager)) {
            return;
        }
        Player player = event.getPlayer();
        if (player.getFoodLevel() > 14) {
            return;
        }
        long now = System.currentTimeMillis();
        long previousGift = lastVillagerGift.getOrDefault(player.getUniqueId(), 0L);
        if (now - previousGift < VILLAGER_GIFT_COOLDOWN_MILLIS) {
            return;
        }
        lastVillagerGift.put(player.getUniqueId(), now);
        player.getInventory().addItem(new ItemStack(Material.BREAD, 2));
        player.sendMessage(Component.text(villager.getProfession().name().toLowerCase().replace('_', ' ')
                + " villager gives you bread.", NamedTextColor.GOLD));
    }
}
