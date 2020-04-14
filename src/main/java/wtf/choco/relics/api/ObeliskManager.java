package wtf.choco.relics.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;

import wtf.choco.relics.api.obelisk.Obelisk;
import wtf.choco.relics.api.obelisk.ObeliskState;

/**
 * Represents a manager to handle all interactions regarding obelisks.
 *
 * @author Parker Hawke - Choco
 */
public class ObeliskManager {

    private final Map<String, Obelisk> obelisks = new HashMap<>();
    private final List<ObeliskState> states = new ArrayList<>();

    /**
     * Register a custom {@link Obelisk}.
     *
     * @param obelisk the obelisk to register
     */
    public void registerObelisk(Obelisk obelisk) {
        this.obelisks.put(obelisk.getKey().toString(), obelisk);
    }

    /**
     * Get an {@link Obelisk} by its unique key (including the namespace). i.e. {@code relics:example}.
     *
     * @param key the key of the obelisk to get
     *
     * @return the obelisk. null if none registered with the given key
     */
    public Obelisk getObelisk(String key) {
        return obelisks.get(key);
    }

    /**
     * Get an {@link Obelisk} by its unique key.
     *
     * @param key the key of the obelisk to get
     *
     * @return the obelisk. null if none registered with the given key
     */
    public Obelisk getObelisk(NamespacedKey key) {
        return getObelisk(key.toString());
    }

    /**
     * Unregister the given {@link Obelisk}.
     *
     * @param obelisk the obelisk to unregister
     */
    public void unregisterObelisk(Obelisk obelisk) {
        this.unregisterObelisk(obelisk.getKey());
    }

    /**
     * Unregister the {@link Obelisk} with the given key.
     *
     * @param key the key of the obelisk to unregister
     */
    public void unregisterObelisk(NamespacedKey key) {
        this.obelisks.remove(key.toString());
    }

    /**
     * Get an unmodifiable collection of all registered {@link Obelisk}s.
     *
     * @return all registered obelisks
     */
    public Collection<Obelisk> getRegisteredObelisks() {
        return Collections.unmodifiableCollection(obelisks.values());
    }

    /**
     * Add an {@link ObeliskState} for tracking and ticking.
     *
     * @param obelisk the obelisk state to add
     */
    public void addObelisk(ObeliskState obelisk) {
        this.states.add(obelisk);
    }

    /**
     * Get an obelisk at the given location. If the location is occupied by a block that has been
     * assigned as an obelisk state's components, it will be returned.
     *
     * @param location the location at which to get the obelisk state
     * @param withinBounds whether or not a block not deemed a component but within the bounds of
     * an obelisk ({@link ObeliskState#getBounds()}) should be considered an obelisk component. If
     * true, blocks within the bounds will be considered part of the obelisk
     *
     * @return the fetched obelisk
     */
    public ObeliskState getObelisk(Location location, boolean withinBounds) {
        return getObelisk(location.getBlock(), withinBounds);
    }

    /**
     * Get an obelisk at the given location. If the location is occupied by a block that has been
     * assigned as an obelisk state's components, it will be returned.
     *
     * @param location the location at which to get the obelisk state
     *
     * @return the fetched obelisk
     */
    public ObeliskState getObelisk(Location location) {
        return getObelisk(location.getBlock(), false);
    }

    /**
     * Get an obelisk at the given block. If the block has been assigned as an obelisk state's components,
     * it will be returned.
     *
     * @param block the block at which to get the obelisk state
     * @param withinBounds whether or not a block not deemed a component but within the bounds of
     * an obelisk ({@link ObeliskState#getBounds()}) should be considered an obelisk component. If
     * true, blocks within the bounds will be considered part of the obelisk
     *
     * @return the fetched obelisk
     */
    public ObeliskState getObelisk(Block block, boolean withinBounds) {
        for (ObeliskState state : states) {
            if (withinBounds && state.getBounds().contains(block.getX(), block.getY(), block.getZ())) {
                return state;
            }
            else if (!withinBounds && state.getComponents().contains(block)) {
                return state;
            }
        }

        return null;
    }

    /**
     * Get an obelisk at the given block. If the block has been assigned as an obelisk state's components,
     * it will be returned.
     *
     * @param block the block at which to get the obelisk state
     *
     * @return the fetched obelisk
     */
    public ObeliskState getObelisk(Block block) {
        return getObelisk(block, false);
    }

    public List<ObeliskState> getNearbyObelisks(Location location, double distance, boolean fromCenter) {
        List<ObeliskState> nearby = new ArrayList<>();

        distance = Math.pow(distance, 2);
        World world = location.getWorld();

        for (ObeliskState state : states) {
            if (fromCenter && location.distanceSquared(state.getBounds().getCenter().toLocation(world)) < distance) {
                nearby.add(state);
            }

            else { // I really hope no one wants to use this, but oh well!
                for (Block component : state.getComponents()) {
                    if (location.distanceSquared(component.getLocation()) < distance) {
                        nearby.add(state);
                        break;
                    }
                }
            }
        }

        return nearby;
    }

    public List<ObeliskState> getNearbyObelisks(Location location, double distance) {
        return getNearbyObelisks(location, distance, true);
    }

    public List<ObeliskState> getNearbyObelisks(Block block, double distance, boolean fromCenter) {
        return getNearbyObelisks(block.getLocation(), distance, fromCenter);
    }

    public List<ObeliskState> getNearbyObelisks(Block block, double distance) {
        return getNearbyObelisks(block.getLocation(), distance, true);
    }

    /**
     * Get an unmodifiable list of all obelisk states.
     *
     * @return all tracked obelisk states
     */
    public List<ObeliskState> getObelisks() {
        return Collections.unmodifiableList(states);
    }

    /**
     * Remove and untrack an obelisk state.
     *
     * @param obelisk the obelisk state to remove
     */
    public void removeObelisk(ObeliskState obelisk) {
        this.states.remove(obelisk);
    }

    /**
     * Clear the manager of all registered obelisks and obelisk states.
     */
    public void clear() {
        this.obelisks.clear();
        this.states.clear();
    }

}
