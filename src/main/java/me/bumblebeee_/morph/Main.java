package me.bumblebeee_.morph;

import lombok.Getter;
import me.bumblebeee_.morph.events.RegisterEvents;
import me.bumblebeee_.morph.managers.*;
import me.bumblebeee_.morph.morphs.*;
import me.bumblebeee_.morph.utils.Utils;
import me.libraryaddict.disguise.DisguiseAPI;
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

    ManaManager mana = new ManaManager();

	public static boolean health;
	public static HashMap<UUID, String> using = new HashMap<>();
	public static List<UUID> undisguiseBuffer = new ArrayList<>();
	public static HashMap<UUID, String> respawnBuffer = new HashMap<>();

	public static @Getter Messages messages;
	public static @Getter MorphManager morphManager;

	//Changelog
	//Breeze ability
	//Empty mob list error
	//Scale hit boxes
	//
	//TODO
	//Magically dieing when morphing into iron golem under unknown conditions?
	//Update git author

	public void onEnable() {
		pl = this;
		messages = new Messages();
		double version = Utils.getVersion(true);

        setupFiles();
        setupCommands();

		messages.setup();
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
		morphManager.registerMorph(new RabbitMorph());
		morphManager.registerMorph(new StrayMorph());
		if (version >= 1.13) {
			morphManager.registerMorph(new DolphinMorph());
			morphManager.registerMorph(new DrownedMorph());
			morphManager.registerMorph(new CodMorph());
			morphManager.registerMorph(new SalmonMorph());
			morphManager.registerMorph(new PufferFishMorph());
			morphManager.registerMorph(new TropicalFishMorph());
			morphManager.registerMorph(new PhantomMorph());
			morphManager.registerMorph(new TurtleMorph());
		}
		if (version >= 1.14) {
			morphManager.registerMorph(new CatMorph());
			morphManager.registerMorph(new FoxMorph());
			morphManager.registerMorph(new PandaMorph());
			morphManager.registerMorph(new PillagerMorph());
			morphManager.registerMorph(new RavagerMorph());

		}
		if (version >= 1.15) {
			morphManager.registerMorph(new BeeMorph());
		}
		if (version >= 1.16) {
			morphManager.registerMorph(new HoglinMorph());
			morphManager.registerMorph(new PiglinBruteMorph());
			morphManager.registerMorph(new PiglinMorph());
			morphManager.registerMorph(new ZombifiedPiglinMorph());
			morphManager.registerMorph(new StriderMorph());
			morphManager.registerMorph(new ZoglinMorph());
		}
		if (version >= 1.17) {
			morphManager.registerMorph(new AxolotlMorph());
			morphManager.registerMorph(new GlowSquidMorph());
			morphManager.registerMorph(new GoatMorph());
		}
		if (version >= 1.19) {
			morphManager.registerMorph(new AllayMorph());
			morphManager.registerMorph(new FrogMorph());
			morphManager.registerMorph(new TadpoleMorph());
			morphManager.registerMorph(new WardenMorph());
		}
		if (version >= 1.20) {
			morphManager.registerMorph(new CamelMorph());
			morphManager.registerMorph(new SnifferMorph());
		}
		if (version >= 1.21) {
			morphManager.registerMorph(new BoggedMorph());
			morphManager.registerMorph(new BreezeMorph());
		}

		Runnables.potionEffects();
        RegisterEvents.register(this);

		if (getConfig().getBoolean("morph-power")) {
			Runnables.morphPower();
		}
		if (getConfig().getBoolean("ignoreMobsWhenMorphed")) {
			Runnables.agroMobs();
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
				if (mob.contains("baby")) {
					mob = mob.split(" ")[1] + ":" + mob.split(" ")[0];
				}

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
		Bukkit.getServer().getPluginCommand("randommorph").setExecutor(new MorphCommand());
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
		String prefix = messages.getMessage("prefix");

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (!DisguiseAPI.isDisguised(p))
				continue;

			DisguiseAPI.undisguiseToAll(p);

			if (health) {
				p.resetMaxHealth();
			}
            if (!p.hasPermission("morph.fly")) {
                p.setAllowFlight(false);
                p.setFlying(false);
            }
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());

			mana.getManaPlayers().put(p.getUniqueId(), 100.0);
			p.sendMessage(prefix + " " + messages.getMessage("unmorphedByStaff"));
		}
	}
}