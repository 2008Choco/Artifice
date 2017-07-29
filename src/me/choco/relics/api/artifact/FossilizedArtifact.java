package me.choco.relics.api.artifact;

import org.bukkit.Material;

import me.choco.relics.artifacts.ArtifactType;

/**
 * Represents a more specific Artifact implementation discoverable
 * through mining blocks such as stone, dirt, gravel, etc.
 * 
 * @author Parker Hawke - 2008Choco
 */
public abstract class FossilizedArtifact extends Artifact {
	
	/** 
	 * Whether the passed material is a valid material to obtain the artifact or not
	 * By default, most mining materials will be used such as stone, dirt, gravel, etc.
	 */
	public boolean isValidMaterial(Material material) {
		return (material.equals(Material.STONE) || material.equals(Material.DIRT) || material.equals(Material.GRAVEL)
				|| material.equals(Material.NETHERRACK) || material.equals(Material.ENDER_STONE));
	}
	
	/** 
	 * The percentage (0.00% - 100.00%) that this item can be discovered; 
	 * 100 being guaranteed. Decimal values (up to two places) are accepted
	 * 
	 * @return the percentage chance
	 */
	public abstract double discoveryPercent();
	
	@Override
	public ArtifactType getType() {
		return ArtifactType.FOSSILIZED;
	}
}