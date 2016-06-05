package me.choco.relics.artifacts;

import me.choco.relics.artifacts.ancient.AncientArtifact;
import me.choco.relics.artifacts.corrupted.CorruptedArtifact;
import me.choco.relics.artifacts.fossilized.FossilizedArtifact;
import me.choco.relics.artifacts.necrotic.NecroticArtifact;

public enum ArtifactType {
	
	REGULAR(Artifact.class),
	ANCIENT(AncientArtifact.class),
	FOSSILIZED(FossilizedArtifact.class),
	CORRUPTED(CorruptedArtifact.class),
	NECROTIC(NecroticArtifact.class);
	
	private Class<? extends Artifact> clazz;
	private ArtifactType(Class<? extends Artifact> clazz){
		this.clazz = clazz;
	}
	
	public Class<? extends Artifact> getParentClass() {
		return clazz;
	}
}