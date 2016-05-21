package me.choco.relics.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import me.choco.relics.utils.ObeliskType;

public abstract class Obelisk {
	
	private final UUID uuid;
	private Class<? extends Obelisk> customClazz;
	
	private List<Block> components = new ArrayList<>();
	
	private final OfflinePlayer owner;
	private final ObeliskType type;
	protected Obelisk(OfflinePlayer owner, UUID uuid, ObeliskType type, List<Block> components){
		if (type.equals(ObeliskType.CUSTOM)) 
			throw new IllegalStateException("Must specify a custom class if ObeliskType is custom!");
		this.owner = owner;
		this.type = type;
		this.components = components;
		this.uuid = uuid;
		this.customClazz = type.getObeliskClass();
	}
	
	protected Obelisk(OfflinePlayer owner, UUID uuid, ObeliskType type, List<Block> components, Class<? extends Obelisk> customClazz){
		if (customClazz == null)
			throw new UnsupportedOperationException("Custom obelisk class cannot be null!");
		this.owner = owner;
		this.type = type;
		this.components = components;
		this.uuid = uuid;
		this.customClazz = customClazz;
	}
	
	public OfflinePlayer getOwner(){
		return owner;
	}
	
	public UUID getUniqueId(){
		return uuid;
	}
	
	public ObeliskType getType(){
		return type;
	}
	
	public void setComponents(List<Block> components){
		this.components = components;
	}
	
	public List<Block> getComponents(){
		return components;
	}
	
	public boolean isObeliskComponent(Block block){
		return components.contains(block);
	}
	
	public Class<? extends Obelisk> getCustomClass(){
		return customClazz;
	}
	
	public abstract void displayWisps();
	
	@Override
	public String toString() {
		return "Obelisk:{owner:" + owner + ",obeliskUUID:" + uuid + ",type:" + type.name().toLowerCase() + "}";
	}
}