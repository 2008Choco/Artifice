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
	
	/** The percentage (0.00% - 100.00%) that this item can be discovered; 100 being guaranteed
	 * Decimal values (up to two places) are accepted
	 * @return The percentage chance
	 */
	public abstract double retrievalPercent();
	
	/** Whether or not the artifact should execute its effect. 
	 * @param random Used to generate random numbers in case the effect is desired to be random
	 */
	public abstract boolean shouldEffect(Random random);
	
	/** The effect to occur when {@link #shouldEffect(Random)} returns true
	 * @param player - The player <i>currently</i> being affected
	 */
	public abstract void executeEffect(Player player);
	
	/** A utility method used internally
	 * @return The type of artifact
	 */
	public ArtifactType getType(){
		return ArtifactType.REGULAR;
	}
}