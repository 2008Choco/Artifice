package me.choco.relics.api.artifact;

import me.choco.relics.artifacts.ArtifactType;

public abstract class CorruptedArtifact extends Artifact {
	
	/** The percentage (0.00% - 100.00%) that this item can corrupt a discovered artifact; 100 being guaranteed
	 * Decimal values (up to two places) are accepted
	 * @return The percentage chance
	 */
	public abstract double corruptionPercent();
	
	/** Check whether the specified artifact type can be corrupted by this artifact
	 * @return Whether the artifact is corruptable or not
	 */
	public abstract boolean canCorrupt(ArtifactType type);
	
	@Override
	public ArtifactType getType() {
		return ArtifactType.CORRUPTED;
	}
}