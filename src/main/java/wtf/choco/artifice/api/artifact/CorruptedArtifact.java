package wtf.choco.artifice.api.artifact;

import org.bukkit.NamespacedKey;

import wtf.choco.artifice.artifacts.ArtifactType;
import wtf.choco.artifice.artifacts.Rarity;

/**
 * Represents a special item ("artifact") capable of corrupting any type of artifact.
 * Corrupted artifacts will randomly corrupt any discovered artifact.
 *
 * @author Parker Hawke - Choco
 */
public abstract class CorruptedArtifact extends AbstractArtifact {

    protected CorruptedArtifact(NamespacedKey key, Rarity rarity) {
        super(key, rarity, ArtifactType.CORRUPTED);
    }

    /**
     * Get the percent chance (0.0 - 100.0) that this item will corrupt a discovered artifact where
     * 0.0 is not at all and 100.0 is guaranteed.
     *
     * @return the corruption chance
     */
    public abstract double corruptionPercent();

    /**
     * Check whether the specified artifact type can be corrupted by this artifact.
     *
     * @param type the type to check
     *
     * @return true if the artifact type is corruptable, false otherwise
     */
    public abstract boolean canCorrupt(ArtifactType type);

}
