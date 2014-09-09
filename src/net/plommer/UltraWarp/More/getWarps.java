package net.plommer.UltraWarp.More;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Warps;

public class getWarps {
	protected static UltraWarp plugin;
	public getWarps(UltraWarp plugin) {
		getWarps.plugin = plugin;
	}
	
	public static HashMap<Integer, ArrayList<Warps>> getWarItemPos(Player player) {
		HashMap<Integer, ArrayList<Warps>> wa = new HashMap<Integer, ArrayList<Warps>>();
		ArrayList<Warps> wap = new ArrayList<Warps>();
		int i = 0, page = 0, p = 0;
		for(Warps w : plugin.db.checkWarpByUUID(player.getUniqueId())) {
			if(i > 17 || p == plugin.db.checkWarpByUUID(player.getUniqueId()).size()-1) {
				page++;
				ArrayList<Warps> test = new ArrayList<Warps>();
				test.addAll(wap);
				wa.put(page, test);
				wap.clear();
				i = 0;
			}
			wap.add(w);
			i++; p++;
		}
		return wa;
	}
	
}
