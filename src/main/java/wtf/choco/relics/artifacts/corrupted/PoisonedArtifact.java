package wtf.choco.relics.artifacts.corrupted;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.relics.Relics;
import wtf.choco.relics.api.artifact.CorruptedArtifact;
import wtf.choco.relics.artifacts.ArtifactType;
import wtf.choco.relics.artifacts.Rarity;
import wtf.choco.relics.utils.ItemBuilder;

public class PoisonedArtifact extends CorruptedArtifact {

    private static final ItemStack ITEM = new ItemBuilder(Material.GHAST_TEAR).setName(ChatColor.DARK_PURPLE + "Poisoned Artifact").setLore(Arrays.asList(ChatColor.DARK_GREEN + "A demonic artifact, crafted by witches", ChatColor.DARK_GREEN + "Only the purest of pure may witness its true power")).build();

    public PoisonedArtifact(Relics plugin) {
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
