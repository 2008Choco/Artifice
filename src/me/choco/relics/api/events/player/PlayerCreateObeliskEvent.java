package me.choco.relics.api.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.choco.relics.structures.Obelisk;

/**
 * Called when a player successfully creates an obelisk
 * 
 * @author Parker Hawke - 2008Choco
 */
public class PlayerCreateObeliskEvent extends PlayerEvent {
	
	private static final HandlerList handlers = new HandlerList();
	
	private final Obelisk obelisk;
	
	/**
	 * Construct a new PlayerCreateObeliskEvent
	 * 
	 * @param player the player that created the obelisk
	 * @param obelisk the created obelisk
	 */
	public PlayerCreateObeliskEvent(Player player, Obelisk obelisk) {
		super(player);
		this.obelisk = obelisk;
	}
	
	/**
	 * Get the obelisk that was created in this event
	 * 
	 * @return the created obelisk
	 */
	public Obelisk getObelisk() {
		return obelisk;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}