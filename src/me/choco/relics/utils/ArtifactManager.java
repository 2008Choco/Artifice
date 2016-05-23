package me.choco.relics.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import me.choco.relics.artifacts.Artifact;

public class ArtifactManager {
	
	private final Map<Class<? extends Artifact>, Artifact> artifacts = new HashMap<>();
	
	public void registerArtifact(Class<? extends Artifact> clazz, Artifact artifact){
		this.artifacts.put(clazz, artifact);
	}
	
	public Map<Class<? extends Artifact>, Artifact> getArtifactRegistry(){
		return artifacts;
	}
	
	public boolean isArtifact(ItemStack item, Class<? extends Artifact> artifact){
		if (!artifacts.containsKey(artifact))
			throw new UnsupportedOperationException(artifact.getName() + " has not been registered");
		return artifacts.get(artifact).getItem().isSimilar(item);
	}
	
	public Artifact getArtifact(Class<? extends Artifact> artifact){
		return artifacts.get(artifact);
	}
}