package me.choco.relics.artifacts;

import org.bukkit.ChatColor;

/**
 * Represents an artifact's rarity
 * 
 * @author Parker Hawke - 2008Choco
 */
public enum Rarity {
	
	COMMON(1, "Common", ChatColor.GRAY),
	
	RARE(2, "Rare", ChatColor.BLUE),
	
	EPIC(3, "Epic", ChatColor.DARK_PURPLE),
	
	MYTHICAL(4, "Mythical", ChatColor.LIGHT_PURPLE),
	
	LEGENDARY(5, "Legendary", ChatColor.GOLD);
	
	
	private int tier;
	private String name, displayName;
	
	private Rarity(int tier, String name, ChatColor color){
		this.tier = tier;
		this.name = name;
		this.displayName = color.toString() + ChatColor.BOLD + name;
	}
	
	/**
	 * Get the tier of this rarity
	 * 
	 * @return the rarity tier
	 */
	public int getTier() {
		return tier;
	}
	
	/**
	 * Get the friendly name for this rarity
	 * 
	 * @return the friendly name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the name to be displayed on artifacts (includes colours)
	 * 
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Get an artifact type based on its friendly name
	 * 
	 * @param name the name to search for
	 * @return the resulting rarity, or null if non-existent
	 */
	public static Rarity getByName(String name){
		for (Rarity rarity : values())
			if (name.equalsIgnoreCase(rarity.getName())) return rarity;
		return null;
	}
	
	/**
	 * Get an artifact type based on its tier
	 * 
	 * @param tier the rarity tier
	 * @return the resulting rarity, or null if non-existent
	 */
	public static Rarity getByTier(int tier){
		for (Rarity rarity : values())
			if (rarity.getTier() == tier) return rarity;
		return null;
	}
}