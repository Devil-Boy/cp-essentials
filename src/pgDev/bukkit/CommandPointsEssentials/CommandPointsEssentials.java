package pgDev.bukkit.CommandPointsEssentials;

import java.io.*;
import java.util.*;

import org.bukkit.entity.Player;
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
    //private CPECommandListener commandListener = new CPECommandListener(this, cpAPI);
    
    // File Locations
    String pluginMainDir = "./plugins/CommandPointsEssentials";
    String pluginConfigLocation = pluginMainDir + "/CommandPointsEssentials.cfg";
    
    // Plugin Configuration
    CPEConfig pluginSettings;
    
    // Whether or not CommandPoints is on the server
    boolean cpLoaded = false;
    
    // The CTP Request Database
    HashMap<String, CTPRequest> ctps = new HashMap<String, CTPRequest>(); // The string is the person the request is sent to.
    
    // /accept vs /caccept
    String cAcceptOr;
    
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

        // Integrations turn on!
        setupCommandPoints();
    	setupPermissions();
        
        // Send commands to the executor
    	CPECommandListener commandListener = new CPECommandListener(this, cpAPI);
        String[] commandList = {"chelp", "creative", "survival", "ctp", "cgive", "i", "day", "night",
        		"spawn", "bed", "accept", "caccept", "reject", "creject", "buyexp"};
        boolean backupAccept = false;
        if (mcMMOinstalled()) {
        	backupAccept = true;
        }
        for (int i=0; i < commandList.length; i++) {
        	try {
        		this.getCommand(commandList[i]).setExecutor(commandListener);
        	} catch (NullPointerException e) {
        		if (commandList[i].equals("i")) {
        			System.out.println("Another plugin is using the /i command. CPE will default to /cgive.");
        		} else if (commandList[i].equals("accept")) {
        			backupAccept = true;
        			System.out.println("Another plugin is using the /accept command. CPE will default to /caccept.");
        		} else if (commandList[i].equals("reject")) {
        			backupAccept = true;
        			System.out.println("Another plugin is using the /reject command. CPE will default to /creject.");
        		} else {
        			System.out.println("Another plugin is using CPE's /" + commandList[i] + " command! CPE will be unable to provide the function which that command supplies.");
        		}
        	}
        }
        // set the return for accept vs caccept
        if (backupAccept) {
        	cAcceptOr = "/caccept or /creject";
        } else {
        	cAcceptOr = "/accept or /reject";
        }
        
        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        
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
        Plugin commandPoints = getServer().getPluginManager().getPlugin("CommandPoints");
        
        if (commandPoints != null) {
        	cpLoaded = true;
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
    
    // Check for mcMMO
    boolean mcMMOinstalled() {
    	Plugin mcMMO = getServer().getPluginManager().getPlugin("mcMMO");
    	if (mcMMO == null) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
}

