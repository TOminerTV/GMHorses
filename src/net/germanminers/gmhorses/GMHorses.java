package net.germanminers.gmhorses;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GMHorses extends JavaPlugin
{
    private static String version;

    private static JavaPlugin plugin;
    private static FileConfiguration config;

    public void onEnable()
    {
        plugin = this;
        version = plugin.getDescription().getVersion();

        config = getConfig();

        config.options().copyDefaults(true);
        saveConfig();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractEventListener(), this);
        pluginManager.registerEvents(new PlayerInteractEntityEventListener(), this);
        pluginManager.registerEvents(new PlayerTeleportEventListener(), this);
        pluginManager.registerEvents(new PlayerQuitEventListener(), this);
        pluginManager.registerEvents(new PlayerDeathEventListener(), this);
        pluginManager.registerEvents(new InventoryClickEventListener(), this);
        pluginManager.registerEvents(new EntityDamageEventListener(), this);
        pluginManager.registerEvents(new VehicleEnterEventListener(), this);

        this.getCommand("gmhorses").setExecutor(new GMHorsesCommand());

        Bukkit.getConsoleSender().sendMessage("[GMHorses] " + ChatColor.GREEN + "GMHorses wurde erfolgreich aktiviert!");
        Bukkit.getConsoleSender().sendMessage("[GMHorses] " + ChatColor.GREEN + "GMHorses Version: " + version);
    }

    public void onDisable()
    {
        HorseManager.removeAllHorses();

        Bukkit.getConsoleSender().sendMessage("[GMHorses] " + ChatColor.RED + "GMHorses wurde erfolgreich deaktiviert!");
    }

    public static JavaPlugin getPlugin()
    {
        return plugin;
    }

    public static FileConfiguration getMyConfig()
    {
        return config;
    }

    public static String getVersion()
    {
        return version;
    }
}