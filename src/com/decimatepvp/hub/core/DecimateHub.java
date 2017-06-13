package com.decimatepvp.hub.core;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.decimatepvp.hub.server.Server;
import com.decimatepvp.hub.server.ServerManager;
import com.decimatepvp.hub.user.UserInventory;
import com.decimatepvp.hub.user.UserManager;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class DecimateHub extends JavaPlugin implements Listener, PluginMessageListener {

	private static DecimateHub instance;
	
	private ServerManager serverManager;
	private UserManager userManager;
	private UserInventory userInventory;
//	private long then;	
	
	private Location spawn;
	
//	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(){
		instance = this;
		
	    this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
	    
		spawn = new Location(Bukkit.getServer().getWorlds().get(0), -5, 121, -274);
		spawn.setYaw(45);
		
		userInventory = new UserInventory();
		
		serverManager = new ServerManager(Arrays.asList(
				new Server(ChatColor.LIGHT_PURPLE.toString() + ChatColor.UNDERLINE + "Original Factions", new ItemStack(Material.BOW), "DecimatePVP's original faction server", "factions")));
		userManager = new UserManager(instance);
		
		registerListeners(serverManager, userManager, this);
		
		startServerCount();
		
//		then = (new Date(2017-1900, 6-1, 16, 18-2, 0)).getTime();
	}
	
	private void registerListeners(Listener... listeners){
		for(Listener listener : listeners){
			instance.getServer().getPluginManager().registerEvents(listener, instance);
		}
	}
	
	private void startServerCount(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

			@Override
			public void run() {
				for(Server server : serverManager.getServers()){
					if(server.getUp()){
						server.updatePlayers();
					}
				}
				serverManager.updateServerList();
			}
			
		}, 20, 20);
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent event){
//		String color = randomColor();
//		event.setMotd(randomSpacing(color, 21) + ChatColor.translateAlternateColorCodes('&', " &c&lD&6&lE&e&lC&a&lI&b&lM&9&lA&5&lT&c&lE&f&lPVP&r&7: ") + randomSpacing(color, 17) + "\n" + ChatColor.GRAY + "Season II open: " + ChatColor.YELLOW + nextMapRelease());
		event.setMaxPlayers(0);
		event.setMotd(ChatColor.RED + "Decimate" + ChatColor.GRAY + ": Hub server (lobby)");
	}
	
//	private String randomSpacing(String color, int length){
//		String str = "";
//		boolean last = false;
//		for(int i = 0; i < length; i++){
//			if(Math.random() <= .2 && !last){
//				ChatColor col = randomBlue();
//				if(color.equals("green")){
//					col = randomGreen();
//				}else if(color.equals("red")){
//					col = randomRed();
//				}else if(color.equals("purple")){
//					col = randomPurple();
//				}else if(color.equals("gray")){
//					col = randomGray();
//				}
//				str += col + "*";
//				last = true;
//			}else{
//				str += " ";
//				last = false;
//			}
//		}
//		return str;
//	}
//	
//	private String randomColor(){
//		double d = Math.random();
//		if(d < .2){
//			return "blue";
//		}else if(d < .4){
//			return "green";
//		}else if(d < .6){
//			return "red";
//		}else if(d < .8){
//			return "purple";
//		}else{
//			return "gray";
//		}
//	}
//	
//	private ChatColor randomBlue(){
//		double d = Math.random();
//		if(d < .25){
//			return ChatColor.BLUE;
//		}else if(d < .5){
//			return ChatColor.AQUA;
//		}else if(d < .75){
//			return ChatColor.DARK_BLUE;
//		}else{
//			return ChatColor.DARK_AQUA;
//		}
//	}
//	
//	private ChatColor randomGray(){
//		double d = Math.random();
//		if(d < .25){
//			return ChatColor.WHITE;
//		}else if(d < .50){
//			return ChatColor.BLACK;
//		}else if(d < .75){
//			return ChatColor.DARK_GRAY;
//		}else{
//			return ChatColor.GRAY;
//		}
//	}
//	
//	private ChatColor randomRed(){
//		double d = Math.random();
//		if(d < .25){
//			return ChatColor.RED;
//		}else if(d < .50){
//			return ChatColor.YELLOW;
//		}else if(d < .75){
//			return ChatColor.GOLD;
//		}else{
//			return ChatColor.DARK_RED;
//		}
//	}
//	
//	private ChatColor randomGreen(){
//		double d = Math.random();
//		if(d < .333){
//			return ChatColor.DARK_GREEN;
//		}else if(d < .666){
//			return ChatColor.YELLOW;
//		}else{
//			return ChatColor.GREEN;
//		}
//	}
//	
//	private ChatColor randomPurple(){
//		double d = Math.random();
//		if(d < .5){
//			return ChatColor.LIGHT_PURPLE;
//		}else{
//			return ChatColor.DARK_PURPLE;
//		}
//	}
	
//	public String nextMapRelease(){
//		//PST
//		if(System.currentTimeMillis() >= then){
//			return "open!";
//		}
//		long time = then - System.currentTimeMillis();
//		
//		int seconds = (int) ((time / 1000) % 60);
//		int minutes = (int) ((time / (1000*60)) % 60);
//		int hours   = (int) ((time / (1000*60*60)) % 24);
//		int days = (int) ((time / (1000*60*60*24)));
//		
//		return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
//	}
	
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
	
	@EventHandler
	public void onChange(WeatherChangeEvent event){
		event.setCancelled(true);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message){
		if(!channel.equals("BungeeCord")){
			return;
		}
		ByteArrayDataInput input = ByteStreams.newDataInput(message);
		String subchannel = input.readUTF();
		if(subchannel.equalsIgnoreCase("PlayerCount")){
			String server = input.readUTF();
			int count = input.readInt();
			serverManager.getServer(server).setPlayers(count);
		}
	}
	
}
