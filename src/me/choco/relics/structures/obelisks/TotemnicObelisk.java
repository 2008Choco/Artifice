package me.choco.relics.structures.obelisks;

import java.util.List;
import java.util.Random;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.choco.relics.structures.Obelisk;

/**
 * Represents a totemnic obelisk
 * 
 * @author Parker Hawke - 2008Choco
 */
public class TotemnicObelisk extends Obelisk {

	public TotemnicObelisk(OfflinePlayer owner, List<Block> components){
		super(owner, components, TotemnicObelisk.class);
	}

	@Override
	public void displayWisps(Player player) {
		
	}

	@Override
	public int getEffectRadius() {
		return 0;
	}
	
	@Override
	public boolean shouldEffect(Random random) {
		return (random.nextInt(100) < 25);
	}

	@Override
	public void executeEffect(LivingEntity entity) {
		
	}
	
	@Override
	public String getName() {
		return "Totemnic Obelisk";
	}
}