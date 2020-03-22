package wtf.choco.relics.artifacts;

import org.bukkit.ChatColor;

/**
 * Represents an artifact's rarity
 *
 * @author Parker Hawke - 2008Choco
 */
public enum Rarity {

    COMMON("Common", ChatColor.GRAY),

    RARE("Rare", ChatColor.BLUE),

    EPIC("Epic", ChatColor.DARK_PURPLE),

    MYTHICAL("Mythical", ChatColor.LIGHT_PURPLE),

    LEGENDARY("Legendary", ChatColor.GOLD);


    private final String name, displayName;
    private final ChatColor color;

    private Rarity(String name, ChatColor color) {
        this.name = name;
        this.displayName = color.toString() + ChatColor.BOLD + name;
        this.color = color;
    }

    /**
     * Get the tier of this rarity
     *
     * @return the rarity tier
     */
    public int getTier() {
        return ordinal() + 1;
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

    public ChatColor getColor() {
        return color;
    }

    /**
     * Get an artifact type based on its friendly name
     *
     * @param name the name to search for
     *
     * @return the resulting rarity, or null if non-existent
     */
    public static Rarity getByName(String name) {
        for (Rarity rarity : values()) {
            if (name.equalsIgnoreCase(rarity.getName())) {
                return rarity;
            }
        }

        return null;
    }

    /**
     * Get an artifact type based on its tier
     *
     * @param tier the rarity tier
     *
     * @return the resulting rarity, or null if non-existent
     */
    public static Rarity getByTier(int tier) {
        for (Rarity rarity : values()) {
            if (rarity.getTier() == tier) {
                return rarity;
            }
        }

        return null;
    }
}
