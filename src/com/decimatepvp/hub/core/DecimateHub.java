package com.decimatepvp.hub.core;

import java.util.Arrays;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.decimatepvp.hub.server.Server;
import com.decimatepvp.hub.server.ServerManager;
import com.decimatepvp.hub.user.UserInventory;
import com.decimatepvp.hub.user.UserManager;

public class DecimateHub extends JavaPlugin implements Listener {

	private static DecimateHub instance;
	
	private ServerManager serverManager;
	private UserManager userManager;
	private UserInventory userInventory;
	
	private Location spawn;
	
	public void onEnable(){
		instance = this;
		
		spawn = new Location(Bukkit.getServer().getWorlds().get(0), 0, 100, 0);
		
		userInventory = new UserInventory();
		
		serverManager = new ServerManager(Arrays.asList(
				new Server(ChatColor.LIGHT_PURPLE.toString() + ChatColor.UNDERLINE + "Original Factions", new ItemStack(Material.BOW), "DecimatePVP's original faction server", true)));
		userManager = new UserManager(instance);
		
		registerListeners(serverManager, userManager, this);
	}
	
	private void registerListeners(Listener... listeners){
		for(Listener listener : listeners){
			instance.getServer().getPluginManager().registerEvents(listener, instance);
		}
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent event){
		event.setMotd(ChatColor.LIGHT_PURPLE + "DecimatePVP:\n" + ChatColor.GRAY + "Map 2 release: " + ChatColor.YELLOW + nextMapRelease());
	}
	
	@SuppressWarnings("deprecation")
	public String nextMapRelease(){
		Date now = new Date();
		Date then = new Date(2017-1900, 7-1, 25, 12, 0);
		long time = then.getTime() - now.getTime();
		
		int seconds = (int) ((time / 1000) % 60);
		int minutes = (int) ((time / (1000*60)) % 60);
		int hours   = (int) ((time / (1000*60*60)) % 24);
		int days = (int) ((time / (1000*60*60*24)));
		
		return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
	}
	
	public static DecimateHub getInstance(){
		return instance;
	}
	
	public ServerManager getServerManager(){
		return serverManager;
	}
	
	public UserInventory getUserInventory(){
		return userInventory;
	}
	
	public UserManager getUserManager(){
		return userManager;
	}
	
	public Location getSpawn(){
		return spawn;
	}
	
}
