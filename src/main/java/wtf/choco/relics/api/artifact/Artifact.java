package wtf.choco.relics.api.artifact;

import java.util.Random;

import org.bukkit.Keyed;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.relics.artifacts.ArtifactType;
import wtf.choco.relics.artifacts.Rarity;

/**
 * Represents a special item ("artifact") with special effects applicable to players
 *
 * @author Parker Hawke - 2008Choco
 */
public interface Artifact extends Keyed {

    /**
     * The item representation of the artifact (Used for identification)
     *
     * @return the item to reference
     */
    public ItemStack getItem();

    /**
     * Get the rarity of the artifact
     *
     * @return the rarity
     */
    public Rarity getRarity();

    /**
     * Get the type of artifact
     *
     * @return the type of artifact
     */
    public ArtifactType getType();

    /**
     * The name of the obelisk used in game for identification
     *
     * @return the artifact name
     */
    public String getName();

    /**
     * The name of the obelisk used in game for identification
     *
     * @return the artifact name
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
