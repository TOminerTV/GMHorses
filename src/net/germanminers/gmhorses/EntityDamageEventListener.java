package net.germanminers.gmhorses;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener implements Listener
{
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();

        if(entity instanceof Horse)
        {
            if(HorseManager.validateHorseName(entity.getCustomName()))
            {
                event.setCancelled(true);
            }
        }
    }
}