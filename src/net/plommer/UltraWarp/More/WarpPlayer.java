package net.plommer.UltraWarp.More;

import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;

import org.bukkit.entity.Player;

public class WarpPlayer {

	public static void playerTo(String name, Player player, UltraWarp plugin) {
		if(plugin.warps.containsKey(name)) {
			Warps warp = plugin.warps.get(name);
			System.out.print(warp.getWarpName() + " " + warp.getLocation().toString());
			player.teleport(warp.getInLocation());
			Utils.sendMessage(player, "&aWelcome to " + name);
		} else {
			Utils.sendMessage(player, "&bThis is warp dosn't exist!");
		}
	}
	
}
