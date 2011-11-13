package pgDev.bukkit.CommandPointsEssentials;

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
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), 100, plugin)){  // TODO: Change to Config Value
					ply.setGameMode(GameMode.CREATIVE);
					cpAPI.setPoints(ply.getName(), cpAPI.getPoints(ply.getName(), plugin)-100, plugin); // TODO: Change to Config Value
					ply.sendMessage("You are now in creative mode.");
					ply.sendMessage("You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" Points left");
					return true;
				}else{ply.sendMessage("You don't have enough points to run this command.");}
			}else{ply.sendMessage("You don't have an account.");}
		}
		
		// survival command
		if(label.equalsIgnoreCase("survival")){
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), 100, plugin)){  // TODO: Change to Config Value
					ply.setGameMode(GameMode.SURVIVAL);
					cpAPI.setPoints(ply.getName(), cpAPI.getPoints(ply.getName(), plugin)-100, plugin); // TODO: Change to Config Value
					ply.sendMessage("You are now in survival mode.");
					ply.sendMessage("You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" Points left");
					return true;
				}else{ply.sendMessage("You don't have enough points to run this command.");}
			}else{ply.sendMessage("You don't have an account.");}
		}
		
		// day command
		if(label.equalsIgnoreCase("day")){
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), 100, plugin)){  // TODO: Change to Config Value
					ply.getWorld().setTime(0);
					cpAPI.setPoints(ply.getName(), cpAPI.getPoints(ply.getName(), plugin)-100, plugin); // TODO: Change to Config Value
					ply.sendMessage("You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" Points left");
					return true;
				}else{ply.sendMessage("You don't have enough points to run this command.");}
			}else{ply.sendMessage("You don't have an account.");}
		}
		
		// night command
		if(label.equalsIgnoreCase("night")){
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), 100, plugin)){  // TODO: Change to Config Value
					ply.getWorld().setTime(12);
					cpAPI.setPoints(ply.getName(), cpAPI.getPoints(ply.getName(), plugin)-100, plugin); // TODO: Change to Config Value
					ply.sendMessage("You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" Points left");
					return true;
				}else{ply.sendMessage("You don't have enough points to run this command.");}
			}else{ply.sendMessage("You don't have an account.");}
		}
		
		// give command
		if(label.equalsIgnoreCase("give")){
			int amount = 1;  // TODO: Change to Config Value(Standard amount)
			Material item;
			int id;
			
			if(args.length == 0){
				ply.sendMessage("Not enough arguments.");
				return true;
			}
			
			if(args.length == 2){
				try{
					amount = Integer.parseInt(args[1]);
				}catch(NumberFormatException e){
					ply.sendMessage("Invalid Argument");
					return true;
				}
			}
			
			try{
				id = Integer.parseInt(args[0]);
				item = Material.getMaterial(id);
			}catch(NumberFormatException e){
				item = Material.getMaterial(args[0]);
			}
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), 100, plugin)){  // TODO: Change to Config Value
					if(item != null){
						ply.getInventory().addItem(new ItemStack(item,amount));
						cpAPI.setPoints(ply.getName(), cpAPI.getPoints(ply.getName(), plugin)-100, plugin); // TODO: Change to Config Value
						ply.sendMessage("You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" Points left");
						return true;
					}else{ply.sendMessage("Item not found.");}
				}else{ply.sendMessage("You don't have enough points to run this command.");}
			}else{ply.sendMessage("You don't have an account.");}
		}
		
		// tp command
		if(label.equalsIgnoreCase("tp")){
			if(args.length == 0){
				ply.sendMessage("Not enough arguments.");
				return true;
			}
			
			Player otherPly = plugin.getServer().getPlayer(args[0]);
			
			if(cpAPI.hasAccount(ply.getName(), plugin)){
				if(cpAPI.hasPoints(ply.getName(), 100, plugin)){  // TODO: Change to Config Value
					if(otherPly != null){
						ply.teleport(otherPly);
						cpAPI.setPoints(ply.getName(), cpAPI.getPoints(ply.getName(), plugin)-100, plugin); // TODO: Change to Config Value
						ply.sendMessage("You still have: "+cpAPI.getPoints(ply.getName(), plugin)+" Points left");
						return true;
					}else{ply.sendMessage("Player not found.");}
				}else{ply.sendMessage("You don't have enough points to run this command.");}
			}else{ply.sendMessage("You don't have an account.");}
			
		}
		
		return false;
	}

	
}
