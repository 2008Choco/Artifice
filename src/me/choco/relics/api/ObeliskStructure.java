package me.choco.relics.api;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

import me.choco.relics.Relics;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskManager;

/**
 * Represents a structure pattern for an obelisk. Each structure pattern is composed
 * of 3 different axis of materials which correspond to a relative coordinate in the
 * world. This class is similar to the construction of a {@link ShapedRecipe}
 * 
 * @author Parker Hawke - 2008Choco
 */
public class ObeliskStructure {
	
	private Material[][][] materials; /* [length][height][width] */
	private Material formationMaterial;
	
	private final int length, width, height;
	private int xFormationIndex = 0, yFormationIndex = 0, zFormationIndex = 0;
	
	private final Class<? extends Obelisk> clazz;
	
	/** 
	 * Create a new obelisk structure to be detected 
	 * 
	 * @param length the size of the structure along the x axis
	 * @param height the size of the structure along the y axis
	 * @param width the size of the structure along the z axis
	 * @param clazz the resulting obelisk type for this structure pattern
	 */
	public ObeliskStructure(int length, int height, int width, Class<? extends Obelisk> clazz) {
		 this.materials = new Material[length][height][width];
		 this.length = length; this.height = height; this.width = width;
		 this.clazz = clazz;
	}
	
	private ObeliskStructure(int length, int height, int width, Class<? extends Obelisk> clazz, Material[][][] materials) {
		this.materials = materials;
		this.length = length; this.height = height; this.width = width;
		this.clazz = clazz;
	}
	
	/**
	 * Set the relative coordinates that must be clicked by a player in order to 
	 * complete construction of the obelisk
	 * 
	 * @param relXLoc the relative x coordinate
	 * @param relYLoc the relative y coordinate
	 * @param relZLoc the relative z coordinate
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public ObeliskStructure setFormationMaterial(int relXLoc, int relYLoc, int relZLoc) {
		if (relXLoc > getLength() || relYLoc > getHeight() || relZLoc > getWidth()
				|| relXLoc < 0 || relYLoc < 0 || relZLoc < 0) 
			throw new IllegalArgumentException("Cannot use a size exceeding the dimensions of the structure");
		
		this.formationMaterial = materials[relXLoc][relYLoc][relZLoc];
		this.xFormationIndex = relXLoc; this.yFormationIndex = relYLoc; this.zFormationIndex = relZLoc;
		
		return this;
	}

	/**
	 * Get the material representing the formation
	 * 
	 * @return the formation material
	 */
	public Material getFormationMaterial() {
		return formationMaterial;
	}
	
	/**
	 * Get the relative x coordinate in which a formation will be made
	 * 
	 * @return the x formation index
	 */
	public int getXFormationIndex() {
		return xFormationIndex;
	}
	
	/**
	 * Get the relative y coordinate in which a formation will be made
	 * 
	 * @return the y formation index
	 */
	public int getYFormationIndex() {
		return yFormationIndex;
	}
	
	/**
	 * Get the relative z coordinate in which a formation will be made
	 * 
	 * @return the z formation index
	 */
	public int getZFormationIndex() {
		return zFormationIndex;
	}
	
	/**
	 * Set a relative position's material
	 * 
	 * @param xPos the relative x position
	 * @param yPos the relative y position
	 * @param zPos the relative z position
	 * @param material the material to set
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public ObeliskStructure setBlockPosition(int xPos, int yPos, int zPos, Material material) {
		materials[xPos][yPos][zPos] = material;
		return this;
	}
	
	/**
	 * Get the underlying block formation
	 * 
	 * @return the block formation
	 */
	public Material[][][] getBlockFormation() {
		return Arrays.copyOf(materials, materials.length);
	}
	
	/**
	 * Get the length of this obelisk structure (x axis)
	 * 
	 * @return the structure length
	 */
	public int getLength(){
		return length;
	}
	
	/**
	 * Get the height of this obelisk structure (y axis)
	 * 
	 * @return the structure height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get the width of this obelisk structure (z axis)
	 * 
	 * @return the structure width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the obelisk class that will be created for this structure
	 * 
	 * @return the resulting obelisk
	 */
	public Class<? extends Obelisk> getObeliskClass() {
		return clazz;
	}
	
	/**
	 * Build the structure and register it to the {@link ObeliskManager}
	 */
	public void build() {
		ObeliskManager manager = Relics.getPlugin().getObeliskManager();
		if (manager.getStructures().contains(this)) throw new IllegalStateException("Cannot register same obelisk structure twice");
		Material[][][] axisSwap = new Material[width][height][length];
		
		for (int x = 0; x < length; x++){
			for (int y = 0; y < height; y++){
				for (int z = 0; z < width; z++){
					if (materials[x][y][z] == null) 
						materials[x][y][z] = Material.AIR;
					
					axisSwap[z][y][x] = materials[x][y][z];
				}
			}
		}
		
		// Inverted axis for the structure
		ObeliskStructure axisSwapStructure = new ObeliskStructure(width, height, length, clazz, axisSwap);
		axisSwapStructure.setFormationMaterial(zFormationIndex, yFormationIndex, xFormationIndex);
		
		// Register both structures
		manager.registerStructure(this, clazz);
		manager.registerStructure(axisSwapStructure, clazz); // Swapped axis
	}
}