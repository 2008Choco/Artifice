package wtf.choco.relics.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import wtf.choco.relics.api.artifact.Artifact;

/**
 * Various utilities related to artifacts
 *
 * @author Parker Hawke - 2008Choco
 */
public final class ArtifactUtils {

    private ArtifactUtils() { }

    /**
     * Apply lore to an artifact's item and return the item.
     *
     * @param artifact the artifact to get the item for
     *
     * @return the resulting ItemStack
     */
    public static ItemStack createItemStack(Artifact artifact) {
        ItemStack item = artifact.getItem().clone();
        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        } else {
            lore.add(" ");
        }

        lore.add(ChatColor.WHITE + "Type: " + ChatColor.GRAY + artifact.getType().getName());
        lore.add(ChatColor.WHITE + "Rarity: " + artifact.getRarity().getDisplayName());

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

}
