package pgDev.CommandPointsEssentials;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * CommandPointsEssentials block listener
 * @author pgDev
 */
public class CommandPointsEssentialsBlockListener extends BlockListener {
    private final CommandPointsEssentials plugin;

    public CommandPointsEssentialsBlockListener(final CommandPointsEssentials plugin) {
        this.plugin = plugin;
    }

    //put all Block related code here
}
