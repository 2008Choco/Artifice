package me.choco.relics.api.artifact;

import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import me.choco.relics.artifacts.ArtifactType;

public abstract class AncientArtifact extends Artifact {
	
	/** Get the shaped recipe this artifact requires to be made (If any)
	 * @return The recipe
	 */
	public abstract ShapedRecipe getShapedRecipe();
	
	/** Get the shapeless reipce this ratifact requires to be made (If any)
	 * @return The recipe
	 */
	public abstract ShapelessRecipe getShapelessRecipe();
	
	/** Check whether this artifact has a shaped recipe or not
	 * @return Whether a shaped recipe is available or not
	 */
	public boolean hasShapedRecipe(){
		return getShapedRecipe() != null;
	}
	
	/** Check whether this artifact has a shapeless recipe or not
	 * @return Whether a shapeless recipe is available or not
	 */
	public boolean hasShapelessRecipe(){
		return getShapelessRecipe() != null;
	}
	
	@Override
	public ArtifactType getType() {
		return ArtifactType.ANCIENT;
	}
}