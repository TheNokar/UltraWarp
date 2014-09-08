package net.plommer.UltraWarp.More;

import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;

import org.bukkit.entity.Player;

public class WarpPlayer {

	public static void playerTo(String name, Player player, UltraWarp plugin) {
		Warps warp = plugin.db.checkWarp(name);
		if(warp != null) {
			player.teleport(warp.getInLocation());
			Utils.sendMessage(player, "&aWelcome to " + name);
		} else {
			Utils.sendMessage(player, "&bThis is warp dosn't exist!");
		}
	}
	
}
