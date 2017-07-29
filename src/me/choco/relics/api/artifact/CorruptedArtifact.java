package me.choco.relics.api.artifact;

import me.choco.relics.artifacts.ArtifactType;

/**
 * Represents a special item ("artifact") capable of corrupting any type of
 * artifact. Corrupted artifacts will randomly corrupt any discovered artifact
 * 
 * @author Parker Hawke - 2008Choco
 */
public abstract class CorruptedArtifact extends Artifact {
	
	/** 
	 * The percentage (0.00% - 100.00%) that this item can corrupt a discovered artifact; 
	 * 100 being guaranteed. Decimal values (up to two places) are accepted
	 * 
	 * @return the percentage chance
	 */
	public abstract double corruptionPercent();
	
	/** 
	 * Check whether the specified artifact type can be corrupted by this artifact
	 * 
	 * @param type the type to check
	 * @return whether the artifact is corruptable or not
	 */
	public abstract boolean canCorrupt(ArtifactType type);
	
	@Override
	public ArtifactType getType() {
		return ArtifactType.CORRUPTED;
	}
}