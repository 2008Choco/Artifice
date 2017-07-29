package me.choco.relics.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.ImmutableList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.choco.relics.api.ObeliskStructure;

/**
 * Represents an in-world structure capable of passively providing beneficial (or 
 * negative) effects towards any entities within proximity. Obelisks are constructed 
 * by players and must follow patterns constructed with {@link ObeliskStructure}s
 * 
 * @author Parker Hawke - 2008Choco
 */
public abstract class Obelisk {
	
	private final UUID uuid, owner;
	private Class<? extends Obelisk> customClazz;
	
	private List<Block> components = new ArrayList<>();
	
	/**
	 * Construct a new Obelisk
	 * 
	 * @param owner the owner of the obelisk
	 * @param uuid the obelisk's UUID
	 * @param components a list of all obelisk components (all its blocks)
	 * @param customClazz the class that represents this obelisk
	 */
	public Obelisk(OfflinePlayer owner, UUID uuid, List<Block> components, Class<? extends Obelisk> customClazz) {
		if (customClazz == null)
			throw new UnsupportedOperationException("Custom obelisk class cannot be null!");
		this.owner = owner.getUniqueId();
		this.components = components;
		this.uuid = uuid;
		this.customClazz = customClazz;
	}
	
	/**
	 * Construct a new Obelisk with a random UUID
	 * 
	 * @param owner the owner of the obelisk
	 * @param components a list of all obelisk components (all its blocks)
	 * @param customClazz the class that represents this obelisk
	 */
	public Obelisk(OfflinePlayer owner, List<Block> components, Class<? extends Obelisk> customClazz) {
		this(owner, UUID.randomUUID(), components, customClazz);
	}
	
	/**
	 * Get the player that created this obelisk
	 * 
	 * @return the obelisk owner
	 */
	public OfflinePlayer getOwner() {
		return Bukkit.getOfflinePlayer(owner);
	}
	
	/**
	 * Get the obelisk's unique ID (UUID)
	 * 
	 * @return the obelisk UUID
	 */
	public UUID getUniqueId() {
		return uuid;
	}
	
	/**
	 * Get a list of all the obelisks block components
	 * 
	 * @return the obelisk components
	 */
	public List<Block> getComponents() {
		return ImmutableList.copyOf(components);
	}
	
	/**
	 * Check whether a block is a component of the obelisk
	 * 
	 * @param block the block to check
	 * @return true if part of the obelisk
	 */
	public boolean isObeliskComponent(Block block) {
		return components.contains(block);
	}
	
	/**
	 * Get the specific class that represents this obelisk
	 * 
	 * @return the obelisk class
	 */
	public Class<? extends Obelisk> getCustomClass() {
		return customClazz;
	}
	
	/** 
	 * The name of the obelisk used in game for identification 
	 */
	public abstract String getName();
	
	/** 
	 * Called when wisps should be displayed around the obelisk. Should contain 
	 * particle effects that surround the obelisk. 
	 * <br>Leave this method body empty if particles are not desired
	 * 
	 * @param player the player to display wisps too
	 */
	public abstract void displayWisps(Player player);
	
	/** 
	 * A utility method used internally
	 * 
	 * @return the radius in which entities will be affected by {@link #executeEffect(Player)}
	 */
	public abstract int getEffectRadius();
	
	/** 
	 * Whether or not the obelisk should execute its effect. 
	 * 
	 * @param random a random instance
	 */
	public abstract boolean shouldEffect(Random random);
	
	/** 
	 * The effect to occur when {@link #shouldEffect(Random)} returns true
	 * 
	 * @param entity the entity <i>currently</i> being affected
	 */
	public abstract void executeEffect(LivingEntity entity);
	
	@Override
	public String toString() {
		return "Obelisk:{owner:" + owner + ",obeliskUUID:" + uuid + ",class:" + customClazz.getName() + "}";
	}
}