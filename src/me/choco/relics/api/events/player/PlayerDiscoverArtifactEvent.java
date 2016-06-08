package me.choco.relics.api.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.choco.relics.api.artifact.Artifact;

public class PlayerDiscoverArtifactEvent extends PlayerEvent implements Cancellable{
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	
	private Artifact artifact;
	public PlayerDiscoverArtifactEvent(Player player, Artifact artifact) {
		super(player);
		this.artifact = artifact;
	}
	
	public void setArtifact(Artifact artifact){
		this.artifact = artifact;
	}
	
	public Artifact getArtifact(){
		return artifact;
	}
	
	@Override
	public boolean isCancelled(){
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