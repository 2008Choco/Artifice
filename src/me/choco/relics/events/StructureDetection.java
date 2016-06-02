package me.choco.relics.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.choco.relics.Relics;
import me.choco.relics.api.ObeliskStructure;
import me.choco.relics.api.events.player.PlayerCreateObeliskEvent;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskManager;

public class StructureDetection implements Listener{
	
	private static final Random random = new Random();
	private static final String[] creationMessages = {
			"A strange aura seems to empower and surround the structure",
			"The structure seems to start shaking as it illuminates dimly",
			"As you step away from the structures, you hear silent whispers",
	};
	
	private Relics plugin;
	private ObeliskManager manager;
	public StructureDetection(Relics plugin){
		this.plugin = plugin;
		this.manager = plugin.getObeliskManager();
	}
	
	@EventHandler
	public void onClickFormationBlock(PlayerInteractEvent event){
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				|| event.getHand().equals(EquipmentSlot.HAND)) return; 
		
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (manager.isObeliskComponent(block)){
			String obeliskName = manager.getObelisk(block).getName().toLowerCase();
			plugin.sendMessage(player, "You observe the strange structure. You notice an engraving that reads, \"" 
					+ obeliskName.toLowerCase() + (!obeliskName.toLowerCase().contains("obelisk") ? " obelisk" : "") + "\"");
			return;
		}
		
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
				plugin.sendMessage(player, creationMessages[random.nextInt(creationMessages.length)]);
				player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 5, 0);
				player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 5, 1);
			}
		}
	}
	
	private boolean isObeliskStructure(Block block, ObeliskStructure structure){
		if (!block.getType().equals(structure.getFormationMaterial())
				|| manager.isObeliskComponent(block)) return false;
		
		Location location = new Location(block.getWorld(), block.getX() - structure.getXFormationIndex(), block.getY() - structure.getYFormationIndex(), block.getZ() - structure.getZFormationIndex());
		Material[][][] materials = new Material[structure.getLength()][structure.getHeight()][structure.getWidth()];
		
		for (int y = 0; y < structure.getHeight(); y++){
			for (int x = 0; x < structure.getLength(); x++){
				for (int z = 0; z < structure.getWidth(); z++){
					location.add(x, y, z);
					materials[x][y][z] = location.getBlock().getType();
					location.subtract(x, y, z);
				}
			}
		}
		
		return Arrays.deepEquals(materials, structure.getBlockFormation());
	}
}