package me.choco.relics.artifacts;

import org.bukkit.ChatColor;

public enum Rarity {
	
	COMMON(1, "Common", ChatColor.GRAY + "" + ChatColor.BOLD + "Common"),
	RARE(2, "Rare", ChatColor.BLUE + "" + ChatColor.BOLD + "Rare"),
	EPIC(3, "Epic", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Epic"),
	MYTHICAL(4, "Mythical", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Mythical"),
	LEGENDARY(5, "Legendary", ChatColor.GOLD + "" + ChatColor.BOLD + "Legendary");
	
	private int tier;
	private String name, displayName;
	private Rarity(int tier, String name, String displayName){
		this.tier = tier;
		this.name = name;
		this.displayName = displayName;
	}
	
	public int getTier() {
		return tier;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public static Rarity getByName(String name){
		for (Rarity rarity : values())
			if (name.equalsIgnoreCase(rarity.getName())) return rarity;
		return null;
	}
	
	public static Rarity getByRarity(int tier){
		if (tier <= 0 || tier > 5) throw new IllegalArgumentException("Tier value out of bounds. Tiers range from 0 - 5");
		
		for (Rarity rarity : values())
			if (rarity.getTier() == tier) return rarity;
		return null;
	}
}