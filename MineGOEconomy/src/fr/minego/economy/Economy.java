package fr.minego.economy;

import org.bukkit.plugin.java.JavaPlugin;

import fr.minego.economy.commands.BalanceCommand;
import fr.minego.economy.commands.GiveCommand;
import fr.minego.economy.listeners.EconomyListener;

public class Economy extends JavaPlugin {
	
	// External Strings
	public static String pluginTag;
	public static String pluginTagError;
	public static String executeTaskMessage;
	public static String playerRecieveMessage;
	public static String playerRecieveMessage2;
	public static String host;
	public static String dbName; 
	public static String tableName; 
	public static String user; 
	public static String pwd; 
	public static int port; 
	public static int defaultMoney;
	
	@Override
	public void onLoad() {
			
		//Load Configuration
		loadConfig();
	}
	
	@Override
	public void onEnable() {
		
		//Register Commands
		getCommand("give").setExecutor(new GiveCommand());
		getCommand("balance").setExecutor(new BalanceCommand());
		
		//Register Listeners
		getServer().getPluginManager().registerEvents(new EconomyListener(), this);
	}
	
	@Override
	public void onDisable() {
		//TODO on plugin disable
	}
	
	/**
	 * Load configuration Objects
	 * 
	 * @return void
	 */
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		String path = "Economy.";
		String dbPath = "Economy.Database.";
		
		pluginTag = getConfig().getString(path + "PluginTag");
		pluginTagError = getConfig().getString(path + "PluginTagError");
		executeTaskMessage = getConfig().getString(path + "Strings.GiveCommand.ExecuteTaskMessage");
		playerRecieveMessage = getConfig().getString(path + "Strings.GiveCommand.PlayerRecieveMessage");
		playerRecieveMessage2 = getConfig().getString(path + "Strings.BalanceCommand.PlayerRecieveMessage");
		host = getConfig().getString(dbPath + "host");
		dbName = getConfig().getString(dbPath + "dbName"); 
		tableName = getConfig().getString(dbPath + "tableName"); 
		user = getConfig().getString(dbPath + "user"); 
		pwd = getConfig().getString(dbPath + "pwd"); 
		port = getConfig().getInt(dbPath + "port"); 
		defaultMoney = getConfig().getInt(path + "defaultMoney"); 
		
		
	}
}
