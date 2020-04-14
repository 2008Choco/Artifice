package wtf.choco.artifice.listeners.discovery;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.artifact.Artifact;
import wtf.choco.artifice.api.artifact.NecroticArtifact;
import wtf.choco.artifice.api.events.player.PlayerDiscoverArtifactEvent;
import wtf.choco.artifice.artifacts.ArtifactType;
import wtf.choco.artifice.utils.ArtifactUtils;

public class ArtifactNecroticLootListener implements Listener {

    private static final Random RANDOM = new Random();

    private final Artifice plugin;

    public ArtifactNecroticLootListener(Artifice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKillEntity(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null || !(event.getEntity() instanceof Monster))
            return;

        List<Artifact> artifacts = plugin.getArtifactManager().getArtifacts(ArtifactType.NECROTIC);
        for (Artifact a : artifacts) {
            NecroticArtifact artifact = (NecroticArtifact) a;
            if (RANDOM.nextDouble() * 100 > artifact.discoveryPercent()) {
                return;
            }

            // PlayerDiscoverArtifactEvent
            PlayerDiscoverArtifactEvent pdae = new PlayerDiscoverArtifactEvent(player, artifact);
            Bukkit.getPluginManager().callEvent(pdae);
            if (pdae.isCancelled()) {
                return;
            }

            // Give actual artifact (Can be modified in event)
            this.plugin.sendMessage(player, pdae.getMessage());
            event.getDrops().add(ArtifactUtils.createItemStack(pdae.getArtifact()));
            // TODO: Play a "mystical sound of discovery" :P
            break;
        }
    }
}
