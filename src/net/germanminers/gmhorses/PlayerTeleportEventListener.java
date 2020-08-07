package net.germanminers.gmhorses;

import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportEventListener implements Listener
{
    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event)
    {
        System.out.println(event.getCause());

        if(event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL && event.getCause() != PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT && event.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN)
        {
            if(HorseManager.hasActiveHorse(event.getPlayer().getUniqueId()))
            {
                HorseManager.removeHorse(event.getPlayer().getUniqueId());
            }
        }
    }
}