package me.choco.relics.events.discovery;

import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.choco.relics.Relics;
import me.choco.relics.api.artifact.Artifact;
import me.choco.relics.api.artifact.NecroticArtifact;
import me.choco.relics.api.events.player.PlayerDiscoverArtifactEvent;
import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.utils.ArtifactManager;
import me.choco.relics.utils.general.ArtifactUtils;

public class KillEntityArtifact implements Listener {
	
	 /* THIS LISTENER IS TO DISCOVER ARTIFACTS OF THE TYPE "ArtifactType.NECROTIC" */
	
	private static final Random random = new Random();
	
	private final Relics plugin;
	private final ArtifactManager manager;
	
	public KillEntityArtifact(Relics plugin) {
		this.plugin = plugin;
		this.manager = plugin.getArtifactManager();
	}
	
	@EventHandler
	public void onKillEntity(EntityDeathEvent event) {
		Player player = event.getEntity().getKiller();
		if (player == null || !(event.getEntity() instanceof Monster)) return;
		
		Set<Artifact> artifacts = manager.getArtifacts(ArtifactType.NECROTIC);
		for (Artifact a : artifacts){
			NecroticArtifact artifact = (NecroticArtifact) a;
			
			// Check requirements
			if (ArtifactUtils.playerHasArtifact(player, artifact)) return; // Duplicate artifact prevention
			if (random.nextDouble() * 100 > artifact.discoveryPercent()) return;
			
			// PlayerDiscoverArtifactEvent
			PlayerDiscoverArtifactEvent pdae = new PlayerDiscoverArtifactEvent(player, artifact);
			Bukkit.getPluginManager().callEvent(pdae);
			if (pdae.isCancelled()) return;
			
			// Give actual artifact (Can be modified in event)
			plugin.sendMessage(player, pdae.getMessage());
			event.getDrops().add(ArtifactUtils.getArtifactItem(pdae.getArtifact()));
			// TODO: Play a, "mystical sound of discovery" :P
			break;
		}
	}
}