package net.plommer.UltraWarp.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import net.plommer.UltraWarp.UltraWarp;
import net.plommer.UltraWarp.Warps;

public class DatabaseConnection {
	private UltraWarp plugin;
	public DatabaseConnection(UltraWarp plugin) {
		this.plugin = plugin;
		try {
			this.db().prepareStatement(this.getSQLTables("warps.sql")).execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection db() {
		try {
			return DriverManager.getConnection("jdbc:mysql://" + plugin.config.getString("mysql.host") + ":" + Integer.toString(plugin.config.getInt("mysql.port")) + "/" + plugin.config.getString("mysql.database") + "?autoReconnect=true&user=" + plugin.config.getString("mysql.username") + "&password=" + plugin.config.getString("mysql.password"));
		} catch (SQLException ex) {
			ex.printStackTrace();
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
		return null;
	}
	
	public boolean addTo(Warps warp) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("INSERT INTO `ultrawarp` (warpName, playerUUID, X, Y, Z, YAW, PITCH, world, public) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, warp.getWarpName());
			ps.setString(2, warp.getPlayerUUID().toString());
			ps.setFloat(3, (float)warp.getLocation()[0]);
			ps.setFloat(4, (float)warp.getLocation()[1]);
			ps.setFloat(5, (float)warp.getLocation()[2]);			
			ps.setFloat(6, (float)warp.getLocation()[3]);
			ps.setFloat(7, (float)warp.getLocation()[4]);
			ps.setString(8, warp.getWorld());
			ps.setBoolean(9, warp.isPublic());
			return ps.executeUpdate() != 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public Warps checkWarp(String name) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("SELECT `warpName`, `playerUUID`, `X`, `Y`, `Z`, `YAW`, `PITCH`, `world`, `public` FROM `ultrawarp` WHERE `warpName` = ?");
			ps.setString(1, name);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				Warps w = new Warps();
				double[] test = {(double)rs.getFloat(3), (double)rs.getFloat(4), (double)rs.getFloat(5), (double)rs.getFloat(6), (double)rs.getFloat(7)};
				w.setWarpName(rs.getString(1));
				w.setUUID(UUID.fromString(rs.getString(2)));
				w.setLocation(test);
				w.setWorld(rs.getString(8));
				w.setIsPublic(rs.getBoolean(9));
				return w;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public ArrayList<Warps> checkWarpByUUID(UUID ui) {
		PreparedStatement ps = null;
		ArrayList<Warps> hah = new ArrayList<Warps>();
		hah.clear();
		try {
			ps = this.db().prepareStatement("SELECT `warpName`, `playerUUID`, `X`, `Y`, `Z`, `YAW`, `PITCH`, `world`, `public` FROM `ultrawarp` WHERE `playerUUID` = ? OR `public` = ? ORDER BY id");
			ps.setString(1, ui.toString());
			ps.setBoolean(2, true);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				Warps w = new Warps();
				double[] test = {(double)rs.getFloat(3), (double)rs.getFloat(4), (double)rs.getFloat(5), (double)rs.getFloat(6), (double)rs.getFloat(7)};
				w.setWarpName(rs.getString(1));
				w.setUUID(UUID.fromString(rs.getString(2)));
				w.setLocation(test);
				w.setWorld(rs.getString(8));
				w.setIsPublic(rs.getBoolean(9));
				hah.add(w);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hah;
	}
	
	private String getSQLTables(String table) {
		Scanner scan = new Scanner(UltraWarp.class.getResourceAsStream("/databases/" + table));
		StringBuilder builder = new StringBuilder();
		while(scan.hasNextLine()) {
			builder.append(scan.nextLine());
		}
		scan.close();
		return builder.toString();
	}
	
}
