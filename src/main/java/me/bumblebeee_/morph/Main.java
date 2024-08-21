package me.bumblebeee_.morph;

import lombok.Getter;
import me.bumblebeee_.morph.events.RegisterEvents;
import me.bumblebeee_.morph.morphs.*;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

	public static Plugin pl = null;

	Messages m = new Messages();
    ManaManager mana = new ManaManager();

	public static boolean health;
	public static HashMap<UUID, String> using = new HashMap<>();
	public static List<UUID> undisguiseBuffer = new ArrayList<>();
	public static HashMap<UUID, String> respawnBuffer = new HashMap<>();

	public static @Getter MorphManager morphManager;

	//	TODO
	//	Look into changing morph menu title breaking click events
	//
	//	Fixed some mobs not having abilities
	//	Fixed some mobs not displaying in the morph menu
	//	Fixed some mobs having incorrect head in morph menu
	//	Fixed player morphs not appearing in the morph menu
	//	Fixed some morphs not having a sound
	//	Fixed formatting on /morph list
	//	Fixed some /morph info descriptions not accurate
	//	Added some abilities to some mobs
	//	Added list to blacklist certain players from being morphed into

	public void onEnable() {
		pl = this;

        setupFiles();
        setupCommands();

        morphManager = new MorphManager();
		morphManager.registerMorph(new BlazeMorph());
		morphManager.registerMorph(new SkeletonHorseMorph());
		morphManager.registerMorph(new HorseMorph());
		morphManager.registerMorph(new OcelotMorph());
		morphManager.registerMorph(new WolfMorph());
		morphManager.registerMorph(new PigMorph());
		morphManager.registerMorph(new CowMorph());
		morphManager.registerMorph(new BatMorph());
		morphManager.registerMorph(new ChickenMorph());
		morphManager.registerMorph(new CreeperMorph());
		morphManager.registerMorph(new EndermanMorph());
		morphManager.registerMorph(new EndermiteMorph());
		morphManager.registerMorph(new GhastMorph());
		morphManager.registerMorph(new GuardianMorph());
		morphManager.registerMorph(new IronGolemMorph());
		morphManager.registerMorph(new MagmaCubeMorph());
		morphManager.registerMorph(new MushroomCowMorph());
		morphManager.registerMorph(new MuleMorph());
		morphManager.registerMorph(new SheepMorph());
		morphManager.registerMorph(new SilverfishMorph());
		morphManager.registerMorph(new SkeletonMorph());
		morphManager.registerMorph(new SlimeMorph());
		morphManager.registerMorph(new SnowmanMorph());
		morphManager.registerMorph(new SquidMorph());
		morphManager.registerMorph(new WitherSkeletonMorph());
		morphManager.registerMorph(new VillagerMorph());
		morphManager.registerMorph(new VillagerZombieMorph());
		morphManager.registerMorph(new WitchMorph());
		morphManager.registerMorph(new WitherMorph());
		morphManager.registerMorph(new ZombieMorph());
		morphManager.registerMorph(new ZombifiedPiglinMorph());
		morphManager.registerMorph(new HuskMorph());
		morphManager.registerMorph(new ShulkerMorph());
		morphManager.registerMorph(new PolarBearMorph());
		morphManager.registerMorph(new LlamaMorph());
		morphManager.registerMorph(new VindicatorMorph());
		morphManager.registerMorph(new EvokerMorph());
		morphManager.registerMorph(new VexMorph());
		morphManager.registerMorph(new GiantMorph());
		morphManager.registerMorph(new EnderdragonMorph());
		morphManager.registerMorph(new DonkeyMorph());
		morphManager.registerMorph(new SpiderMorph());
		morphManager.registerMorph(new CaveSpiderMorph());
		morphManager.registerMorph(new IllusionerMorph());
		morphManager.registerMorph(new ParrotMorph());
		morphManager.registerMorph(new DolphinMorph());
		morphManager.registerMorph(new DrownedMorph());
		morphManager.registerMorph(new CodMorph());
		morphManager.registerMorph(new SalmonMorph());
		morphManager.registerMorph(new PufferFishMorph());
		morphManager.registerMorph(new TropicalFishMorph());
		morphManager.registerMorph(new PhantomMorph());
		morphManager.registerMorph(new TurtleMorph());
		morphManager.registerMorph(new BeeMorph());
		morphManager.registerMorph(new RabbitMorph());
		morphManager.registerMorph(new CatMorph());
		morphManager.registerMorph(new FoxMorph());
		morphManager.registerMorph(new PandaMorph());
		morphManager.registerMorph(new HoglinMorph());
		morphManager.registerMorph(new StriderMorph());
		morphManager.registerMorph(new ZoglinMorph());
		morphManager.registerMorph(new PillagerMorph());
		morphManager.registerMorph(new PiglinBruteMorph());
		morphManager.registerMorph(new PiglinMorph());
		morphManager.registerMorph(new RavagerMorph());
		morphManager.registerMorph(new GoatMorph());
		morphManager.registerMorph(new GlowSquidMorph());
		morphManager.registerMorph(new AxolotlMorph());
		morphManager.registerMorph(new AllayMorph());
		morphManager.registerMorph(new FrogMorph());
		morphManager.registerMorph(new TadpoleMorph());
		morphManager.registerMorph(new WardenMorph());
		morphManager.registerMorph(new StrayMorph());

		m.setup();
		Runnables.potionEffects();

        RegisterEvents.register(this);
		if (getConfig().getBoolean("morph-power")) {
			Runnables.morphPower();
		}
		mana.setup();
        Runnables.mobSounds();

        checkReload();
		health = !getConfig().getBoolean("disableHealthSystem");

		Metrics metrics = new Metrics(this);

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (getMorphManager().soundDisabled.contains(p.getUniqueId()))
				continue;

			File f = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
			YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

			boolean sound = c.getString("sounds") == null || c.getBoolean("sounds");
			if (!sound)
				getMorphManager().soundDisabled.add(p.getUniqueId());
		}
	}

	public void onDisable() {
		if (Main.pl.getConfig().getBoolean("persistMorphs")) {
			//store last used morph
			for (UUID uuid : Main.using.keySet()) {
				String mob = Main.using.get(uuid);
				File userFile = new File(pl.getDataFolder() + "/UserData/" + uuid + ".yml");
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
				fileConfig.set("lastMorph", mob);

				try {
					fileConfig.save(userFile);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}

        using.clear();
		getMorphManager().soundDisabled.clear();
        mana.getManaPlayers().clear();
	}

	public void setupCommands() {
		Bukkit.getServer().getPluginCommand("morph").setExecutor(new MorphCommand());
		Bukkit.getServer().getPluginCommand("unmorph").setExecutor(new MorphCommand());
		Bukkit.getServer().getPluginCommand("addmorph").setExecutor(new MorphCommand());
		Bukkit.getServer().getPluginCommand("delmorph").setExecutor(new MorphCommand());
		Bukkit.getServer().getPluginCommand("forcemorph").setExecutor(new MorphCommand());
	}

	public void setupFiles() {
		saveDefaultConfig();

		for (Config config : Config.values()) {
			config.createOrLoad();
		}

		File userfiles = new File(getDataFolder() + File.separator + "UserData" + File.separator);
		if (!userfiles.exists()) {
			userfiles.mkdirs();
		}
	}

	public void checkReload() {
		if (!(Bukkit.getServer().getOnlinePlayers().size() > 0))
			return;
		String prefix = m.getMessage("prefix");

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (!DisguiseAPI.isDisguised(p))
				continue;

			DisguiseAPI.undisguiseToAll(p);

			if (health) {
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
			p.sendMessage(prefix + " " + m.getMessage("unmorphedByStaff"));
		}
	}
}