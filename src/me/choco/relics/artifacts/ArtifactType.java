package me.choco.relics.artifacts;

import me.choco.relics.artifacts.ancient.AncientArtifact;
import me.choco.relics.artifacts.corrupted.CorruptedArtifact;
import me.choco.relics.artifacts.fossilized.FossilizedArtifact;
import me.choco.relics.artifacts.necrotic.NecroticArtifact;

public enum ArtifactType {
	
	REGULAR(Artifact.class, "Regular"),
	ANCIENT(AncientArtifact.class, "Ancient"),
	FOSSILIZED(FossilizedArtifact.class, "Fossilized"),
	CORRUPTED(CorruptedArtifact.class, "Corrupted"),
	NECROTIC(NecroticArtifact.class, "Necrotic");
	
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