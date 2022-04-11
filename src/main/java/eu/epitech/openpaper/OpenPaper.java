package eu.epitech.openpaper;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Hello world!
 *
 */
public class OpenPaper extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Hello World!");
        // Copy the config.yml in the plugin configuration folder if it doesn't exists.
        this.saveDefaultConfig();
        // TODO : Do something if your plugin needs it (registering commands /
        // listeners)
    }

    @Override
    public void onDisable() {
        // TODO : Do something if your plugin needs it (saving custom configs, clearing
        // cache, closing connections...)
        getLogger().info("Goodby World!");
    }
}
