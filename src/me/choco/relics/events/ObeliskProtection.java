package me.choco.relics.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.choco.relics.Relics;
import me.choco.relics.api.events.player.PlayerDestroyObeliskEvent;
import me.choco.relics.structures.Obelisk;
import me.choco.relics.utils.ObeliskManager;
import net.md_5.bungee.api.ChatColor;

public class ObeliskProtection implements Listener{
	
	private Relics plugin;
	private ObeliskManager manager;
	public ObeliskProtection(Relics plugin) {
		this.plugin = plugin;
		this.manager = plugin.getObeliskManager();
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event){
		Block block = event.getBlock();
		if (!manager.isObeliskComponent(block)) return;
		
		Player player = event.getPlayer();
		Obelisk obelisk = manager.getObelisk(block);
		if (!obelisk.getOwner().getUniqueId().equals(player.getUniqueId())){
			event.setCancelled(true);
			plugin.sendMessage(player, "You are not allowed to disrupt the aura of " + obelisk.getOwner().getName() + "'s obelisk");
			return;
		}
		
		PlayerDestroyObeliskEvent pdoe = new PlayerDestroyObeliskEvent(player, obelisk);
		Bukkit.getPluginManager().callEvent(pdoe);
		
		plugin.sendMessage(player, ChatColor.RED + "The aura of the obelisk has been disrupted!");
		manager.unregisterObelisk(obelisk);
		player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 0);
	}
	
	@EventHandler
	public void onBreakObeliskBlock(BlockBreakEvent event){
		Block block = event.getBlock();
		if (!manager.isObeliskComponent(block)) return;
		
		Player player = event.getPlayer();
		Obelisk obelisk = manager.getObelisk(block);
		if (!obelisk.getOwner().getUniqueId().equals(player.getUniqueId())){
			event.setCancelled(true);
			plugin.sendMessage(player, "You are not allowed to disrupt the aura of " + obelisk.getOwner().getName() + "'s obelisk");
			return;
		}
		
		PlayerDestroyObeliskEvent pdoe = new PlayerDestroyObeliskEvent(player, obelisk);
		Bukkit.getPluginManager().callEvent(pdoe);
		
		plugin.sendMessage(player, ChatColor.RED + "The aura of the obelisk has been disrupted!");
		manager.unregisterObelisk(obelisk);
		player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 0);
	}
}