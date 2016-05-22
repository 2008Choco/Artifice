package me.choco.relics.utils.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import me.choco.relics.Relics;
import me.choco.relics.api.ObeliskStructure;
import me.choco.relics.structures.Obelisk;

public class ObeliskManager {
	
	private final Set<Obelisk> obelisks = new HashSet<>();
	private final HashMap<ObeliskStructure, Class<? extends Obelisk>> structures = new HashMap<>();
	
	@SuppressWarnings("unused")
	private Relics plugin;
	public ObeliskManager(Relics plugin){
		this.plugin = plugin;
	}
	
	public Set<Obelisk> getObelisks(){
		return obelisks;
	}
	
	public void registerObelisk(Obelisk obelisk){
		this.obelisks.add(obelisk);
	}
	
	public void unregisterObelisk(Obelisk obelisk){
		this.obelisks.remove(obelisk);
	}
	
	public Obelisk getObelisk(Block block){
		for (Obelisk obelisk : obelisks)
			if (obelisk.isObeliskComponent(block)) return obelisk;
		return null;
	}
	
	public boolean isObeliskComponent(Block block){
		for (Obelisk obelisk : obelisks)
			if (obelisk.isObeliskComponent(block)) return true;
		return false;
	}
	
	public void registerStructure(ObeliskStructure structure, Class<? extends Obelisk> obeliskClass){
		this.structures.put(structure, obeliskClass);
	}
	
	public Set<ObeliskStructure> getStructures(){
		return structures.keySet();
	}
	
	public HashMap<ObeliskStructure, Class<? extends Obelisk>> getStructureRegistry(){
		return structures;
	}
	
	public Obelisk createObelisk(ObeliskStructure structure, OfflinePlayer owner, List<Block> components){
		return createObelisk(structure, owner, UUID.randomUUID(), components);
	}
	
	public Obelisk createObelisk(ObeliskStructure structure, OfflinePlayer owner, UUID uuid, List<Block> components){
		try{
			return structures.get(structure).getConstructor(OfflinePlayer.class, UUID.class, List.class, Class.class).newInstance(owner, uuid, components, structure.getObeliskClass());
		}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Obelisk createObelisk(Class<? extends Obelisk> clazz, OfflinePlayer owner, List<Block> components){
		return createObelisk(clazz, owner, UUID.randomUUID(), components);
	}
	
	public Obelisk createObelisk(Class<? extends Obelisk> clazz, OfflinePlayer owner, UUID uuid, List<Block> components){
		try {
			return clazz.getConstructor(OfflinePlayer.class, UUID.class, List.class, Class.class).newInstance(owner, uuid, components, clazz);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}