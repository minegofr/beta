package fr.minego.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.minego.economy.commands.BalanceCommand;
import fr.minego.economy.commands.MoneyCommand;
import fr.minego.economy.listeners.EconomyListener;
import fr.minego.economy.sql.SQLConnection;
import fr.minego.economy.sql.SQLRequests;

public class Economy extends JavaPlugin {
	
	public static SQLConnection sqlConnection;
	private static SQLRequests sqlRequests = new SQLRequests();
	
	// External Strings
	public static String pluginTag;
	public static String pluginTagError;
	public static String executeTaskMessage;
	public static String executeTaskMessage2;
	public static String playerRecieveMessage;
	public static String playerRecieveMessage2;
	public static String playerRecieveMessage3;
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
		getCommand("money").setExecutor(new MoneyCommand());
		getCommand("balance").setExecutor(new BalanceCommand());
		
		//Register Listeners
		getServer().getPluginManager().registerEvents(new EconomyListener(), this);
		
		// SQLConnection Instance
	    sqlConnection = new SQLConnection(host, dbName, user, pwd, port);
	}
	
	@Override
	public void onDisable() {
		sqlConnection.disconnect();
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[MineGo-Economy] Disconnected !");
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
		
		// Load Strings from configuration file
		pluginTag = getConfig().getString(path + "PluginTag");
		pluginTagError = getConfig().getString(path + "PluginTagError");
		executeTaskMessage = getConfig().getString(path + "Strings.GiveCommand.ExecuteTaskMessage");
		executeTaskMessage2 = getConfig().getString(path + "Strings.RemoveCommand.ExecuteTaskMessage");
		playerRecieveMessage = getConfig().getString(path + "Strings.GiveCommand.PlayerRecieveMessage");
		playerRecieveMessage2 = getConfig().getString(path + "Strings.BalanceCommand.PlayerRecieveMessage");
		playerRecieveMessage3 = getConfig().getString(path + "Strings.RemoveCommand.PlayerRecieveMessage");
		host = getConfig().getString(dbPath + "host");
		dbName = getConfig().getString(dbPath + "dbName"); 
		tableName = getConfig().getString(dbPath + "tableName"); 
		user = getConfig().getString(dbPath + "user"); 
		pwd = getConfig().getString(dbPath + "pwd"); 
		port = getConfig().getInt(dbPath + "port"); 
		defaultMoney = getConfig().getInt(path + "defaultMoney"); 
		
		
	}
	/**
	 * Static Method
	 * 
	 * @param Player to return money
	 * @return Player Money
	 */
	public static int getMoney(Player player) {
		
		return sqlRequests.getBalance(player);
	}
}
