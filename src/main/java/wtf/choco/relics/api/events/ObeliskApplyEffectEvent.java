package wtf.choco.relics.api.events;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import wtf.choco.relics.api.obelisk.ObeliskState;

/**
 * Called when entities receive an effect from an obelisks
 *
 * @author Parker Hawke - 2008Choco
 */
public class ObeliskApplyEffectEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private final ObeliskState obelisk;
    private final ImmutableSet<Entity> entities;

    /**
     * Construct a new ObeliskApplyEffectEvent
     *
     * @param obelisk the obelisk that applied the effect
     * @param entities the entities affected
     */
    public ObeliskApplyEffectEvent(ObeliskState obelisk, Set<Entity> entities) {
        this.obelisk = obelisk;
        this.entities = ImmutableSet.copyOf(entities);
    }

    /**
     * Get the obelisk that applied the effect
     *
     * @return the obelisk
     */
    public ObeliskState getObelisk() {
        return obelisk;
    }

    /**
     * Get an immutable set of all entities that were affected by the obelisk
     *
     * @return all affected entities
     */
    public Set<Entity> getAffectedEntities() {
        return entities;
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
