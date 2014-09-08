package net.plommer.UltraWarp.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.plommer.UltraWarp.IconMenu;
import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Warps;
import net.plommer.UltraWarp.More.UsefullItems;
import net.plommer.UltraWarp.More.WarpPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

	UltraWarp plugin;
	IconMenu menu;
	public InteractListener(UltraWarp plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void interActEvent(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.PHYSICAL) || event.getAction().equals(Action.RIGHT_CLICK_AIR)  || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			final Player player = event.getPlayer();
			final HashMap<Integer, List<Warps>> wp = new HashMap<Integer, List<Warps>>();
			if(event.getItem().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
				menu = new IconMenu(ChatColor.GOLD + "My Warps", 27, new IconMenu.OptionClickEventHandler() {
		            @Override
		            public boolean onOptionClick(IconMenu.OptionClickEvent event) {
		    			int page = 1;
			            if(event.getName().equalsIgnoreCase("Next Page")) {
			            	page++;
			            	System.out.print(wp.size());
			    			int i = 0;
			            	for(Warps w : wp.get(page-1)) {
			            		menu.destroy();
			            		menu.setOption(i, new ItemStack(Material.PAPER), w.getWarpName(), new String[] {"Test", "Test"});
			            		i++;
			            	}
			            } else {
			            	WarpPlayer.playerTo(event.getName(), player, plugin);
			                event.setWillDestroy(true);
			            	event.setWillClose(true);
			            	return false;
		            	}
		            	return true;
		            }
		        }, plugin);
				int i = 0;
				int wo = 0;
				int page = 1;
				List<Warps> test = new ArrayList<Warps>();
				for(Warps w : plugin.db.checkWarpByUUID(player.getUniqueId())) {
					if(i >= 18) {
						wp.put(page, test);
						page++;
						test.clear();
						test.add(w);
						wp.put(page, test);
						if(page > 1) menu.setOption(18, new ItemStack(Material.ARROW, 1), "Back", "Go back");
						menu.setOption(26, new ItemStack(Material.ARROW, 1), "Next page", "Next page");
						i = 0;
					} else {
						if(page == 1) {
							String a;
							if(w.isPublic()) {
								a = ChatColor.GREEN + "Public";
							} else {
								a = ChatColor.RED + "Private";
							}
							String[] info = {ChatColor.AQUA + "Location: " + ChatColor.YELLOW + (int)w.getLocation()[0]+ ", " + (int)w.getLocation()[1] + ", " + (int)w.getLocation()[2], ChatColor.AQUA + "Status: " + a};
							menu.setOption(i, new ItemStack(Material.PAPER, 1), w.getWarpName(), info);
							}
						test.add(w);
						i++;
					}
					wo++;
				}
				if(wo == plugin.db.checkWarpByUUID(player.getUniqueId()).size()) {
					wp.put(page, test);
				}
				menu.open(player);
			}
		}
	}
	
}
