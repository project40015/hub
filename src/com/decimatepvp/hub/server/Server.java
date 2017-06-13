package com.decimatepvp.hub.server;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.decimatepvp.hub.core.DecimateHub;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Server {

	private String name;
	private ItemStack icon;
	private String description;
	private boolean up;
	private String id;
	private int players = 0;
	
	public Server(String name, ItemStack icon, String description, boolean up){
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.up = up;
		
		setupItemStack();
	}

	public Server(String name, ItemStack icon, String description, String id){
		this(name, icon, description, true);
		this.id = id;
	}
	
	private void setupItemStack(){
		ItemMeta im = icon.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + name);
		im.setLore(Arrays.asList(
				"",
				ChatColor.DARK_GRAY + description,
				"",
				(up ? ChatColor.GRAY + "Online: " + ChatColor.GREEN + players : ChatColor.RED + "Coming soon!")));
		icon.setItemMeta(im);
	}
	
	public void updateOnlineItemStack(){
		ItemMeta im = icon.getItemMeta();
		List<String> lore = im.getLore();
		lore.set(3, (up ? ChatColor.GRAY + "Online: " + ChatColor.GREEN + players : ChatColor.RED + "Coming soon!"));
		im.setLore(lore);
		icon.setItemMeta(im);
	}
	
	public int getOnline(){
		return players;
	}
	
	public void setPlayers(int amount){
		this.players = amount;
	}
	
	public void updatePlayers(){
		if(Bukkit.getServer().getOnlinePlayers().size() <= 0){
			return;
		}
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("PlayerCount");
		out.writeUTF(id);
		Iterables.getFirst(Bukkit.getOnlinePlayers(), null).sendPluginMessage(DecimateHub.getInstance(), "BungeeCord", out.toByteArray());
	}
	
	public void sendTo(Player player){
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(id);
		player.sendPluginMessage(DecimateHub.getInstance(), "BungeeCord", out.toByteArray());
	}
	
	public String getName(){
		return name;
	}
	
	public ItemStack getIcon(){
		return icon;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getId(){
		return id;
	}
	
	public boolean getUp(){
		return up;
	}
	
}
