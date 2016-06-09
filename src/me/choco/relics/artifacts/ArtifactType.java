package me.choco.relics.artifacts;

import me.choco.relics.api.artifact.Artifact;
import me.choco.relics.api.artifact.CorruptedArtifact;
import me.choco.relics.api.artifact.FossilizedArtifact;
import me.choco.relics.api.artifact.NecroticArtifact;

public enum ArtifactType {
	
	REGULAR(Artifact.class, "Regular"),
	FOSSILIZED(FossilizedArtifact.class, "Fossilized"),
	CORRUPTED(CorruptedArtifact.class, "Corrupted"),
	NECROTIC(NecroticArtifact.class, "Necrotic");
	// ANCIENT - Re-add with the capability to discover them in dungeon chests
	
	private String name;
	private Class<? extends Artifact> clazz;
	private ArtifactType(Class<? extends Artifact> clazz, String name){
		this.clazz = clazz;
		this.name = name;
	}
	
	public Class<? extends Artifact> getParentClass() {
		return clazz;
	}
	
	public String getName() {
		return name;
	}
	
	public static ArtifactType getByName(String name){
		for (ArtifactType type : values())
			if (type.getName().equalsIgnoreCase(name)) return type;
		return null;
	}
}