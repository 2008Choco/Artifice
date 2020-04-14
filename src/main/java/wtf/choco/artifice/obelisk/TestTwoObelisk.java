package wtf.choco.artifice.obelisk;

import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.obelisk.Obelisk;
import wtf.choco.artifice.api.obelisk.ObeliskState;
import wtf.choco.artifice.api.obelisk.ObeliskStructure;
import wtf.choco.artifice.utils.SoundData;

public class TestTwoObelisk extends Obelisk {

    private static final int EFFECT_RADIUS = 5;

    private static final String NAME = "Test Two Obelisk", NAME_FORMATTED = ChatColor.GOLD + "Test Two Obelisk";
    private static final Map<StructureRotation, ObeliskStructure> STRUCTURES = ObeliskStructure.withSize(3, 3, 2)
            .set(0, 0, 1, Material.OAK_LOG).set(0, 1, 1, Material.OAK_LOG).set(0, 2, 1, Material.OAK_LOG)
            .set(1, 2, 1, Material.OAK_FENCE).set(2, 2, 1, Material.OAK_FENCE).set(2, 2, 0, Material.OAK_FENCE)
            .formationPoint(1, 2, 1).compileRotationMap();

    private static final SoundData FORMATION_SOUND = SoundData.of(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 5F, 0F);
    private static final SoundData DISRUPTION_SOUND = SoundData.of(Sound.ENTITY_ELDER_GUARDIAN_CURSE, 5F, 0F);

    public TestTwoObelisk(Artifice plugin) {
        super(new NamespacedKey(plugin, "test_two"));
    }

    @Override
    public ObeliskStructure getStructure(StructureRotation rotation) {
        return STRUCTURES.get(rotation);
    }

    @Override
    public String getName(ObeliskState state) {
        return NAME;
    }

    @Override
    public String getNameFormatted(ObeliskState state) {
        return NAME_FORMATTED;
    }

    @Override
    public String getDisruptionMessage(ObeliskState state, Random random) {
        return ChatColor.RED + "The aura of the obelisk has been disrupted!";
    }

    @Override
    public SoundData getFormationSound(ObeliskState state) {
        return FORMATION_SOUND;
    }

    @Override
    public SoundData getDisruptionSound(ObeliskState state) {
        return DISRUPTION_SOUND;
    }

    @Override
    public Particle getFormationHintParticle() {
        return Particle.VILLAGER_HAPPY;
    }

    @Override
    public void tick(ObeliskState state, int partialTick) {
        if (partialTick % 10 != 0) {
            return;
        }

        World world = state.getWorld();
        Location center = state.getBounds().getCenter().toLocation(world);

        world.getNearbyEntities(center, EFFECT_RADIUS, EFFECT_RADIUS, EFFECT_RADIUS).forEach(entity -> {
            if (!(entity instanceof Player)) {
                return;
            }

            Player player = (Player) entity;
            Location location = player.getLocation().clone().add(0, 1, 0);

            for (double theta = 0; theta < Math.PI * 2; theta += 0.25) {
                double x = Math.cos(theta), y = 1, z = Math.sin(theta);

                location.add(x, y, z);
                world.spawnParticle(Particle.VILLAGER_HAPPY, location, 1, 0, 0, 0, 0);
                location.subtract(x, y, z);
            }
        });
    }

}
