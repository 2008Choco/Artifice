package me.choco.relics.api.artifact;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.artifacts.Rarity;

/**
 * Represents a special item ("artifact") with special effects applicable to players
 * 
 * @author Parker Hawke - 2008Choco
 */
public abstract class Artifact {
	
	/** 
	 * The name of the obelisk used in game for identification 
	 */
	public abstract String getName();
	
	/** 
	 * The item representation of the artifact (Used for identification)
	 * 
	 * @return the item to reference
	 */
	public abstract ItemStack getItem();
	
	/** 
	 * Whether or not the artifact should execute its effect. 
	 * 
	 * @param random a random instance
	 */
	public abstract boolean shouldEffect(Random random);
	
	/** 
	 * The effect to occur when {@link #shouldEffect(Random)} returns true
	 * 
	 * @param player the player <i>currently</i> being affected
	 */
	public abstract void executeEffect(Player player);
	
	/** 
	 * Get the rarity of the artifact
	 * 
	 * @return the rarity
	 */
	public abstract Rarity getRarity();
	
	/** 
	 * Get the type of artifact
	 * 
	 * @return the type of artifact
	 */
	public ArtifactType getType() {
		return ArtifactType.REGULAR;
	}
}