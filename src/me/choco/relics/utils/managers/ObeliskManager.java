package me.choco.relics.utils.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import me.choco.relics.Relics;
import me.choco.relics.api.ObeliskStructure;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskType;

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
	
	public void unregisterObelisks(ObeliskType type){
		Iterator<Obelisk> it = obelisks.iterator();
		while (it.hasNext()){
			if (it.next().getType().equals(type)) it.remove();
		}
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
	
	public Obelisk createObeliskFromStructure(ObeliskStructure structure, OfflinePlayer owner, List<Block> components){
		try{
			return structures.get(structure).getConstructor(OfflinePlayer.class, UUID.class, ObeliskType.class, List.class).newInstance(owner, UUID.randomUUID(), structure.getObeliskType(), components);
		}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Obelisk createObeliskFromStructure(ObeliskStructure structure, UUID uuid, OfflinePlayer owner, List<Block> components){
		try{
			return structures.get(structure).getConstructor(OfflinePlayer.class, UUID.class, ObeliskType.class, List.class).newInstance(owner, uuid, structure.getObeliskType(), components);
		}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
			e.printStackTrace();
			return null;
		}
	}
}