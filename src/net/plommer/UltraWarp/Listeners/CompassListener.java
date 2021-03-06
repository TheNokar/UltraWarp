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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
			if(player.getItemInHand().getType() == Material.COMPASS) {
				if(player.getItemInHand().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
					page = 1;
					new getWarps(plugin);
					HashMap<Integer, ArrayList<Warps>> wa = getWarps.getWarItemPos(player);
	    			createMenu(player, Utils.buildString(LoadConfig.inventory_name), wa, 1);
	    			event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerInv(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(event.getSlot() == LoadConfig.compass_slot) {
			if(event.getCursor().getType() == Material.COMPASS || event.getCurrentItem().getType() == Material.COMPASS) {
				if(event.getCursor().getItemMeta() != null && event.getCursor().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta()) || event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().equals(UsefullItems.WarpCompass().getItemMeta())) {
					if(LoadConfig.sticky_compass == true) {
						event.getCurrentItem().setType(Material.AIR);
						UsefullItems.addWarpCompass(player, 8);
						event.setCancelled(true);
					}
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
	public void playerRespawn(final PlayerRespawnEvent event) {
		if(LoadConfig.sticky_compass) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
        			UsefullItems.addWarpCompass(event.getPlayer(), LoadConfig.compass_slot);
                }
            }, 1);
		}
	}
	
	@EventHandler
	public void playerJoinEvent(final PlayerJoinEvent event) {
		if(LoadConfig.sticky_compass) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
        			UsefullItems.addWarpCompass(event.getPlayer(), LoadConfig.compass_slot);
                }
            }, 1);
		}
	}
	
	public IconMenu menus(final Player player, final String name, final HashMap<Integer, ArrayList<Warps>> wa) {
		return new IconMenu(ChatColor.GOLD + name, 27+9, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
            	if(Utils.removeChar(event.getName()).equalsIgnoreCase("next")) {
            		if(wa.containsKey(page+1)) {
	            		page++;
	            		event.setWillDestroy(true);
	            		event.setWillClose(true);
	            		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	                        public void run() {
	    	            		createMenu(player, Utils.buildString(LoadConfig.inventory_name), wa, page);
	                        }
	                    }, 0);
            		}
            	} else if(Utils.removeChar(event.getName()).equalsIgnoreCase("back")) {
            		if(wa.containsKey(page-1)) {
	            		page--;
	            		event.setWillDestroy(true);
	            		event.setWillClose(true);
	            		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	                        public void run() {
	    	            		createMenu(player, Utils.buildString(LoadConfig.inventory_name), wa, page);
	                        }
	                    }, 0);
            		}
            	} else {
            		ArrayList<String> t = new ArrayList<String>();
            		for(Warps w : wa.get(page)) {
        				if(w.isPublic() && w.getPlayer().getName().equalsIgnoreCase(event.WaOwner())) {
        					t.add("true");
        				}
        			}
            		String n = null;
            		if(t.contains("true")) {
            			n = event.WaOwner();
            		}
            		WarpPlayer.playerTo(event.getName(), player, plugin, n);
            		event.setWillClose(true);
            		event.setWillDestroy(true);
            	}
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
		menu.setOption(pos, new ItemStack(mat, 1), w.getWarpName(), new String[] {Utils.buildString("&bLocation: &e" + (int)w.getLocation()[0] + ", " + (int)w.getLocation()[1] + ", " + (int)w.getLocation()[3]), Utils.buildString("&bStatus: ") + v, Utils.buildString("&bOwner: &6" + w.getPlayer().getName())});
	}
	
	public void createMenu(Player player, String name, HashMap<Integer, ArrayList<Warps>> wa, int page) {
		IconMenu menu = this.menus(player, name, wa);
		if(wa.size() > 0) {
			if(wa.containsKey(page+1)) {
				menu.setOption(26+9, new ItemStack(Material.ARROW, 1), Utils.buildString("&aNext"));
			}
			if(wa.containsKey(page-1)) {
				menu.setOption(18+9, new ItemStack(Material.ARROW, 1), Utils.buildString("&cBack"));
			}
		    int pos = 0;
			for(Warps w : wa.get(page)) {
				setMenu(menu, w, pos, Material.PAPER);
				pos++;
			}
		} else {
			menu.setOption(0, new ItemStack(Material.REDSTONE_BLOCK, 1), Utils.buildString("&cYou don't have any warps!"));
		}
		menu.open(player);
	}
	
}
