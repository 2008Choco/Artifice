package wtf.choco.artifice.api.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import wtf.choco.artifice.api.obelisk.ObeliskState;

/**
 * Called when a player successfully destroys an obelisk in the world.
 *
 * @author Parker Hawke - 2008Choco
 */
public class PlayerDestroyObeliskEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final ObeliskState obelisk;

    /**
     * Construct a new PlayerDestroyObeliskEvent.
     *
     * @param player the player that destroyed the obelisk
     * @param obelisk the destroyed obelisk
     */
    public PlayerDestroyObeliskEvent(Player player, ObeliskState obelisk) {
        super(player);
        this.obelisk = obelisk;
    }

    /**
     * Get the obelisk that was destroyed in this event.
     *
     * @return the destroyed obelisk
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
