package me.choco.relics.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.choco.relics.Relics;
import net.md_5.bungee.api.ChatColor;

public class RelicsCmd implements CommandExecutor{
	
	private Relics plugin;
	public RelicsCmd(Relics plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1){
			if (args[0].equalsIgnoreCase("reload")){
				plugin.reloadConfig();
				plugin.obeliskFile.reloadConfig();
				plugin.sendMessage(sender, ChatColor.GREEN + "Configuration and obelisk file successfully reloaded");
			}
			
			else if (args[0].equalsIgnoreCase("version")){
				sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Version: " + ChatColor.RESET + ChatColor.GRAY  + plugin.getDescription().getVersion());
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Developer / Maintainer: " + ChatColor.RESET + ChatColor.GRAY + "2008Choco");
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Development Page: " + ChatColor.RESET + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/relics");
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Report Bugs To: " + ChatColor.RESET + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/relics/tickets");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
			}
			
			else{ 
				plugin.sendMessage(sender, "Unknown command argument, " + ChatColor.AQUA + args[0]); 
			}
		}else{ plugin.sendMessage(sender, "/relics <reload|version>"); }
		return true;
	}
}