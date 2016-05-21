package me.choco.relics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import me.choco.relics.api.ObeliskStructure;
import me.choco.relics.events.StructureDetection;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskType;
import me.choco.relics.utils.general.ConfigAccessor;
import me.choco.relics.utils.managers.ObeliskManager;

public class Relics extends JavaPlugin{
	
	private ConfigAccessor obeliskFile;
	
	private static Relics instance;
	private ObeliskManager obeliskManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable(){
		instance = this;
		obeliskManager = new ObeliskManager(this);
		
		obeliskFile = new ConfigAccessor(this, "obelisks.yml");
		obeliskFile.loadConfig(); 
		obeliskFile.getConfig().options().copyDefaults(true); 
		obeliskFile.saveConfig();
		
		Bukkit.getPluginManager().registerEvents(new StructureDetection(this), this);
		
		new ObeliskStructure(2, 2, 2, ObeliskType.BASIC)
				.setBlockPosition(0, 0, 0, Material.GOLD_BLOCK).setBlockPosition(1, 0, 0, Material.GOLD_BLOCK)
				.setBlockPosition(1, 0, 1, Material.GOLD_BLOCK).setBlockPosition(0, 0, 1, Material.GOLD_BLOCK)
				.setBlockPosition(0, 1, 0, Material.DIAMOND_BLOCK).setBlockPosition(1, 1, 0, Material.DIAMOND_BLOCK)
				.setBlockPosition(1, 1, 1, Material.DIAMOND_BLOCK).setBlockPosition(0, 1, 1, Material.DIAMOND_BLOCK)
				.setFormationMaterial(0, 0, 0).build();
		
		/* LAL
		 * AIA -> Anvil on top of I
		 * LAL
		 * 
		 * I = Iron Block
		 * A = Air
		 * L = Log
		 */
		
		for (String uuid : obeliskFile.getConfig().getKeys(false)){
			ObeliskType type = ObeliskType.valueOf(obeliskFile.getConfig().getString(uuid + ".type"));
			try{
				if (type.equals(ObeliskType.CUSTOM)){
					Obelisk obelisk = type.loadObelisk(
							(Class<? extends Obelisk>) Class.forName(obeliskFile.getConfig().getString(uuid + ".customClass")), 
							Bukkit.getOfflinePlayer(UUID.fromString(obeliskFile.getConfig().getString(uuid + ".ownerUUID"))),
							UUID.fromString(uuid), 
							stringListToBlockList(obeliskFile.getConfig().getStringList(uuid + ".components")));
					obeliskManager.registerObelisk(obelisk);
				}else{
					Obelisk obelisk = type.loadObelisk(
							Bukkit.getOfflinePlayer(UUID.fromString(obeliskFile.getConfig().getString(uuid + ".ownerUUID"))), 
							UUID.fromString(uuid), 
							stringListToBlockList(obeliskFile.getConfig().getStringList(uuid + ".components")));
					obeliskManager.registerObelisk(obelisk);
				}
			}catch(ClassNotFoundException e){ this.getLogger().warning("Obelisk " + uuid + " could not be loaded. The type " + obeliskFile.getConfig().getString(uuid + ".customClass") + " could not be found. Ignoring"); }
		}
	}
	
	@Override
	public void onDisable(){
		for (Obelisk obelisk : obeliskManager.getObelisks()){
			System.out.println("Saving obelisk: " + obelisk.getUniqueId());
			obeliskFile.getConfig().set(obelisk.getUniqueId() + ".ownerUUID", obelisk.getOwner().getUniqueId().toString());
			obeliskFile.getConfig().set(obelisk.getUniqueId() + ".ownerName", obelisk.getOwner().getName());
			obeliskFile.getConfig().set(obelisk.getUniqueId() + ".components", blockListToStringList(obelisk.getComponents()));
			obeliskFile.getConfig().set(obelisk.getUniqueId() + ".type", obelisk.getType().name());
			if (obelisk.getType().equals(ObeliskType.CUSTOM)){
				obeliskFile.getConfig().set(obelisk.getUniqueId() + ".customClass", obelisk.getCustomClass().getName());
			}
		}
		obeliskFile.saveConfig();
	}
	
	public static Relics getPlugin(){
		return instance;
	}
	
	public ObeliskManager getObeliskManager(){
		return obeliskManager;
	}
	
	private List<String> blockListToStringList(List<Block> blocks){
		List<String> strings = new ArrayList<>();
		for (Block block : blocks)
			strings.add(block.getX() + "," + block.getY() + "," + block.getZ() + "," + block.getWorld().getName());
		return strings;
	}
	
	private List<Block> stringListToBlockList(List<String> strings){
		List<Block> blocks = new ArrayList<>();
		for (String string : strings){
			String[] values = string.split(",");
			blocks.add(Bukkit.getWorld(values[3]).getBlockAt(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2])));
		}
		return blocks;
	}
	
	/* Relics - General Idea:
	 *   Hidden artifacts can be found throughout the world
	 *   	- Mining
	 *   	- Killing Entities
	 *   	- Crafting
	 *   	- Dungeon Loot
	 *   Different relics grant different abilities / effects
	 *   Hopefully expandable relics?
	 *   Craftable totemnic objects / obelisks that act similar to beacons
	 *   Spirits fly around obelisks (particles)
	 */
}