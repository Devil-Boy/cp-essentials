package pgDev.bukkit.CommandPointsEssentials;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;

/**
 * Handle events for all Player related events
 * @author pgDev
 */
public class CPEPlayerListener extends PlayerListener {
    private final CommandPointsEssentials plugin;
    
    public CPEPlayerListener(CommandPointsEssentials instance) {
        plugin = instance;
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
    	// Remove teleport requests
    	if (plugin.ctps.containsKey(event.getPlayer().getName())) {
    		Player requester = plugin.getServer().getPlayer(plugin.ctps.get(event.getPlayer().getName()).requesterName);
    		if (requester != null) {
    			requester.sendMessage(ChatColor.RED + event.getPlayer().getName() + " has left the server. Your ctp request has been terminated.");
    		}
    		plugin.ctps.remove(event.getPlayer().getName());
    	}
    	
    	// Can he go back?
    	if (!plugin.pluginSettings.backAfterQuit && plugin.deathPoints.containsKey(event.getPlayer().getName())) {
    		plugin.deathPoints.remove(event.getPlayer().getName());
    	}
    }
    
}

