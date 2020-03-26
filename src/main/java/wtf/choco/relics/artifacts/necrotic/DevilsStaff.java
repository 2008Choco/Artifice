package wtf.choco.relics.artifacts.necrotic;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.relics.Relics;
import wtf.choco.relics.api.artifact.NecroticArtifact;
import wtf.choco.relics.artifacts.Rarity;
import wtf.choco.relics.utils.ItemBuilder;

public class DevilsStaff extends NecroticArtifact {

    private static final ItemStack ITEM = ItemBuilder.of(Material.BLAZE_ROD).name(ChatColor.RED + "Devil's Staff").build();

    public DevilsStaff(Relics plugin) {
        super(new NamespacedKey(plugin, "devils_staff"), Rarity.MYTHICAL);
    }

    @Override
    public double discoveryPercent() {
        return 100;
    }

    @Override
    public String getName() {
        return "Devil's Staff";
    }

    @Override
    public ItemStack getItem() {
        return ITEM;
    }

    @Override
    public void tick(Player player, ItemStack item, int slot, Random random, int partialTick) {
        player.sendMessage(ChatColor.RED + "effect.devils_staff.execute");
    }

}
