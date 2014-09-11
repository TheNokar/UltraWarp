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

	@SuppressWarnings("deprecation")
	public static void addWarpCompass(Player player, int pos) {
		if(!player.getInventory().contains(WarpCompass())) {
			if(player.getInventory().getItem(8) != null) {
				ItemStack a = player.getInventory().getItem(pos);
				player.getInventory().addItem(a);
				player.getInventory().remove(pos);
			}
			player.getInventory().setItem(pos, WarpCompass());
		}
	}
	
}
