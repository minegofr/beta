package fr.minego.economy.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.minego.economy.sql.SQLRequests;

public class EconomyListener implements Listener {

	SQLRequests requests = new SQLRequests();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		requests.createAccount(e.getPlayer());
	}
}
