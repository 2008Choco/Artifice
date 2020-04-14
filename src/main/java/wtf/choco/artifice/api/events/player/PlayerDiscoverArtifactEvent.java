package wtf.choco.artifice.api.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import wtf.choco.artifice.api.artifact.Artifact;

/**
 * Called when a player discovers an artifact in the world.
 *
 * @author Parker Hawke - 2008Choco
 */
public class PlayerDiscoverArtifactEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private String message;
    private Artifact artifact;

    /**
     * Construct a new PlayerDiscoverArtifactEvent.
     *
     * @param player the player that discovered the artifact
     * @param artifact the discovered artifact
     */
    public PlayerDiscoverArtifactEvent(Player player, Artifact artifact) {
        super(player);
        this.artifact = artifact;
        this.message = "You have discovered a " + artifact.getName();
    }

    /**
     * Set the artifact to be discovered in this event.
     *
     * @param artifact the new artifact to discover
     */
    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
        this.message = "You have discovered a " + artifact.getName();
    }

    /**
     * Get the artifact that has been discovered in this event.
     *
     * @return the discovered artifact
     */
    public Artifact getArtifact() {
        return artifact;
    }

    /**
     * Set the message that should display to the player upon discovery.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the message that should display to the player upon discovery.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
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
