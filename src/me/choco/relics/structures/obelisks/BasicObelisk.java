package me.choco.relics.structures.obelisks;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
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
		return 0;
	}

	@Override
	public boolean shouldEffect(Random random) {
		return (random.nextInt(100) < 25);
	}

	@Override
	public void executeEffect(Player player) {
		
	}
	
	@Override
	public String getName(){
		return "Basic Obelisk";
	}
}