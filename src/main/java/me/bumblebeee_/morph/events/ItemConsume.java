package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ItemConsume implements Listener {

	MorphManager mm = new MorphManager();
	Plugin pl = null;
	public ItemConsume(Plugin plugin) {
		pl = plugin;
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e) {
		if (e.getItem().getType() == Material.ROTTEN_FLESH) {
			if (pl.getConfig().getString("zombie-eat") == "true") {
				Player p = e.getPlayer();
				if (!Morph.using.containsKey(p.getUniqueId()))
					return;

				File userFile = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
				String using = mm.getUsing(p);
				if (using.equalsIgnoreCase("zombie")) {
					ItemStack remove = new ItemStack(e.getItem().getType(), 1);
					p.getInventory().removeItem(remove);
					p.setFoodLevel(p.getFoodLevel() + 6);
					e.setCancelled(true);
				}
			}
		}
	}

}
