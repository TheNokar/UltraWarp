package net.plommer.UltraWarp.MySQL;

import java.io.File;
import java.io.IOException;
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
import net.plommer.UltraWarp.Configs.LoadConfig;

public class DatabaseConnection {
	private UltraWarp plugin;
	public DatabaseConnection(UltraWarp plugin) {
		this.plugin = plugin;
		try {
			if(LoadConfig.use_mysql == true) {
				this.db().prepareStatement(this.getSQLTables("warps.sql")).execute();
			} else {
				this.db().prepareStatement(this.getSQLTables("warpslite.sql")).execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection db() {
		try {
			if(LoadConfig.use_mysql == true) {
				return DriverManager.getConnection("jdbc:mysql://" + LoadConfig.mysql_host + ":" + LoadConfig.mysql_port + "/" + LoadConfig.mysql_database + "?autoReconnect=true&user=" + LoadConfig.mysql_username + "&password=" + LoadConfig.mysql_password);
			} else {
			    Class.forName("org.sqlite.JDBC");
			    File file = new File(plugin.getDataFolder().getAbsoluteFile() + "/warps.db");
			    if(!file.exists()) {
			    	try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
				return DriverManager.getConnection("jdbc:sqlite:"+file.getAbsolutePath());
			}
		} catch (SQLException | ClassNotFoundException ex) {
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
	
	public boolean updateWarp(Warps w) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("UPDATE `ultrawarp` SET X = (?), Y = (?), Z = (?), YAW = (?), PITCH = (?), world = (?), public = (?) WHERE warpName = (?)");
			ps.setFloat(1, (float) w.getLocation()[0]);
			ps.setFloat(2, (float) w.getLocation()[1]);
			ps.setFloat(3, (float) w.getLocation()[2]);
			ps.setFloat(4, (float) w.getLocation()[3]);
			ps.setFloat(5, (float) w.getLocation()[4]);
			ps.setString(6, w.getWorld());
			ps.setBoolean(7, w.isPublic());
			ps.setString(8, w.getWarpName());
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
	
	public boolean removeWarps(Warps w) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("DELETE FROM `ultrawarp` WHERE warpName = (?)");
			ps.setString(1, w.getWarpName());
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
	
	public ArrayList<Warps> checkWarp(String name) {
		PreparedStatement ps = null;
		ArrayList<Warps> hah = new ArrayList<Warps>();
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
	
	public ArrayList<Warps> checkWarpByUUID(UUID ui, boolean withPublic) {
		PreparedStatement ps = null;
		ArrayList<Warps> hah = new ArrayList<Warps>();
		String query = "SELECT `warpName`, `playerUUID`, `X`, `Y`, `Z`, `YAW`, `PITCH`, `world`, `public` FROM `ultrawarp` WHERE `playerUUID` = ? OR `public` = ? ORDER BY id";
		if(withPublic == false) {
			query = "SELECT `warpName`, `playerUUID`, `X`, `Y`, `Z`, `YAW`, `PITCH`, `world`, `public` FROM `ultrawarp` WHERE `playerUUID` = ? ORDER BY id";
		}
		hah.clear();
		try {
			ps = this.db().prepareStatement(query);
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
		Scanner scan = new Scanner(plugin.getResource(table));
		StringBuilder builder = new StringBuilder();
		while(scan.hasNextLine()) {
			builder.append(scan.nextLine());
		}
		scan.close();
		return builder.toString();
	}
	
}
