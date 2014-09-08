package net.plommer.UltraWarp.More;

import net.plommer.UltraWarp.Configs.LoadConfig;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UsefullItems {
	
	public static ItemStack WarpCompass() {
		ItemStack item = new ItemStack(Material.COMPASS, 1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(LoadConfig.compass_name);
		item.setItemMeta(im);
		return item;
	}

	public static void addWarpCompass(Player player) {
		if(!player.getInventory().contains(WarpCompass())) {
			player.getInventory().addItem(WarpCompass());
		}
	}
	
}
