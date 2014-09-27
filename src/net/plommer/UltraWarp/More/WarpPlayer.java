package net.plommer.UltraWarp.More;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WarpPlayer {

	public static boolean playerTo(String name, Player player, UltraWarp plugin, String arg2) {
		ArrayList<Warps> warp = plugin.db.checkWarp(name);
		HashMap<String, Warps> t = new HashMap<String, Warps>();
		int size = 1;
		
		if(warp.size() > 0) {
			for(Warps w : warp) {
				if(!w.isPublic() && arg2 == null) {
					if(w.getPlayerUUID().equals(player.getUniqueId())) {
						t.put("true", w);
					}
				} 
				if(w.isPublic()) {
					if(arg2 != null) {
						try {
							if(w.getPlayerUUID().equals(new UUIDFetcher(Arrays.asList(arg2)).getUUIDOf(arg2))) {
								t.put("true", w);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if(size == warp.size()) {
					if(t.size() > 0) {
						if(!t.containsKey("true")) {
							return false;
						} else {
							w = t.get("true");
						}
					}
					player.teleport(w.getInLocation());
					Utils.sendMessage(player, "&aWelcome to " + name);
				}
				size++;
			}
		} else {
			Utils.sendMessage(player, "&bThat warp doesn't exist!");
		}
		return true;
	}
	
}
