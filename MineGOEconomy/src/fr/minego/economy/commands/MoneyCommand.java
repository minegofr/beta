package fr.minego.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.minego.economy.Economy;
import fr.minego.economy.sql.SQLRequests;

public class MoneyCommand implements CommandExecutor {

	// SQLRequests Class Instance
	private SQLRequests SQLRequests = new SQLRequests();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

		if (args.length == 3) {

			switch (args[0]) {

			/* /money give <player> <mount> */
			case "give":

				// Get args
				String playerNameGive = String.valueOf(args[1]);

				// Check if player exist
				if (Bukkit.getPlayerExact(playerNameGive) == null) {
					sender.sendMessage("§cCe joueur n'existe pas !");
					return false;
				}

				// Check if amount is Interger
				try {
					Integer.parseUnsignedInt(args[2]);
				} catch (Exception e) {
					sender.sendMessage("§cLe montant n'est pas un entier !");
					sender.sendMessage("§c/money give <player> <montant> !");
					return false;
				}

				try {
					SQLRequests.giveMoney(Bukkit.getPlayerExact(playerNameGive), Integer.valueOf(args[2]));
					sender.sendMessage(Economy.executeTaskMessage
							.replace("&", "§")
							.replace("<tag>", Economy.pluginTag)
							.replace("<player>", playerNameGive)
							.replace("<montant>", Integer.valueOf(args[2]) + ""));
					Bukkit.getPlayer(playerNameGive).sendMessage(Economy.playerRecieveMessage
							.replace("&", "§")
							.replace("<tag>", Economy.pluginTag)
							.replace("<montant>", Integer.valueOf(args[2]) + "")
							.replace("<player>", sender.getName()));
				} catch (Exception e) {
					e.printStackTrace();
					sender.sendMessage("§cUne erreure c'est produite lors de l'execution de la commande.");
					return false;
				}
				break;

			/*/money remove <player> <amount> */
			case "remove":

				// Get args
				String playerNameRemove = String.valueOf(args[1]);

				// Check if player exist
				if (Bukkit.getPlayerExact(playerNameRemove) == null) {
					sender.sendMessage("§cCe joueur n'existe pas !");
					return false;
				}

				// Check if amount is Interger
				try {
					Integer.parseUnsignedInt(args[2]);
				} catch (Exception e) {
					sender.sendMessage("§cLe montant n'est pas un entier !");
					return false;
				}

				try {
					SQLRequests.removeMoney(Bukkit.getPlayer(playerNameRemove), Integer.valueOf(args[2]));
					sender.sendMessage(Economy.executeTaskMessage2
							.replace("&", "§")
							.replace("<tag>", Economy.pluginTag)
							.replace("<player>", playerNameRemove)
							.replace("<montant>", Integer.valueOf(args[2]) + ""));
					Bukkit.getPlayer(playerNameRemove).sendMessage(Economy.playerRecieveMessage3
									.replace("&", "§")
									.replace("<tag>", Economy.pluginTag)
									.replace("<player>", playerNameRemove)
									.replace("<montant>", Integer.valueOf(args[2]) + ""));
				} catch (Exception e) {
					e.printStackTrace();
					sender.sendMessage("§cUne erreure c'est produite lors de l'execution de la commande.");
					return false;
				}
				break;
			}
		}
		return false;
	}

}
