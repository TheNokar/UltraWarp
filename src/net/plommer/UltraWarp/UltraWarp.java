package net.plommer.UltraWarp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import net.plommer.UltraWarp.Commands.*;
import net.plommer.UltraWarp.Configs.GenerateConfigs;
import net.plommer.UltraWarp.Configs.LoadConfig;
import net.plommer.UltraWarp.Listeners.CompassListener;
import net.plommer.UltraWarp.More.UrlDownload;
import net.plommer.UltraWarp.More.UsefullItems;
import net.plommer.UltraWarp.MySQL.DatabaseConnection;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraWarp extends JavaPlugin {
	
	public ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();
	public HashMap<String, Warps> warps = new HashMap<String, Warps>();
	public GenerateConfigs gc = new GenerateConfigs(this);
	public FileConfiguration config;
	public DatabaseConnection db;
	
	public void onEnable() {
		config = GenerateConfigs.getCustomConfig();
		registerCommands();		
		getServer().getPluginManager().registerEvents(new CompassListener(this), this);
		for(Player p : Bukkit.getOnlinePlayers()) {
			UsefullItems.addWarpCompass(p, LoadConfig.compass_slot);
		}
		if(LoadConfig.use_mysql == false) {
			try {
				File file = new File(this.getDataFolder().getAbsolutePath() + "/lib/spring-jdbc.jar");
				if(!file.exists()) {
					this.getLogger().info("Downloading SqlLite...");
					new UrlDownload("http://central.maven.org/maven2/org/xerial/sqlite-jdbc/3.8.5-pre1/sqlite-jdbc-3.8.5-pre1.jar", this, "/lib/spring-jdbc.jar");
				}
				URLClassLoader.newInstance(new URL[] {file.toURI().toURL()}, this.getClass().getClassLoader());
		      } catch (MalformedURLException e) {
		         return;
		      }
		}
		db = new DatabaseConnection(this);
	}
	
	public void onDisable() {
		
	}
	
	public void registerCommands() {
		getCommand("warp").setExecutor(new CommandHandler(this));
		commands.add(new CreateCommand());
		commands.add(new ListCommand());
		commands.add(new RemoveCommand());
		commands.add(new ModifyCommand());
		commands.add(new WarpCommand()); // <-- This has to bee on the bottom
	}
	
}
