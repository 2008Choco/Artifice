package wtf.choco.artifice.listeners.artifact;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.artifact.Artifact;

public class ArtifactDroppedListener implements Listener {

    private final Artifice plugin;

    public ArtifactDroppedListener(Artifice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onArtifactItemEntityCreated(EntitySpawnEvent event) {
        if (event.getEntityType() != EntityType.DROPPED_ITEM) {
            return;
        }

        Item item = (Item) event.getEntity();
        Artifact artifact = plugin.getArtifactManager().getArtifact(item.getItemStack());
        if (artifact == null) {
            return;
        }

        this.plugin.getArtifactEffectLoop().trackArtifactItem(item, artifact);
    }

}
