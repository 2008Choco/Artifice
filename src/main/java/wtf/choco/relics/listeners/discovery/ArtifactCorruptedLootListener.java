package wtf.choco.relics.listeners.discovery;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import wtf.choco.relics.Relics;
import wtf.choco.relics.api.artifact.Artifact;
import wtf.choco.relics.api.artifact.CorruptedArtifact;
import wtf.choco.relics.api.events.ArtifactCorruptEvent;
import wtf.choco.relics.api.events.player.PlayerDiscoverArtifactEvent;
import wtf.choco.relics.artifacts.ArtifactType;

public class ArtifactCorruptedLootListener implements Listener {

    private static final Random RANDOM = new Random();

    private final Relics plugin;

    public ArtifactCorruptedLootListener(Relics plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDiscoverArtifact(PlayerDiscoverArtifactEvent event) {
        if (event.getArtifact().getType() == ArtifactType.CORRUPTED) {
            return;
        }

        Player player = event.getPlayer();

        List<Artifact> artifacts = plugin.getArtifactManager().getArtifacts(ArtifactType.CORRUPTED);
        for (Artifact a : artifacts) {
            CorruptedArtifact artifact = (CorruptedArtifact) a;

            // Check conditions
            if (!artifact.canCorrupt(event.getArtifact().getType()) || RANDOM.nextDouble() * 100 > artifact.corruptionPercent()) {
                return;
            }

            // Call the event
            ArtifactCorruptEvent ace = new ArtifactCorruptEvent(player, event.getArtifact(), artifact);
            Bukkit.getPluginManager().callEvent(ace);
            if (ace.isCancelled()) {
                return;
            }

            // Corrupt the artifact
            event.setArtifact(artifact);
            event.setMessage("The artifact's aura corrupts around you violently. You have discovered a " + artifact.getNameFormatted());
            break;
        }
    }
}
