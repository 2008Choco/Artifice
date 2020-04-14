package wtf.choco.artifice.listeners.obelisk;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.ObeliskManager;
import wtf.choco.artifice.api.obelisk.ObeliskState;

public class ObeliskEngraveListener implements Listener {

    private final Artifice plugin;

    public ObeliskEngraveListener(Artifice plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEngraveObelisk(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (event.getMaterial() != Material.NAME_TAG || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        ObeliskManager manager = plugin.getObeliskManager();
        Block block = event.getClickedBlock();
        ObeliskState obeliskState = manager.getObelisk(block);
        if (obeliskState == null) {
            return;
        }

        obeliskState.setEngraving(item.getItemMeta().getDisplayName());

        Player player = event.getPlayer();
        item.setAmount(item.getAmount() - 1);
        if (event.getHand() == EquipmentSlot.HAND) {
            player.getInventory().setItemInMainHand(item);
        } else {
            player.getInventory().setItemInOffHand(item);
        }

        this.plugin.sendMessage(event.getPlayer(), "You have changed the engraving on this obelisk. It now reads: " + obeliskState.getEngraving());
        event.setUseInteractedBlock(Result.DENY);
        event.setUseItemInHand(Result.ALLOW);
    }

}
