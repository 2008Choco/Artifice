package wtf.choco.artifice.api;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;

import wtf.choco.artifice.api.obelisk.ObeliskQuality;

public class QualityRegistry {

    private final Map<String, ObeliskQuality> qualities = new HashMap<>();

    /**
     * Register a custom {@link ObeliskQuality}.
     *
     * @param quality the quality to register
     */
    public void registerQuality(ObeliskQuality quality) {
        this.qualities.put(quality.getKey().toString(), quality);
    }

    /**
     * Get an {@link ObeliskQuality} by its unique key (including the namespace). i.e. {@code artifice:example}.
     *
     * @param key the key of the quality to get
     *
     * @return the quality. null if none registered with the given key
     */
    public ObeliskQuality getQuality(String key) {
        return qualities.get(key);
    }

    /**
     * Get an {@link ObeliskQuality} by its unique key.
     *
     * @param key the key of the quality to get
     *
     * @return the quality. null if none registered with the given key
     */
    public ObeliskQuality getQuality(NamespacedKey key) {
        return getQuality(key.toString());
    }

    /**
     * Unregister the given {@link ObeliskQuality}.
     *
     * @param quality the quality to unregister
     */
    public void unregisterQuality(ObeliskQuality quality) {
        this.unregisterQuality(quality.getKey());
    }

    /**
     * Unregister the {@link ObeliskQuality} with the given key.
     *
     * @param key the key of the quality to unregister
     */
    public void unregisterQuality(NamespacedKey key) {
        this.qualities.remove(key.toString());
    }

    /**
     * Get an unmodifiable collection of all registered {@link ObeliskQuality}s.
     *
     * @return all registered qualities
     */
    public Collection<ObeliskQuality> getRegisteredQualities() {
        return Collections.unmodifiableCollection(qualities.values());
    }

    /**
     * Clear the manager of all registered qualities.
     */
    public void clear() {
        this.qualities.clear();
    }

}
