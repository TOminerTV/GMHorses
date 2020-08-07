package net.germanminers.gmhorses;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GMHorsesCommand implements CommandExecutor
{
    private static FileConfiguration config = GMHorses.getMyConfig();

    private int horseCounter = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("gmhorses"))
        {
            if(args.length > 0)
            {
                if(args[0].equalsIgnoreCase("removeall"))
                {
                    if(sender.hasPermission("gm.gmhorses.removeallhorses"))
                    {
                        horseCounter = 0;

                        for(World world : Bukkit.getWorlds())
                        {
                            for(Entity entity : Bukkit.getWorld(world.getUID()).getEntities())
                            {
                                if(entity != null && entity.getType() == EntityType.HORSE)
                                {
                                    if(HorseManager.validateHorseName(entity.getCustomName()))
                                    {
                                        sender.sendMessage(entity.getCustomName() + ChatColor.RED + " wurde entfernt");
                                        horseCounter++;

                                        entity.remove();
                                    }
                                }
                            }
                        }

                        HorseManager.removeAllHorses();

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("prefix")) + ChatColor.GOLD + "Es wurden insgesamt " + ChatColor.RED + horseCounter + ChatColor.GOLD + " Pferde entfernt!");
                    }
                }
                else if(args[0].equalsIgnoreCase("saddle"))
                {
                    // gmhorses saddle $PLAYER $SADDLE_TYPE

                    if(args.length < 3)
                    {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("prefix")) + ChatColor.GOLD + "Es wurden insgesamt " + ChatColor.RED + horseCounter + ChatColor.GOLD + " Pferde entfernt!");

                        return false;
                    }

                    Player player = Bukkit.getPlayer(args[1]);

                    if(player == null || !player.isOnline())
                    {
                        // TODO: different error message
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("prefix")) + ChatColor.GOLD + "Es wurden insgesamt " + ChatColor.RED + horseCounter + ChatColor.GOLD + " Pferde entfernt!");

                        return false;
                    }

                    ItemStack saddle = ItemManager.getSaddle(args[2]);

                    if(saddle == null)
                    {
                        // TODO: different error message
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("prefix")) + ChatColor.GOLD + "Es wurden insgesamt " + ChatColor.RED + horseCounter + ChatColor.GOLD + " Pferde entfernt!");

                        return false;
                    }

                    // players inventory is full if the return value is -1
                    if(player.getInventory().firstEmpty() == -1)
                    {
                        player.getWorld().dropItem(player.getLocation(), saddle);
                    }
                    else
                    {
                        player.getInventory().addItem(saddle);
                    }

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("prefix") + config.getString("onHorseReceive")));
                }
            }
            else
            {
                sender.sendMessage(ChatColor.DARK_GRAY + "\n\n------------- " + ChatColor.GOLD + "GM " + ChatColor.RED + "# " + ChatColor.GOLD + "Horses " + ChatColor.DARK_GRAY + "-------------\n");

                sendCommands(ChatColor.GRAY + "/" + ChatColor.GOLD + "gmhorses " + ChatColor.DARK_GRAY + "# " + ChatColor.GRAY + "Zeigt diese Hilfe!", "/gmhorses", sender);
                sendCommands(ChatColor.GRAY + "/" + ChatColor.GOLD + "gmhorses saddle " + ChatColor.DARK_RED + "$" + ChatColor.RED + "PLAYER " + ChatColor.DARK_RED + "$" + ChatColor.RED + "SADDLE_TIER " + ChatColor.DARK_GRAY + "# " + ChatColor.GRAY + "Legt Spieler " + ChatColor.DARK_RED + "$" + ChatColor.RED + "PLAYER " + ChatColor.GRAY + "einen Sattel der Stufe " + ChatColor.DARK_RED + "$" + ChatColor.RED + "SADDLE_TIER" + ChatColor.GRAY + " ins Inventar, ist das Inventar voll so wird der Sattel an der Position von Spieler " + ChatColor.DARK_RED + "$" + ChatColor.RED + "PLAYER " + ChatColor.GRAY + "gedroppt", "/gmhorses saddle ", sender);
                sendCommands(ChatColor.GRAY + "/" + ChatColor.GOLD + "gmhorses removeall " + ChatColor.DARK_GRAY + "# " + ChatColor.GRAY + "Löscht alle gespawnten Pferde\n", "/gmhorses removeall", sender);

                sender.sendMessage(ChatColor.GRAY + "Mögliche Werte für " + ChatColor.DARK_RED + "$" + ChatColor.RED + "SADDLE_TIER " + ChatColor.DARK_GRAY + "# " + ChatColor.RED + "0" + ChatColor.GRAY + ", " + ChatColor.RED + "1" + ChatColor.GRAY + ", " + ChatColor.RED + "2" + ChatColor.GRAY + ", " + ChatColor.RED + "3" + ChatColor.GRAY + ", " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Stufen " + ChatColor.RED + "0 " + ChatColor.DARK_GRAY + "- " + ChatColor.RED + "3" + ChatColor.DARK_GRAY + "]\n \n \n");
            }
        }

        return true;
    }

    private void sendCommands(String message, String command, CommandSender sender)
    {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(message);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Befehl in den Chat schreiben").create()));
        sender.spigot().sendMessage(textComponent);
    }
}