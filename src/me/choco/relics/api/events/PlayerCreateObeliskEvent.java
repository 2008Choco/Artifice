package me.choco.relics.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.choco.relics.structures.Obelisk;

public class PlayerCreateObeliskEvent extends PlayerEvent{
	public static HandlerList handlers = new HandlerList();
	
	private final Obelisk obelisk;
	public PlayerCreateObeliskEvent(Player player, Obelisk obelisk) {
		super(player);
		this.obelisk = obelisk;
	}
	
	public Obelisk getObelisk(){
		return obelisk;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}