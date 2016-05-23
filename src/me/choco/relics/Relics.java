package me.choco.relics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.choco.relics.api.ObeliskStructure;
import me.choco.relics.artifacts.regular.TestArtifact;
import me.choco.relics.events.ObeliskProtection;
import me.choco.relics.events.StructureDetection;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.structures.obelisks.BasicObelisk;
import me.choco.relics.utils.ArtifactManager;
import me.choco.relics.utils.ObeliskManager;
import me.choco.relics.utils.general.ConfigAccessor;
import me.choco.relics.utils.loops.ArtifactEffectLoop;
import me.choco.relics.utils.loops.ObeliskEffectLoop;

public class Relics extends JavaPlugin{
	
	public ConfigAccessor obeliskFile;
	
	private static Relics instance;
	private ObeliskManager obeliskManager;
	private ArtifactManager artifactManager;
	
	private ObeliskEffectLoop obeliskEffectLoop;
	private ArtifactEffectLoop artifactEffectLoop;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable(){
		instance = this;
		obeliskManager = new ObeliskManager(this);
		artifactManager = new ArtifactManager();
		
		obeliskFile = new ConfigAccessor(this, "obelisks.yml");
		obeliskFile.loadConfig(); 
		obeliskFile.getConfig().options().copyDefaults(true); 
		obeliskFile.saveConfig();
		
		// Register events
		Bukkit.getPluginManager().registerEvents(new StructureDetection(this), this);
		Bukkit.getPluginManager().registerEvents(new ObeliskProtection(this), this);
		
		// Load structures
		new ObeliskStructure(1, 3, 3, BasicObelisk.class)
			.setBlockPosition(0, 0, 1, Material.LOG).setBlockPosition(0, 1, 0, Material.FENCE)
			.setBlockPosition(0, 1, 1, Material.LOG).setBlockPosition(0, 1, 2, Material.FENCE)
			.setBlockPosition(0, 2, 0, Material.WOOD).setBlockPosition(0, 2, 1, Material.LOG)
			.setBlockPosition(0, 2, 2, Material.WOOD).setFormationMaterial(0, 0, 1).build();
		
		// Load obelisks
		for (String uuid : obeliskFile.getConfig().getKeys(false)){
			try {
				Obelisk obelisk = obeliskManager.createObelisk(
						(Class<? extends Obelisk>) Class.forName(obeliskFile.getConfig().getString(uuid + ".class")), 
						Bukkit.getOfflinePlayer(UUID.fromString(obeliskFile.getConfig().getString(uuid + ".ownerUUID"))),
						UUID.fromString(uuid), 
						stringListToBlockList(obeliskFile.getConfig().getStringList(uuid + ".components")));
				obeliskManager.registerObelisk(obelisk);
			} catch (ClassNotFoundException e) {
				this.getLogger().warning("Could not find obelisk variation for class "
						+ obeliskFile.getConfig().getString(uuid + ".class"));
			}
		}
		
		// Register artifacts
		artifactManager.registerArtifact(TestArtifact.class, new TestArtifact());
		
		// Commence effect loops
		obeliskEffectLoop = new ObeliskEffectLoop(this);
		obeliskEffectLoop.runTaskTimer(this, 0, 100);
		
		artifactEffectLoop = new ArtifactEffectLoop(this);
		artifactEffectLoop.runTaskTimer(this, 0, 100);
	}
	
	@Override
	public void onDisable(){
		for (Obelisk obelisk : obeliskManager.getObelisks()){
			obeliskFile.getConfig().set(obelisk.getUniqueId() + ".ownerUUID", obelisk.getOwner().getUniqueId().toString());
			obeliskFile.getConfig().set(obelisk.getUniqueId() + ".ownerName", obelisk.getOwner().getName());
			obeliskFile.getConfig().set(obelisk.getUniqueId() + ".components", blockListToStringList(obelisk.getComponents()));
			obeliskFile.getConfig().set(obelisk.getUniqueId() + ".class", obelisk.getCustomClass().getName());
		}
		obeliskFile.saveConfig();
		
		obeliskManager.getObelisks().clear();
		obeliskManager.getStructureRegistry().clear();
		obeliskEffectLoop.cancel();
		artifactEffectLoop.cancel();
	}
	
	public static Relics getPlugin(){
		return instance;
	}
	
	public ObeliskManager getObeliskManager(){
		return obeliskManager;
	}
	
	public ArtifactManager getArtifactManager(){
		return artifactManager;
	}
	
	public void sendMessage(CommandSender sender, String message){
		sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Relics" + ChatColor.GRAY + "] " + message);
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
	 *   Different artifacts grant different abilities / effects
	 *   Hopefully expandable artifacts?
	 *   Craftable totemnic objects / obelisks that act similar to beacons
	 *   Spirits fly around obelisks (particles)
	 */
}