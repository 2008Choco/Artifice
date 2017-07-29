package me.choco.relics.utils.loops;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import me.choco.relics.Relics;
import me.choco.relics.api.ObeliskStructure;
import me.choco.relics.api.events.ObeliskApplyEffectEvent;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskManager;

public class ObeliskEffectLoop extends BukkitRunnable {
	
	private static final Random RANDOM = new Random();
	
	private final ObeliskManager manager;
	
	public ObeliskEffectLoop(Relics plugin) {
		this.manager = plugin.getObeliskManager();
	}
	
	@Override
	public void run() {
		Iterator<Obelisk> it = manager.getObelisks().iterator();
		while (it.hasNext()) {
			Obelisk obelisk = it.next();
			if (obelisk == null || obelisk.getComponents().isEmpty()) it.remove();
			if (!obelisk.getComponents().get(0).getChunk().isLoaded()) continue;
			if (!obelisk.shouldEffect(RANDOM)) continue;
			
			Location center = obelisk.getComponents().get(0).getLocation();
			ObeliskStructure structure = manager.getStructure(obelisk);
			center.add(structure.getXFormationIndex() + 0.5, 0, structure.getZFormationIndex() + 0.5);
			
			// Display particles around the obelisks' structure
			
//			double radius = obelisk.getEffectRadius();
//			Player player = Bukkit.getPlayer("2008Choco");
//			if (player != null){
//				for (double theta = 0; theta < Math.PI * 2; theta += 0.05){
//					double x = radius * Math.cos(theta),
//							y = 1,
//							z = radius * Math.sin(theta);
//					center.add(x, y, z);
//					player.spawnParticle(Particle.FLAME, center, 1, 0.01, 0.01, 0.01, 0.01);
//					center.subtract(x, y, z);
//					player.spawnParticle(Particle.SPELL, center, 1, 0.01, 0.01, 0.01, 0.01);
//				}
//			}
			
			Set<Entity> entities = getNearbyEntities(center, obelisk.getEffectRadius());
			ObeliskApplyEffectEvent oaee = new ObeliskApplyEffectEvent(obelisk, entities);
			Bukkit.getPluginManager().callEvent(oaee);
			if (oaee.isCancelled()) continue;
			
			entities = oaee.getAffectedEntities(); // Update just in case it's modified in the event
			for (Entity entity : entities) {
				if (!(entity instanceof LivingEntity)) continue;
				obelisk.executeEffect((LivingEntity) entity);
			}
			entities.clear();
		}
	}
	
    private Set<Entity> getNearbyEntities(Location location, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        Set<Entity> entities = new HashSet<Entity>();
        
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX ++){
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++){
                int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();
                for (Entity entity : new Location(location.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()){
                    if (entity.getLocation().distance(location) <= radius && entity.getLocation().getBlock() != location.getBlock()) 
                    	entities.add(entity);
                }
            }
        }
        
        return entities;
    }
}