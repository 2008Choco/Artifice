package me.choco.relics.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import me.choco.relics.Relics;
import me.choco.relics.api.ObeliskStructure;
import me.choco.relics.structures.Obelisk;

/**
 * Represents a manager to handle all interactions regarding obelisks
 * 
 * @author Parker Hawke - 2008Choco
 */
public class ObeliskManager {
	
	private final Set<Obelisk> obelisks = new HashSet<>();
	private final Map<ObeliskStructure, Class<? extends Obelisk>> structures = new HashMap<>();
	
	private final Relics plugin;
	
	public ObeliskManager(Relics plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Get an immutable copy of all obelisks in the world
	 * 
	 * @return all obelisks in the world
	 */
	public Set<Obelisk> getObelisks() {
		return ImmutableSet.copyOf(obelisks);
	}
	
	/**
	 * Register an in-world obelisk to the obelisk manager
	 * 
	 * @param obelisk the obelisk to register
	 */
	public void registerObelisk(Obelisk obelisk) {
		this.obelisks.add(obelisk);
	}
	
	/**
	 * Unregister an in-world obelisk from the obelisk manager
	 * 
	 * @param obelisk the obelisk to unregister
	 */
	public void unregisterObelisk(Obelisk obelisk) {
		this.obelisks.remove(obelisk);
		plugin.obeliskFile.getConfig().set(obelisk.getUniqueId().toString(), null);
		plugin.obeliskFile.saveConfig();
	}
	
	/**
	 * Get an in-world obelisk instance based on a block
	 * 
	 * @param block the block to reference
	 * 
	 * @return the obelisk that owns the provided block, or null if not an 
	 * obelisk component
	 */
	public Obelisk getObelisk(Block block) {
		for (Obelisk obelisk : obelisks)
			if (obelisk.isObeliskComponent(block)) return obelisk;
		return null;
	}
	
	/**
	 * Check whether a block is an obelisk component or not
	 * 
	 * @param block the block to check
	 * @return true if an obelisk component, false otherwise
	 */
	public boolean isObeliskComponent(Block block) {
		for (Obelisk obelisk : obelisks)
			if (obelisk.isObeliskComponent(block)) return true;
		return false;
	}
	
	/**
	 * Register an obelisk structure to the structure registry
	 * 
	 * @param structure the structure to register
	 * @param obeliskClass the representing obelisk class
	 */
	public void registerStructure(ObeliskStructure structure, Class<? extends Obelisk> obeliskClass) {
		this.structures.put(structure, obeliskClass);
	}
	
	/**
	 * Get a set of all registered obelisk structures
	 * 
	 * @return all registered obelisk structures
	 */
	public Set<ObeliskStructure> getStructures() {
		return structures.keySet();
	}
	
	/**
	 * Get the structure that represents the given obelisk class
	 * 
	 * @param obelisk the obelisk class to reference
	 * @return the related obelisk structure
	 */
	public ObeliskStructure getStructure(Class<? extends Obelisk> obelisk) {
		for (ObeliskStructure structure : structures.keySet())
			if (structure.getObeliskClass().equals(obelisk)) return structure;
		return null;
	}
	
	/**
	 * Get the structure for a given in-world obelisk
	 * 
	 * @param obelisk the in-world obelisk
	 * @return the resulting obelisk structure
	 */
	public ObeliskStructure getStructure(Obelisk obelisk) {
		return getStructure(obelisk.getClass());
	}
	
	/**
	 * Get an immutable copy of the structure registry map
	 * 
	 * @return the structure registry map
	 */
	public Map<ObeliskStructure, Class<? extends Obelisk>> getStructureRegistry() {
		return ImmutableMap.copyOf(structures);
	}
	
	/**
	 * Create a specific obelisk and return its instance
	 * 
	 * @param structure the structure to create
	 * @param owner the owner of the obelisk
	 * @param components the obelisk block components
	 * 
	 * @return the resulting obelisk structure
	 */
	public Obelisk createObelisk(ObeliskStructure structure, OfflinePlayer owner, List<Block> components) {
		return createObelisk(structure, owner, UUID.randomUUID(), components);
	}
	
	/**
	 * Create a specific obelisk and return its instance
	 * 
	 * @param structure the structure to create
	 * @param owner the owner of the obelisk
	 * @param uuid the obelisk's pre-defined UUID
	 * @param components the obelisk block components
	 * 
	 * @return the resulting obelisk structure
	 */
	public Obelisk createObelisk(ObeliskStructure structure, OfflinePlayer owner, UUID uuid, List<Block> components) {
		try{
			return structures.get(structure).getConstructor(OfflinePlayer.class, UUID.class, List.class, Class.class).newInstance(owner, uuid, components, structure.getObeliskClass());
		}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Construct a specific obelisk and return its instance
	 * 
	 * @param clazz the obelisk class
	 * @param owner the owner of the obelisk
	 * @param components the obelisk block components
	 * 
	 * @return the obelisk block components
	 */
	public Obelisk createObelisk(Class<? extends Obelisk> clazz, OfflinePlayer owner, List<Block> components) {
		return createObelisk(clazz, owner, UUID.randomUUID(), components);
	}
	
	/**
	 * Construct a specific obelisk and return its instance
	 * 
	 * @param clazz the obelisk class
	 * @param owner the owner of the obelisk
	 * @param uuid the obelisk's pre-defined UUID
	 * @param components the obelisk block components
	 * 
	 * @return the resulting obelisk structure
	 */
	public Obelisk createObelisk(Class<? extends Obelisk> clazz, OfflinePlayer owner, UUID uuid, List<Block> components) {
		try {
			return clazz.getConstructor(OfflinePlayer.class, UUID.class, List.class, Class.class).newInstance(owner, uuid, components, clazz);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}