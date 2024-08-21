package me.bumblebeee_.morph.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class RegisterEvents {
	
	public static void register(Plugin pl) {
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDamage(pl), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(pl), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(pl), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDeath(pl), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLeave(pl), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new ChangeWorld(pl), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new WeatherChange(), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new RespawnEvent(pl), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityTarget(), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerUndisguise(), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerSwapHandItems(), pl);
		Bukkit.getServer().getPluginManager().registerEvents(new ItemDrop(), pl);
	}
}
