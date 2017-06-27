package com.decimatepvp.hub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.decimatepvp.hub.core.DecimateHub;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class JoinFactionsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player){
			Player player = (Player) arg0;
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF("factions");
			player.sendPluginMessage(DecimateHub.getInstance(), "BungeeCord", out.toByteArray());
		}
		return false;
	}

}
