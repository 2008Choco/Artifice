package wtf.choco.artifice.runnable;

import com.google.common.base.Preconditions;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.ObeliskManager;
import wtf.choco.artifice.api.obelisk.Obelisk;
import wtf.choco.artifice.api.obelisk.ObeliskStructure;

public class AsyncObeliskFormationHint extends BukkitRunnable {

    private static AsyncObeliskFormationHint instance;

    private final Artifice plugin;

    public AsyncObeliskFormationHint(Artifice plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        ObeliskManager obeliskManager = plugin.getObeliskManager();

        for (Player player : Bukkit.getOnlinePlayers()) {
            Block targetBlock = player.getTargetBlockExact(5, FluidCollisionMode.NEVER);
            if (targetBlock == null) {
                continue;
            }

            Location targetLocation = targetBlock.getLocation();

            if (obeliskManager.getObelisk(targetBlock, true) != null) {
                continue; // Ignore obelisks that have already been created
            }

            obeliskLoop:
            for (Obelisk obelisk : plugin.getObeliskManager().getRegisteredObelisks()) {
                Particle particle = obelisk.getFormationHintParticle();

                for (StructureRotation rotation : StructureRotation.values()) {
                    ObeliskStructure structure = obelisk.getStructure(rotation);
                    Location obeliskLocation = targetLocation.clone().subtract(structure.getFormationX(), structure.getFormationY(), structure.getFormationZ());

                    if (!structure.matches(obeliskLocation, obelisk.hasStrongAura())) {
                        continue;
                    }

                    double increment = (1 / 3.0);
                    for (double x = 0.0; x <= 1.0; x += increment) {
                        for (double y = 0.0; y <= 1.0; y += increment) {
                            for (double z = 0.0; z <= 1.0; z += increment) {
                                int components = 0;

                                if (x <= 0.0 || x >= 1.0) {
                                    components++;
                                }
                                if (y <= 0.0 || y >= 1.0) {
                                    components++;
                                }
                                if (z <= 0.0 || z >= 1.0) {
                                    components++;
                                }

                                if (components >= 2) {
                                    targetLocation.add(x, y, z);
                                    player.spawnParticle(particle, targetLocation, 1, 0, 0, 0, 0);
                                    targetLocation.subtract(x, y, z);
                                }
                            }
                        }
                    }

                    break obeliskLoop;
                }
            }
        }
    }

    /**
     * Run the {@link AsyncObeliskFormationHint} and create a singleton instance. If the task
     * has already been scheduled, this method will throw an IllegalStateException.
     *
     * @param plugin the plugin to schedule the task
     *
     * @return the {@link AsyncObeliskFormationHint} instance
     */
    public static AsyncObeliskFormationHint runTask(Artifice plugin) {
        Preconditions.checkState(instance == null, "Runnable has already been scheduled");

        instance = new AsyncObeliskFormationHint(plugin);
        instance.runTaskTimerAsynchronously(plugin, 0L, 20L);
        return instance;
    }

}
