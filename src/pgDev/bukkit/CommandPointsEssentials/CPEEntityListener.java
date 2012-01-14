package pgDev.bukkit.CommandPointsEssentials;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;

public class CPEEntityListener extends EntityListener {
	private final CommandPointsEssentials plugin;
	
	public CPEEntityListener(CommandPointsEssentials pluginI) {
		plugin = pluginI;
	}
	
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (plugin.hasPermissions(player, "CPE.back")) {
				plugin.deathPoints.put(player.getName(), player.getLocation());
			}
		}
	}
}
