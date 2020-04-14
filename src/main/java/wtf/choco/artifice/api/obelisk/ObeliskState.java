package wtf.choco.artifice.api.obelisk;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Objects;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

/**
 * Represents an {@link Obelisk}'s state. One will be created for each instance of an obelisk
 * in the world. All state-specific information for an obelisk is represented by this object.
 *
 * @author Parker Hawke - Choco
 */
public class ObeliskState {

    private int partialTick = 0;
    private String engraving = null;

    protected final Obelisk obelisk;
    protected final UUID owner;
    protected final World world;
    protected final Set<Block> components;
    protected final StructureRotation rotation;

    private final BoundingBox bounds;

    public ObeliskState(Obelisk obelisk, OfflinePlayer owner, World world, BoundingBox bounds, StructureRotation rotation, Set<Block> components) {
        this.obelisk = obelisk;
        this.owner = owner.getUniqueId();
        this.world = world;
        this.bounds = bounds;
        this.rotation = rotation;

        components.removeIf(b -> b == null || b.isEmpty());
        this.components = Collections.unmodifiableSet(components);

        this.setEngraving(obelisk.getName(this));
    }

    /**
     * Get the state's obelisk definition.
     *
     * @return the obelisk
     */
    public final Obelisk getObelisk() {
        return obelisk;
    }

    /**
     * Get the UUID of the owning player.
     *
     * @return the owner's UUID
     */
    public final UUID getOwnerUUID() {
        return owner;
    }

    /**
     * Get the {@link OfflinePlayer} of the owning player.
     *
     * @return the owning player
     */
    public final OfflinePlayer getOwner() {
        return Bukkit.getOfflinePlayer(owner);
    }

    /**
     * Get the owning {@link Player} if online. If the owner is not online, the returned optional
     * will be empty.
     *
     * @return the owning player
     */
    public final Optional<Player> getOwnerOnline() {
        return Optional.ofNullable(Bukkit.getPlayer(owner));
    }

    /**
     * Check whether the specified player owns this obelisk.
     *
     * @param player the player to check
     *
     * @return true if the player owns this obelisk, false otherwise
     */
    public final boolean isOwner(OfflinePlayer player) {
        return player != null && player.getUniqueId().equals(owner);
    }

    /**
     * Get the world in which this obelisk state resides.
     *
     * @return the world
     */
    public final World getWorld() {
        return world;
    }

    /**
     * Get this obelisk's formation block. This is a convenience method and is equivalent to
     * {@code getObelisk().getStructure(getRotation()).getFormationBlock(this)}.
     *
     * @return the formation block
     */
    public final Block getFormationBlock() {
        return obelisk.getStructure(rotation).getFormationBlock(this);
    }

    /**
     * Get this obelisk's bounds. Any changes made to the returned bounds will not affect those
     * of this obelisk.
     *
     * @return the obelisk's bounds
     */
    public BoundingBox getBounds() {
        return bounds.clone();
    }

    /**
     * Get an immutable Set of all non-air block components belonging to this obelisk.
     *
     * @return the components
     */
    public final Set<Block> getComponents() {
        return components; // Unmodifiable through constructor
    }

    /**
     * Get the rotation of this obelisk.
     *
     * @return the rotation
     */
    public StructureRotation getRotation() {
        return rotation;
    }

    /**
     * Get this obelisk's structure. This is a convenience method and is equivalent to
     * {@code getObelisk().getStructure(getRotation())}.
     *
     * @return the obelisk structure
     */
    public ObeliskStructure getStructure() {
        return obelisk.getStructure(rotation);
    }

    /**
     * Check whether any part of this obelisk is loaded. If any occupying chunk is loaded, this obelisk
     * will be considered loaded.
     *
     * @return true if loaded, false otherwise.
     */
    public final boolean isLoaded() {
        return components.stream().anyMatch(b -> b.getWorld().isChunkLoaded(b.getX() >> 4, b.getZ() >> 4));
    }

    /**
     * Check whether this obelisk has the queried quality. This is a convenience method and is equivalent
     * to {@code getObelisk().hasQuality(quality)}.
     *
     * @param quality the quality to check
     *
     * @return true if this obelisk has the quality. false otherwise
     */
    public final boolean hasQuality(ObeliskQuality quality) {
        return obelisk.hasQuality(quality);
    }

    /**
     * Get an unmodifiable set of all qualities present on this obelisk. This is a convenience method and
     * is equivalent to {@code getObelisk().getQualities()}.
     *
     * @return this obelisk's qualities
     */
    public final Set<ObeliskQuality> getQualities() {
        return obelisk.getQualities();
    }

    /**
     * Tick this obelisk. Consequently invokes {@link Obelisk#tick(ObeliskState, int)}.
     */
    public final void tick() {
        this.obelisk.tick(this, partialTick);

        // Reset partial tick back to 0 after 20 whole ticks
        if (++partialTick == 20) {
            this.partialTick = 0;
        }

        this.tick(partialTick);
    }

    /**
     * Tick this obelisk state. This tick will be called after the {@link Obelisk#tick(ObeliskState, int)}.
     *
     * @param partialTick the partial tick
     */
    protected void tick(int partialTick) {
        // For implementations
    }

    /**
     * Set this obelisk's engraving.
     *
     * @param engraving the engraving to set. null to set no engraving
     */
    public void setEngraving(String engraving) {
        this.engraving = engraving;
    }

    /**
     * Get this obelisk's engraving.
     *
     * @return the engraving. null if none
     */
    public String getEngraving() {
        return engraving;
    }

    /**
     * Check whether or not this obelisk has an engraving.
     *
     * @return true if engraved, false otherwise
     */
    public boolean hasEngraving() {
        return engraving != null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(obelisk, owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ObeliskState)) {
            return false;
        }

        ObeliskState other = (ObeliskState) obj;
        return obelisk == other.obelisk && owner.equals(other.owner);
    }

}
