package net.plommer.UltraWarp.Listeners;

import net.plommer.UltraWarp.IconMenu;
import net.plommer.UltraWarp.UltraWarp;
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
	public InteractListener(UltraWarp plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void interActEvent(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.PHYSICAL) || event.getAction().equals(Action.RIGHT_CLICK_AIR)  || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			final Player player = event.getPlayer();
			if(event.getItem().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
				IconMenu menu = new IconMenu(ChatColor.GOLD + "My Warps", 27, new IconMenu.OptionClickEventHandler() {
		            @Override
		            public void onOptionClick(IconMenu.OptionClickEvent event) {
		            	WarpPlayer.playerTo(event.getName(), player, plugin);
		                event.setWillClose(true);
		            }
		        }, plugin);
		        //.setOption(3, new ItemStack(Material.APPLE, 1), "Food", "The food is delicious");
				int i = 0;
				for(String warp : plugin.warps.keySet()) {
					menu.setOption(i, new ItemStack(Material.PAPER, 1), warp, "");
					i++;
				}
				menu.open(player);
			}
		}
	}
	
}
