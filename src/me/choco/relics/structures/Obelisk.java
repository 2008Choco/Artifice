package me.choco.relics.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

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
	
	/** The name of the obelisk used in game for identification */
	public abstract String getName();
	
	/** Called when wisps should be displayed around the obelisk. 
	 * Should contain particle effects that surround the obelisk. 
	 * <br>Leave this method body empty if particles are not desired
	 */
	public abstract void displayWisps(Player player);
	
	/** A utility method used internally
	 * @return The radius in which entities will be affected by {@link #executeEffect(Player)}
	 */
	public abstract int getEffectRadius();
	
	/** Whether or not the obelisk should execute its effect. 
	 * @param Used to generate random numbers in case the effect is desired to be random
	 */
	public abstract boolean shouldEffect(Random random);
	
	/** The effect to occur when {@link #shouldEffect(Random)} returns true
	 * @param player - The player <i>currently</i> being affected
	 */
	public abstract void executeEffect(Player player);
	
	@Override
	public String toString() {
		return "Obelisk:{owner:" + owner + ",obeliskUUID:" + uuid + ",class:" + customClazz.getName() + "}";
	}
}