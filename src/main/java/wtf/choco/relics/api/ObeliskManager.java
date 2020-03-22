package wtf.choco.relics.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

import wtf.choco.relics.api.obelisk.Obelisk;
import wtf.choco.relics.api.obelisk.ObeliskState;

/**
 * Represents a manager to handle all interactions regarding obelisks
 *
 * @author Parker Hawke - 2008Choco
 */
public class ObeliskManager {

    private final Map<String, Obelisk> obelisks = new HashMap<>();
    private final List<ObeliskState> states = new ArrayList<>();

    public void registerObelisk(Obelisk obelisk) {
        this.obelisks.put(obelisk.getKey().toString(), obelisk);
    }

    public Obelisk getObelisk(String key) {
        return obelisks.get(key);
    }

    public Obelisk getObelisk(NamespacedKey key) {
        return getObelisk(key.toString());
    }

    public void unregisterObelisk(Obelisk obelisk) {
        this.unregisterObelisk(obelisk.getKey());
    }

    public void unregisterObelisk(NamespacedKey key) {
        this.obelisks.remove(key.toString());
    }

    public Collection<Obelisk> getRegisteredObelisks() {
        return Collections.unmodifiableCollection(obelisks.values());
    }

    public void addObelisk(ObeliskState obelisk) {
        this.states.add(obelisk);
    }

    public ObeliskState getObelisk(Location location) {
        return getObelisk(location.getBlock());
    }

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

    public ObeliskState getObelisk(Block block) {
        return getObelisk(block, false);
    }

    public List<ObeliskState> getObelisks() {
        return Collections.unmodifiableList(states);
    }

    public void removeObelisk(ObeliskState obelisk) {
        this.states.remove(obelisk);
    }

    public void clear() {
        this.obelisks.clear();
        this.states.clear();
    }

}
