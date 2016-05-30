package me.choco.relics.utils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.choco.relics.Relics;
import me.choco.relics.artifacts.Artifact;
import me.choco.relics.utils.ArtifactManager;

public class ArtifactCmd implements CommandExecutor {
	
	private Relics plugin;
	private ArtifactManager manager;
	public ArtifactCmd(Relics plugin){
		this.plugin = plugin;
		this.manager = plugin.getArtifactManager();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage("You cannot run this command from the console");
			return true;
		}
		Player player = (Player) sender;
		
		if (args.length >= 1){
			if (args[0].equalsIgnoreCase("list")){
				StringBuilder artifacts = new StringBuilder();
				for (Artifact artifact : manager.getArtifactRegistry().values())
					artifacts.append(artifact.getName()).append(", ");
				
				String message = artifacts.toString().substring(0, artifacts.toString().length() - 2);
				plugin.sendMessage(player, "Registered artifacts: ");
				player.sendMessage(ChatColor.GRAY + message);
			}

			else if (args[0].equalsIgnoreCase("give")){
				if (args.length >= 2){
					System.out.println(args[1]);
					String artifactName = args[1].replace("_", " ");
					Artifact artifact = manager.getArtifact(artifactName);
					if (artifact == null){
						plugin.sendMessage(player, "Unrecognized artifact, " + ChatColor.AQUA + artifactName);
						return true;
					}
					
					player.getInventory().addItem(artifact.getItem());
				}else{
					plugin.sendMessage(player, "/artifact give <artifact_name>");
				}
			}
			
			else{ plugin.sendMessage(player, "Unknown parameter, " + ChatColor.AQUA + args[0]); }
		}else{ plugin.sendMessage(player, "/artifact <list|give>"); }
		
		return true;
	}
}