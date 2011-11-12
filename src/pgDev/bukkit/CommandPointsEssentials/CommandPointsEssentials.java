package pgDev.bukkit.CommandPointsEssentials;

import java.io.File;
import java.util.HashMap;

import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.CommandPoints.*;

/**
 * CommandPointsEssentials for Bukkit
 *
 * @author pgDev
 */
public class CommandPointsEssentials extends JavaPlugin {
	// CommandPoints API
	CommandPointsAPI cpAPI;
	
	// Listeners
    private final CommandPointsEssentialsPlayerListener playerListener = new CommandPointsEssentialsPlayerListener(this);
    private final CPECommandListener commandListener = new CPECommandListener(this, cpAPI);
    
    public void onEnable() {

        // Register our events
        PluginManager pm = getServer().getPluginManager();
        
        // Send commands to the executor
        PluginCommand[] commands = {this.getCommand("creative"), 
        							this.getCommand("tp"),
        							this.getCommand("give"),
        							this.getCommand("day"),
        							this.getCommand("night")};
        for (int i=0; i < commands.length; i++) {
        	commands[i].setExecutor(commandListener);
        }
        
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public void onDisable() {
        System.out.println("CommandPointsEssentials disabled!");
    }
    
    private void setupCommandPoints() {
        Plugin commandPoints = this.getServer().getPluginManager().getPlugin("CommandPoints");
        
        if (commandPoints != null) {
            cpAPI = ((CommandPoints)commandPoints).getAPI();
        }
    }
    
}

