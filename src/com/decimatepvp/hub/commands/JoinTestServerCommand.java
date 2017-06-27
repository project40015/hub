package com.decimatepvp.hub.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.decimatepvp.hub.core.DecimateHub;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class JoinTestServerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(arg0 instanceof Player && arg0.isOp()){
			if(arg3.length == 0){
				send((Player)arg0);
			}else{
				try{
					Player player = Bukkit.getServer().getPlayer(arg3[0]);
					send(player);
				}catch(Exception ex){
					arg0.sendMessage(ChatColor.RED + "Player not found.");
				}
			}
		}else{
			arg0.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
		}
		
		return false;
	}
	
	private void send(Player player){
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF("test");
		player.sendPluginMessage(DecimateHub.getInstance(), "BungeeCord", out.toByteArray());
	}

	
	
}
