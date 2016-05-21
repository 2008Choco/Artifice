package me.choco.relics.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.choco.relics.Relics;
import me.choco.relics.api.ObeliskStructure;
import me.choco.relics.api.events.PlayerCreateObeliskEvent;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.managers.ObeliskManager;

public class StructureDetection implements Listener{
	
	Relics plugin;
	private ObeliskManager manager;
	public StructureDetection(Relics plugin){
		this.plugin = plugin;
		this.manager = plugin.getObeliskManager();
	}
	
	@EventHandler
	public void onClickFormationBlock(PlayerInteractEvent event){
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				|| event.getHand().equals(EquipmentSlot.HAND)) return; 
		
		if (manager.isObeliskComponent(event.getClickedBlock())){
			// return;
			event.getPlayer().sendMessage("This is an obelisk (UUID: " + manager.getObelisk(event.getClickedBlock()).getUniqueId() + ")");
		}
		
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		for (ObeliskStructure structure : manager.getStructures()){
			if (isObeliskStructure(block, structure)){
				
				Location aa = block.getLocation().subtract(structure.getXFormationIndex(), structure.getYFormationIndex(), structure.getZFormationIndex());
				List<Block> components = new ArrayList<>();
				
				for (int x = 0; x < structure.getLength(); x++){
					for (int y = 0; y < structure.getHeight(); y++){
						for (int z = 0; z < structure.getWidth(); z++){
							aa.add(x, y, z);
							components.add(aa.getBlock());
							aa.subtract(x, y, z);
						}
					}
				}
				
				Obelisk obelisk = manager.createObelisk(structure, player, components);
				PlayerCreateObeliskEvent pcoe = new PlayerCreateObeliskEvent(player, obelisk);
				Bukkit.getPluginManager().callEvent(pcoe);
				
				manager.registerObelisk(obelisk);
			}
		}
	}
	
	@EventHandler
	public void onBreakObelisk(BlockBreakEvent event){
		if (manager.isObeliskComponent(event.getBlock())){
			manager.unregisterObelisk(manager.getObelisk(event.getBlock()));
		}
	}
	
	private boolean isObeliskStructure(Block block, ObeliskStructure structure){
		if (!block.getType().equals(structure.getFormationMaterial())
				|| manager.isObeliskComponent(block)) return false;
		
		Location aa = block.getLocation();
		Material[][][] materials = new Material[structure.getLength()][structure.getHeight()][structure.getWidth()];
		
		for (int y = 0; y < structure.getHeight(); y++){
			for (int x = 0; x < structure.getLength(); x++){
				for (int z = 0; z < structure.getWidth(); z++){
					aa.add(x, y, z);
					materials[x][y][z] = aa.getBlock().getType();
					aa.subtract(x, y, z);
				}
			}
		}
		
//		for (Material[][] mat : structure.getBlockFormation()){
//			for (Material[] ma : mat){
//				for (Material m : ma){
//					System.out.println(m.name());
//				}
//			}
//		}
		
		return Arrays.deepEquals(materials, structure.getBlockFormation());
	}
}