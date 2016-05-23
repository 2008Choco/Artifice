package me.choco.relics.api;

import org.bukkit.Material;

import me.choco.relics.Relics;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskManager;

public class ObeliskStructure {
	
	private Material[][][] materials; /* [length][height][width] */
	private final int length, width, height;
	private Material formationMaterial;
	private int xFormationIndex = 0, yFormationIndex = 0, zFormationIndex = 0;
	
	private final Class<? extends Obelisk> clazz;
	/** Create a new obelisk structure to be detected 
	 * @param length - The size of the structure along the x axis
	 * @param height - The size of the structure along the y axis
	 * @param width - The size of the structure along the z axis
	 */
	public ObeliskStructure(int length, int height, int width, Class<? extends Obelisk> clazz){
		 this.materials = new Material[length][height][width];
		 this.length = length; this.height = height; this.width = width;
		 this.clazz = clazz;
	}
	
	private ObeliskStructure(int length, int height, int width, Class<? extends Obelisk> clazz, Material[][][] materials){
		this.materials = materials;
		this.length = length; this.height = height; this.width = width;
		this.clazz = clazz;
	}
	
	public ObeliskStructure setFormationMaterial(int relXLoc, int relYLoc, int relZLoc){
		if (relXLoc > getLength() || relYLoc > getHeight() || relZLoc > getWidth()
				|| relXLoc < 0 || relYLoc < 0 || relZLoc < 0) 
			throw new IllegalArgumentException("Cannot use a size exceeding the dimensions of the structure");
		this.formationMaterial = materials[relXLoc][relYLoc][relZLoc];
		this.xFormationIndex = relXLoc; this.yFormationIndex = relYLoc; this.zFormationIndex = relZLoc;
		return this;
	}
	
	@Deprecated
	public ObeliskStructure setFormationMaterial(Material formationMaterial, int relXLoc, int relYLoc, int relZLoc){
		if (relXLoc > getLength() || relYLoc > getHeight() || relZLoc > getWidth()) 
			throw new IllegalArgumentException("Cannot use a size greater than the dimensions of the structure");
		if (!materials[relXLoc][relYLoc][relZLoc].equals(formationMaterial))
			throw new IllegalStateException("Formation Material " + formationMaterial.name() + " is not identical to index array value" +
					relXLoc + ", " + relYLoc + ", " + relZLoc + "(" + materials[relXLoc][relYLoc][relZLoc].name() + ")");
		this.formationMaterial = formationMaterial;
		this.xFormationIndex = relXLoc; this.yFormationIndex = relYLoc; this.zFormationIndex = relZLoc;
		return this;
	}
	
	public Material getFormationMaterial(){
		return formationMaterial;
	}
	
	public int getXFormationIndex(){
		return xFormationIndex;
	}
	
	public int getYFormationIndex(){
		return yFormationIndex;
	}
	
	public int getZFormationIndex(){
		return zFormationIndex;
	}
	
	public ObeliskStructure setBlockPosition(int xPos, int yPos, int zPos, Material material){
		materials[xPos][yPos][zPos] = material;
		return this;
	}
	
	public ObeliskStructure removeBlockPosition(int xPos, int yPos, int zPos){
		materials[xPos][yPos][zPos] = null;
		return this;
	}
	
	public Material[][][] getBlockFormation(){
		return materials;
	}
	
	public int getLength(){
		return length;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public Class<? extends Obelisk> getObeliskClass(){
		return clazz;
	}
	
	public void build(){
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