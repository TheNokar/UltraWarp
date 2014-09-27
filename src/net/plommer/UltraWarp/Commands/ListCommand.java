package net.plommer.UltraWarp.Commands;

import java.util.ArrayList;

import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;

public class ListCommand extends BaseCommand {
	
	public ListCommand() {
		name = "list:l";
		argLength = 0;
		bePlayer = true;
		usage = "<Warp name> <What to do>";
		permission = "warp.list";
	}

	@Override
	public boolean execute() {
		ArrayList<Warps> w = plugin.db.checkWarpByUUID(player.getUniqueId(), false);
		if(w.size() > 0) {
			StringBuilder builder = new StringBuilder();
			for(Warps warp : w) {
				if(warp.getPlayerUUID().equals(player.getUniqueId()) || player.hasPermission(permission() + "admin")) {
					builder.append(warp.getWarpName() + ", ");
				}
			}
			builder.setLength(builder.length() - 2);
			Utils.sendMessage(sender, "&aYour Warps:");
			Utils.sendMessage(sender, "&e" + builder.toString());
		}
		return false;
	}
}
