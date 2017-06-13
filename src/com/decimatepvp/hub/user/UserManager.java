package com.decimatepvp.hub.user;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import com.decimatepvp.hub.core.DecimateHub;

public class UserManager implements Listener {

	private DecimateHub hub;
	
	public UserManager(DecimateHub hub){
		this.hub = hub;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		event.setJoinMessage("");
		event.getPlayer().teleport(hub.getSpawn());
		event.getPlayer().setHealth(20);
		event.getPlayer().setFoodLevel(20);
		event.getPlayer().setExp(0);
		event.getPlayer().setLevel(0);
		event.getPlayer().setGameMode(GameMode.ADVENTURE);
		hub.getUserInventory().apply(event.getPlayer());
		event.getPlayer().sendMessage(ChatColor.GRAY + "Welcome to " + ChatColor.LIGHT_PURPLE + "DecimatePVP" + ChatColor.GRAY + "!");
		event.getPlayer().setAllowFlight(true);
		event.getPlayer().setPlayerListName(ChatColor.GRAY + event.getPlayer().getName());
		hideAll(event.getPlayer());
	}
	
	private void hideAll(Player p){
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			if(p.canSee(player)){
				p.hidePlayer(player);
			}
			if(player.canSee(p)){
				player.hidePlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		if(!event.getPlayer().isOp()){
			event.getPlayer().sendMessage(ChatColor.RED + "Chat in the hub is disabled.");
			event.setCancelled(true);
		}else{
			Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GRAY + ": " + event.getMessage());
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event){
		event.setQuitMessage("");
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event){
		event.setLeaveMessage("");
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onFly(PlayerToggleFlightEvent event){
		if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
			return;
		}
		Vector v = event.getPlayer().getLocation().getDirection();
		v = v.setY(Math.abs(v.getY())*1.4);
		event.getPlayer().setVelocity(v.multiply(1.3));
		event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 1);
		if(event.getPlayer().isFlying()){
			event.getPlayer().setFlying(false);
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChange(PlayerGameModeChangeEvent event){
		event.getPlayer().setAllowFlight(true);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event){
		if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEscape(PlayerMoveEvent event){
		Location clone = event.getTo().clone();
		clone.setY(DecimateHub.getInstance().getSpawn().getY());
		if(clone.distance(DecimateHub.getInstance().getSpawn()) > 150){
			event.getPlayer().teleport(DecimateHub.getInstance().getSpawn());
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(hub.getUserInventory().getCosmeticSelector().equals(event.getItem())){
			event.getPlayer().sendMessage(ChatColor.RED + "Cosmetics coming soon.");
			return;
		}
		if(hub.getUserInventory().getServerSelector().equals(event.getItem())){
			event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.SHEEP_SHEAR, 1, 1);
			event.getPlayer().openInventory(hub.getServerManager().getServerList());
			return;
		}
	}
	
}
