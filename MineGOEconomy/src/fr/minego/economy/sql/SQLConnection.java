package fr.minego.economy.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import fr.minego.economy.Economy;

public class SQLConnection {

	private Connection connection;


	/*
	 * Database Logs Informations 
	 */
	private final String host = Economy.host;
	private final String database = Economy.dbName;
	private final String user = Economy.user;
	private final String password = Economy.pwd;
	private final int port = Economy.port;

	public SQLConnection() {

	}

	/**
	 * @return if the plugn is connected to DB
	 */
	public boolean isConnected() {
		return connection != null;
	}

	public Connection connection() {
		if (!isConnected()) {
			try {
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[MineGo-Economy] Connection...");
				connection = DriverManager.getConnection(
						"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[MineGo-Economy] Connected !");
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getConsoleSender()
						.sendMessage(ChatColor.RED + "[MineGo-Economy] Error, server couldn't connect DB !");
			}
		}

		return connection;
	}

	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
				System.out.println("[MineGo-Economy] Database Connection Closed !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
