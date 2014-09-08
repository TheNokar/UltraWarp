package net.plommer.UltraWarp;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warps {

	private UUID playerUUID;
	private String warpName;
	private double[] location;
	private String world;
	private boolean isPublic;
	
	public Warps() {}
	
	public Warps(UUID playerUUID, String warpName, double[] loc, String world, boolean isPublic) {
		setUUID(playerUUID);
		setLocation(loc);
		setIsPublic(isPublic);
		setWorld(world);
		setWarpName(warpName);
	}
	
	public UUID getPlayerUUID() {
		return this.playerUUID;
	}
	public String getWarpName() {
		return this.warpName;
	}
	public Player getPlayer() {
		return Bukkit.getPlayer(getPlayerUUID());
	}
	public double[] getLocation() {
		return this.location;
	}
	public String getWorld() {
		return this.world;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public Location getInLocation() {
		return new Location(Bukkit.getWorld(getWorld()), getLocation()[0], getLocation()[1], getLocation()[2], (float)getLocation()[3], (float)getLocation()[4]);
	}
	
	public void setUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}
	public void setWarpName(String name) {
		this.warpName = name;
	}
	public void setLocation(double[] loc) {
		this.location = loc;
	}
	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public void setWorld(String world) {
		this.world = world;
	}
	
}
