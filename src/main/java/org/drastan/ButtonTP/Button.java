package org.drastan.ButtonTP;

import org.bukkit.block.Block;

public class Button {

    String world;
    int x;
    int y;
    int z;
    public boolean takeItems = ButtonTp.defaultTakeItems;
    public int max = ButtonTp.defaultMax;

    /**
     * Constructs a new Button with the given Block
     *
     * @param block The given Block
     * @return The newly created Button
     */
    public Button(Block block) {
        world = block.getWorld().getName();
        x = block.getX();
        y = block.getY();
        z = block.getZ();
    }

    /**
     * Constructs a new Button with the given Block Location data
     *
     * @param world The name of the World
     * @param x The x-coordinate of the Block
     * @param y The y-coordinate of the Block
     * @param z The z-coordinate of the Block
     */
    public Button(String world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns true if the given Block has the same Location data as this Button
     *
     * @param block The given Block
     * @return True if the Location data is the same
     */
    public boolean isBlock(Block block) {
        if (block.getX() != x) {
            return false;
        }

        if (block.getY() != y) {
            return false;
        }

        if (block.getZ() != z) {
            return false;
        }

        return block.getWorld().getName().equals(world);
    }

    /**
     * Returns the String representation of this Button's Location
     * The format of the returned String is as follows
     * world'x'y'z
     *
     * @return The String representation of this Button's Location
     */
    public String getLocationString() {
        return world+"'"+x+"'"+y+"'"+z;
    }

    /**
     * Returns the String representation of this Button
     * The format of the returned String is as follows
     * world'x'y'z'takeItems'max
     *
     * @return The String representation of this Button
     */
    @Override
    public String toString() {
        return world+"'"+x+"'"+y+"'"+z+"'"+takeItems+"'"+max;
    }

    /**
     * Returns the Key for this Button/Player
     * This key is used for the activation times
     *
     * @param player The specified player
     * @return A String in the format ButtonLocation'PlayerName
     */
    public String getKey(String player) {
        return getLocationString() + "'" + player;
    }
}
