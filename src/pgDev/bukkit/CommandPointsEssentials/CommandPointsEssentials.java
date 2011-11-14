package pgDev.bukkit.CommandPointsEssentials;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import pgDev.bukkit.CommandPoints.*;

/**
 * CommandPointsEssentials for Bukkit
 *
 * @author pgDev
 */
public class CommandPointsEssentials extends JavaPlugin {
	// CommandPoints API
	CommandPointsAPI cpAPI;
	
	// Permissions Integration
    private static PermissionHandler Permissions;
	
	// Listeners
    private final CommandPointsEssentialsPlayerListener playerListener = new CommandPointsEssentialsPlayerListener(this);
    private CPECommandListener commandListener = new CPECommandListener(this, cpAPI);
    
    // File Locations
    String pluginMainDir = "./plugins/CommandPointsEssentials";
    String pluginConfigLocation = pluginMainDir + "/CommandPointsEssentials.cfg";
    
    // Plugin Configuration
    CPEConfig pluginSettings;
    
    public void onEnable() {
    	// Check for the plugin directory (create if it does not exist)
    	File pluginDir = new File(pluginMainDir);
		if(!pluginDir.exists()) {
			boolean dirCreation = pluginDir.mkdirs();
			if (dirCreation) {
				System.out.println("New CommandPoints Essentials directory created!");
			}
		}
		
    	// Load the Configuration
    	try {
        	Properties preSettings = new Properties();
        	if ((new File(pluginConfigLocation)).exists()) {
        		preSettings.load(new FileInputStream(new File(pluginConfigLocation)));
        		pluginSettings = new CPEConfig(preSettings, this);
        		if (!pluginSettings.upToDate) {
        			pluginSettings.createConfig();
        			System.out.println("CommandPoints Essentials Configuration updated!");
        		}
        	} else {
        		pluginSettings = new CPEConfig(preSettings, this);
        		pluginSettings.createConfig();
        		System.out.println("CommandPoints Essentials Configuration created!");
        	}
        } catch (Exception e) {
        	System.out.println("Could not load CommandPoints Essentials configuration! " + e);
        }

        // Register our events
        PluginManager pm = getServer().getPluginManager();
        
        // Send commands to the executor
        PluginCommand[] commands = {this.getCommand("creative"), 
        							this.getCommand("survival"),
        							this.getCommand("ctp"),
        							this.getCommand("cgive"),
        							this.getCommand("i"),
        							this.getCommand("day"),
        							this.getCommand("night")};
        for (int i=0; i < commands.length; i++) {
        	commands[i].setExecutor(commandListener);
        }
        
        // Integrations turn on!
        setupCommandPoints();
    	setupPermissions();
        
    	// Check if we haven't disabled
    	if (this.isEnabled()) {
    		PluginDescriptionFile pdfFile = this.getDescription();
            System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    	}
    }
    
    public void onDisable() {
        System.out.println("CommandPointsEssentials disabled!");
    }
    
    private void setupCommandPoints() {
        Plugin commandPoints = this.getServer().getPluginManager().getPlugin("CommandPoints");
        
        if (commandPoints != null) {
            cpAPI = ((CommandPoints)commandPoints).getAPI();
        } else {
        	System.out.println("CommandPoints was not found on this server.");
        	//getServer().getPluginManager().disablePlugin(this);
        }
    }
    
    // Permissions Methods
    private void setupPermissions() {
        Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
            if (permissions != null) {
                Permissions = ((Permissions)permissions).getHandler();
            } else {
            }
        }
    }
    
    protected boolean hasPermissions(Player player, String node) {
        if (Permissions != null) {
        	return Permissions.has(player, node);
        } else {
            return player.hasPermission(node);
        }
    }
    
}

