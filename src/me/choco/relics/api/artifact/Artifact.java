package me.choco.relics.api.artifact;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.artifacts.Rarity;

public abstract class Artifact {
	
	/** The name of the obelisk used in game for identification */
	public abstract String getName();
	
	/** The item representation of the artifact (Used for identification)
	 * @return The item to reference
	 */
	public abstract ItemStack getItem();
	
	/** Whether or not the artifact should execute its effect. 
	 * @param random Used to generate random numbers in case the effect is desired to be random
	 */
	public abstract boolean shouldEffect(Random random);
	
	/** The effect to occur when {@link #shouldEffect(Random)} returns true
	 * @param player - The player <i>currently</i> being affected
	 */
	public abstract void executeEffect(Player player);
	
	/** Get the rarity of the artifact
	 * @return The rarity
	 */
	public abstract Rarity getRarity();
	
	/** A utility method used internally
	 * @return The type of artifact
	 */
	public ArtifactType getType(){
		return ArtifactType.REGULAR;
	}
}