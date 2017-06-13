package com.decimatepvp.hub.server;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.decimatepvp.hub.utils.ItemFactory;

public class ServerManager implements Listener {

	private Inventory inventory;
	private List<Server> servers;
	
	public ServerManager(List<Server> servers){
		this.servers = servers;
		inventory = Bukkit.getServer().createInventory(null, 9, ChatColor.UNDERLINE + "Server Selector");
		generateServerList();
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event){
		if(event.getWhoClicked() instanceof Player){
			Player player = (Player) event.getWhoClicked();
			if(event.getInventory().getTitle().equals(inventory.getTitle())){
				if(isServer(event.getCurrentItem())){
					Server server = getServer(event.getCurrentItem());
					if(server.getUp()){
						player.sendMessage(ChatColor.YELLOW + "Sending you to " + ChatColor.stripColor(server.getName()) + "...");
						server.sendTo(player);
					}else{
						player.sendMessage(ChatColor.RED + "Server " + ChatColor.stripColor(server.getName()) + " is coming soon.");
					}
					player.closeInventory();
				}
				event.setCancelled(true);
			}
		}
	}
	
	public boolean isServer(ItemStack icon){
		return getServer(icon) != null;
	}
	
	private void generateServerList(){
		updateServerList();
		
		ItemStack blank = ItemFactory.createItem(Material.WOOD_BUTTON, " ", null);
		while(inventory.firstEmpty() != -1){
			inventory.setItem(inventory.firstEmpty(), blank);
		}
	}
	
	public void updateServerList(){
		for(Server server : servers){
			server.updateOnlineItemStack();
		}
		inventory.setItem(4, servers.get(0).getIcon());
	}
	
	public Inventory getServerList(){
		return inventory;
	}
	
	public Server getServer(ItemStack icon){
		if(icon == null || icon.getItemMeta() == null){
			return null;
		}
		for(Server server : servers){
			if(server.getIcon().getType().equals(icon.getType()) &&
					server.getIcon().getItemMeta().getDisplayName().equals(icon.getItemMeta().getDisplayName())){
				return server;
			}
		}
		return null;
	}
	
	public Server getServer(String id){
		for(Server server : servers){
			if(server.getId().equals(id)){
				return server;
			}
		}
		return null;
	}
	
	public List<Server> getServers(){
		return servers;
	}
	
}
