package net.plommer.UltraWarp.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import net.plommer.UltraWarp.IconMenu;
import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Warps;
import net.plommer.UltraWarp.More.UsefullItems;
import net.plommer.UltraWarp.More.WarpPlayer;
import net.plommer.UltraWarp.More.getWarps;

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
	public InteractListener(UltraWarp plugin) {
		this.plugin = plugin;
	}
	int page = 1;

	@EventHandler
	public void interActEvent(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.PHYSICAL) || event.getAction().equals(Action.RIGHT_CLICK_AIR)  || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			final Player player = event.getPlayer();
			if(event.getItem().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
				page = 1;
				new getWarps(plugin);
				HashMap<Integer, ArrayList<Warps>> wa = getWarps.getWarItemPos(player);
    			createMenu(player, "My Warps", wa, 1);
			}
		}
	}
	
	public IconMenu menus(final Player player, final String name, final HashMap<Integer, ArrayList<Warps>> wa) {
		return new IconMenu(ChatColor.GOLD + name, 27, new IconMenu.OptionClickEventHandler() {
            @Override
            public boolean onOptionClick(IconMenu.OptionClickEvent event) {
            	if(event.getName().equalsIgnoreCase("next")) {
            		if(wa.containsKey(page+1)) {
            			page++;
            			createMenu(player, "My Warps", wa, page);
            		}
            		event.setWillClose(false);
            		event.setWillDestroy(true);
            	} else if(event.getName().equalsIgnoreCase("back")) {
            		if(wa.containsKey(page-1)) {
            			page--;
            			createMenu(player, "My Warps", wa, page);
            		}
            		event.setWillClose(false);
            		event.setWillDestroy(true);
            	} else if(event.getName().equalsIgnoreCase("no")) {
            		return false;
            	} else {
            		WarpPlayer.playerTo(event.getName(), player, plugin);
            		event.setWillClose(true);
            		event.setWillDestroy(true);
            		return false;
            	}
            	event.setWillClose(false);
            	return false;
            }
        }, plugin);
	}

	public void setMenu(IconMenu menu, Warps w, int pos, Material mat) {
		String v;
		if(!w.isPublic()) {
			v = ChatColor.RED + "Private";
		} else {
			v = ChatColor.GREEN + "Public";
		}
		menu.setOption(pos, new ItemStack(mat, 1), w.getWarpName(), new String[] {ChatColor.AQUA + "Location: " + ChatColor.YELLOW + (int)w.getLocation()[0] + ", " + (int)w.getLocation()[1] + ", " + (int)w.getLocation()[3], ChatColor.AQUA + "Status: " + v});
	}
	
	public void createMenu(Player player, String name, HashMap<Integer, ArrayList<Warps>> wa, int page) {	
		IconMenu menu = this.menus(player, name, wa);
		menu = menus(player, name, wa);
    	menu.setOption(18, new ItemStack(Material.ARROW, 1), ChatColor.RED + "Back");
	    menu.setOption(26, new ItemStack(Material.ARROW, 1), ChatColor.GREEN + "Next");
		int pos = 0;
		for(Warps w : wa.get(page)) {
			setMenu(menu, w, pos, Material.PAPER);
			pos++;
		}
		menu.open(player);
	}
	
}
