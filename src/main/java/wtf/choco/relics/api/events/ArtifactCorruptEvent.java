package wtf.choco.relics.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import wtf.choco.relics.api.artifact.Artifact;

/**
 * Called when an artifact becomes corrupted.
 *
 * @author Parker Hawke - Choco
 */
public class ArtifactCorruptEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private final Artifact originalArtifact, corruptedArtifact;

    /**
     * Construct a new ArtifactCorruptEvent event.
     *
     * @param player the player that discovered the corrupted artifact
     * @param originalArtifact the original artifact
     * @param corruptedArtifact the new corrupted artifact
     */
    public ArtifactCorruptEvent(Player player, Artifact originalArtifact, Artifact corruptedArtifact) {
        super(player);
        this.originalArtifact = originalArtifact;
        this.corruptedArtifact = corruptedArtifact;
    }

    /**
     * Get the original artifact before corruption.
     *
     * @return the original artifact
     */
    public Artifact getOriginalArtifact() {
        return originalArtifact;
    }

    /**
     * Get the newly corrupted artifact.
     *
     * @return the corrupted artifact
     */
    public Artifact getCorruptedArtifact() {
        return corruptedArtifact;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
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
