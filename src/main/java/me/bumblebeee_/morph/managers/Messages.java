package me.bumblebeee_.morph.managers;

import me.bumblebeee_.morph.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Messages {

    public static File f;
    public static YamlConfiguration c;

    private static Map<String, String> stringMsgs = new HashMap<>();
    public static Map<String, List<String>> listMsgs = new HashMap<>();

    public void setup() {

        stringMsgs.put("prefix", "&a[Morph]&e");
        stringMsgs.put("noPermissions", "&cYou do not have the required permissions");
        stringMsgs.put("youCanNowMorph", "&eYou can now morph into a {mob}!");
        stringMsgs.put("invalidArguments", "&eInvalid arguments! Type /morph help for help");
        stringMsgs.put("invalidPlayer", "&eCould not find player called {target}!");
        stringMsgs.put("invalidMorph", "&eInvalid disguise type.");
        stringMsgs.put("morphProgress", "&eYou now have {kills}/{requiredKills} kills for a {mob} morph.");
        stringMsgs.put("removedMorph", "&eRemoved mob {mob} from user {target}");
        stringMsgs.put("targetCanAlreadyMorph", "&e{target} can already morph into a {mob}");
        stringMsgs.put("addedMorphToTarget", "&eAdded mob {mob} to user {target}");
        stringMsgs.put("disableInThisWorld", "&eThis plugin is disabled in this world");
        stringMsgs.put("morphedAs", "&eYou are morphed as a {mob}");
        stringMsgs.put("morphedNearby", "&e{target} is near you and is disguised as a {mob}");
        stringMsgs.put("nobodyNearby", "&eThere is nobody disguised near you!");
        stringMsgs.put("helpFormat", "&e{command}: {description}");
        stringMsgs.put("reloadedConfig", "&eSuccessfully reloaded config!");
        stringMsgs.put("mobDisabled", "&eThis mob is not enabled!");
        stringMsgs.put("cannotMorphAsAnything", "&eYou cannot morph into anything yet! Go kill some mobs!");
        stringMsgs.put("currentlyMorphedAs", "&eYou are morphed as a {mob}");
        stringMsgs.put("canMorphInto", "&eYou can morph into: {canMorphAs}");
        stringMsgs.put("notMorphedAsAnything", "&eYou are not morphed as anything!");
        stringMsgs.put("couldNotFindPlayer", "&eCould not find player {target}");
        stringMsgs.put("unableToMorphAsPlayer", "&eYou are unable to morph into {target}. Have you spelt it right? (Caps matter!)");
        stringMsgs.put("morphedAsPlayer", "&eYou have morphed into {target}");
        stringMsgs.put("endermenDisabled", "&eEndermen have been disabled due to them crashing other players");
        stringMsgs.put("alreadyMorphed", "&eYou are already morphed as a {mob}");
        stringMsgs.put("unableToMorph", "&eYou are unable to morph into a {mob}");
        stringMsgs.put("youHaveMorphed", "&eYou have morphed into a {mob}!");
        stringMsgs.put("noPlayersMorphed", "&eThere is not players currently morphed!");
        stringMsgs.put("unmorphedByTime", "&eYour morph has ran out of time and you have been unmorphed!");
        stringMsgs.put("unmorphedByStaff", "&eYou have been unmorphed by a staff member!");
        stringMsgs.put("unmorphedAllPlayers", "&eAll players have been unmorphed");
        stringMsgs.put("playerUnmorphed", "&ePlayer {target} has been unmorphed!");
        stringMsgs.put("morphReversed", "&eYou morphed back into {player}!");
        stringMsgs.put("cooldown", "&ePlease wait {time} seconds!");
        stringMsgs.put("morphOnCooldown", "&ePlease wait {time} seconds before morphing into this mob!");
        stringMsgs.put("getMorphByKill", "&eYou killed {target} who was morphed as a {mob}, you now get his morph!");
        stringMsgs.put("creeperExploded", "&eYou exploded!");
        stringMsgs.put("diedLostAll", "&eYou were killed by {player} so you lost all your morphs!");
        stringMsgs.put("diedLostCurrent", "&eYou were killed by {player} &eso lost your current morph: A {mob}");
        stringMsgs.put("vexTooManyLayers", "&eToo many layers to phase through!");
        stringMsgs.put("abilityToggledOn", "&eYour abilties have been enabled!");
        stringMsgs.put("abilityToggledOff", "&eYour abilties have been disabled!");
        stringMsgs.put("outOfMorphPower", "&eOut of morph power! You must land to regain.");
        stringMsgs.put("unableToChangeView", "&cPlayers are not allow to change their disguise view!");
        stringMsgs.put("changeViewSuccess", "&eYou can %status% see your own morph!");
        stringMsgs.put("changeSoundVolSuccess", "&eYour mob sounds have been %statusColor%!");
        stringMsgs.put("noBabyType", "&eThat mob does not have a baby type!");
        stringMsgs.put("morphedTitle", "Currently morphed as a {mob}");
        stringMsgs.put("unmorphedTitle", "You are not morphed as anything");
        stringMsgs.put("clickToMorph", "&bClick to morph into a {mob}");
        stringMsgs.put("morphPower", "&eMorph Power: &a");
        stringMsgs.put("timeLeftAsMorph", "&eTime left as {mob}: {min}:{second}");
        stringMsgs.put("openingInventory", "&eOpening inventory..");
        stringMsgs.put("unmorphedByWorld", "&cYou have been unmorphed because morphing is disabled in this world");
        stringMsgs.put("GUIdisabled", "&cMorph GUI is disabled. For more help do /morph help");

        stringMsgs.put("abilityInfo.title", "&eAbility information for {mob}");
        stringMsgs.put("commands.morph", "&eMorph into a mob");
        stringMsgs.put("commands.unmorph", "&eMorph back into yourself");
        stringMsgs.put("commands.morph info <mob>", "&eShow the abilities that a morph gets");
        stringMsgs.put("commands.unmorph <all:player>", "&eUnmorph all players or a certain player");
        stringMsgs.put("commands.morph status", "&eDisplays what you are morphed as");
        stringMsgs.put("commands.morph near", "&eTells you if there is a player near you morphed");
        stringMsgs.put("commands.morph reload", "&eReloads the plugin");
        stringMsgs.put("commands.forcemorph <player> <mob> [ignoreChecks] [silent]", "&eForce a player to morph as a mob");
        stringMsgs.put("commands.addmorph <player> <mob>", "&eAdd a morph to a player");
        stringMsgs.put("commands.delmorph <player> <mob>", "&eRemove a morph from a player");

        stringMsgs.put("mobNames.baby", "baby");
        stringMsgs.put("mobNames.allay", "allay");
        stringMsgs.put("mobNames.axolotl", "axolotl");
        stringMsgs.put("mobNames.bat", "bat");
        stringMsgs.put("mobNames.bee", "bee");
        stringMsgs.put("mobNames.blaze", "blaze");
        stringMsgs.put("mobNames.camel", "camel");
        stringMsgs.put("mobNames.cat", "cat");
        stringMsgs.put("mobNames.cave_spider", "cave_spider");
        stringMsgs.put("mobNames.chicken", "chicken");
        stringMsgs.put("mobNames.cod", "cod");
        stringMsgs.put("mobNames.cow", "cow");
        stringMsgs.put("mobNames.creeper", "creeper");
        stringMsgs.put("mobNames.dolphin", "dolphin");
        stringMsgs.put("mobNames.donkey", "donkey");
        stringMsgs.put("mobNames.drowned", "drowned");
        stringMsgs.put("mobNames.ender_dragon", "ender_dragon");
        stringMsgs.put("mobNames.enderman", "enderman");
        stringMsgs.put("mobNames.endermite", "endermite");
        stringMsgs.put("mobNames.evoker", "evoker");
        stringMsgs.put("mobNames.fox", "fox");
        stringMsgs.put("mobNames.frog", "frog");
        stringMsgs.put("mobNames.ghast", "ghast");
        stringMsgs.put("mobNames.giant", "giant");
        stringMsgs.put("mobNames.glow_squid", "glow_squid");
        stringMsgs.put("mobNames.goat", "goat");
        stringMsgs.put("mobNames.guardian", "guardian");
        stringMsgs.put("mobNames.hoglin", "hoglin");
        stringMsgs.put("mobNames.horse", "horse");
        stringMsgs.put("mobNames.husk", "husk");
        stringMsgs.put("mobNames.illusioner", "illusioner");
        stringMsgs.put("mobNames.iron_golem", "iron_golem");
        stringMsgs.put("mobNames.llama", "llama");
        stringMsgs.put("mobNames.magma_cube", "magma_cube");
        stringMsgs.put("mobNames.mule", "mule");
        stringMsgs.put("mobNames.mushroom_cow", "mushroom_cow");
        stringMsgs.put("mobNames.ocelot", "ocelot");
        stringMsgs.put("mobNames.panda", "panda");
        stringMsgs.put("mobNames.parrot", "parrot");
        stringMsgs.put("mobNames.phantom", "phantom");
        stringMsgs.put("mobNames.piglin_brute", "piglin_brute");
        stringMsgs.put("mobNames.piglin", "piglin");
        stringMsgs.put("mobNames.pig", "pig");
        stringMsgs.put("mobNames.pillager", "pillager");
        stringMsgs.put("mobNames.polar_bear", "polar_bear");
        stringMsgs.put("mobNames.pufferfish", "pufferfish");
        stringMsgs.put("mobNames.rabbit", "rabbit");
        stringMsgs.put("mobNames.ravager", "ravager");
        stringMsgs.put("mobNames.salmon", "salmon");
        stringMsgs.put("mobNames.sheep", "sheep");
        stringMsgs.put("mobNames.shulker", "shulker");
        stringMsgs.put("mobNames.silverfish", "silverfish");
        stringMsgs.put("mobNames.skeleton_horse", "skeleton_horse");
        stringMsgs.put("mobNames.skeleton", "skeleton");
        stringMsgs.put("mobNames.slime", "slime");
        stringMsgs.put("mobNames.sniffer", "sniffer");
        stringMsgs.put("mobNames.snowman", "snowman");
        stringMsgs.put("mobNames.spider", "spider");
        stringMsgs.put("mobNames.squid", "squid");
        stringMsgs.put("mobNames.stray", "stray");
        stringMsgs.put("mobNames.strider", "strider");
        stringMsgs.put("mobNames.tadpole", "tadpole");
        stringMsgs.put("mobNames.tropical_fish", "tropical_fish");
        stringMsgs.put("mobNames.turtle", "turtle");
        stringMsgs.put("mobNames.vex", "vex");
        stringMsgs.put("mobNames.villager", "villager");
        stringMsgs.put("mobNames.zombie_villager", "zombie_villager");
        stringMsgs.put("mobNames.vindicator", "vindicator");
        stringMsgs.put("mobNames.warden", "warden");
        stringMsgs.put("mobNames.witch", "witch");
        stringMsgs.put("mobNames.wither", "wither");
        stringMsgs.put("mobNames.wither_skeleton", "wither_skeleton");
        stringMsgs.put("mobNames.wolf", "wolf");
        stringMsgs.put("mobNames.zoglin", "zoglin");
        stringMsgs.put("mobNames.zombie", "zombie");
        stringMsgs.put("mobNames.zombified_piglin", "zombified_piglin");
        stringMsgs.put("mobNames.bogged", "bogged");
        stringMsgs.put("mobNames.breeze", "breeze");

        f = new File(Main.pl.getDataFolder() + File.separator + "messages.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            c = YamlConfiguration.loadConfiguration(f);

            for (String key : stringMsgs.keySet()) {
                createMessage(key, stringMsgs.get(key));
            }
            for (String key : listMsgs.keySet()) {
                createMessage(key, listMsgs.get(key));
            }

            try {
                c.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            c = YamlConfiguration.loadConfiguration(f);
        }
    }

    public void createMessage(String key, String value) {
        c.set(key, value);
    }

    public void createMessage(String key, List<String> value) {
        c.set(key, value);
    }

    public String getMessage(String key, String target, String player, String mob, String canMorphAs) {
        return getMessage(key).replace("{target}", target).replace("{player}", player).replace("{mob}", mob).replace("{canMorphAs}", canMorphAs);
    }

    public String getMessage(String key, String target, String player, String mob, int time) {
        return getMessage(key).replace("{target}", target).replace("{player}", player).replace("{mob}", mob).replace("{time}", String.valueOf(time));
    }

    public String getMessage(String key, String mob, String mins, String seconds) {
        return getMessage(key).replace("{mob}", mob).replace("{min}", String.valueOf(mins)).replace("{second}", String.valueOf(seconds));
    }

    public String getMessage(String key, String cmd, String desc) {
        return getMessage(key).replace("{command}", cmd).replace("{description}", desc);
    }

    public String getMessage(String key, int currKills, int killsRequired, String type) {
        String msg = getMessage(key);
        if (msg == null)
            return null;

        return msg.replace("{kills}", String.valueOf(currKills))
                .replace("{requiredKills}", String.valueOf(killsRequired))
                .replace("{mob}", type);
    }

    public String getMessage(String key) {
        f = new File(Main.pl.getDataFolder() + File.separator + "messages.yml");
        c = YamlConfiguration.loadConfiguration(f);

        String msg = c.getString(key);
        if (msg == null) {
            if (key.equalsIgnoreCase("morphProgress") || key.equalsIgnoreCase("youCanNowMorph")) {
                return null;
            }
            return ChatColor.translateAlternateColorCodes('&', stringMsgs.get(key));
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public List<String> getListMessage(String key) {
        f = new File(Main.pl.getDataFolder() + File.separator + "messages.yml");
        c = YamlConfiguration.loadConfiguration(f);

        List<String> msg = new ArrayList<>();
        List<String> temp = c.getStringList(key);
        if (c.get(key) == null) {
            temp = listMsgs.get(key);
        }

        for (String line : temp) {
            msg.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        return msg;
    }

    public List<String> getCommands() {
        List<String> cmds = new ArrayList<>();
        cmds.add("morph");
        cmds.add("unmorph");
        cmds.add("unmorph <all:player>");
        cmds.add("morph status");
        cmds.add("morph near");
        cmds.add("morph reload");
        cmds.add("addmorph <player> <mob>");
        cmds.add("delmorph <player> <mob>");
        return cmds;
    }

    public YamlConfiguration getYaml() {
        return YamlConfiguration.loadConfiguration(f);
    }

    public void saveYaml(YamlConfiguration c) {
        try {
            c.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
