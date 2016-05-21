package me.choco.relics.api;

import org.bukkit.Material;

import me.choco.relics.Relics;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskType;
import me.choco.relics.utils.managers.ObeliskManager;

public class ObeliskStructure {
	
	private Material[][][] materials; /* [length][height][width] */
	private final int length, width, height;
	private Material formationMaterial;
	private int xFormationIndex = 0, yFormationIndex = 0, zFormationIndex = 0;
	
	private ObeliskType type;
	
	/** Create a new obelisk structure to be detected 
	 * @param length - The size of the structure along the x axis
	 * @param height - The size of the structure along the y axis
	 * @param width - The size of the structure along the z axis
	 */
	public ObeliskStructure(int length, int height, int width, ObeliskType type){
		 materials = new Material[length][height][width];
		 this.type = type;
		 this.length = length; this.height = height; this.width = width;
	}
	
	public ObeliskStructure setFormationMaterial(int relXLoc, int relYLoc, int relZLoc){
		if (relXLoc > getLength() || relYLoc > getHeight() || relZLoc > getWidth()) 
			throw new ArrayIndexOutOfBoundsException("Cannot use a size greater than the dimensions of the structure");
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
	
	public ObeliskType getObeliskType(){
		return type;
	}
	
	public void build(){
		ObeliskManager manager = Relics.getPlugin().getObeliskManager();
		
		if (manager.getStructures().contains(this)) throw new IllegalStateException("Cannot register same obelisk structure twice");
		if (type.equals(ObeliskType.CUSTOM)){
			throw new IllegalStateException(
					"To register a custom obelisk, use the Build(Class<? extends Obelisk>) method to specify your custom obelisk class");
		}
		
		build(type.getObeliskClass());
	}
	
	public void build(Class<? extends Obelisk> clazz){
		ObeliskManager manager = Relics.getPlugin().getObeliskManager();
		
		if (manager.getStructures().contains(this)) throw new IllegalStateException("Cannot register same obelisk structure twice");
		
		for (int x = 0; x < length; x++){
			for (int y = 0; y < height; y++){
				for (int z = 0; z < width; z++){
					if (materials[x][y][z] == null) 
						materials[x][y][z] = Material.AIR;
				}
			}
		}
		manager.registerStructure(this, clazz);
	}
}