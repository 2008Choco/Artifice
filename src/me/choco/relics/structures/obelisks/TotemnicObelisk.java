package me.choco.relics.structures.obelisks;

import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import me.choco.relics.structures.Obelisk;

public class TotemnicObelisk extends Obelisk{

	public TotemnicObelisk(OfflinePlayer owner, UUID uuid, List<Block> components, Class<? extends Obelisk> customClazz){
		super(owner, uuid, components, customClazz);
	}

	@Override
	public void displayWisps() {
		// TODO Auto-generated method stub
	}
}