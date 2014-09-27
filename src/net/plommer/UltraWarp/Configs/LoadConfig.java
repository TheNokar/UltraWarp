package net.plommer.UltraWarp.Configs;

import net.plommer.UltraWarp.Utils;

import org.bukkit.configuration.file.FileConfiguration;

public class LoadConfig {

	private static FileConfiguration config = GenerateConfigs.getCustomConfig();
	public static boolean use_mysql = config.getBoolean("mysql.use");
	public static String mysql_username = config.getString("mysql.username");
	public static String mysql_password = config.getString("mysql.password");
	public static String mysql_database = config.getString("mysql.database");
	public static String mysql_host = config.getString("mysql.host");
	public static int mysql_port = config.getInt("mysql.port");
	public static String compass_name = Utils.buildString(config.getString("plugin.compass_name"));
	public static boolean public_by_default = config.getBoolean("plugin.public_by_default");
	public static boolean use_compass = config.getBoolean("plugin.use_compass");
	public static boolean sticky_compass = config.getBoolean("plugin.sticky_compass");
	public static int compass_slot = config.getInt("plugin.compass_slot");
	public static String inventory_name = config.getString("plugin.inventory_name");
	public static int max_warps = config.getInt("plugin.max_warps");
	public static boolean cwtu = config.getBoolean("plugin.convert_warpsuite_to_ultrawarp");
	
	//Permissions
	public static String permission_node = "ultrawarp.";
}
