package wtf.choco.relics.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import wtf.choco.relics.Relics;
import wtf.choco.relics.api.artifact.Artifact;
import wtf.choco.relics.utils.ArtifactUtils;

public class ArtifactCmd implements CommandExecutor {

    private final Relics plugin;

    public ArtifactCmd(Relics plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot run this command from the console");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            this.plugin.sendMessage(player, "/artifact <list|give>");
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            StringBuilder artifacts = new StringBuilder();
            for (Artifact artifact : plugin.getArtifactManager().getArtifacts()) {
                artifacts.append(artifact.getRarity().getColor()).append(artifact.getKey()).append(ChatColor.GRAY).append(", ");
            }

            String message = artifacts.toString().substring(0, artifacts.toString().length() - 2);
            this.plugin.sendMessage(player, "Registered artifacts: ");
            player.sendMessage(ChatColor.GRAY + message);
            return true;
        }

        else if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 2) {
                this.plugin.sendMessage(player, "/artifact give <artifact_name>");
                return true;
            }

            Artifact artifact = plugin.getArtifactManager().getArtifact(args[1].toLowerCase());
            if (artifact == null) {
                this.plugin.sendMessage(player, "Unrecognized artifact, " + ChatColor.AQUA + args[1].toLowerCase());
                return true;
            }

            player.getInventory().addItem(ArtifactUtils.createItemStack(artifact));
            return true;
        }

        this.plugin.sendMessage(player, "Unknown parameter, " + ChatColor.AQUA + args[0]);
        return true;
    }
}
