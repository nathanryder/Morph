package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.*;
import me.bumblebeee_.morph.morphs.Morph;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class PlayerJoin implements Listener { 

	ManaManager mana = new ManaManager();
	Messages msgs = new Messages();

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
			Main.pl.getLogger().info("[Morph] Creating new user file " + p.getUniqueId() + " for user " + p.getName());
	        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
	        fileConfig.createSection("Mobs");
	    }

		YamlConfiguration c = YamlConfiguration.loadConfiguration(userFile);
		boolean sound = c.getBoolean("sounds");
		if (c.getString("sounds") == null)
			sound = true;

		if (!sound)
			Main.getMorphManager().soundDisabled.add(p.getUniqueId());


		if (Main.health) {
			p.resetMaxHealth();
		}
		if (!p.hasPermission("morph.fly")) {
			p.setAllowFlight(false);
			p.setFlying(false);
		}
	    for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
        mana.getManaPlayers().put(p.getUniqueId(), 100.0);

        //Give morph item
		FileConfiguration conf = Main.pl.getConfig();
		if (conf.getBoolean("morphItem.giveOnJoin")) {
			ItemStack item = Main.getMorphManager().getMorphItem();
			int slot = conf.getInt("morphItem.slot");
			boolean give = true;


			for (ItemStack i : p.getInventory()) {
				if (item.isSimilar(i))
					give = false;
			}

			if (give)
				p.getInventory().setItem(slot, item);
		}

		if (Main.pl.getConfig().getBoolean("persistMorphs")) {
			String last = c.getString("lastMorph");
			if (last != null) {
				String[] data = last.split(":");
				String typeStr = data[0].toLowerCase();
				Morph type = Main.getMorphManager().getMorphType(typeStr);
				boolean isBaby = data.length > 1 && data[1].equals("baby");

				new BukkitRunnable() {
					@Override
					public void run() {
						Main.getMorphManager().morphPlayer(p, type, false, isBaby);
						cancel();
					}
				}.runTaskTimer(Main.pl, Main.pl.getConfig().getInt("loginMorphDelay"), 20);

				c.set("lastMorph", null);
				try {
					c.save(userFile);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}

	}

}
