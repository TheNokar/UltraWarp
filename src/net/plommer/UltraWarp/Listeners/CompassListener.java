package net.plommer.UltraWarp.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import net.plommer.UltraWarp.IconMenu;
import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;
import net.plommer.UltraWarp.Configs.LoadConfig;
import net.plommer.UltraWarp.More.UsefullItems;
import net.plommer.UltraWarp.More.WarpPlayer;
import net.plommer.UltraWarp.More.getWarps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class CompassListener implements Listener {

	UltraWarp plugin;
	public CompassListener(UltraWarp plugin) {
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
    			createMenu(player, Utils.buildString("&6&lMy Warps"), wa, 1);
			}
		}
	}
	
	@EventHandler
	public void playerInv(InventoryClickEvent event) {
		if(event.getSlot() == LoadConfig.compass_slot) {
			if(event.getCurrentItem().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
				if(LoadConfig.sticky_compass == true) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void playerDropItem(PlayerDropItemEvent event) {
		if(event.getItemDrop().getItemStack() != null) {
			if(event.getItemDrop().getItemStack().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
				if(LoadConfig.sticky_compass == true) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event) {
		if(event.getDrops().size() > 0) {
			for(ItemStack i : event.getDrops()) {
				if(i.getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
					event.getDrops().remove(i);
					event.getDrops().add(new ItemStack(Material.AIR, 1));
				}
			}
		}
	}
	
	@EventHandler
	public void playerRespawn(PlayerRespawnEvent event) {
		UsefullItems.addWarpCompass(event.getPlayer(), 8);
	}
	
	public IconMenu menus(final Player player, final String name, final HashMap<Integer, ArrayList<Warps>> wa) {
		return new IconMenu(ChatColor.GOLD + name, 27, new IconMenu.OptionClickEventHandler() {
            @Override
            public boolean onOptionClick(IconMenu.OptionClickEvent event) {
            	if(Utils.removeChar(event.getName()).equalsIgnoreCase("next")) {
            		if(wa.containsKey(page+1)) {
            			page++;
            			createMenu(player, Utils.buildString("&6&lMy Warps"), wa, page);
            		}
            		event.setWillClose(false);
            		event.setWillDestroy(true);
            	event.setWillDestroy(true);
            		} else if(Utils.removeChar(event.getName()).equalsIgnoreCase("back")) {
            		if(wa.containsKey(page-1)) {
            			page--;
            			createMenu(player, Utils.buildString("&6&lMy Warps"), wa, page);
            		}
            		event.setWillClose(false);
            		event.setWillDestroy(true);
            	} else {
            		WarpPlayer.playerTo(event.getName(), player, plugin);
            		event.setWillClose(true);
            		return false;
            	}
            	event.setWillClose(false);
            	event.setWillDestroy(true);
            	return false;
            }
        }, plugin);
	}

	public void setMenu(IconMenu menu, Warps w, int pos, Material mat) {
		String v;
		if(!w.isPublic()) {
			v = Utils.buildString("&cPrivate");
		} else {
			v = Utils.buildString("&aPublic");
		}
		menu.setOption(pos, new ItemStack(mat, 1), w.getWarpName(), new String[] {Utils.buildString("&bLocation: &e" + (int)w.getLocation()[0] + ", " + (int)w.getLocation()[1] + ", " + (int)w.getLocation()[3]), Utils.buildString("&bStatus: ") + v});
	}
	
	public void createMenu(Player player, String name, HashMap<Integer, ArrayList<Warps>> wa, int page) {	
		IconMenu menu = this.menus(player, name, wa);
		menu = menus(player, name, wa);
		menu.setOption(26, new ItemStack(Material.ARROW, 1), Utils.buildString("&aNext"));
	    menu.setOption(18, new ItemStack(Material.ARROW, 1), Utils.buildString("&cBack"));
	    int pos = 0;
		for(Warps w : wa.get(page)) {
			setMenu(menu, w, pos, Material.PAPER);
			pos++;
		}
		menu.open(player);
	}
	
}
