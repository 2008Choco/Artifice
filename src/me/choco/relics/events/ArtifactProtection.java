package me.choco.relics.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.choco.relics.Relics;
import me.choco.relics.utils.ArtifactManager;

public class ArtifactProtection implements Listener {
	
	private final ArtifactManager manager;
	
	public ArtifactProtection(Relics plugin) {
		this.manager = plugin.getArtifactManager();
	}
	
	@EventHandler
	public void onEatItem(PlayerItemConsumeEvent event) {
		if (manager.isArtifact(event.getItem())) 
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent event) {
		if (manager.isArtifact(event.getEntity().getItemStack()))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void placeArtifact(BlockPlaceEvent event) {
		if (manager.isArtifact(event.getItemInHand()))
			event.setCancelled(true);
	}
}