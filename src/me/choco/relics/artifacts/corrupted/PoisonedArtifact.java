package me.choco.relics.artifacts.corrupted;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.artifacts.Rarity;
import me.choco.relics.utils.general.ItemBuilder;

public class PoisonedArtifact extends CorruptedArtifact {
	
	private static final ItemStack item = new ItemBuilder(Material.GHAST_TEAR).setName(ChatColor.DARK_PURPLE + "Poisoned Artifact")
			.setLore(Arrays.asList(ChatColor.DARK_GREEN + "A demonic artifact, crafted by witches", 
									ChatColor.DARK_GREEN + "Only the purest of pure may witness its true power")).build();

	@Override
	public double corruptionPercent() {
		return 2.5;
	}

	@Override
	public boolean canCorrupt(ArtifactType type) {
		return type.equals(ArtifactType.FOSSILIZED);
	}

	@Override
	public String getName() {
		return "Poisoned Artifact";
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public boolean shouldEffect(Random random) {
		return random.nextInt(100) < 20;
	}

	@Override
	public void executeEffect(Player player) {
		player.sendMessage(ChatColor.DARK_PURPLE + "OOOOOOOOOOO... CORRUPTIONNNNNNN " + ChatColor.MAGIC + "JIUDSDNCJAKNVAYDJJGVHDNAIUV");
	}

	@Override
	public Rarity getRarity() {
		return Rarity.EPIC;
	}
	
}