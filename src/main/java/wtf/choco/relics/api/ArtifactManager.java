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
 * Represents a manager to handle all interactions regarding artifacts
 *
 * @author Parker Hawke - 2008Choco
 */
public class ArtifactManager {

    private final Map<String, Artifact> artifacts = new HashMap<>();
    private final Map<ItemStack, Artifact> artifactsByItem = new HashMap<>();

    /**
     * Register a new artifact to the artifact registry
     *
     * @param artifact the artifact to register
     */
    public void registerArtifact(Artifact artifact) {
        Preconditions.checkArgument(artifact != null, "Cannot register null artifact");
        Preconditions.checkArgument(artifact.getType() != null, "Artifact has null type (%s)", artifact.getType());

        this.artifacts.put(artifact.getKey().toString(), artifact);

        ItemStack item = artifact.getItem();
        item.setAmount(1);
        this.artifactsByItem.put(ArtifactUtils.addTypeAndRarity(artifact), artifact);
    }

    /**
     * Get the artifact instance based on its name
     *
     * @param id the name of the artifact
     *
     * @return the resulting instance, or null if not registered
     */
    public Artifact getArtifact(String id) {
        return artifacts.get(id);
    }

    public Artifact getArtifact(NamespacedKey key) {
        return getArtifact(key.toString());
    }

    /**
     * Get the artifact instance based on its item
     *
     * @param item the item to reference
     *
     * @return the resulting instance, or null if not registered
     */
    public Artifact getArtifact(ItemStack item) {
        item = item.clone();
        item.setAmount(1);

        return artifactsByItem.get(item);
    }

    public Collection<Artifact> getArtifacts() {
        return Collections.unmodifiableCollection(artifacts.values());
    }

    /**
     * Get instances of all artifacts of the given type
     *
     * @param type the type to search for
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
     * Get instances of all artifacts of the given types
     *
     * @param types the types to search for
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

    public void clearArtifacts() {
        this.artifacts.clear();
    }

    /**
     * Give an artifact's item to a player
     *
     * @param player the player to give the artifact to
     * @param artifact the artifact to give
     */
    public void giveArtifact(Player player, Artifact artifact) {
        player.getInventory().addItem(ArtifactUtils.addTypeAndRarity(artifact));
    }

}
