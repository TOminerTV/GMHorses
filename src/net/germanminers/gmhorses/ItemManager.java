package net.germanminers.gmhorses;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class ItemManager
{
    private static ItemStack saddle = new ItemStack(Material.SADDLE);

    public static ItemStack getSaddle(String type)
    {
        String saddleName = "";

        switch(type)
        {
            case "0":
                saddleName = ChatColor.GOLD + "Pferd";
                break;
            case "1":
                saddleName = ChatColor.GOLD + "Pferd - Stufe I";
                break;
            case "2":
                saddleName = ChatColor.GOLD + "Pferd - Stufe II";
                break;
            case "3":
                saddleName = ChatColor.GOLD + "Pferd - Stufe III";
                break;
            default:
                return null;
        }

        ItemMeta saddleMeta = saddle.getItemMeta();
        saddleMeta.setDisplayName(saddleName);

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Level: X");
        lore.add("");
        lore.add(ChatColor.GOLD + "XP: X");

        saddleMeta.setLore(lore);
        saddle.setItemMeta(saddleMeta);

        return saddle;
    }
}