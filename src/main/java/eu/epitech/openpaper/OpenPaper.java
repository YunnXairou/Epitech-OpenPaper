package eu.epitech.openpaper;

import org.bukkit.plugin.java.JavaPlugin;

import eu.epitech.openpaper.events.CropHarvest;

/**
 * Hello world!
 *
 */
public class OpenPaper extends JavaPlugin {
    public static OpenPaper plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getLogger().info("Hello World!");
        // Copy the config.yml in the plugin configuration folder if it doesn't exists.
        this.saveDefaultConfig();
        // TODO : Do something if your plugin needs it (registering commands /
        // listeners)

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
    }

    private void checkPluginDependencies() {
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger()
                    .info("ProtocolLib is not installed. Please install ProtocolLib for Grains to work as intended.");
        }
    }
}
