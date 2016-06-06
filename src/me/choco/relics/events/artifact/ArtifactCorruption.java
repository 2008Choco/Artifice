package me.choco.relics.events.artifact;

import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.choco.relics.Relics;
import me.choco.relics.api.events.ArtifactCorruptEvent;
import me.choco.relics.api.events.player.PlayerDiscoverArtifactEvent;
import me.choco.relics.artifacts.Artifact;
import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.artifacts.corrupted.CorruptedArtifact;
import me.choco.relics.utils.ArtifactManager;
import me.choco.relics.utils.general.ArtifactUtils;

public class ArtifactCorruption implements Listener {
	
	private static final Random random = new Random();
	
	private ArtifactManager manager;
	public ArtifactCorruption(Relics plugin){
		this.manager = plugin.getArtifactManager();
	}
	
	// MAKE SURE THIS EVENT RUNS FIRST
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDiscoverArtifact(PlayerDiscoverArtifactEvent event){
		if (event.getArtifact() instanceof CorruptedArtifact) return;
		Player player = event.getPlayer();
		
		Set<Artifact> artifacts = manager.getArtifacts(ArtifactType.CORRUPTED);
		for (Artifact a : artifacts){
			CorruptedArtifact artifact = (CorruptedArtifact) a;
			
			// Check conditions
			if (!artifact.canCorrupt(event.getArtifact().getType())) return;
			if (ArtifactUtils.playerHasArtifact(player, artifact)) return; // Duplicate artifact prevention
			if (random.nextDouble() * 100 > artifact.corruptionPercent()) return;
			
			// Call the event
			ArtifactCorruptEvent ace = new ArtifactCorruptEvent(player, event.getArtifact(), artifact);
			Bukkit.getPluginManager().callEvent(ace);
			if (ace.isCancelled()) return;
			
			// Corrupt the artifact
			event.setArtifact(artifact);
			break;
		}
	}
}