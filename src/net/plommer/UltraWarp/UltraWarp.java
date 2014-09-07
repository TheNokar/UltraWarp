package net.plommer.UltraWarp;

import java.util.ArrayList;
import java.util.HashMap;

import net.plommer.UltraWarp.Commands.*;
import net.plommer.UltraWarp.Listeners.InteractListener;
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
		config = gc.getCustomConfig();
		registerCommands();		
		getServer().getPluginManager().registerEvents(new InteractListener(this), this);
		for(Player p : Bukkit.getOnlinePlayers()) {
			UsefullItems.addWarpCompass(p);
		}
		db = new DatabaseConnection(this);
	}
	
	public void onDisable() {
		
	}
	
	public void registerCommands() {
		getCommand("warp").setExecutor(new CommandHandler(this));
		commands.add(new CreateCommand());
		commands.add(new WarpCommand()); // <-- This has to bee on the bottom
	}
	
}
