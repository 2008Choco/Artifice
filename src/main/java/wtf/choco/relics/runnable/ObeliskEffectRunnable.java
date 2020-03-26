package wtf.choco.relics.runnable;

import com.google.common.base.Preconditions;

import org.bukkit.scheduler.BukkitRunnable;

import wtf.choco.relics.Relics;
import wtf.choco.relics.api.obelisk.ObeliskState;

public final class ObeliskEffectRunnable extends BukkitRunnable {

    private static ObeliskEffectRunnable instance;

    private final Relics plugin;

    private ObeliskEffectRunnable(Relics plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (ObeliskState state : plugin.getObeliskManager().getObelisks()) {
            if (!state.isLoaded()) {
                continue;
            }

            state.tick();
        }
    }

    /**
     * Run the {@link ObeliskEffectRunnable} and create a singleton instance. If the task
     * has already been scheduled, this method will throw an IllegalStateException.
     *
     * @param plugin the plugin to schedule the task
     *
     * @return the {@link ObeliskEffectRunnable} instance
     */
    public static ObeliskEffectRunnable runTask(Relics plugin) {
        Preconditions.checkState(instance == null, "Runnable has already been scheduled");

        instance = new ObeliskEffectRunnable(plugin);
        instance.runTaskTimer(plugin, 0L, 1L);
        return instance;
    }

}
