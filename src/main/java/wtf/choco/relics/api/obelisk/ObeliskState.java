package wtf.choco.relics.api.obelisk;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Objects;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

public class ObeliskState {

    private int partialTick = 0;
    private String engraving = null;

    protected final Obelisk obelisk;
    protected final UUID owner;
    protected final World world;
    protected final BoundingBox bounds;
    protected final Set<Block> components;

    public ObeliskState(Obelisk obelisk, OfflinePlayer owner, World world, BoundingBox bounds, Set<Block> components) {
        this.obelisk = obelisk;
        this.owner = owner.getUniqueId();
        this.world = world;
        this.bounds = bounds;
        this.components = Collections.unmodifiableSet(components);

        this.setEngraving(obelisk.getName(this));
    }

    public final Obelisk getObelisk() {
        return obelisk;
    }

    public final UUID getOwnerUUID() {
        return owner;
    }

    public final OfflinePlayer getOwner() {
        return Bukkit.getOfflinePlayer(owner);
    }

    public final Optional<Player> getOwnerOnline() {
        return Optional.ofNullable(Bukkit.getPlayer(owner));
    }

    public final boolean isOwner(OfflinePlayer player) {
        return player != null && player.getUniqueId().equals(owner);
    }

    public final World getWorld() {
        return world;
    }

    public BoundingBox getBounds() {
        return bounds.clone();
    }

    public final Set<Block> getComponents() {
        return components; // Unmodifiable through constructor
    }

    public final boolean isLoaded() {
        return components.stream().anyMatch(b -> b.getWorld().isChunkLoaded(b.getX() >> 4, b.getZ() >> 4));
    }

    public final void tick() {
        this.obelisk.tick(this, partialTick);

        // Reset partial tick back to 0 after 20 whole ticks
        if (++partialTick == 20) {
            this.partialTick = 0;
        }

        this.tick(partialTick);
    }

    public void setEngraving(String engraving) {
        this.engraving = engraving;
    }

    public String getEngraving() {
        return engraving;
    }

    public boolean hasEngraving() {
        return !StringUtils.isBlank(engraving);
    }

    protected void tick(@SuppressWarnings("unused") int partialTick) {
        // For implementations
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
