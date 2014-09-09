package net.plommer.UltraWarp.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	final HashMap<Integer, List<Warps>> wp = new HashMap<Integer, List<Warps>>();
	@EventHandler
	public void interActEvent(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.PHYSICAL) || event.getAction().equals(Action.RIGHT_CLICK_AIR)  || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			final Player player = event.getPlayer();
			if(event.getItem().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
		    	this.menus(player, "My Warps");
			}
		}
	}
	
	public IconMenu menus(final Player player, final String name) {
		IconMenu menu = this.menus(player, name);
		menu.setOption(18, new ItemStack(Material.ARROW, 1), "Back", "Back");
    	menu.setOption(26, new ItemStack(Material.ARROW, 1), "Next", "Next");
    	new getWarps(plugin);
		HashMap<Integer, ArrayList<Warps>> wa = getWarps.getWarItemPos(player);
		int pos = 0;
		for(Warps w : wa.get(1)) {
			menu.setOption(pos, new ItemStack(Material.PAPER, 1), w.getWarpName());
			pos++;
		}
		menu.open(player);
		return new IconMenu(ChatColor.GOLD + name, 27, new IconMenu.OptionClickEventHandler() {
            @Override
            public boolean onOptionClick(IconMenu.OptionClickEvent event) {
            	WarpPlayer.playerTo(event.getName(), player, plugin);
            	return false;
            }
        }, plugin);
	}
	
}
