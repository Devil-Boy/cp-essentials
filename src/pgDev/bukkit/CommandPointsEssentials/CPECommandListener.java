package pgDev.bukkit.CommandPointsEssentials;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		
		Player ply;
		
		if(sender instanceof Player){
			ply = (Player)sender;
		}else{
			sender.sendMessage("This command can only be run by a player.");
			return true;
		}
		
		// creative command
		if(label.equalsIgnoreCase("creative")){
			if (plugin.hasPermissions(ply, "CPE.creative")) {
				ply.setGameMode(GameMode.CREATIVE);
				//ply.sendMessage(ChatColor.BLUE + "You are now in creative mode.");
				return true;
			} else {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
		}
		
		// survival command
		if(label.equalsIgnoreCase("survival")){
			if (plugin.hasPermissions(ply, "CPE.survival")) {
				ply.setGameMode(GameMode.SURVIVAL);
				//ply.sendMessage(ChatColor.BLUE + "You are now in survival mode.");
				return true;
			} else {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
		}
		
		// day command
		if(label.equalsIgnoreCase("day")){
			// Check for permissions
			if (!plugin.hasPermissions(ply, "CPE.day")) {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), plugin.pluginSettings.commandCosts.get("day"), plugin)){
					ply.getWorld().setTime(0);
					cpAPI.removePoints(ply.getName(), plugin.pluginSettings.commandCosts.get("day"), "Changed time to day", plugin);
					ply.sendMessage(ChatColor.BLUE + "You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" points left");
					return true;
				} else {
					ply.sendMessage(ChatColor.RED + "You don't have enough points to run this command.");
					return true;
				}
			} else {
				ply.sendMessage(ChatColor.RED + "You don't have an account.");
				return true;
			}
		}
		
		// night command
		if(label.equalsIgnoreCase("night")){
			// Check for permissions
			if (!plugin.hasPermissions(ply, "CPE.night")) {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), plugin.pluginSettings.commandCosts.get("night"), plugin)){
					ply.getWorld().setTime(14000);
					cpAPI.removePoints(ply.getName(), plugin.pluginSettings.commandCosts.get("day"), "Changed time to night", plugin);
					ply.sendMessage(ChatColor.BLUE + "You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" points left");
					return true;
				} else {
					ply.sendMessage(ChatColor.RED + "You don't have enough points to run this command.");
					return true;
				}
			} else {
				ply.sendMessage(ChatColor.RED + "You don't have an account.");
				return true;
			}
		}
		
		// give command
		if(label.equalsIgnoreCase("cgive") || label.equalsIgnoreCase("i")){
			int amount = 1;  // TODO: Change to Config Value(Standard amount)
			Material item;
			int id;
			
			if(args.length == 0){
				ply.sendMessage(ChatColor.RED + "Not enough arguments.");
				return true;
			}
			
			if(args.length == 2){
				try{
					amount = Integer.parseInt(args[1]);
				}catch(NumberFormatException e){
					ply.sendMessage(ChatColor.RED + "Invalid Argument");
					return true;
				}
			}
			
			try{
				id = Integer.parseInt(args[0]);
				item = Material.getMaterial(id);
			}catch(NumberFormatException e){
				item = Material.getMaterial(args[0]);
			}
			
			if (plugin.hasPermissions(ply, "CPE.cgive")) {
				if(item != null){
					ply.getInventory().addItem(new ItemStack(item,amount));
					return true;
				} else {
					ply.sendMessage(ChatColor.RED + "Item not found.");
					return true;
				}
			} else {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
			}
		}
		
		// tp command
		if(label.equalsIgnoreCase("ctp")){
			// Check for permissions
			if (!plugin.hasPermissions(ply, "CPE.ctp")) {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
			
			if(args.length == 0){
				ply.sendMessage(ChatColor.RED + "Not enough arguments.");
				return true;
			}
			
			Player otherPly = plugin.getServer().getPlayer(args[0]);
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), plugin.pluginSettings.commandCosts.get("ctp"), plugin)){
					if(otherPly != null){
						ply.teleport(otherPly);
						cpAPI.removePoints(ply.getName(), plugin.pluginSettings.commandCosts.get("ctp"), "Teleported to " + otherPly.getName(), plugin);
						ply.sendMessage(ChatColor.BLUE + "You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" Points left");
						return true;
					}else{ply.sendMessage(ChatColor.RED + "Player not found.");}
				}else{ply.sendMessage(ChatColor.RED + "You don't have enough points to run this command.");}
			}else{ply.sendMessage(ChatColor.RED + "You don't have an account.");}
			
		}
		
		return false;
	}

	
}
