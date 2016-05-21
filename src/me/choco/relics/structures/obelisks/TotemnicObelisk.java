package me.choco.relics.structures.obelisks;

import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskType;

public class TotemnicObelisk extends Obelisk{

	public TotemnicObelisk(OfflinePlayer owner, UUID uuid, ObeliskType type, List<Block> components) {
		super(owner, uuid, type, components);
	}

	@Override
	public void displayWisps() {
		// TODO Auto-generated method stub
	}
}