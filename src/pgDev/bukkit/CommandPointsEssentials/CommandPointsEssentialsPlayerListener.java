package pgDev.bukkit.CommandPointsEssentials;

import org.bukkit.entity.Player;
import org.bukkit.event.player.*;

/**
 * Handle events for all Player related events
 * @author pgDev
 */
public class CommandPointsEssentialsPlayerListener extends PlayerListener {
    private final CommandPointsEssentials plugin;

    public CommandPointsEssentialsPlayerListener(CommandPointsEssentials instance) {
        plugin = instance;
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
    	if (plugin.ctps.containsKey(event.getPlayer().getName())) {
    		Player requester = plugin.getServer().getPlayer(plugin.ctps.get(event.getPlayer().getName()).requesterName);
    		if (requester != null) {
    			requester.sendMessage(event.getPlayer().getName() + " has left the server. Your ctp request has been terminated.");
    		}
    		plugin.ctps.remove(event.getPlayer());
    	}
    }
    
}

