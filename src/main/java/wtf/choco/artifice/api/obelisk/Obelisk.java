package wtf.choco.artifice.api.obelisk;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Preconditions;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.utils.SoundData;

/**
 * Represents an obelisk with an {@link ObeliskStructure} that may be formed and apply effects
 * to a player, other entities or execute passive functionality.
 *
 * @author Parker Hawke - Choco
 */
public abstract class Obelisk implements Keyed {

    private final NamespacedKey key;
    private final Set<ObeliskQuality> qualities;

    protected Obelisk(NamespacedKey key) {
        Preconditions.checkArgument(key != null, "Key must not be null");
        this.key = key;
        this.qualities = new HashSet<>();
    }

    @Override
    public final NamespacedKey getKey() {
        return key;
    }

    /**
     * Add a quality to this obelisk. The quality added may define the behaviour of this obelisk.
     *
     * @param quality the quality to add
     *
     * @return whether or not the quality was added. If false, the quality likely conflicts
     * with one already present on this obelisk or vice versa
     */
    public boolean addQuality(ObeliskQuality quality) {
        return qualities.stream().noneMatch(q -> q.conflictsWith(quality) || quality.conflictsWith(q)) && qualities.add(quality);
    }

    /**
     * Remove a quality from this obelisk.
     *
     * @param quality the quality to remove
     */
    public void removeQuality(ObeliskQuality quality) {
        this.qualities.remove(quality);
    }

    /**
     * Check whether this obelisk has the queried quality.
     *
     * @param quality the quality to check
     *
     * @return true if this obelisk has the quality, false otherwise
     */
    public boolean hasQuality(ObeliskQuality quality) {
        return qualities.contains(quality);
    }

    /**
     * Get an unmodifiable set of all qualities present on this obelisk.
     *
     * @return this obelisk's qualities
     */
    public Set<ObeliskQuality> getQualities() {
        return Collections.unmodifiableSet(qualities);
    }

    /**
     * Get the structure for this obelisk rotated along the specified {@link StructureRotation}.
     * Whether or not the structure supports rotation is entirely up to implementation. An obelisk may
     * return the same {@link ObeliskStructure} instance for all rotations constants.
     *
     * @param rotation the structure rotation
     *
     * @return the obelisk structure
     */
    public abstract ObeliskStructure getStructure(StructureRotation rotation);

    /**
     * Check whether or not this obelisk has a strong aura. A strong aura determines whether obelisks
     * may be formed if blocks within its structures bounds that were not explicitly declared or whether
     * an obelisk may be disrupted if a block is placed within them.
     * <p>
     * If true, unspecified structure positions may <strong>NOT</strong> be occupied by non-obelisk-specific
     * blocks. If false, such that the core blocks have been positioned correctly, the obelisk will not be
     * disrupted.
     *
     * @return true if the obelisk has a strong aura, false otherwise
     */
    public boolean hasStrongAura() {
        return true;
    }

    /**
     * Get this obelisk's name.
     *
     * @param state the obelisk state. May optionally be used for context. Must not be null
     *
     * @return the obelisk's name
     */
    public String getName(ObeliskState state) {
        return key.getKey();
    }

    /**
     * Get this obelisk's name with format codes.
     *
     * @param state the obelisk state. May optionally be used for context. Must not be null
     *
     * @return the obelisk's formatted name
     */
    public String getNameFormatted(ObeliskState state) {
        return getName(state);
    }

    /**
     * Get this obelisk's engraving.
     *
     * @param state the obelisk state. May optionally be used for context. Must not be null
     *
     * @return the obelisk's engraving. null if no engraving
     */
    public String getEngraving(ObeliskState state) {
        return state.getEngraving();
    }

    /**
     * Get the message to be sent to the player that forms this obelisk. May not be the same
     * from call to call.
     *
     * @param state the obelisk state. May optionally be used for context. Must not be null
     * @param random a random instance
     *
     * @return the formation message. null if no message should be sent
     */
    public String getFormationMessage(ObeliskState state, Random random) {
        return Artifice.GLOBAL_FORMATION_MESSAGES[random.nextInt(Artifice.GLOBAL_FORMATION_MESSAGES.length)];
    }

    /**
     * Get the message to be sent to the player that disrupts this obelisk. May not be the same
     * from call to call.
     *
     * @param state the obelisk state. May optionally be used for context. Must not be null
     * @param random a random instance
     *
     * @return the disruption message. null if no message should be sent
     */
    public String getDisruptionMessage(ObeliskState state, Random random) {
        return null;
    }

    /**
     * Get the sound to be played for the player that forms this obelisk.
     *
     * @param state the obelisk state. May optionally be used for context. Must not be null
     *
     * @return the formation message. null if no sound should be played
     */
    public SoundData getFormationSound(ObeliskState state) {
        return null;
    }

    /**
     * Get the sound to be played for the player that disrupts this obelisk.
     *
     * @param state the obelisk state. May optionally be used for context. Must not be null
     *
     * @return the disruption message. null if no sound should be played
     */
    public SoundData getDisruptionSound(ObeliskState state) {
        return null;
    }

    /**
     * Get the particle to be used as a formation hint for players hovering over the obelisk's
     * formation block ({@link ObeliskState#getFormationBlock()}).
     *
     * @return the formation hint particle
     */
    public Particle getFormationHintParticle() {
        return Particle.FLAME;
    }

    /**
     * Called once every tick to update the given obelisk state.
     *
     * @param state the obelisk state being ticked.
     * @param partialTick the partial tick
     */
    public void tick(ObeliskState state, int partialTick) { }

    /**
     * Called when a player disrupts the given obelisk state.
     *
     * @param state the obelisk state being disrupted
     * @param player the player that disrupted the obelisk
     */
    public void onDisrupted(ObeliskState state, Player player) { }

    /**
     * Create an ObeliskState given the supplied information.
     *
     * @param owner the owner of the obelisk
     * @param world the world in which the obelisk should be created
     * @param bounds the obelisk bounds
     * @param rotation the obelisk rotation
     * @param components all non-air blocks belonging to the obelisk
     *
     * @return the created obelisk state
     */
    public ObeliskState createObelisk(OfflinePlayer owner, World world, BoundingBox bounds, StructureRotation rotation, Set<Block> components) {
        return new ObeliskState(this, owner, world, bounds, rotation, components);
    }

}
