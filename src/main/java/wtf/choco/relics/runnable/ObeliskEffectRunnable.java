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

    public static ObeliskEffectRunnable runTask(Relics plugin) {
        Preconditions.checkArgument(instance == null, "Obelisk effect runnable has already been scheduled");

        instance = new ObeliskEffectRunnable(plugin);
        instance.runTaskTimer(plugin, 0L, 1L);
        return instance;
    }

}
