package org.nixfrost.schizo.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Random;

public class SchizoPower implements Listener {


    // EntityTargetEvent takes care of the zombies spawned by the player. It ensures that the player that spawned the zombie (called Owner) cant be target by the mobs that they created.
    // That is taken care of by a custom metadata called "owner" which links directly to the player that spawned them.
    @EventHandler
    public void EntityTargetEvent(EntityTargetEvent event) {
        // Only runs for zombies.
        if (event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();

            // Checks if the metadata is present.
            if (zombie.hasMetadata("owner") && event.getTarget() instanceof Player) {
                Player owner = ((Player) zombie.getMetadata("owner").get(0).value());
                String ownerName = owner.getName();
                String targetName = event.getTarget().getName();

                // If the owner is the target it will choose a different target based on the proximity of the player.
                if (ownerName.equals(targetName)) {
                    List<Entity> ls = owner.getNearbyEntities(5, 5, 5);
                    for (Entity l : ls) {
                        if (l instanceof LivingEntity && !(l instanceof Player) && !(l.hasMetadata("owner"))) {
                            event.setTarget(l);
                        }
                    }

                    // If no other target is found, set target to be null.
                    if (ownerName.equals(event.getTarget().getName())) {
                        event.setTarget(null);
                    }
                }
            }
        }

    }


    // PlayerInteract handles spawning the zombies and keeps them elevated to roughly the same y position as the player.
    @EventHandler
    public void PlayerInteract(PlayerInteractEvent event) {


        // Checks if the player is right clicking a blaze rod and starts spawning zombies.
        if ((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && event.getHand() == EquipmentSlot.HAND) {
            if (event.hasItem() && event.getItem().getType().name().equals("BLAZE_ROD")) {
                event.setCancelled(true);
                ItemStack item = event.getItem();
                Player player = event.getPlayer();
                World world = player.getWorld();

                // The outer loop that determines how many zombies to spawn.
                outer:
                for (int numZombies = 0; numZombies < 4; numZombies++) {
                    Location loc = randomLoc(player.getLocation());

                    // Moves the spawnpoint up until there is a free block. This ensures that zombies do not spawn inside the ground.
                    int tries = 0;
                    while (!world.getBlockAt(loc).getType().name().equals("AIR")) {
                        tries++;
                        if (tries > 100) {
                            Bukkit.broadcastMessage("Failed to spawn");
                            break outer;
                        }
                        loc.add(0, 1, 0);
                        if (loc.getY() - player.getLocation().getY() > 4) {
                            loc = randomLoc(player.getLocation());
                        }
                    }

                    // Moves the spawnpoint down so that zombies don't spawn while flying.
                    tries = 0;
                    while (world.getBlockAt(loc.clone().add(0, -1, 0)).getType().name().equals("AIR")) {
                        tries++;
                        loc.add(0, -1, 0);
                        if (tries == 200) {
                            break outer;
                        }
                    }

                    // Spawns the actual zombie in the last part and attaches the new owner metadata.
                    Zombie zombie = (Zombie) player.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    zombie.setMetadata("owner", new FixedMetadataValue(Util.getInstance(), player));
                }
            }
        }
    }


    // A random location gen for where to spawn the zombies.
    private Location randomLoc(Location loc) {
        Random rand = new Random();
        int maxDistance = 5;
        loc = new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getBlockY(), loc.getBlockZ() + 0.5);
        return loc.add(rand.nextInt(maxDistance * 2) - maxDistance, 0, rand.nextInt(maxDistance * 2) - maxDistance);
    }
}
