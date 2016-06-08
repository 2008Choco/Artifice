package me.choco.relics.utils.general;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.choco.relics.Relics;
import me.choco.relics.api.artifact.Artifact;
import me.choco.relics.utils.ArtifactManager;

public class ArtifactUtils {
	
	private static final ArtifactManager manager = Relics.getPlugin().getArtifactManager();
	
	public static boolean playerHasArtifact(Player player, Class<? extends Artifact> artifact){
		for (ItemStack item : player.getInventory().getContents())
			if (manager.isArtifact(item, artifact)) return true;
		return false;
	}
	
	public static boolean playerHasArtifact(Player player, Artifact artifact){
		return playerHasArtifact(player, artifact.getClass());
	}
	
	public static ItemStack getArtifactItem(Artifact artifact){
		ItemStack item = artifact.getItem();
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
		if (meta.hasLore()){
			List<String> lore = meta.getLore();
			boolean hasTypeLore = false, hasRarityLore = false;
			for (String line : lore){
				if (line.contains(ChatColor.WHITE + "Type: ")) hasTypeLore = true;
				if (line.contains(ChatColor.WHITE + "Rarity: ")) hasRarityLore = true;
			}
			
			if (!hasTypeLore){
				lore.add("");
				lore.add(ChatColor.WHITE + "Type: " + ChatColor.GRAY + artifact.getType().getName());
			}
			if (!hasRarityLore){
				lore.add(ChatColor.WHITE + "Rarity: " + artifact.getRarity().getDisplayName());
			}
			meta.setLore(lore);
		}else{
			meta.setLore(Arrays.asList(
				ChatColor.WHITE + "Type: " + ChatColor.GRAY + artifact.getType().getName(),
				ChatColor.WHITE + "Rarity: " + artifact.getRarity().getDisplayName()
			));
		}
		item.setItemMeta(meta);
		return item;
	}
}