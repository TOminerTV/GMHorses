package net.germanminers.gmhorses;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventListener implements Listener
{
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event)
    {
        if(HorseManager.hasActiveHorse(event.getEntity().getUniqueId()))
        {
            HorseManager.removeHorse(event.getEntity().getUniqueId());
        }
    }
}
