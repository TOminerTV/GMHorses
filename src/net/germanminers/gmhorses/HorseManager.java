package net.germanminers.gmhorses;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class HorseManager
{
    private static FileConfiguration config = GMHorses.getMyConfig();
    private static HashMap<UUID, UUID> spawnedHorses = new HashMap<UUID, UUID>();

    public static boolean validateLore(ItemStack itemInHand, ItemStack horseItem)
    {
        if(itemInHand == null || horseItem == null)
        {
            return false;
        }

        if(itemInHand.getItemMeta().getDisplayName().startsWith(horseItem.getItemMeta().getDisplayName()))
        {
            return false;
        }

        List<String> itemInHandLore = itemInHand.getItemMeta().getLore();
        List<String> horseItemLore = horseItem.getItemMeta().getLore();

        if(itemInHandLore == null || horseItemLore == null)
        {
            return false;
        }

        for(int i=0; i < 4; i++)
        {
            if(itemInHandLore.get(i) == null || horseItemLore.get(i) == null)
            {
                return false;
            }

            if(!ChatColor.stripColor(itemInHandLore.get(i)).startsWith(horseItemLore.get(i)))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean validateHorseName(String horseName)
    {
        if(horseName == null)
        {
            return false;
        }
        else if(horseName.startsWith(ChatColor.GOLD + "Pferd von"))
        {
            return true;
        }

        return false;
    }

    public static void spawnHorse(Player player, ItemStack horseSaddle)
    {
        if(spawnedHorses.containsKey(player.getUniqueId()))
        {
            removeHorse(player.getUniqueId());

            for(Entity entity : player.getNearbyEntities(5, 5, 5))
            {
                if(entity instanceof Player)
                {
                    ((Player) entity).playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.5f, 0.1f);
                }
            }

            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.5f, 0.1f);

            return;
        }

        int level = 1;
        Horse.Color color = Horse.Color.BLACK;
        ItemStack horseArmor = new ItemStack(Material.LEATHER_HORSE_ARMOR);

        if(!horseSaddle.getItemMeta().getDisplayName().replace("Pferd - Stufe ", "").isEmpty())
        {
            switch(ChatColor.stripColor(horseSaddle.getItemMeta().getDisplayName()).replace("Pferd - Stufe ", ""))
            {
                case "I":
                    level = 2;
                    color = Horse.Color.BROWN;
                    horseArmor.setType(Material.IRON_HORSE_ARMOR);

                    break;
                case "II":
                    level = 3;
                    color = Horse.Color.CHESTNUT;
                    horseArmor.setType(Material.GOLDEN_HORSE_ARMOR);

                    break;
                case "III":
                    level = 4;
                    color = Horse.Color.WHITE;
                    horseArmor.setType(Material.DIAMOND_HORSE_ARMOR);

                    break;
                default:
                    break;
            }
        }

        try // TODO: fix?
        {
            Block block = player.getTargetBlock(null, 6);

            if(block.getType() == Material.AIR || block.getType() == Material.VOID_AIR)
            {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("prefix") + config.getString("targetIsAir")));

                return;
            }

            Horse horse = (Horse) player.getWorld().spawnEntity(block.getLocation().add(new Vector(0, 1, 0)), EntityType.HORSE);
            horse.setTamed(true);
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            horse.setOwner(player);
            horse.setAdult();
            //horse.setVariant(Horse.Variant.HORSE);
            horse.setColor(color);
            horse.setStyle(Horse.Style.NONE); // TODO: Random-Style? / Ability to swap style? [must cost something]
            horse.getInventory().setArmor(horseArmor);
            horse.setMaxHealth(level);
            horse.setHealth(level);
            horse.setJumpStrength(level * 0.2);
            horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(level * 0.1);
            horse.setInvulnerable(true);
            horse.setCanPickupItems(false);
            horse.setCustomName(ChatColor.GOLD + "Pferd von " + ChatColor.stripColor(player.getDisplayName()));

            for(Entity entity : player.getNearbyEntities(5, 5, 5))
            {
                if(entity instanceof Player)
                {
                    ((Player) entity).playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 8.0f, 1.0f);
                }
            }

            player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 8.0f, 1.0f);

            addHorse(player.getUniqueId(), horse.getUniqueId());
        }
        catch(IllegalStateException e) { }
    }

    public static boolean hasActiveHorse(UUID playerUUID)
    {
        return spawnedHorses.containsKey(playerUUID);
    }

    public static void checkDespawnDistance()
    {
        for(UUID playerUUID : spawnedHorses.keySet())
        {
            Horse horse = (Horse) Bukkit.getEntity(spawnedHorses.get(playerUUID));

            if(horse != null)
            {
                //TODO: from config
                if(!horse.getNearbyEntities(30, 30, 30).contains(Bukkit.getPlayer(playerUUID)))
                {
                    removeHorse(playerUUID);
                }
            }
        }
    }

    public static void removeHorse(UUID playerUUID)
    {
        Horse horse = (Horse) Bukkit.getEntity(spawnedHorses.get(playerUUID));

        if(horse != null)
        {
            horse.remove();

            spawnedHorses.remove(playerUUID);
        }
    }

    public static void removeAllHorses()
    {
        for(UUID playerUUID : spawnedHorses.keySet())
        {
            removeHorse(playerUUID);
        }

        spawnedHorses.clear(); // TODO: refactor
    }


    private static void addHorse(UUID playerUUID, UUID horseUUID)
    {
        spawnedHorses.put(playerUUID, horseUUID);
    }
}
