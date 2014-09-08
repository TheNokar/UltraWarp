package net.plommer.UltraWarp.Commands;

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
		WarpPlayer.playerTo(args.get(0), player, plugin);
		return false;
	}

	
	
}
