package net.germanminers.gmhorses;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickEventListener implements Listener
{
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event)
    {
        if(HorseManager.validateHorseName(event.getView().getTitle()))
        {
            if(event.getRawSlot() == 0 || event.getRawSlot() == 1)
            {
                event.setCancelled(true);
            }
        }
    }
}