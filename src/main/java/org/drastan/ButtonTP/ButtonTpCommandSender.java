package org.drastan.ButtonTP;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

/**
 * Dispatches Commands for the PhatLoots Plugin
 *
 * @author Sega0795
 */

public class ButtonTpCommandSender implements CommandSender{

    @Override
    public void sendMessage(String string) {
        //ButtonTp.logger.info(string);
    }

    @Override
    public void sendMessage(String[] strings) {
        for (String string: strings) {
            sendMessage(string);
        }
    }

    @Override
    public Server getServer() {
        return ButtonTp.server;
    }

    @Override
    public String getName() {
        return "ButtonWarp";
    }

    @Override
    public boolean isPermissionSet(String string) {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission prmsn) {
        return true;
    }

    @Override
    public boolean hasPermission(String string) {
        return true;
    }

    @Override
    public boolean hasPermission(Permission prmsn) {
        return true;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeAttachment(PermissionAttachment pa) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void recalculatePermissions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public void sendMessage(UUID sender, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(UUID sender, String... messages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spigot spigot() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
