package me.choco.relics.artifacts;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Artifact {
	
	/** The name of the obelisk used in game for identification */
	public abstract String getName();
	
	/** The item representation of the artifact (Used for identification)
	 * @return The item to reference
	 */
	public abstract ItemStack getItem();
	
	/** A utility method used internally
	 * @return The type of artifact
	 */
	public abstract ArtifactType getType();
	
	/** Whether or not the artifact should execute its effect. 
	 * @param random Used to generate random numbers in case the effect is desired to be random
	 */
	public abstract boolean shouldEffect(Random random);
	
	/** The effect to occur when {@link #shouldEffect(Random)} returns true
	 * @param player - The player <i>currently</i> being affected
	 */
	public abstract void executeEffect(Player player);
}