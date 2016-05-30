package me.choco.relics.artifacts.regular;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.choco.relics.artifacts.Artifact;
import me.choco.relics.artifacts.ArtifactType;
import me.choco.relics.utils.general.ItemBuilder;
import net.md_5.bungee.api.ChatColor;

public class TestArtifact extends Artifact {
	
	private static final ItemStack item = new ItemBuilder(Material.GOLDEN_APPLE).setName(ChatColor.LIGHT_PURPLE + "Test Artifact").build();

	@Override
	public String getName() {
		return "Test Artifact";
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public ArtifactType getType() {
		return ArtifactType.NORMAL;
	}

	@Override
	public boolean shouldEffect(Random random) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		player.removePotionEffect(PotionEffectType.ABSORPTION);
		player.addPotionEffect(PotionEffectType.ABSORPTION.createEffect(25, 9));
	}
}