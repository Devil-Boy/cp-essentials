package pgDev.bukkit.CommandPointsEssentials;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

/**
 * Handle events for all Player related events
 * @author pgDev
 */
public class CPEPlayerListener implements Listener {
    private final CommandPointsEssentials plugin;
    
    public CPEPlayerListener(CommandPointsEssentials instance) {
        plugin = instance;
    }
    
    @EventHandler
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

