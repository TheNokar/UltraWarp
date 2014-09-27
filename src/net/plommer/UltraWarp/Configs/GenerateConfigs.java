package net.plommer.UltraWarp.Configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import net.plommer.UltraWarp.UltraWarp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class GenerateConfigs {
	protected static UltraWarp plugin;
	public GenerateConfigs(UltraWarp plugin) {
		GenerateConfigs.plugin = plugin;
	}
	
	private static FileConfiguration customConfig = null;
	private static File customConfigFile = null;
	
	public static void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    	customConfigFile = new File(plugin.getDataFolder(), "config.yml");
	    	customConfigFile.getParentFile().mkdir();
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Look for defaults in the jar
	    Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(plugin.getResource("config.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
			saveDefaultConfig();
	    }
	}
	
	public static FileConfiguration getCustomConfig() {
	    if (customConfig == null) {
	        reloadCustomConfig();
	    }
	    return customConfig;
	}
	
	public static void saveCustomConfig() {
	    if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getCustomConfig().save(customConfigFile);
	    } catch (IOException ex) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	
	public static void saveDefaultConfig() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(plugin.getDataFolder(), "config.yml");
	    }
	    if (!customConfigFile.exists()) {            
	         plugin.saveResource("config.yml", false);
	     }
	}
	
}
