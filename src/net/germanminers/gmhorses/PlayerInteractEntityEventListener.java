package net.germanminers.gmhorses;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityEventListener implements Listener
{
    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event)
    {
        Entity entity = event.getRightClicked();

        if(entity instanceof Horse)
        {
            if(HorseManager.validateHorseName(entity.getCustomName()))
            {
                if(!validateAction(event))
                {
                    event.setCancelled(true);
                }
            }
        }
    }

    private boolean validateAction(PlayerInteractEntityEvent event)
    {
        Entity entity = event.getRightClicked();

        if(((Horse) entity).getOwner() == null)
        {
            return false;
        }

        if(event.getPlayer().getUniqueId() != ((Horse) entity).getOwner().getUniqueId())
        {
            return false;
        }

        if(event.getPlayer().getInventory().getItem(event.getHand()).getType() == Material.NAME_TAG)
        {
            return false;
        }

        return true;
    }
}
