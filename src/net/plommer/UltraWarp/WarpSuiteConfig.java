package net.plommer.UltraWarp;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpSuiteConfig {
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	private JavaPlugin plugin;
	
	private final String location;
	
	public WarpSuiteConfig(String location, JavaPlugin plugin)
	{
		this.location = location;
		this.plugin = plugin;
	}
	
	public void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    	customConfigFile = new File("plugins/WarpSuite/players/" + location);
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	}
	
	public FileConfiguration getCustomConfig() {
	    if (customConfig == null) {
	        this.reloadCustomConfig();
	    }
	    return customConfig;
	}
	
	public void saveCustomConfig() {
	    //Debug.print("Saving config " + location,"MyConfig","SaveCustomConfig");
	    if (customConfig == null || customConfigFile == null) {
	    return;
	    }
	    try {
	        getCustomConfig().save(customConfigFile);
	    } catch (IOException ex) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
}
