package wtf.choco.relics.api.artifact;

import org.bukkit.NamespacedKey;

import wtf.choco.relics.artifacts.ArtifactType;
import wtf.choco.relics.artifacts.Rarity;

/**
 * Represents a more specific Artifact implementation discoverable through the killing of
 * hostile entities
 *
 * @author Parker Hawke - 2008Choco
 */
public abstract class NecroticArtifact extends AbstractArtifact {

    protected NecroticArtifact(NamespacedKey key, Rarity rarity) {
        super(key, rarity, ArtifactType.NECROTIC);
    }

    /**
     * The percentage (0.00% - 100.00%) that this item can be discovered; 100 being
     * guaranteed. Decimal values (up to two places) are accepted
     *
     * @return the percentage chance
     */
    public abstract double discoveryPercent();

}
