package wtf.choco.relics.api.artifact;

import org.bukkit.NamespacedKey;

import wtf.choco.relics.artifacts.ArtifactType;
import wtf.choco.relics.artifacts.Rarity;

/**
 * An abstract, default implementation of {@link Artifact}. Developers should prefer extending
 * this class rather than implementing its parent, Artifact, directly.
 *
 * @author Parker Hawke - Choco
 */
public abstract class AbstractArtifact implements Artifact {

    private final NamespacedKey key;
    private final Rarity rarity;
    private final ArtifactType type;

    /**
     * Supply this abstract artifact with necessary information.
     *
     * @param key see {@link #getKey()}
     * @param rarity see {@link #getRarity()}
     * @param type see {@link #getType()}
     */
    protected AbstractArtifact(NamespacedKey key, Rarity rarity, ArtifactType type) {
        this.key = key;
        this.rarity = rarity;
        this.type = type;
    }

    @Override
    public final NamespacedKey getKey() {
        return key;
    }

    @Override
    public final Rarity getRarity() {
        return rarity;
    }

    @Override
    public final ArtifactType getType() {
        return type;
    }

    @Override
    public String getName() {
        return key.getKey();
    }

}
