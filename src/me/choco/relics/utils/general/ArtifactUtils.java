package me.choco.relics.utils.general;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.relics.Relics;
import me.choco.relics.artifacts.Artifact;
import me.choco.relics.utils.ArtifactManager;

public class ArtifactUtils {
	
	private static final ArtifactManager manager = Relics.getPlugin().getArtifactManager();
	
	public static boolean playerHasArtifact(Player player, Class<? extends Artifact> artifact){
		for (ItemStack item : player.getInventory().getContents())
			if (manager.isArtifact(item, artifact)) return true;
		return false;
	}
	
	public static boolean playerHasArtifact(Player player, Artifact artifact){
		return playerHasArtifact(player, artifact.getClass());
	}
}