package me.choco.relics.artifacts;

import me.choco.relics.api.artifact.Artifact;
import me.choco.relics.api.artifact.CorruptedArtifact;
import me.choco.relics.api.artifact.FossilizedArtifact;
import me.choco.relics.api.artifact.NecroticArtifact;

/**
 * Represents the different types of discoverable artifacts
 * 
 * @author Parker Hawke - 2008Choco
 */
public enum ArtifactType {
	
	REGULAR(Artifact.class, "Regular"),
	
	FOSSILIZED(FossilizedArtifact.class, "Fossilized"),
	
	CORRUPTED(CorruptedArtifact.class, "Corrupted"),
	
	NECROTIC(NecroticArtifact.class, "Necrotic");
	
	// TODO ANCIENT - Re-add with the capability to discover them in dungeon chests
	
	
	private String name;
	private Class<? extends Artifact> clazz;
	
	private ArtifactType(Class<? extends Artifact> clazz, String name){
		this.clazz = clazz;
		this.name = name;
	}
	
	/**
	 * Get the parent class that represents this artifact type
	 * 
	 * @return the artifact class
	 */
	public Class<? extends Artifact> getParentClass() {
		return clazz;
	}
	
	/**
	 * Get the friendly name for this artifact type
	 * 
	 * @return the friendly name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get an artifact type based on its name
	 * 
	 * @param name the artifact type name
	 * @return the resulting artifact type, or null if non-existent
	 */
	public static ArtifactType getByName(String name){
		for (ArtifactType type : values())
			if (type.getName().equalsIgnoreCase(name)) return type;
		return null;
	}
}