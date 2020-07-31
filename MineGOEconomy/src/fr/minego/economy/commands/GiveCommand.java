package fr.minego.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.minego.economy.Economy;
import fr.minego.economy.sql.SQLRequests;

public class GiveCommand implements CommandExecutor {

	// SQLRequests Class Instance
	private SQLRequests SQLRequests = new SQLRequests();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

		if (args.length == 2) {

			String playerName = String.valueOf(args[0]);
			int amount = Integer.valueOf(args[1]);

			/*
			 * Add amount to player balance by using SQL requests method
			 */
			try {
				SQLRequests.giveMoney(Bukkit.getPlayer(playerName), amount);
				sender.sendMessage(Economy.executeTaskMessage.replace("<tag>", Economy.pluginTag).replace("<player>", playerName).replace("<montant>", amount+""));
				Bukkit.getPlayer(playerName).sendMessage(Economy.playerRecieveMessage.replace("<tag>", Economy.pluginTag).replace("<player>", playerName).replace("<montant>", amount+""));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			sender.sendMessage("§8[§4MineGO-Economy§8]§c /give <player> <montant> !");
		}

		return false;
	}

}
