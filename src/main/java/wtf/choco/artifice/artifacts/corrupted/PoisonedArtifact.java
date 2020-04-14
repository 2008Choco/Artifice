package wtf.choco.artifice.artifacts.corrupted;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.artifact.CorruptedArtifact;
import wtf.choco.artifice.artifacts.ArtifactType;
import wtf.choco.artifice.artifacts.Rarity;
import wtf.choco.artifice.utils.ItemBuilder;

public class PoisonedArtifact extends CorruptedArtifact {

    private static final ItemStack ITEM = ItemBuilder.of(Material.GHAST_TEAR).name(ChatColor.DARK_PURPLE + "Poisoned Artifact")
            .lore(ChatColor.DARK_GREEN + "A demonic artifact, crafted by witches", ChatColor.DARK_GREEN + "Only the purest of pure may witness its true power")
            .build();

    public PoisonedArtifact(Artifice plugin) {
        super(new NamespacedKey(plugin, "poisoned"), Rarity.EPIC);
    }

    @Override
    public String getName() {
        return "Poisoned Artifact";
    }

    @Override
    public ItemStack getItem() {
        return ITEM;
    }

    @Override
    public void tick(Player player, ItemStack item, int slot, Random random, int partialTick) {
        if (random.nextInt(100) >= 20) {
            return;
        }

        player.sendMessage(ChatColor.DARK_PURPLE + "OOOOOOOOOOO... CORRUPTIONNNNNNN " + ChatColor.MAGIC + "JIUDSDNCJAKNVAYDJJGVHDNAIUV");
    }

    @Override
    public double corruptionPercent() {
        return 2.5;
    }

    @Override
    public boolean canCorrupt(ArtifactType type) {
        return type == ArtifactType.FOSSILIZED;
    }

}
