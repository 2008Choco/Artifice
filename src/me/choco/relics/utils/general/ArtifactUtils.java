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

/**
 * Various utilities related to artifacts
 * 
 * @author Parker Hawke - 2008Choco
 */
public class ArtifactUtils {
	
	private static final ArtifactManager MANAGER = Relics.getPlugin().getArtifactManager();
	
	/**
	 * Check whether a player has an artifact of a given class
	 * 
	 * @param player the player to check
	 * @param artifact the artifact to check for
	 * 
	 * @return true if the artifact is in the player's inventory. false otherwise
	 */
	public static boolean playerHasArtifact(Player player, Class<? extends Artifact> artifact) {
		for (ItemStack item : player.getInventory().getContents())
			if (MANAGER.isArtifact(item, artifact)) return true;
		return false;
	}
	
	/**
	 * Check whether a player has a given artifact
	 * 
	 * @param player the player to check
	 * @param artifact the artifact to check for
	 *
	 * @return true if the artifact is in the player's inventory. false otherwise
	 */
	public static boolean playerHasArtifact(Player player, Artifact artifact) {
		return playerHasArtifact(player, artifact.getClass());
	}
	
	/**
	 * Apply lore to an artifact's item and return the item
	 * 
	 * @param artifact the artifact to get the item for
	 * @return the resulting ItemStack
	 */
	public static ItemStack getArtifactItem(Artifact artifact) {
		ItemStack item = artifact.getItem();
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
		if (meta.hasLore()) {
			List<String> lore = meta.getLore();
			boolean hasTypeLore = false, hasRarityLore = false;
			for (String line : lore){
				if (line.contains(ChatColor.WHITE + "Type: ")) hasTypeLore = true;
				if (line.contains(ChatColor.WHITE + "Rarity: ")) hasRarityLore = true;
			}
			
			if (!hasTypeLore) {
				lore.add("");
				lore.add(ChatColor.WHITE + "Type: " + ChatColor.GRAY + artifact.getType().getName());
			}
			if (!hasRarityLore) {
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