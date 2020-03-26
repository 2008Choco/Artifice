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
 * @author Parker Hawke - Choco
 */
public abstract class FossilizedArtifact extends AbstractArtifact {

    private static final Set<Material> VALID_MATERIALS = EnumSet.of(Material.STONE, Material.ANDESITE, Material.DIORITE, Material.GRANITE, Material.DIRT, Material.SAND, Material.RED_SAND, Material.GRAVEL, Material.NETHERRACK, Material.END_STONE);

    protected FossilizedArtifact(NamespacedKey key, Rarity rarity) {
        super(key, rarity, ArtifactType.FOSSILIZED);
    }

    /**
     * Check whether or not the passed material is valid to obtain the artifact. By default, most
     * materials typically associated with mining will be used. These materials are:
     * <ul>
     *   <li>{@link Material#STONE}
     *   <li>{@link Material#ANDESITE}
     *   <li>{@link Material#DIORITE}
     *   <li>{@link Material#GRANITE}
     *   <li>{@link Material#DIRT}
     *   <li>{@link Material#SAND}
     *   <li>{@link Material#RED_SAND}
     *   <li>{@link Material#GRAVEL}
     *   <li>{@link Material#NETHERRACK}
     *   <li>{@link Material#END_STONE}
     * </ul>
     *
     * @param material the material to check
     *
     * @return true if valid material, false otherwise
     */
    public boolean isValidMaterial(Material material) {
        return VALID_MATERIALS.contains(material);
    }

    /**
     * Get the percent chance (0.0 - 100.0) that this item will be discovered where 0.0 is not
     * at all and 100.0 is guaranteed.
     *
     * @return the discovery chance
     */
    public abstract double discoveryPercent();

}
