package com.decimatepvp.hub.user;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.decimatepvp.hub.utils.ItemFactory;

public class UserInventory {

	private ItemStack serverSelector, cosmeticSelector;
	
	public UserInventory(){
		createItems();
	}
	
	private void createItems(){
		serverSelector = ItemFactory.createItem(Material.COMPASS, ChatColor.GOLD + "Server Selector " + ChatColor.GRAY + "(Right Click)",
				Arrays.asList("", ChatColor.DARK_GRAY + "View the network's servers"));
		cosmeticSelector = ItemFactory.createItem(Material.BONE, ChatColor.GOLD + "Cosmetics " + ChatColor.GRAY + "(Right Click)",
				Arrays.asList("", ChatColor.DARK_GRAY + "View the hub's cosmetics"));
	}
	
	public void apply(Player player){
		player.getInventory().clear();
		player.getInventory().setItem(0, serverSelector);
		player.getInventory().setItem(1, cosmeticSelector);
	}
	
	public ItemStack getServerSelector(){
		return serverSelector;
	}
	
	public ItemStack getCosmeticSelector(){
		return cosmeticSelector;
	}
	
}
