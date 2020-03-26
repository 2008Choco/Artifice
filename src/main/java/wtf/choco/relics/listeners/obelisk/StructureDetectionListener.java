package wtf.choco.relics.listeners.obelisk;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import wtf.choco.relics.Relics;
import wtf.choco.relics.api.ObeliskManager;
import wtf.choco.relics.api.events.player.PlayerCreateObeliskEvent;
import wtf.choco.relics.api.obelisk.Obelisk;
import wtf.choco.relics.api.obelisk.ObeliskState;
import wtf.choco.relics.api.obelisk.ObeliskStructure;
import wtf.choco.relics.utils.SoundData;

public class StructureDetectionListener implements Listener {

    private final Relics plugin;

    public StructureDetectionListener(Relics plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onActivateObelisk(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ObeliskManager manager = plugin.getObeliskManager();
        ObeliskState obeliskState = manager.getObelisk(block);

        if (obeliskState != null) {
            if (obeliskState.hasEngraving() && event.useInteractedBlock() != Result.DENY) {
                this.plugin.sendMessage(player, "You observe the strange structure. You notice an engraving that reads, \"" + obeliskState.getObelisk().getEngraving(obeliskState) + ChatColor.GRAY + "\"");
            }

            return;
        }

        obeliskLoop:
        for (Obelisk obelisk : manager.getRegisteredObelisks()) {
            for (StructureRotation rotation : StructureRotation.values()) {
                ObeliskStructure structure = obelisk.getStructure(rotation);
                Location obeliskLocation = block.getLocation().subtract(structure.getFormationX(), structure.getFormationY(), structure.getFormationZ());

                if (!structure.matches(obeliskLocation, obelisk.hasStrongAura())) {
                    continue;
                }

                Set<Block> components = new HashSet<>();

                for (int x = 0; x < structure.getSizeX(); x++) {
                    for (int y = 0; y < structure.getSizeY(); y++) {
                        for (int z = 0; z < structure.getSizeZ(); z++) {
                            obeliskLocation.add(x, y, z);

                            if (!structure.get(x, y, z).isAir()) {
                                components.add(obeliskLocation.getBlock());
                            }

                            obeliskLocation.subtract(x, y, z);
                        }
                    }
                }

                obeliskState = obelisk.createObelisk(player, block.getWorld(), structure.asBoundsFrom(obeliskLocation), rotation, components);
                PlayerCreateObeliskEvent pcoe = new PlayerCreateObeliskEvent(player, obeliskState);
                Bukkit.getPluginManager().callEvent(pcoe);

                manager.addObelisk(obeliskState);

                SoundData sound = obelisk.getFormationSound(obeliskState);
                if (sound != null) {
                    sound.play(player.getLocation());
                }

                String formationMessage = obeliskState.getObelisk().getFormationMessage(obeliskState, ThreadLocalRandom.current());
                if (formationMessage != null) {
                    this.plugin.sendMessage(player, formationMessage);
                }

                break obeliskLoop;
            }
        }
    }

}
