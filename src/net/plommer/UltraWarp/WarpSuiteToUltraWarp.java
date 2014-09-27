package net.plommer.UltraWarp;

import java.io.File;
import java.util.Arrays;

import net.plommer.UltraWarp.Configs.GenerateConfigs;
import net.plommer.UltraWarp.More.UUIDFetcher;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class WarpSuiteToUltraWarp {

	public WarpSuiteToUltraWarp(UltraWarp plugin) {
		File[] files = new File("plugins/WarpSuite/players").listFiles();
		for(File file : files) {
			FileConfiguration config = new WarpSuiteConfig(file.getName(), plugin).getCustomConfig();
			String name = file.getName().replace(".yml", "");
			for(String wn : config.getConfigurationSection("Warps").getKeys(false)) {
				double[] loc = {config.getDouble("Warps."+wn+".X"), config.getDouble("Warps."+wn+".Y"), config.getDouble("Warps."+wn+".Z"), config.getDouble("Warps."+wn+".Yaw"), config.getDouble("Warps."+wn+".Pitch")};
				try {
					plugin.db.addTo(new Warps(new UUIDFetcher(Arrays.asList(name)).getUUIDOf(name), config.getString("Warps."+wn+".ListingName"), loc, Bukkit.getWorld(config.getString("Warps."+wn+".World")).getName(), false));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		GenerateConfigs.getCustomConfig().set("plugin.convert_warpsuite_to_ultrawarp", false);
		//GenerateConfigs.reloadCustomConfig();
		GenerateConfigs.saveCustomConfig();
	}
	
}
