package net.germanminers.gmhorses;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener
{
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if(HorseManager.hasActiveHorse(event.getPlayer().getUniqueId()))
        {
            HorseManager.removeHorse(event.getPlayer().getUniqueId());
        }
    }
}