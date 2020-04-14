package wtf.choco.artifice.listeners.obelisk;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.ObeliskManager;
import wtf.choco.artifice.api.obelisk.ObeliskQuality;
import wtf.choco.artifice.api.obelisk.ObeliskState;
import wtf.choco.artifice.obelisk.qualities.ObeliskQualities;

public class ObeliskDebugQualityListener implements Listener {

    private final Artifice plugin;

    public ObeliskDebugQualityListener(Artifice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPunchObeliskWithTestQuality(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        ObeliskManager manager = plugin.getObeliskManager();
        Block block = event.getClickedBlock();
        ObeliskState obeliskState = manager.getObelisk(block);
        if (obeliskState == null || !obeliskState.hasQuality(ObeliskQualities.DEBUG_QUALITY)) {
            return;
        }

        Player player = event.getPlayer();
        this.plugin.sendMessage(player, "This obelisk has the debug quality! Here is the list of qualities:");
        for (ObeliskQuality quality : obeliskState.getQualities()) {
            player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.YELLOW + quality.getKey());

            for (String descriptionLine : quality.getDescription()) {
                player.sendMessage("    | " + ChatColor.GRAY + descriptionLine);
            }
        }
    }

}
