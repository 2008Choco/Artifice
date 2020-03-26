package wtf.choco.relics.api.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import wtf.choco.relics.api.obelisk.ObeliskState;

/**
 * Called when a player successfully creates an obelisk.
 *
 * @author Parker Hawke - Choco
 */
public class PlayerCreateObeliskEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final ObeliskState obelisk;

    /**
     * Construct a new PlayerCreateObeliskEvent.
     *
     * @param player the player that created the obelisk
     * @param obelisk the created obelisk
     */
    public PlayerCreateObeliskEvent(Player player, ObeliskState obelisk) {
        super(player);
        this.obelisk = obelisk;
    }

    /**
     * Get the obelisk that was created in this event.
     *
     * @return the created obelisk
     */
    public ObeliskState getObelisk() {
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
