package net.plommer.UltraWarp.Commands;

import org.bukkit.Location;

import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;

public class CreateCommand extends BaseCommand {

	public CreateCommand() {
		name = "create:c:add:a:set:s";
		argLength = 1;
		bePlayer = true;
		usage = "<Warp name>";
		permission = "warp.create";
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		if(args.get(0) != null) {
			Location l = player.getLocation();
			double[] loc = {l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch()};
			boolean isPublic = true;
			if(args.size() > 1) {
				if(args.get(1).equalsIgnoreCase("private") || args.get(1).equalsIgnoreCase("p")) {
					Utils.sendMessage(sender, args.get(1));
					isPublic = false;
				}
			}
			plugin.db.addTo(new Warps(player.getUniqueId(), args.get(0), loc, l.getWorld().getName(), isPublic));
			Utils.sendMessage(sender, "&aYou have successfully create warp " + args.get(0));
		}
		return false;
	}
	
	
	
}
