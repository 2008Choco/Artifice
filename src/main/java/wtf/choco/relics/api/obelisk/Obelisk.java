package wtf.choco.relics.api.obelisk;

import java.util.Random;
import java.util.Set;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;

import wtf.choco.relics.utils.SoundData;

public class Obelisk implements Keyed {

    private final NamespacedKey key;
    private final ObeliskStructure structure;

    public Obelisk(NamespacedKey key, ObeliskStructure structure) {
        this.key = key;
        this.structure = structure;
    }

    @Override
    public final NamespacedKey getKey() {
        return key;
    }

    public ObeliskStructure getStructure() {
        return structure;
    }

    public String getName(@SuppressWarnings("unused") ObeliskState state) {
        return key.getKey();
    }

    public String getNameFormatted(ObeliskState state) {
        return getName(state);
    }

    public String getEngraving(ObeliskState state) {
        return state.getEngraving();
    }

    @SuppressWarnings("unused")
    public String getFormationMessage(ObeliskState state, Random random) {
        return null;
    }

    @SuppressWarnings("unused")
    public String getDisruptionMessage(ObeliskState state, Random random) {
        return null;
    }

    public SoundData getFormationSound(@SuppressWarnings("unused") ObeliskState state) {
        return null;
    }

    public SoundData getDisruptionSound(@SuppressWarnings("unused") ObeliskState state) {
        return null;
    }

    @SuppressWarnings("unused")
    public void tick(ObeliskState state, int partialTick) { }

    public ObeliskState createObelisk(OfflinePlayer owner, World world, BoundingBox bounds, Set<Block> components) {
        return new ObeliskState(this, owner, world, bounds, components);
    }

}
