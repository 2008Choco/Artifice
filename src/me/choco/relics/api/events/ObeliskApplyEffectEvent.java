package me.choco.relics.api.events;

import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.choco.relics.structures.Obelisk;

public class ObeliskApplyEffectEvent extends Event implements Cancellable{
	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled = false;
	
	private final Obelisk obelisk;
	private final Set<Entity> entities;
	public ObeliskApplyEffectEvent(Obelisk obelisk, Set<Entity> entities) {
		this.obelisk = obelisk;
		this.entities = entities;
	}
	
	public Obelisk getObelisk(){
		return obelisk;
	}
	
	public Set<Entity> getAffectedEntities(){
		return entities;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel){
		this.cancelled = cancel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}