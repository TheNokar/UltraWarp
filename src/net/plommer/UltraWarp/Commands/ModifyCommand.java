package net.plommer.UltraWarp.Commands;

import org.bukkit.Location;

import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;

public class ModifyCommand extends BaseCommand {
	
	public ModifyCommand() {
		name = "modify:mod:change:c";
		argLength = 1;
		bePlayer = false;
		usage = "<Warp name> <What to do>";
		permission = "warp.modify";
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		if(args.size() > 1) {
			Warps w = plugin.db.checkWarp(args.get(0));
			if(w != null) {
				Location l = player.getLocation();
				if(args.get(1).equalsIgnoreCase("position") || args.get(1).equalsIgnoreCase("pos") || args.get(1).equalsIgnoreCase("setposition") || args.get(1).equalsIgnoreCase("setpos")) {
					w.setLocation(new double[] {l.getX(), l.getY(), l.getZ(), (double)l.getYaw(), (double)l.getPitch()});
					w.setWorld(l.getWorld().getName());
					if(plugin.db.updateWarp(w)) {
						Utils.sendMessage(sender, "&aYou have updated the position off " + w.getWarpName());
					} else {
						Utils.sendMessage(sender, "&cSomething went wrong!");
					}
				}
			} else {
				Utils.sendMessage(sender, "&cThat warp dosn't exist!");
			}
		} else {
			Utils.sendMessageList(sender, help());
		}
		return false;
	}
	
	public String[] help() {
		String[] ss = new String[] {"HAHA", "HAHA"};
		return ss;
	}

}
