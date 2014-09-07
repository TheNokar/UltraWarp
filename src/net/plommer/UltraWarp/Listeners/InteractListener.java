package net.plommer.UltraWarp.Listeners;

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
				int i = 0;
				for(Warps w : plugin.db.checkWarpByUUID(player.getUniqueId())) {
					String a;
					if(w.isPublic()) {
						a = ChatColor.GREEN + "Public";
					} else {
						a = ChatColor.RED + "Private";
					}
					String[] info = {ChatColor.AQUA + "Location: " + ChatColor.YELLOW + (int)w.getLocation()[0]+ ", " + (int)w.getLocation()[1] + ", " + (int)w.getLocation()[2], ChatColor.AQUA + "Status: " + a};
					menu.setOption(i, new ItemStack(Material.PAPER, 1), w.getWarpName(), info);
					i++;
				}
				menu.open(player);
			}
		}
	}
	
}
