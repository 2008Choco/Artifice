package me.choco.relics.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.choco.relics.artifacts.Artifact;

public class ArtifactCorruptEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	
	private final Player player;
	private final Artifact originalArtifact, corruptedArtifact;
	public ArtifactCorruptEvent(Player player, Artifact originalArtifact, Artifact corruptedArtifact){
		this.player = player;
		this.originalArtifact = originalArtifact;
		this.corruptedArtifact = corruptedArtifact;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Artifact getOriginalArtifact() {
		return originalArtifact;
	}
	
	public Artifact getCorruptedArtifact() {
		return corruptedArtifact;
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