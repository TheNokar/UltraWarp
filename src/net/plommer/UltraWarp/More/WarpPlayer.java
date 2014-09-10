package net.plommer.UltraWarp.More;

import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;

import org.bukkit.entity.Player;

public class WarpPlayer {

	public static boolean playerTo(String name, Player player, UltraWarp plugin) {
		Warps warp = plugin.db.checkWarp(name);
		if(warp != null) {
			if(!warp.isPublic()) {
				if(!warp.getPlayerUUID().equals(player.getUniqueId())) {
					Utils.sendMessage(player, "&cYou can't warp to a warp you don't own!");
					return false;
				}
			}
			player.teleport(warp.getInLocation());
			Utils.sendMessage(player, "&aWelcome to " + name);
		} else {
			Utils.sendMessage(player, "&bThis is warp dosn't exist!");
		}
		return true;
	}
	
}
