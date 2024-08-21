package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.ManaManager;
import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import me.bumblebeee_.morph.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;

public class PlayerJoin implements Listener { 

	UpdateChecker uc = new UpdateChecker();
	ManaManager mana = new ManaManager();
	MorphManager morph = new MorphManager();

	Plugin pl = null;
	public PlayerJoin(Plugin plugin) {
		pl = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
	    Player p = e.getPlayer();
	    File userFile = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
	    if (!userFile.exists()) {
	        try {
	        	userFile.createNewFile();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        Bukkit.getServer().getLogger().info("[Morph] Creating new user file " + p.getUniqueId() + " for user " + p.getName());
	        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
	        fileConfig.createSection("Mobs");
	    }

		if (p.isOp()) {
			if (UpdateChecker.update) {
				p.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "A update is available for Morph!");
				p.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "The latest version is " + uc.getUpdatedVersion("8846") + ". You are running version " + uc.getVersion());
			}
		}

		YamlConfiguration c = YamlConfiguration.loadConfiguration(userFile);
		boolean sound = c.getBoolean("sounds");
		if (c.getString("sounds") == null)
			sound = true;

		if (!sound)
			MorphManager.soundDisabled.add(p.getUniqueId());


		if (Morph.health) {
			p.setHealthScale(20.0);
			p.setMaxHealth(20.0);
		}
		if (!p.hasPermission("morph.fly")) {
			p.setAllowFlight(false);
			p.setFlying(false);
		}
	    for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
        mana.getManaPlayers().put(p.getUniqueId(), 100.0);

        //Give morph item
		FileConfiguration conf = Morph.pl.getConfig();
		if (conf.getBoolean("morphItem.giveOnJoin")) {
			ItemStack item = morph.getMorphItem();
			int slot = conf.getInt("morphItem.slot");
			boolean give = true;


			for (ItemStack i : p.getInventory()) {
				if (item.isSimilar(i))
					give = false;
			}

			if (give)
				p.getInventory().setItem(slot, item);
		}
	}

}
