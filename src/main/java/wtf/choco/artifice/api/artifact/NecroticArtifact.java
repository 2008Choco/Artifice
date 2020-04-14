package wtf.choco.artifice.api.artifact;

import org.bukkit.NamespacedKey;

import wtf.choco.artifice.artifacts.ArtifactType;
import wtf.choco.artifice.artifacts.Rarity;

/**
 * Represents a more specific Artifact implementation discoverable through the killing of
 * hostile entities.
 *
 * @author Parker Hawke - Choco
 */
public abstract class NecroticArtifact extends AbstractArtifact {

    protected NecroticArtifact(NamespacedKey key, Rarity rarity) {
        super(key, rarity, ArtifactType.NECROTIC);
    }

    /**
     * Get the percent chance (0.0 - 100.0) that this item will be discovered where 0.0 is
     * not at all and 100.0 is guaranteed.
     *
     * @return the discovery chance
     */
    public abstract double discoveryPercent();

}
