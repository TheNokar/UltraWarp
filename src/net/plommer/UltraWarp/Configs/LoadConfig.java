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
	
}
