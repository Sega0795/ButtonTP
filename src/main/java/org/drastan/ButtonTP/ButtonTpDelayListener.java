package org.drastan.ButtonTP;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;


/**
 * Checks if warping Players leave their current Block.
 */
public class ButtonTpDelayListener implements Listener{

    static HashMap<Player, BukkitTask> warpers = new HashMap<Player, BukkitTask>();

    @EventHandler (ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (warpers.containsKey(player) && !event.getTo().getBlock().equals(event.getFrom().getBlock())) {
            warpers.get(player).cancel();
            warpers.remove(player);
            if (!ButtonTpMessages.cancel.isEmpty()) {
                player.sendMessage(ButtonTpMessages.cancel);
            }
        }
    }
	
}
