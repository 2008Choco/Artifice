package wtf.choco.relics.api.artifact;

import org.bukkit.NamespacedKey;

import wtf.choco.relics.artifacts.ArtifactType;
import wtf.choco.relics.artifacts.Rarity;

/**
 * Represents a special item ("artifact") capable of corrupting any type of artifact.
 * Corrupted artifacts will randomly corrupt any discovered artifact
 *
 * @author Parker Hawke - 2008Choco
 */
public abstract class CorruptedArtifact extends AbstractArtifact {

    protected CorruptedArtifact(NamespacedKey key, Rarity rarity) {
        super(key, rarity, ArtifactType.CORRUPTED);
    }

    /**
     * The percentage (0.00% - 100.00%) that this item can corrupt a discovered artifact;
     * 100 being guaranteed. Decimal values (up to two places) are accepted
     *
     * @return the percentage chance
     */
    public abstract double corruptionPercent();

    /**
     * Check whether the specified artifact type can be corrupted by this artifact
     *
     * @param type the type to check
     *
     * @return whether the artifact is corruptable or not
     */
    public abstract boolean canCorrupt(ArtifactType type);

}
