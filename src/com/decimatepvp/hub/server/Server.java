package com.decimatepvp.hub.server;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Server {

	private String name;
	private ItemStack icon;
	private String description;
	private boolean up;
	private String id;
	private int players = 10;
	
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
	
	public void updatePlayers(){
		//TODO
		players = 10;
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
