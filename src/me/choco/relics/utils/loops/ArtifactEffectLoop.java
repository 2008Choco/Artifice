package me.choco.relics.utils.loops;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.choco.relics.Relics;
import me.choco.relics.api.artifact.Artifact;
import me.choco.relics.utils.ArtifactManager;

public class ArtifactEffectLoop extends BukkitRunnable {
	
	private static final Random RANDOM = new Random();
	
	private ArtifactManager manager;
	
	public ArtifactEffectLoop(Relics plugin) {
		this.manager = plugin.getArtifactManager();
	}
	
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Inventory inventory = player.getInventory();
			for (ItemStack item : inventory.getContents()) {
				if (item == null) continue;
				
				Artifact artifact = manager.getArtifact(item);
				if (artifact == null) continue;
				
				if (artifact.shouldEffect(RANDOM))
					artifact.executeEffect(player);
			}
		}
	}
}