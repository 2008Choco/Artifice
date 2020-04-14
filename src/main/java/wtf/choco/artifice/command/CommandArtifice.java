package wtf.choco.artifice.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import wtf.choco.artifice.Artifice;

public class CommandArtifice implements CommandExecutor {

    private final Artifice plugin;

    public CommandArtifice(Artifice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            this.plugin.sendMessage(sender, "/" + label + " <reload|version>");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            this.plugin.reloadConfig();
            this.plugin.sendMessage(sender, ChatColor.GREEN + "Plugin data successfully reloaded");
            return true;
        }

        else if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Version: " + ChatColor.RESET + ChatColor.GRAY + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Developer / Maintainer: " + ChatColor.RESET + ChatColor.GRAY + plugin.getDescription().getAuthors());
            sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Development Page: " + ChatColor.RESET + ChatColor.GRAY + "N/A");
            sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Report Bugs To: " + ChatColor.RESET + ChatColor.GRAY + "N/A");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
            return true;
        }

        this.plugin.sendMessage(sender, "Unknown command argument, " + ChatColor.AQUA + args[0]);
        return true;
    }
}
