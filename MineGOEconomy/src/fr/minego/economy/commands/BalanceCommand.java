package fr.minego.economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.minego.economy.Economy;
import fr.minego.economy.sql.SQLRequests;

public class BalanceCommand implements CommandExecutor {

	// SQLRequests Class Instance
	private SQLRequests sqlRequests = new SQLRequests();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

		if (args.length == 0 && sender instanceof Player) {

			Player player = (Player) sender;

			/*
			 * Get Player Balance from mySQL request
			 */
			try {
				int balance = sqlRequests.getBalance(player);
				player.sendMessage(Economy.playerRecieveMessage2.replace("<tag>", Economy.pluginTag).replace("<deuxPoints>", ":").replace("<player>", player.getName()).replace("<montant>", balance+""));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}

}
