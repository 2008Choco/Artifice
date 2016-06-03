package me.choco.relics.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.relics.artifacts.Artifact;
import me.choco.relics.artifacts.ArtifactType;

public class ArtifactManager {
	
	private final Map<Class<? extends Artifact>, Artifact> artifacts = new HashMap<>();
	
	public void registerArtifact(Class<? extends Artifact> clazz, Artifact artifact){
		if (artifact.getType() == null) throw new UnsupportedOperationException("You cannot register an artifact with a null type");
		if (!artifact.getType().getParentClass().equals(artifact.getClass().getSuperclass()))
			throw new UnsupportedOperationException("The artifact superclass " + artifact.getClass().getSuperclass().getSimpleName() 
					+ " is not compatible with the parent class for type \"" + artifact.getType().name() + "\" "
					+ "(" + artifact.getType().getParentClass().getSimpleName() + "). Violating Class: " + artifact.getClass().getName());
		
		this.artifacts.put(clazz, artifact);
	}
	
	public Map<Class<? extends Artifact>, Artifact> getArtifactRegistry(){
		return artifacts;
	}
	
	public boolean isArtifact(ItemStack item){
		for (Artifact artifact : artifacts.values())
			if (artifact.getItem().isSimilar(item)) return true;
		return false;
	}
	
	public boolean isArtifact(ItemStack item, Class<? extends Artifact> artifact){
		if (!artifacts.containsKey(artifact))
			throw new UnsupportedOperationException(artifact.getName() + " has not been registered");
		return artifacts.get(artifact).getItem().isSimilar(item);
	}
	
	public Artifact getArtifact(Class<? extends Artifact> artifact){
		return artifacts.get(artifact);
	}
	
	public Artifact getArtifact(ItemStack item){
		for (Artifact artifact : artifacts.values())
			if (artifact.getItem().isSimilar(item)) return artifact;
		return null;
	}
	
	public Artifact getArtifact(String name){
		for (Artifact artifact : artifacts.values())
			if (artifact.getName().equalsIgnoreCase(name)) return artifact;
		return null;
	}
	
	public Class<? extends Artifact> getArtifactClass(String name){
		for (Class<? extends Artifact> artifact : artifacts.keySet())
			if (artifact.getSimpleName().equalsIgnoreCase(name)) return artifact;
		return null;
	}
	
	public Set<Artifact> getArtifacts(ArtifactType type){
		Set<Artifact> artifacts = new HashSet<>();
		for (Artifact artifact : this.artifacts.values())
			if (artifact.getType().equals(type)) artifacts.add(artifact);
		return artifacts;
	}
	
	public void giveArtifact(Player player, Class<? extends Artifact> artifact){
		if (!artifacts.containsKey(artifact))
			throw new UnsupportedOperationException(artifact.getName() + " has not been registered");
		giveArtifact(player, getArtifact(artifact));
	}
	
	public void giveArtifact(Player player, Artifact artifact){
		player.getInventory().addItem(artifact.getItem());
	}
}