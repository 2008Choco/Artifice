package wtf.choco.relics.listeners.obelisk;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import wtf.choco.relics.Relics;
import wtf.choco.relics.api.ObeliskManager;
import wtf.choco.relics.api.events.player.PlayerDestroyObeliskEvent;
import wtf.choco.relics.api.obelisk.Obelisk;
import wtf.choco.relics.api.obelisk.ObeliskState;
import wtf.choco.relics.api.obelisk.ObeliskStructure;
import wtf.choco.relics.utils.SoundData;

public class ObeliskProtectionListener implements Listener {

    private final Relics plugin;

    public ObeliskProtectionListener(Relics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDisruptObeliskStructure(BlockPlaceEvent event) {
        ObeliskManager manager = plugin.getObeliskManager();
        Block block = event.getBlock();
        ObeliskState obeliskState = manager.getObelisk(block, true);
        if (obeliskState == null || !obeliskState.getObelisk().hasStrongAura()) {
            return;
        }

        Obelisk obelisk = obeliskState.getObelisk();
        ObeliskStructure structure = obeliskState.getStructure();
        if (structure.hasIgnorePredicate() && structure.getIgnorePredicate().test(block)) {
            return;
        }

        Player player = event.getPlayer();
        if (!obeliskState.isOwner(player)) {
            event.setCancelled(true);
            this.plugin.sendMessage(player, "You are not allowed to disrupt the aura of " + obeliskState.getOwner().getName() + "'s obelisk");
            return;
        }

        PlayerDestroyObeliskEvent pdoe = new PlayerDestroyObeliskEvent(player, obeliskState);
        Bukkit.getPluginManager().callEvent(pdoe);

        obelisk.onDisrupted(obeliskState, player);
        manager.removeObelisk(obeliskState);

        SoundData sound = obelisk.getDisruptionSound(obeliskState);
        if (sound != null) {
            sound.play(player.getLocation());
        }

        String disruptionMessage = obelisk.getDisruptionMessage(obeliskState, ThreadLocalRandom.current());
        if (disruptionMessage != null) {
            this.plugin.sendMessage(player, disruptionMessage);
        }
    }

    @EventHandler
    public void onDestroyObeliskBlock(BlockBreakEvent event) {
        ObeliskManager manager = plugin.getObeliskManager();
        Block block = event.getBlock();
        ObeliskState obeliskState = manager.getObelisk(block, true);
        if (obeliskState == null) {
            return;
        }

        Obelisk obelisk = obeliskState.getObelisk();
        ObeliskStructure structure = obeliskState.getStructure();
        if (structure.hasIgnorePredicate() && structure.getIgnorePredicate().test(block)) {
            return;
        }

        if (!obelisk.hasStrongAura()) {
            BoundingBox bounds = obeliskState.getBounds();
            Vector min = bounds.getMin();
            Vector blockPos = block.getLocation().toVector();

            int relativeX = blockPos.getBlockX() - min.getBlockX();
            int relativeY = blockPos.getBlockY() - min.getBlockY();
            int relativeZ = blockPos.getBlockZ() - min.getBlockZ();

            if (bounds.contains(blockPos) && obeliskState.getStructure().get(relativeX, relativeY, relativeZ).isAir()) {
                return;
            }
        }

        Player player = event.getPlayer();
        if (!obeliskState.isOwner(player)) {
            event.setCancelled(true);
            this.plugin.sendMessage(player, "You are not allowed to disrupt the aura of " + obeliskState.getOwner().getName() + "'s obelisk");
            return;
        }

        PlayerDestroyObeliskEvent pdoe = new PlayerDestroyObeliskEvent(player, obeliskState);
        Bukkit.getPluginManager().callEvent(pdoe);

        obelisk.onDisrupted(obeliskState, player);
        manager.removeObelisk(obeliskState);

        SoundData sound = obelisk.getDisruptionSound(obeliskState);
        if (sound != null) {
            sound.play(player.getLocation());
        }

        String disruptionMessage = obelisk.getDisruptionMessage(obeliskState, ThreadLocalRandom.current());
        if (disruptionMessage != null) {
            this.plugin.sendMessage(player, disruptionMessage);
        }
    }
}
