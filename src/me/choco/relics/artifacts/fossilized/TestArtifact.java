package me.choco.relics.artifacts.fossilized;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.choco.relics.artifacts.Rarity;
import me.choco.relics.utils.general.ItemBuilder;

public class TestArtifact extends FossilizedArtifact {
	
	private static final ItemStack item = new ItemBuilder(Material.GOLDEN_APPLE).setName(ChatColor.GOLD + "Test Artifact").build();

	@Override
	public String getName() {
		return "Test Artifact";
	}

	@Override
	public ItemStack getItem() {
		return item;
	}
	
	@Override
	public double discoveryPercent() {
		return 0.5;
	}
	
	@Override
	public Rarity getRarity() {
		return Rarity.LEGENDARY;
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