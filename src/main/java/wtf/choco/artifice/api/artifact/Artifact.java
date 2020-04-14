package wtf.choco.artifice.api.artifact;

import java.util.Random;

import org.bukkit.Keyed;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.artifice.artifacts.ArtifactType;
import wtf.choco.artifice.artifacts.Rarity;

/**
 * Represents a special item ("artifact") with special effects applicable to players.
 *
 * @author Parker Hawke - Choco
 */
public interface Artifact extends Keyed {

    /**
     * Get the item representation of the artifact.
     *
     * @return the item to reference
     */
    public ItemStack getItem();

    /**
     * Get the artifact's rarity.
     *
     * @return the rarity
     */
    public Rarity getRarity();

    /**
     * Get the artifact's type.
     *
     * @return the type of artifact
     */
    public ArtifactType getType();

    /**
     * Get the name of the artifact.
     *
     * @return the artifact's name
     */
    public String getName();

    /**
     * Get the name of the artifact with format codes applied. Defaults to {@link #getName()}.
     *
     * @return the artifact's formatted name
     */
    public default String getNameFormatted() {
        return getName();
    }

    /**
     * Called once every server tick while in a player's inventory.
     *
     * @param player the player <i>currently</i> being affected
     * @param item the artifact item
     * @param slot the slot in which the artifact is present
     * @param random an instance of random
     * @param partialTick the partial tick
     */
    public void tick(Player player, ItemStack item, int slot, Random random, int partialTick);

    /**
     * Called once every server tick while the artifact is on the ground.
     *
     * @param item the item entity
     * @param partialTick the partial tick
     */
    public default void worldTick(Item item, int partialTick) { }

}
