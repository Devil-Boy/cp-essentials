package pgDev.bukkit.CommandPointsEssentials;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		
		// CP Check
		if ((label.equalsIgnoreCase("chelp") || label.equalsIgnoreCase("day") || label.equalsIgnoreCase("night") || label.equalsIgnoreCase("ctp") || label.equalsIgnoreCase("spawn") || label.equalsIgnoreCase("bed") || label.equalsIgnoreCase("buyexp")) && !plugin.cpLoaded) {
			ply.sendMessage(ChatColor.RED + "This command is only availible on servers where CommandPoints is installed.");
			return true;
		}
		
		// chelp command
		if(label.equalsIgnoreCase("chelp")){
			if (plugin.hasPermissions(ply, "CPE.chelp")) {
				if (!cpAPI.hasAccount(ply.getName(), plugin)) {
					ply.sendMessage(ChatColor.RED + "You do not have an account. Rejoin the server to create one.");
					return true;
				}
				
				// Start listing commands
				ply.sendMessage(ChatColor.GREEN + "CPE commands available to you:");
				LinkedList<String> availableCommands = new LinkedList<String>();
				if (plugin.hasPermissions(ply, "CPE.creative")) {
					availableCommands.add("/creative");
				}
				if (plugin.hasPermissions(ply, "CPE.survival")) {
					availableCommands.add("/survival");
				}
				if (plugin.hasPermissions(ply, "CPE.cgive")) {
					availableCommands.add("/cgive or /i");
				}
				if (plugin.hasPermissions(ply, "CPE.ctp")) {
					if (plugin.hasPermissions(ply, "CPE.ctp.free")) {
						availableCommands.add("/ctp [-r] (free)");
					} else {
						availableCommands.add("/ctp");
					}
				}
				if (plugin.hasPermissions(ply, "CPE.day")) {
					if (plugin.hasPermissions(ply, "CPE.day.free")) {
						availableCommands.add("/day (free)");
					} else {
						availableCommands.add("/day");
					}
				}
				if (plugin.hasPermissions(ply, "CPE.night")) {
					if (plugin.hasPermissions(ply, "CPE.night.free")) {
						availableCommands.add("/night (free)");
					} else {
						availableCommands.add("/night");
					}
				}
				if (plugin.hasPermissions(ply, "CPE.spawn")) {
					if (plugin.hasPermissions(ply, "CPE.spawn.free")) {
						availableCommands.add("/spawn (free)");
					} else {
						availableCommands.add("/spawn");
					}
				}
				if (plugin.hasPermissions(ply, "CPE.bed")) {
					if (plugin.hasPermissions(ply, "CPE.bed.free")) {
						availableCommands.add("/bed (free)");
					} else {
						availableCommands.add("/bed");
					}
				}
				if (plugin.hasPermissions(ply, "CPE.buyexp")) {
					if (plugin.hasPermissions(ply, "CPE.buyexp.free")) {
						availableCommands.add("/buyexp (free)");
					} else {
						availableCommands.add("/buyexp");
					}
				}
				String outCommandList = "";
				for (String leCommand : availableCommands) {
					if (outCommandList.equals("")) {
						outCommandList = leCommand;
					} else {
						outCommandList =outCommandList + ", " + leCommand;
					}
				}
				ply.sendMessage(outCommandList);
				return true;
			}	
			else {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
		}
		
		// creative command
		if(label.equalsIgnoreCase("creative")){
			if (plugin.hasPermissions(ply, "CPE.creative")) {
				ply.setGameMode(GameMode.CREATIVE);
				//ply.sendMessage(ChatColor.BLUE + "You are now in creative mode.");
				return true;
			}	
			else {
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
			
			// For free
			if (plugin.hasPermissions(ply, "CPE.day.free")) {
				ply.getWorld().setTime(0);
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
			
			// For free
			if (plugin.hasPermissions(ply, "CPE.night.free")) {
				ply.getWorld().setTime(14000);
				return true;
			}
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), plugin.pluginSettings.commandCosts.get("night"), plugin)){
					ply.getWorld().setTime(14000);
					cpAPI.removePoints(ply.getName(), plugin.pluginSettings.commandCosts.get("night"), "Changed time to night", plugin);
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
				ply.sendMessage(ChatColor.GREEN + "Usage: /" + label + " <item> [amount]");
				ply.sendMessage(ChatColor.GREEN + "Note: Underscores are part of the names of certain items.");
				return true;
			}
			
			if(args.length == 2){
				try{
					amount = Integer.parseInt(args[1]);
				}catch(NumberFormatException e){
					ply.sendMessage(ChatColor.RED + "The amount you supplied was not a valid number.");
					return true;
				}
			}
			
			try{
				id = Integer.parseInt(args[0]);
				item = Material.getMaterial(id);
			}catch(NumberFormatException e){
				item = Material.getMaterial(args[0].toUpperCase());
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
		
		// ctp command
		if(label.equalsIgnoreCase("ctp")){
			// Check for permissions
			if (!plugin.hasPermissions(ply, "CPE.ctp")) {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
			
			if(args.length == 0){
				ply.sendMessage(ChatColor.GREEN + "Usage /" + label + " [-r] <otherplayer>");
				return true;
			}
			
			Player otherPly;
			boolean toTheRequester = false;
			if (args[0].equals("-r")) {
				if (args.length < 2) {
					ply.sendMessage(ChatColor.RED + "You failed to specify the other player.");
					return true;
				} else {
					otherPly = plugin.getServer().getPlayer(args[1]);
				}
				toTheRequester = true;
			} else {
				otherPly = plugin.getServer().getPlayer(args[0]);
			}
			
			if(plugin.hasPermissions(ply, "CPE.ctp.free")) {
				if(otherPly != null) {
					if (plugin.ctps.containsKey(otherPly.getName())) {
						ply.sendMessage(ChatColor.RED + otherPly.getName() + " must still respond to another request.");
						return true;
					}
					plugin.ctps.put(otherPly.getName(), new CTPRequest(ply.getName(), toTheRequester));
					if (toTheRequester) {
						otherPly.sendMessage(ChatColor.BLUE + ply.getName() + " wishes for you to teleport to them. Type: " + plugin.cAcceptOr);
					} else {
						otherPly.sendMessage(ChatColor.BLUE + ply.getName() + " wishes to teleport to you. Type: " + plugin.cAcceptOr);
					}
					ply.sendMessage(ChatColor.BLUE + "Your request has been sent to " + otherPly.getName() + ".");
				} else{
					ply.sendMessage(ChatColor.RED + "Player not found.");
				}
				return true;
			}
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), plugin.pluginSettings.commandCosts.get("ctp"), plugin)){
					if(otherPly != null) {
						if (plugin.ctps.containsKey(otherPly.getName())) {
							ply.sendMessage(ChatColor.RED + otherPly.getName() + " must still respond to another request.");
							return true;
						}
						plugin.ctps.put(otherPly.getName(), new CTPRequest(ply.getName(), toTheRequester));
						if (toTheRequester) {
							otherPly.sendMessage(ChatColor.BLUE + ply.getName() + " wishes for you to teleport to them. Type: " + plugin.cAcceptOr);
						} else {
							otherPly.sendMessage(ChatColor.BLUE + ply.getName() + " wishes to teleport to you. Type: " + plugin.cAcceptOr);
						}
						ply.sendMessage(ChatColor.BLUE + "Your request has been sent to " + otherPly.getName() + ".");
					} else{
						ply.sendMessage(ChatColor.RED + "Player not found.");
					}
				}else{
					ply.sendMessage(ChatColor.RED + "You don't have enough points to run this command.");
				}
			}else{
				ply.sendMessage(ChatColor.RED + "You don't have an account.");
			}
			return true;
			
		}
		
		// accept command
		if (label.equalsIgnoreCase("accept") || label.equalsIgnoreCase("caccept")) {
			if (plugin.ctps.containsKey(ply.getName())) {
				Player requester = plugin.getServer().getPlayer(plugin.ctps.get(ply.getName()).requesterName);
				if (requester == null) { // Check if requester is gone
					ply.sendMessage(ChatColor.RED + plugin.ctps.get(ply.getName()).requesterName + " is no longer on the server. Request terminated.");
					plugin.ctps.remove(ply.getName());
					return true;
				}
				
				if (!plugin.hasPermissions(requester, "CPE.ctp.free") && !cpAPI.hasPoints(requester.getName(), plugin.pluginSettings.commandCosts.get("ctp"), plugin)) { // Check for points
					ply.sendMessage(ChatColor.RED + requester.getName() + " no longer has enough points to teleport. Request terminated.");
					plugin.ctps.remove(ply.getName());
					return true;
				}
				
				if (plugin.ctps.get(ply.getName()).toRequester) {
					ply.teleport(requester);
					if (!plugin.hasPermissions(requester, "CPE.ctp.free")) {
						cpAPI.removePoints(requester.getName(), plugin.pluginSettings.commandCosts.get("ctp"), "Teleported " + ply.getName() + " to self", plugin);
					}
				} else {
					requester.teleport(ply);
					if (!plugin.hasPermissions(requester, "CPE.ctp.free")) {
						cpAPI.removePoints(requester.getName(), plugin.pluginSettings.commandCosts.get("ctp"), "Teleported to " + ply.getName(), plugin);
					}
				}
				ply.sendMessage(ChatColor.BLUE + "Accepted!");
				plugin.ctps.remove(ply.getName());
				if (!plugin.hasPermissions(requester, "CPE.ctp.free")) {
					requester.sendMessage(ChatColor.BLUE + "You now have " + cpAPI.getPoints(requester.getName(), plugin) + " points.");
				}
			}
			return true;
		}
		
		// reject command
		if (label.equalsIgnoreCase("reject") || label.equalsIgnoreCase("creject")) {
			if (plugin.ctps.containsKey(ply.getName())) {
				Player requester = plugin.getServer().getPlayer(plugin.ctps.get(ply.getName()).requesterName);
				if (requester != null) { // Check if requester is gone
					requester.sendMessage(ChatColor.BLUE + ply.getName() + " rejected your request.");
				}
				ply.sendMessage(ChatColor.BLUE + "Rejected!");
				plugin.ctps.remove(ply.getName());
			}
			return true;
		}
		
		// spawn command
		if (label.equalsIgnoreCase("spawn")) {
			// Check for permissions
			if (!plugin.hasPermissions(ply, "CPE.spawn")) {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
			
			if(plugin.hasPermissions(ply, "CPE.spawn.free")) {
				ply.teleport(ply.getWorld().getSpawnLocation());
				return true;
			}
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), plugin.pluginSettings.commandCosts.get("spawn"), plugin)){
					ply.teleport(ply.getWorld().getSpawnLocation());
					cpAPI.removePoints(ply.getName(), plugin.pluginSettings.commandCosts.get("spawn"), "Teleported to spawn", plugin);
					ply.sendMessage(ChatColor.BLUE + "You still have: " + cpAPI.getPoints(ply.getName(), plugin) + " points left");
				}else{
					ply.sendMessage(ChatColor.RED + "You don't have enough points to run this command.");
				}
			}else{
				ply.sendMessage(ChatColor.RED + "You don't have an account.");
			}
			return true;
		}
		
		// bed command
		if (label.equalsIgnoreCase("bed")) {
			// Check for permissions
			if (!plugin.hasPermissions(ply, "CPE.bed")) {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
			
			if(plugin.hasPermissions(ply, "CPE.bed.free")) {
				Location playerBed = ply.getBedSpawnLocation();
				if (playerBed == null) {
					ply.sendMessage(ChatColor.RED + "Either you have not slept in a bed yet, or your current bed spawn is invalid.");
					return true;
				}
				ply.teleport(playerBed);
				return true;
			}
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), plugin.pluginSettings.commandCosts.get("bed"), plugin)){
					Location playerBed = ply.getBedSpawnLocation();
					if (playerBed == null) {
						ply.sendMessage(ChatColor.RED + "Either you have not slept in a bed yet, or your current bed spawn is invalid.");
						return true;
					}
					ply.teleport(playerBed);
					cpAPI.removePoints(ply.getName(), plugin.pluginSettings.commandCosts.get("bed"), "Teleported to bed", plugin);
					ply.sendMessage(ChatColor.BLUE + "You still have: " + cpAPI.getPoints(ply.getName(), plugin) + " points left");
				}else{
					ply.sendMessage(ChatColor.RED + "You don't have enough points to run this command.");
				}
			}else{
				ply.sendMessage(ChatColor.RED + "You don't have an account.");
			}
			return true;
		}
		
		
		// buyexp command
		if (label.equalsIgnoreCase("buyexp")) {
			// Check for permissions
			if (!plugin.hasPermissions(ply, "CPE.buyexp")) {
				ply.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}
			
			if(plugin.hasPermissions(ply, "CPE.bed.free")) {
				ply.setLevel(ply.getLevel() + 1);
				return true;
			}
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), plugin.pluginSettings.commandCosts.get("buyexp"), plugin)){
					ply.setLevel(ply.getLevel() + 1);
					cpAPI.removePoints(ply.getName(), plugin.pluginSettings.commandCosts.get("buyexp"), "Teleported to bed", plugin);
					ply.sendMessage(ChatColor.BLUE + "You still have: " + cpAPI.getPoints(ply.getName(), plugin) + " points left");
				}else{
					ply.sendMessage(ChatColor.RED + "You don't have enough points to run this command.");
				}
			}else{
				ply.sendMessage(ChatColor.RED + "You don't have an account.");
			}
			return true;
		}
		
		return false;
	}

	
}
