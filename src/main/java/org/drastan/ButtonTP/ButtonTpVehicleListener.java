package org.drastan.ButtonTP;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

/**
 * Listens for interactions with Warps
 *
 * @author Sega0795
 */
public class ButtonTpVehicleListener implements Listener {

    /**
     * Activates Warps when Players click a linked button
     *
     * @param event The PlayerInteractEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent event) {
        Block block = event.getTo().getBlock();
        if (block.getType() != Material.DETECTOR_RAIL) {
            return;
        }

        //Return if the Block is not part of an existing Warp
        Warp warp = ButtonTp.findWarp(block);
        if (warp == null) {
            return;
        }
        /*
        Entity entity = event.getVehicle().getPassenger();
        if (!(entity instanceof Player)) {
            return;
        }

        //Return if the Player does not have permission to use Warps
        Player player = (Player) entity;
        if (!ButtonWarp.hasPermission(player, "use")) {
            player.sendMessage(ButtonWarpMessages.permission);
            return;
        }
        */
        final List<Entity> entity = event.getVehicle().getPassengers();
        final Vehicle vehicle = event.getVehicle();

        //Eject the Player
        for (Entity ent : entity) {
        	ent.leaveVehicle();
        	
            Location location = vehicle.getLocation();
            location.setX(warp.x);
            location.setY(warp.y + 1);
            location.setZ(warp.z);
            if (!location.getChunk().isLoaded()) {
                location.getChunk().load();
            }

            vehicle.teleport(location);

            Location loc = ent.getLocation();
            location .setYaw(loc.getYaw());
            location.setPitch(loc.getPitch());
            ent.teleport(location);
            
            ButtonTp.server.getScheduler().runTaskLater(ButtonTp.plugin, new Runnable() {
                @Override
                public void run() {
                    vehicle.addPassenger(ent);
                }
            }, 10L);
		}

    }
	
}
