package me.choco.relics.artifacts.necrotic;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.relics.api.artifact.NecroticArtifact;
import me.choco.relics.artifacts.Rarity;
import me.choco.relics.utils.general.ItemBuilder;

public class DevilsStaff extends NecroticArtifact {
	
	private static final ItemStack ITEM = new ItemBuilder(Material.BLAZE_ROD)
			.setName(ChatColor.RED + "Devil's Staff")
			.build();

	@Override
	public double discoveryPercent() {
		return 100;
	}

	@Override
	public String getName() {
		return "Devil's Staff";
	}

	@Override
	public ItemStack getItem() {
		return ITEM;
	}

	@Override
	public boolean shouldEffect(Random random) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		player.sendMessage(ChatColor.RED + "effect.devils_staff.execute");
	}

	@Override
	public Rarity getRarity() {
		return Rarity.MYTHICAL;
	}
}