package me.choco.relics.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

public abstract class Obelisk {
	
	private final UUID uuid;
	private Class<? extends Obelisk> customClazz;
	
	private List<Block> components = new ArrayList<>();
	
	private final OfflinePlayer owner;
	public Obelisk(OfflinePlayer owner, UUID uuid, List<Block> components, Class<? extends Obelisk> customClazz){
		if (customClazz == null)
			throw new UnsupportedOperationException("Custom obelisk class cannot be null!");
		this.owner = owner;
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
		return "Obelisk:{owner:" + owner + ",obeliskUUID:" + uuid + ",class:" + customClazz.getName() + "}";
	}
}