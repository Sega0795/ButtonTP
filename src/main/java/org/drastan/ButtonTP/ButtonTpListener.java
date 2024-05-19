package org.drastan.ButtonTP;

import java.util.HashSet;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;


/**
 * Listens for interactions with Warps
 *
 * @author Sega0795
 * 

 */
public class ButtonTpListener implements Listener{

    static int delay;
    private static HashSet<String> antiSpam = new HashSet<String>();

    /**
     * Activates Warps when Players click a linked button
     *
     * @param event The PlayerInteractEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        //Return if the Event was arm flailing
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        Action action = event.getAction();
        final Player player = event.getPlayer();

        //Return if the Block is not a switch
        Material type = block.getType();
        switch (type) {
        case LEVER: //Fall through
        case STONE_BUTTON: //Fall through
        case POLISHED_BLACKSTONE_BUTTON:
        case WARPED_BUTTON:
        case CRIMSON_BUTTON:
        case BAMBOO_BUTTON:
        case CHERRY_BUTTON:
        case MANGROVE_BUTTON:
        case DARK_OAK_BUTTON:
        case ACACIA_BUTTON:
        case JUNGLE_BUTTON:
        case BIRCH_BUTTON:
        case SPRUCE_BUTTON:
        case OAK_BUTTON: 
            switch (action) {
            case LEFT_CLICK_BLOCK: break;
            case RIGHT_CLICK_BLOCK: break;
            default: return;
            }
            break;

        case TRIPWIRE:
            //Find Tripwire Hook
            Block temp = block;
            //Check North
            while (temp.getType() == Material.TRIPWIRE) {
                temp = temp.getRelative(BlockFace.NORTH);
            }
            if (temp.getType() == Material.TRIPWIRE_HOOK
                    && ButtonTp.findWarp(temp) != null) {
                block = temp;
                break;
            }
            //Check East
            while (temp.getType() == Material.TRIPWIRE) {
                temp = temp.getRelative(BlockFace.EAST);
            }
            if (temp.getType() == Material.TRIPWIRE_HOOK
                    && ButtonTp.findWarp(temp) != null) {
                block = temp;
                break;
            }
            //Check South
            while (temp.getType() == Material.TRIPWIRE) {
                temp = temp.getRelative(BlockFace.SOUTH);
            }
            if (temp.getType() == Material.TRIPWIRE_HOOK
                    && ButtonTp.findWarp(temp) != null) {
                block = temp;
                break;
            }
            //Check West
            while (temp.getType() == Material.TRIPWIRE) {
                temp = temp.getRelative(BlockFace.WEST);
            }
            if (temp.getType() == Material.TRIPWIRE_HOOK
                    && ButtonTp.findWarp(temp) != null) {
                block = temp;
                break;
            }
            //Fall through
        case POLISHED_BLACKSTONE_PRESSURE_PLATE: 
        case STONE_PRESSURE_PLATE:
        case WARPED_PRESSURE_PLATE:
        case CRIMSON_PRESSURE_PLATE:
        case BAMBOO_PRESSURE_PLATE: 
        case CHERRY_PRESSURE_PLATE: 
        case MANGROVE_PRESSURE_PLATE: 
        case DARK_OAK_PRESSURE_PLATE: 
        case ACACIA_PRESSURE_PLATE: 
        case JUNGLE_PRESSURE_PLATE:
        case BIRCH_PRESSURE_PLATE: 
        case SPRUCE_PRESSURE_PLATE: 
        case OAK_PRESSURE_PLATE:
        case HEAVY_WEIGHTED_PRESSURE_PLATE:
        case LIGHT_WEIGHTED_PRESSURE_PLATE:
            if (action.equals(Action.PHYSICAL)) {
                break;
            } else {
                return;
            }

        default: return;
        }

        if (ButtonTpDelayListener.warpers.containsKey(player)) {
            return;
        }

        //Return if the Block is not part of an existing Warp
        final Warp warp = ButtonTp.findWarp(block);
        if (warp == null) {
            return;
        }

        switch (type) {
        case POLISHED_BLACKSTONE_PRESSURE_PLATE: 
        case STONE_PRESSURE_PLATE:
        case WARPED_PRESSURE_PLATE:
        case CRIMSON_PRESSURE_PLATE:
        case BAMBOO_PRESSURE_PLATE: 
        case CHERRY_PRESSURE_PLATE: 
        case MANGROVE_PRESSURE_PLATE: 
        case DARK_OAK_PRESSURE_PLATE: 
        case ACACIA_PRESSURE_PLATE: 
        case JUNGLE_PRESSURE_PLATE:
        case BIRCH_PRESSURE_PLATE: 
        case SPRUCE_PRESSURE_PLATE: 
        case OAK_PRESSURE_PLATE:
        case HEAVY_WEIGHTED_PRESSURE_PLATE:
        case LIGHT_WEIGHTED_PRESSURE_PLATE:
            Block playerBlock = player.getLocation().getBlock();
            if (!block.equals(playerBlock)
                    || antiSpam.contains(player.getName()
                    + '@' + playerBlock.getLocation().toString())) {
                event.setCancelled(true);
                return;
            } else {
                break;
            }
        default: break;
        }

        //Return if the Player does not have permission to use Warps
        if (!ButtonTp.hasPermission(player, "use")) {
            player.sendMessage(ButtonTpMessages.cannotUseWarps);
            event.setCancelled(true);
            return;
        }

        //Cancel the event if the Warp was not successfully activated
        final Button button = warp.findButton(block);
        if (!warp.canActivate(player, button)) {
            event.setCancelled(true);
            final String key = player.getName() + '@'
                    + player.getLocation().getBlock().getLocation().toString();
            antiSpam.add(key);
            ButtonTp.server.getScheduler().scheduleSyncDelayedTask(ButtonTp.plugin, new Runnable() {
                @Override
                public void run() {
                    antiSpam.remove(key);
                }
            }, 100L);
            return;
        }

        if (warp.world == null) {
            warp.activate(player, button);
            return;
        }

        //Delay Teleporting
        BukkitTask teleTask = ButtonTp.server.getScheduler().runTaskLater(ButtonTp.plugin, new Runnable() {
                @Override
                public void run() {
                    warp.activate(player, button);
                    if (delay > 0) {
                        ButtonTpDelayListener.warpers.remove(player);
                    }
                }
            }, 20L * delay);

        if (delay > 0) {
            ButtonTpDelayListener.warpers.put(player, teleTask);
            if (!ButtonTpMessages.delay.isEmpty()) {
                player.sendMessage(ButtonTpMessages.delay);
            }
        }
    }

    /**
     * Only allows admins to break Blocks that are linked to Warps
     *
     * @param event The BlockBreakEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!ButtonTpCommand.LINKABLE.contains(block.getType())) {
            return;
        }

        //Return if the Block is not linked to a Warp
        Warp warp = ButtonTp.findWarp(block);
        if (warp == null) {
            return;
        }

        //Cancel the event if it was the Block was not broken by a Player
        Player player = event.getPlayer();
        if (player == null) {
            event.setCancelled(true);
            return;
        }

        //Cancel the event if the Player does not have the admin node
        if (!ButtonTp.hasPermission(player, "admin")) {
            player.sendMessage(ButtonTpMessages.permission);
            event.setCancelled(true);
        }
    }
	
}
