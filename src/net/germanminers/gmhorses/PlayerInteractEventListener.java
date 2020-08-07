package net.germanminers.gmhorses;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteractEventListener implements Listener
{
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        Player player =  event.getPlayer();
        ItemStack itemInHand = event.getItem();

        ItemStack horseItem = new ItemStack(Material.SADDLE);
        ItemMeta horseItemMeta = horseItem.getItemMeta();
        horseItemMeta.setDisplayName("Pferd");

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Level:");
        lore.add("");
        lore.add("XP:");

        horseItemMeta.setLore(lore);
        horseItem.setItemMeta(horseItemMeta);

        if(itemInHand != null && itemInHand.getType() == horseItem.getType())
        {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                if(HorseManager.validateLore(itemInHand, horseItem))
                {
                    HorseManager.spawnHorse(player, itemInHand);
                }
            }
        }
    }
}