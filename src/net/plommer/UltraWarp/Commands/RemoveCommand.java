package net.plommer.UltraWarp.Commands;

import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;

public class RemoveCommand extends BaseCommand {

	public RemoveCommand() {
		name = "remove:rem:delete:del";
		argLength = 1;
		bePlayer = false;
		usage = "<Warp name>";
		permission = "warp.remove";
	}
	
	@Override
	public boolean execute() {
		Warps w = plugin.db.checkWarp(args.get(0));
		if(w != null) {
			if(w.getPlayerUUID() == player.getUniqueId() || player.hasPermission(permission() + "admin")) {
				if(plugin.db.removeWarps(w)) {
					Utils.sendMessage(sender, "&a" + w.getWarpName() + " &eHas been deleted!");
				} else {
					Utils.sendMessage(sender, "&cUnexpected error!");
				}
			} else {
				Utils.sendMessage(sender, "&cYou do not own that warp!");
			}
		} else {
			Utils.sendMessage(sender, "&cThat warp dosn't exist!");
		}
		return true;
	}
}
