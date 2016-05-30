package me.choco.relics.structures.obelisks;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.choco.relics.structures.Obelisk;

public class BasicObelisk extends Obelisk{

	public BasicObelisk(OfflinePlayer owner, UUID uuid, List<Block> components, Class<? extends Obelisk> customClazz) {
		super(owner, uuid, components, customClazz);
	}

	@Override
	public void displayWisps(Player player) {
		
	}

	@Override
	public int getEffectRadius() {
		return 5;
	}

	@Override
	public boolean shouldEffect(Random random) {
		return true;
		// return (random.nextInt(100) < 25);
	}

	@Override
	public void executeEffect(LivingEntity entity) {
		if (entity instanceof Player){
			Location location = entity.getLocation().clone();
			Player player = (Player) entity;
			double radius = 1;
			for (double theta = 0; theta < Math.PI * 2; theta += 0.25){
				double x = radius * Math.cos(theta),
						y = 1,
						z = radius * Math.sin(theta);
				location.add(x, y, z);
				player.spawnParticle(Particle.FLAME, location, 1, 0.01, 0.01, 0.01, 0.01);
				location.subtract(x, y, z);
			}
		}
	}
	
	@Override
	public String getName(){
		return "Basic Obelisk";
	}
}