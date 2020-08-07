package net.germanminers.gmhorses;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class VehicleEnterEventListener implements Listener
{
    private static FileConfiguration config = GMHorses.getMyConfig();

    @EventHandler
    public void onVehicleEnterEvent(VehicleEnterEvent event)
    {
        if(event.getVehicle() instanceof Horse)
        {
            Horse horse = (Horse) event.getVehicle();

            if(HorseManager.validateHorseName(horse.getCustomName()))
            {
                Player player = (Player) event.getEntered();

                if(!horse.getOwner().getUniqueId().equals(player.getUniqueId()))
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("prefix") + config.getString("notTheOwnHorse")));

                    for(Entity entity : player.getNearbyEntities(5, 5, 5))
                    {
                        if(entity instanceof Player)
                        {
                            ((Player) entity).playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.1f);
                        }
                    }

                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.1f);

                    event.setCancelled(true);
                }
            }
        }
    }
}