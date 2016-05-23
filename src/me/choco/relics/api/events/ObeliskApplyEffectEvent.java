package me.choco.relics.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.choco.relics.structures.Obelisk;

public class ObeliskApplyEffectEvent extends Event implements Cancellable{
	public static HandlerList handlers = new HandlerList();
	
	private boolean cancelled = false;
	
	private final Obelisk obelisk;
	private final Player player;
	public ObeliskApplyEffectEvent(Obelisk obelisk, Player player) {
		this.obelisk = obelisk;
		this.player = player;
	}
	
	public Obelisk getObelisk(){
		return obelisk;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel){
		this.cancelled = cancel;
	}
}