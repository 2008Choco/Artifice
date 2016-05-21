package me.choco.relics.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import me.choco.relics.structures.Obelisk;
import me.choco.relics.structures.obelisks.BasicObelisk;
import me.choco.relics.structures.obelisks.TotemnicObelisk;

public enum ObeliskType {
	BASIC(BasicObelisk.class),
	TOTEMNIC(TotemnicObelisk.class),
	CUSTOM(null);
	
	private Class<? extends Obelisk> obeliskClass;
	ObeliskType(Class<? extends Obelisk> obeliskClass){
		this.obeliskClass = obeliskClass;
	}
	
	public Class<? extends Obelisk> getObeliskClass(){
		return obeliskClass;
	}
	
	public Obelisk createObelisk(OfflinePlayer owner, List<Block> components){
		try {
			return obeliskClass.getConstructor(OfflinePlayer.class, UUID.class, ObeliskType.class, List.class).newInstance(owner, UUID.randomUUID(), this, components);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Obelisk createObelisk(Class<? extends Obelisk> clazz, OfflinePlayer owner, List<Block> components){
		try {
			return clazz.getConstructor(OfflinePlayer.class, UUID.class, ObeliskType.class, List.class).newInstance(owner, UUID.randomUUID(), this, components);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Obelisk loadObelisk(OfflinePlayer owner, UUID uuid, List<Block> components){
		try {
			return obeliskClass.getConstructor(OfflinePlayer.class, UUID.class, ObeliskType.class, List.class).newInstance(owner, uuid, this, components);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Obelisk loadObelisk(Class<? extends Obelisk> clazz, OfflinePlayer owner, UUID uuid, List<Block> components){
		try {
			return clazz.getConstructor(OfflinePlayer.class, UUID.class, ObeliskType.class, List.class).newInstance(owner, uuid, this, components);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}