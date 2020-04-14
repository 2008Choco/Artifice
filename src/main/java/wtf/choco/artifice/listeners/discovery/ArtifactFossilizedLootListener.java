package wtf.choco.artifice.listeners.discovery;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.artifact.Artifact;
import wtf.choco.artifice.api.artifact.FossilizedArtifact;
import wtf.choco.artifice.api.events.player.PlayerDiscoverArtifactEvent;
import wtf.choco.artifice.artifacts.ArtifactType;
import wtf.choco.artifice.utils.ArtifactUtils;

public class ArtifactFossilizedLootListener implements Listener {

    private static final Random RANDOM = new Random();

    private final Artifice plugin;

    public ArtifactFossilizedLootListener(Artifice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMineBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockMat = event.getBlock().getType();

        List<Artifact> artifacts = plugin.getArtifactManager().getArtifacts(ArtifactType.FOSSILIZED);
        for (Artifact a : artifacts) {
            FossilizedArtifact artifact = (FossilizedArtifact) a;

            // Check requirements
            if (!artifact.isValidMaterial(blockMat) || RANDOM.nextDouble() * 100 > artifact.discoveryPercent()) {
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
            player.getInventory().addItem(ArtifactUtils.createItemStack(pdae.getArtifact()));
            // TODO: Play a, "mystical sound of discovery" :P
            break;
        }
    }
}
