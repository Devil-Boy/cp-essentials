package pgDev.bukkit.CommandPointsEssentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import pgDev.bukkit.CommandPoints.CommandPoints;
import pgDev.bukkit.CommandPoints.CommandPointsAPI;

public class CPECommandListener implements CommandExecutor{
	CommandPointsEssentials plugin;
	CommandPointsAPI cpAPI;

	public CPECommandListener(CommandPointsEssentials pluginI, CommandPointsAPI api) {
		plugin = pluginI;
		cpAPI = api;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return false;
	}

}
