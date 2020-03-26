package wtf.choco.relics.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.relics.api.artifact.Artifact;
import wtf.choco.relics.artifacts.ArtifactType;
import wtf.choco.relics.utils.ArtifactUtils;

/**
 * Represents a manager to handle all interactions regarding artifacts.
 *
 * @author Parker Hawke - Choco
 */
public class ArtifactManager {

    private final Map<String, Artifact> artifacts = new HashMap<>();
    private final Map<ItemStack, Artifact> artifactsByItem = new HashMap<>();

    /**
     * Register an artifact to the artifact registry.
     *
     * @param artifact the artifact to register
     */
    public void registerArtifact(Artifact artifact) {
        Preconditions.checkArgument(artifact != null, "Cannot register null artifact");
        Preconditions.checkArgument(artifact.getType() != null, "Artifact has null type (%s)", artifact.getType());

        this.artifacts.put(artifact.getKey().toString(), artifact);

        ItemStack item = artifact.getItem();
        item.setAmount(1);
        this.artifactsByItem.put(ArtifactUtils.createItemStack(artifact), artifact);
    }

    /**
     * Get an {@link Artifact} by its unique key (including the namespace). i.e. {@code relics:example}.
     *
     * @param key the key of the artifact to get
     *
     * @return the artifact. null if none registered with the given key
     */
    public Artifact getArtifact(String key) {
        return artifacts.get(key);
    }

    /**
     * Get an {@link Artifact} by its unique id.
     *
     * @param key the key of the artifact to get
     *
     * @return the artifact. null if none registered with the given key
     */
    public Artifact getArtifact(NamespacedKey key) {
        return getArtifact(key.toString());
    }

    /**
     * Get an {@link Artifact} by its {@link ItemStack}. Comparisons are made by calls to
     * {@link ItemStack#equals(Object)}, though amount will not be taken into consideration.
     *
     * @param item the item of the artifact to get
     *
     * @return the artifact. null if no artifact is associated with the given item
     */
    public Artifact getArtifact(ItemStack item) {
        item = item.clone();
        item.setAmount(1);

        return artifactsByItem.get(item);
    }

    /**
     * Get an unmodifiable collection of all registered artifacts.
     *
     * @return all registered artifacts
     */
    public Collection<Artifact> getArtifacts() {
        return Collections.unmodifiableCollection(artifacts.values());
    }

    /**
     * Get a list of all artifacts of the given type.
     *
     * @param type the type of artifact to get
     *
     * @return all registered artifacts with the given type
     */
    public List<Artifact> getArtifacts(ArtifactType type) {
        List<Artifact> result = new ArrayList<>();

        for (Artifact artifact : artifacts.values()) {
            if (artifact.getType() == type) {
                result.add(artifact);
            }
        }

        return result;
    }

    /**
     * Get a list of all artifacts of the given types
     *
     * @param types the types of artifacts to get
     *
     * @return all registered artifacts with one of the given types
     */
    public Set<Artifact> getArtifacts(ArtifactType... types) {
        Set<Artifact> result = new HashSet<>();

        for (Artifact artifact : artifacts.values()) {
            for (ArtifactType type : types) {
                if (artifact.getType() == type) {
                    result.add(artifact);
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Clear all registered artifacts.
     */
    public void clearArtifacts() {
        this.artifacts.clear();
    }

    /**
     * Give an artifact's item to a player with additional lore.
     *
     * @param player the player to whom the artifact should be given
     * @param artifact the artifact to give
     *
     * @see ArtifactUtils#createItemStack(Artifact)
     */
    public void giveArtifact(Player player, Artifact artifact) {
        player.getInventory().addItem(ArtifactUtils.createItemStack(artifact));
    }

}
