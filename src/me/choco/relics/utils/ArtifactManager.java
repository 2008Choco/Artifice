package me.choco.relics.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.relics.api.artifact.Artifact;
import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.utils.general.ArtifactUtils;

/**
 * Represents a manager to handle all interactions regarding artifacts
 * 
 * @author Parker Hawke - 2008Choco
 */
public class ArtifactManager {
	
	private final Map<Class<? extends Artifact>, Artifact> artifacts = new HashMap<>();
	
	/**
	 * Register a new artifact to the artifact registry
	 * 
	 * @param artifact the artifact to register
	 */
	public void registerArtifact(Artifact artifact) {
		if (artifact.getType() == null) throw new UnsupportedOperationException("You cannot register an artifact with a null type");
		if (!artifact.getType().getParentClass().equals(artifact.getClass().getSuperclass()))
			throw new UnsupportedOperationException("The artifact superclass " + artifact.getClass().getSuperclass().getSimpleName() 
					+ " is not compatible with the parent class for type \"" + artifact.getType().name() + "\" "
					+ "(" + artifact.getType().getParentClass().getSimpleName() + "). Violating Class: " + artifact.getClass().getName());
		
		this.artifacts.put(artifact.getClass(), artifact);
	}
	
	/**
	 * Get an immutable copy of the underlying artifact registry Map
	 * 
	 * @return the artifact registry map
	 */
	public Map<Class<? extends Artifact>, Artifact> getArtifactRegistry() {
		return ImmutableMap.copyOf(artifacts);
	}
	
	/**
	 * Check whether an ItemStack is a registered artifact
	 * 
	 * @param item the item to check
	 * @return true if an artifact, false otherwise
	 */
	public boolean isArtifact(ItemStack item) {
		for (Artifact artifact : artifacts.values())
			if (artifact.getItem().isSimilar(item)) return true;
		return false;
	}
	
	/**
	 * Check whether an ItemStack is a specific artifact type
	 * 
	 * @param item the item to check
	 * @param artifact the type of artifact to check for
	 * 
	 * @return true if an artifact, false otherwise
	 */
	public boolean isArtifact(ItemStack item, Class<? extends Artifact> artifact) {
		if (!artifacts.containsKey(artifact))
			throw new UnsupportedOperationException(artifact.getName() + " has not been registered");
		return artifacts.get(artifact).getItem().isSimilar(item);
	}
	
	/**
	 * Get the artifact instance based on its class
	 * 
	 * @param artifact the artifact to get
	 * @return the resulting instance, or null if not registered
	 */
	public Artifact getArtifact(Class<? extends Artifact> artifact) {
		return artifacts.get(artifact);
	}
	
	/**
	 * Get the artifact instance based on its item
	 * 
	 * @param item the item to reference
	 * @return the resulting instance, or null if not registered
	 */
	public Artifact getArtifact(ItemStack item) {
		for (Artifact artifact : artifacts.values())
			if (artifact.getItem().isSimilar(item)) return artifact;
		return null;
	}
	
	/**
	 * Get the artifact instance based on its name
	 * 
	 * @param name the name of the artifact
	 * @return the resulting instance, or null if not registered
	 */
	public Artifact getArtifact(String name) {
		for (Artifact artifact : artifacts.values())
			if (artifact.getName().equalsIgnoreCase(name)) return artifact;
		return null;
	}
	
	/**
	 * Get an artifacts class based on its name
	 * 
	 * @param name the name of the artifact
	 * @return the resulting instance, or null if not registered
	 */
	public Class<? extends Artifact> getArtifactClass(String name) {
		for (Class<? extends Artifact> artifact : artifacts.keySet())
			if (artifact.getSimpleName().equalsIgnoreCase(name)) return artifact;
		return null;
	}
	
	/**
	 * Get instances of all artifacts of the given type
	 * 
	 * @param type the type to search for
	 * @return all registered artifacts with the given type
	 */
	public Set<Artifact> getArtifacts(ArtifactType type) {
		Set<Artifact> result = new HashSet<>();
		
		for (Artifact artifact : artifacts.values())
			if (artifact.getType().equals(type)) result.add(artifact);
		
		return result;
	}
	
	/**
	 * Get instances of all artifacts of the given types
	 * 
	 * @param types the types to search for
	 * @return all registered artifacts with one of the given types
	 */
	public Set<Artifact> getArtifacts(ArtifactType... types) {
		Set<Artifact> result = new HashSet<>();
		
		for (Artifact artifact : artifacts.values())
			for (ArtifactType type : types)
				if (artifact.getType().equals(type)){
					result.add(artifact);
					break;
				}
		
		return result;
	}
	
	/**
	 * Give an artifact's item to a player
	 * 
	 * @param player the player to give the artifact to
	 * @param artifact the artifact to give
	 */
	public void giveArtifact(Player player, Class<? extends Artifact> artifact) {
		if (!artifacts.containsKey(artifact))
			throw new UnsupportedOperationException(artifact.getName() + " has not been registered");
		this.giveArtifact(player, getArtifact(artifact));
	}
	
	/**
	 * Give an artifact's item to a player
	 * 
	 * @param player the player to give the artifact to
	 * @param artifact the artifact to give
	 */
	public void giveArtifact(Player player, Artifact artifact) {
		player.getInventory().addItem(ArtifactUtils.getArtifactItem(artifact));
	}
}