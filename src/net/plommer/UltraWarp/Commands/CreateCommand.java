package net.plommer.UltraWarp.Commands;

import org.bukkit.Location;

import net.plommer.UltraWarp.Utils;
import net.plommer.UltraWarp.Warps;
import net.plommer.UltraWarp.Configs.LoadConfig;

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
			if(plugin.db.checkWarp(args.get(0)) != null) {
				Utils.sendMessage(sender, "&cThat warp does already exist!");
				return false;
			}
			Location l = player.getLocation();
			double[] loc = {l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch()};
			boolean isPublic = LoadConfig.public_by_default;
			if(args.size() > 1) {
				if(LoadConfig.public_by_default == true && !player.hasPermission(permission() + "public") || !player.hasPermission(permission() + "public")) {
					if(args.get(1).equalsIgnoreCase("private") || args.get(1).equalsIgnoreCase("p")) {
						Utils.sendMessage(sender, args.get(1));
						isPublic = false;
					}
				} else {
					if(args.get(1).equalsIgnoreCase("public") || args.get(1).equalsIgnoreCase("p")) {
						if(!player.hasPermission(permission() + "warp.create.public")) {Utils.sendMessage(sender, "&cYou do not have permission to do that!"); return false;}
						Utils.sendMessage(sender, args.get(1));
						isPublic = true;
					}
				}
			}
			plugin.db.addTo(new Warps(player.getUniqueId(), args.get(0), loc, l.getWorld().getName(), isPublic));
			Utils.sendMessage(sender, "&aYou have successfully create warp " + args.get(0));
		}
		return false;
	}
	
	
	
}
