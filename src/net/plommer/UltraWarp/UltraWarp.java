package net.plommer.UltraWarp;

import java.util.ArrayList;
import java.util.HashMap;

import net.plommer.UltraWarp.Commands.*;
import net.plommer.UltraWarp.Listeners.InteractListener;
import net.plommer.UltraWarp.More.UsefullItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraWarp extends JavaPlugin {
	
	public ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();
	public HashMap<String, Warps> warps = new HashMap<String, Warps>();
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		registerCommands();
		getServer().getPluginManager().registerEvents(new InteractListener(this), this);
		for(Player p : Bukkit.getOnlinePlayers()) {
			UsefullItems.addWarpCompass(p);
		}
	}
	
	public void onDisable() {
		
	}
	
	public void registerCommands() {
		getCommand("warp").setExecutor(new CommandHandler(this));
		commands.add(new CreateCommand());
		commands.add(new WarpCommand()); // <-- This has to bee on the bottom
	}
	
}
