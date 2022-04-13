package eu.epitech.openpaper;

import org.bukkit.plugin.java.JavaPlugin;

import eu.epitech.openpaper.events.AdvancementListener;
import eu.epitech.openpaper.events.CropHarvest;

/**
 * Hello world!
 *
 */
public class OpenPaper extends JavaPlugin {
    public static OpenPaper plugin;
    public static WorldBorder worldBorder;

    @Override
    public void onEnable() {
        plugin = this;
        worldBorder = new WorldBorder();

        // Copy the config.yml in the plugin configuration folder if it doesn't exists.
        this.saveDefaultConfig();

        registerEvents();
    }

    @Override
    public void onDisable() {
        // TODO : Do something if your plugin needs it (saving custom configs, clearing
        // cache, closing connections...)
        getLogger().info("Goodby World!");
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new CropHarvest(), this);
        getServer().getPluginManager().registerEvents(new AdvancementListener(), this);
    }
}
