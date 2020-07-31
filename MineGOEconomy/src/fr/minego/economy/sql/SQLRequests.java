package fr.minego.economy.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import fr.minego.economy.Economy;

public class SQLRequests {
	
	Connection connection = new SQLConnection().connection();

	/**
	 * Execute method when player is joining the server Create a line in DB
	 * 
	 * @param Player to create account
	 * @return void
	 */
	public void createAccount(Player player) {

		// Check if player is already log in DB
		if (!hasAccount(player)) {
			try {
				PreparedStatement q = connection
						.prepareStatement("INSERT INTO " + Economy.tableName + " (uid, name, money) VALUES (?, ?, ?)");
				q.setString(1, player.getUniqueId().toString());
				q.setString(2, player.getName());
				q.setInt(3, Economy.defaultMoney); // Set default money value for first connection
				q.execute();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Execute the method when player is joining the server Method use in the
	 * createAccount(Player player) method
	 * 
	 * @param Player to check
	 * @return boolean, true = Has an account, False = hasn't an account
	 */
	public boolean hasAccount(Player player) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT uid from " + Economy.tableName + " WHERE uid = ?");
			q.setString(1, player.getUniqueId().toString());
			ResultSet rs = q.executeQuery();
			boolean hasAccount = rs.next();
			return hasAccount;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Execute the method when player execute /balance Return his own balance
	 * 
	 * @param Player to get balance
	 * @return Player balance
	 */
	public int getBalance(Player player) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT money from " + Economy.tableName + " WHERE uid = ?");
			q.setString(1, player.getUniqueId().toString());
			int money = 0;
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				money = rs.getInt("money");
			}

			q.close();
			return money;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Execute method when op player wants to give money to someone 
	 * Execute when executing /give <player> <amount>
	 * 
	 * @param Player to give money
	 * @param Amount of money to give
	 * @return void
	 */
	public void giveMoney(Player player, int amount) {
		try {
			PreparedStatement q = connection.prepareStatement("UPDATE " + Economy.tableName + " SET money = ? WHERE uid = ?");
			q.setInt(1, getBalance(player) + amount);
			q.setString(2, player.getUniqueId().toString());
			q.executeUpdate();
			q.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
