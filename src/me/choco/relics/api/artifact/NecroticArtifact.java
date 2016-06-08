package me.choco.relics.api.artifact;

import me.choco.relics.artifacts.ArtifactType;

public abstract class NecroticArtifact extends Artifact {
	
	/** The percentage (0.00% - 100.00%) that this item can be discovered; 100 being guaranteed
	 * Decimal values (up to two places) are accepted
	 * @return The percentage chance
	 */
	public abstract double discoveryPercent();
	
	@Override
	public ArtifactType getType() {
		return ArtifactType.NECROTIC;
	}
}