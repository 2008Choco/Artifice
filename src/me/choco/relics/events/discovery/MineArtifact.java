package me.choco.relics.events.discovery;

import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.choco.relics.Relics;
import me.choco.relics.artifacts.Artifact;
import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.utils.ArtifactManager;

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
		for (Artifact artifact : artifacts){
			if (!artifact.isValidMaterial(blockMat)) return;
			if (random.nextDouble() * 100 > artifact.retrievalPercent()) return;
			
			plugin.sendMessage(player, "You have discovered a " + artifact.getName() + (artifact.getName().contains("artifact") ? "" : " artifact"));
			player.getInventory().addItem(artifact.getItem());
			// TODO: Play a, "mystical sound of discovery" :P
			break;
		}
	}
}