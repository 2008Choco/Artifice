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
import me.choco.relics.api.events.ObeliskApplyEffectEvent;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskManager;

public class ObeliskEffectLoop extends BukkitRunnable{
	
	private static final Random random = new Random();
	
	private ObeliskManager manager;
	public ObeliskEffectLoop(Relics plugin){
		this.manager = plugin.getObeliskManager();
	}
	
	@Override
	public void run(){
		Iterator<Obelisk> it = manager.getObelisks().iterator();
		while (it.hasNext()){
			Obelisk obelisk = it.next();
			if (obelisk == null || obelisk.getComponents().isEmpty()) it.remove();
			if (!obelisk.shouldEffect(random)) continue;
			
			Set<Entity> entities = getNearbyEntities(obelisk.getComponents().get(0).getLocation(), obelisk.getEffectRadius());
			ObeliskApplyEffectEvent oaee = new ObeliskApplyEffectEvent(obelisk, entities);
			Bukkit.getPluginManager().callEvent(oaee);
			if (oaee.isCancelled()) continue;
			
			entities = oaee.getAffectedEntities(); // Update just in case it's modified in the event
			for (Entity entity : entities){
				if (!(entity instanceof LivingEntity)) continue;
				obelisk.executeEffect((LivingEntity) entity);
			}
			entities.clear();
		}
	}
	
    private Set<Entity> getNearbyEntities(Location location, int radius){
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