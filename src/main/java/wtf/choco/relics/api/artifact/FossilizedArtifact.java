package wtf.choco.relics.api.artifact;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import wtf.choco.relics.artifacts.ArtifactType;
import wtf.choco.relics.artifacts.Rarity;

/**
 * Represents a more specific Artifact implementation discoverable through mining blocks
 * such as stone, dirt, gravel, etc.
 *
 * @author Parker Hawke - 2008Choco
 */
public abstract class FossilizedArtifact extends AbstractArtifact {

    private static final Set<Material> VALID_MATERIALS = EnumSet.of(Material.STONE, Material.DIRT, Material.GRAVEL, Material.NETHERRACK, Material.END_STONE);

    protected FossilizedArtifact(NamespacedKey key, Rarity rarity) {
        super(key, rarity, ArtifactType.FOSSILIZED);
    }

    /**
     * Whether the passed material is a valid material to obtain the artifact or not By
     * default, most mining materials will be used such as stone, dirt, gravel, etc.
     *
     * @param material the material to check
     *
     * @return true if valid material, false otherwise
     */
    public boolean isValidMaterial(Material material) {
        return VALID_MATERIALS.contains(material);
    }

    /**
     * The percentage (0.00% - 100.00%) that this item can be discovered; 100 being
     * guaranteed. Decimal values (up to two places) are accepted
     *
     * @return the percentage chance
     */
    public abstract double discoveryPercent();

}
