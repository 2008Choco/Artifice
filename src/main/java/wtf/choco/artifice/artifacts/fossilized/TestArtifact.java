package wtf.choco.artifice.artifacts.fossilized;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.artifact.FossilizedArtifact;
import wtf.choco.artifice.artifacts.Rarity;
import wtf.choco.artifice.utils.ItemBuilder;

public class TestArtifact extends FossilizedArtifact {

    private static final ItemStack ITEM = ItemBuilder.of(Material.GOLDEN_APPLE).name(ChatColor.GOLD + "Test Artifact").build();
    private static final PotionEffect EFFECT_ABSORPTION = PotionEffectType.ABSORPTION.createEffect(21, 9);

    public TestArtifact(Artifice plugin) {
        super(new NamespacedKey(plugin, "test"), Rarity.LEGENDARY);
    }

    @Override
    public String getName() {
        return "Test Artifact";
    }

    @Override
    public ItemStack getItem() {
        return ITEM.clone();
    }

    @Override
    public double discoveryPercent() {
        return 0.5;
    }

    @Override
    public void tick(Player player, ItemStack item, int slot, Random random, int partialTick) {
        if (partialTick == 0) {
            player.addPotionEffect(EFFECT_ABSORPTION);
        }
    }

    @Override
    public void worldTick(Item item, int partialTick) {
        if (partialTick % 10 == 0) {
            item.getWorld().spawnParticle(Particle.FLAME, item.getLocation().add(0, 0.5, 0), 10, 0.5, 0.5, 0.5, 0.0);
        }
    }

}
