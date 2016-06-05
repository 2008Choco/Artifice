package me.choco.relics.artifacts;

import org.bukkit.ChatColor;

public enum Rarity {
	
	COMMON("Common", ChatColor.GRAY + "" + ChatColor.BOLD + "Common"),
	RARE("Rare", ChatColor.BLUE + "" + ChatColor.BOLD + "Rare"),
	EPIC("Epic", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Epic"),
	MYTHICAL("Mythical", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Mythical"),
	LEGENDARY("Legendary", ChatColor.GOLD + "" + ChatColor.BOLD + "Legendary");
	
	private String name, displayName;
	private Rarity(String name, String displayName){
		this.name = name;
		this.displayName = displayName;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}