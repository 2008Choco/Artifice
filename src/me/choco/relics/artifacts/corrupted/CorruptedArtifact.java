package me.choco.relics.artifacts.corrupted;

import me.choco.relics.artifacts.Artifact;
import me.choco.relics.artifacts.ArtifactType;

public abstract class CorruptedArtifact extends Artifact {
	
	@Override
	public ArtifactType getType() {
		return ArtifactType.CORRUPTED;
	}
}