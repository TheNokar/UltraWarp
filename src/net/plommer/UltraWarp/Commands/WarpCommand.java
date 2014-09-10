package net.plommer.UltraWarp.Commands;

import org.bukkit.entity.Player;

import net.plommer.UltraWarp.More.WarpPlayer;

public class WarpCommand extends BaseCommand {

	public WarpCommand() {
		bePlayer = true;
		name = " ";
		argLength = 0;
		usage = "<Warp name>";
		permission = "warp";
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		player = (Player)sender;
		if(WarpPlayer.playerTo(args.get(0), player, plugin));
		return false;
	}

	
	
}
