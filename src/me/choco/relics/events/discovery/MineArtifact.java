package me.choco.relics.events.discovery;

import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.choco.relics.Relics;
import me.choco.relics.api.artifact.Artifact;
import me.choco.relics.api.artifact.FossilizedArtifact;
import me.choco.relics.api.events.player.PlayerDiscoverArtifactEvent;
import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.utils.ArtifactManager;
import me.choco.relics.utils.general.ArtifactUtils;

public class MineArtifact implements Listener{
	
	 /* THIS LISTENER IS TO DISCOVER ARTIFACTS OF THE TYPE "ArtifactType.FOSSILIZED" */
	
	private static final Random random = new Random();
	
	private Relics plugin;
	private ArtifactManager manager;
	public MineArtifact(Relics plugin){
		this.plugin = plugin;
		this.manager = plugin.getArtifactManager();
	}
	
	@EventHandler
	public void onMineBlock(BlockBreakEvent event){
		Player player = event.getPlayer();
		Material blockMat = event.getBlock().getType();
		
		Set<Artifact> artifacts = manager.getArtifacts(ArtifactType.FOSSILIZED);
		for (Artifact a : artifacts){
			FossilizedArtifact artifact = (FossilizedArtifact) a;
			
			// Check requirements
			if (!artifact.isValidMaterial(blockMat)) return;
			
			if (ArtifactUtils.playerHasArtifact(player, artifact)) return; // Duplicate artifact prevention
			if (random.nextDouble() * 100 > artifact.discoveryPercent()) return;
			
			// PlayerDiscoverArtifactEvent
			PlayerDiscoverArtifactEvent pdae = new PlayerDiscoverArtifactEvent(player, artifact);
			Bukkit.getPluginManager().callEvent(pdae);
			if (pdae.isCancelled()) return;
			
			// Give actual artifact (Can be modified in event)
			plugin.sendMessage(player, pdae.getMessage());
			manager.giveArtifact(player, pdae.getArtifact());
			// TODO: Play a, "mystical sound of discovery" :P
			break;
		}
	}
}