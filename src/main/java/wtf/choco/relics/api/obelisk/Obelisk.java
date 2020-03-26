package wtf.choco.relics.api.obelisk;

import java.util.Random;
import java.util.Set;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.util.BoundingBox;

import wtf.choco.relics.Relics;
import wtf.choco.relics.utils.SoundData;

public abstract class Obelisk implements Keyed {

    private final NamespacedKey key;

    public Obelisk(NamespacedKey key) {
        this.key = key;
    }

    @Override
    public final NamespacedKey getKey() {
        return key;
    }

    public abstract ObeliskStructure getStructure(StructureRotation rotation);

    public boolean hasStrongAura() {
        return true;
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

    public String getFormationMessage(@SuppressWarnings("unused") ObeliskState state, Random random) {
        return Relics.GLOBAL_FORMATION_MESSAGES[random.nextInt(Relics.GLOBAL_FORMATION_MESSAGES.length)];
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

    public Particle getFormationHintParticle() {
        return Particle.FLAME;
    }

    @SuppressWarnings("unused")
    public void tick(ObeliskState state, int partialTick) { }

    public ObeliskState createObelisk(OfflinePlayer owner, World world, BoundingBox bounds, StructureRotation rotation, Set<Block> components) {
        return new ObeliskState(this, owner, world, bounds, rotation, components);
    }

}
