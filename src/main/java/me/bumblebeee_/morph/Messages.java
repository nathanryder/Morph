package me.bumblebeee_.morph;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Messages {

    public static File f;
    public static YamlConfiguration c;

    private static Map<String, String> stringMsgs = new HashMap<>();
    private static Map<String, List<String>> listMsgs = new HashMap<>();

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
        listMsgs.put("abilityInfo.horse", new ArrayList<>(Arrays.asList("&5Passive: &eSpeed 4")));
        listMsgs.put("abilityInfo.wolf", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.ocelot", new ArrayList<>(Arrays.asList("&5Passive: &eSpeed 7")));
        listMsgs.put("abilityInfo.cow", new ArrayList<>(Arrays.asList("&5Ability: &eEating grass restores hunger")));
        listMsgs.put("abilityInfo.pig", new ArrayList<>(Arrays.asList("&5Ability: &eEating grass restores hunger")));
        listMsgs.put("abilityInfo.wither_skeleton", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.bat", new ArrayList<>(Arrays.asList("&5Passive: &eFlying and night vision 3")));
        listMsgs.put("abilityInfo.blaze", new ArrayList<>(Arrays.asList("&5Passive: &eFlying and Fire resistance 8","&5Ability: &eShoots a fireball")));
        listMsgs.put("abilityInfo.cave_spider", new ArrayList<>(Arrays.asList("&5Passive: &eAllows you to climb walls")));
        listMsgs.put("abilityInfo.chicken", new ArrayList<>(Arrays.asList("&5Ability: &eAllows your wildest dreams to come true.. and lay an egg!")));
        listMsgs.put("abilityInfo.creeper", new ArrayList<>(Arrays.asList("&5Passive: &eBlows up when you die","&5Ability: &eMakes you explode, dying in the process")));
        listMsgs.put("abilityInfo.enderman", new ArrayList<>(Arrays.asList("&5Ability: &eAllows you to teleport","&5Weakness: Doesn't like standing in the rain")));
        listMsgs.put("abilityInfo.endermite", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.ghast", new ArrayList<>(Arrays.asList("&5Ability: &eShoots a fireball")));
        listMsgs.put("abilityInfo.guardian", new ArrayList<>(Arrays.asList("&5Passive: &eWater breathing 8 and night vision 3")));
        listMsgs.put("abilityInfo.iron_golem", new ArrayList<>(Arrays.asList("&5Passive: &eStrength 6","&5Weakness: Slowness 1")));
        listMsgs.put("abilityInfo.magma_cube", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.mushroom_cow", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.pig_zombie", new ArrayList<>(Arrays.asList("&5Passive: &eSpeed 2 and can safely eat rotten flesh")));
        listMsgs.put("abilityInfo.sheep", new ArrayList<>(Arrays.asList("&5Ability: &eEating grass restores hunger")));
        listMsgs.put("abilityInfo.silverfish", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.skeleton", new ArrayList<>(Arrays.asList("&5Ability: &eAllows you to shoot arrows","&5Weakness: Burns in daylight")));
        listMsgs.put("abilityInfo.slime", new ArrayList<>(Arrays.asList("&5Passive: &eJump boost 4 and splits into multiple slimes when taking damage")));
        listMsgs.put("abilityInfo.snowman", new ArrayList<>(Arrays.asList("&5Passive: &ePlaces snow down wherever you go","&5Weakness: Starts to melt when it is raining")));
        listMsgs.put("abilityInfo.spider", new ArrayList<>(Arrays.asList("&5Ability: &eAllows you to shoot webs","&5Passive: &eAllows you to climb walls")));
        listMsgs.put("abilityInfo.squid", new ArrayList<>(Arrays.asList("&5Weakness: Squids are very slow and can't see very far out of water")));
        listMsgs.put("abilityInfo.villager", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.witch", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.wither", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.llama", new ArrayList<>(Arrays.asList("&5Ability: &eDamages people by spitting at them")));
        listMsgs.put("abilityInfo.vex", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.vindicator", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.evoker", new ArrayList<>(Arrays.asList("&5Ability:&e")));
        listMsgs.put("abilityInfo.zombie", new ArrayList<>(Arrays.asList("&5Passive: &eCan safely eat rotten flesh","&5Weakness: Burns in daylight")));
        listMsgs.put("abilityInfo.rabbit", new ArrayList<>(Arrays.asList("&5Passive: &eJump boost 6")));
        listMsgs.put("abilityInfo.giant", new ArrayList<>(Arrays.asList("&5Passive: &eSlowness 3 and throws blocks wherever you walk","&5Ability: &eThrows players away from you")));
        listMsgs.put("abilityInfo.enderdragon", new ArrayList<>(Arrays.asList("&5Passive: &eAllows you to fly")));
        listMsgs.put("abilityInfo.mule", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.donkey", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.zombie_villager", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.parrot", new ArrayList<>(Arrays.asList("&5Passive: &eAllows you to fly")));
        listMsgs.put("abilityInfo.illusioner", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.stray", new ArrayList<>(Arrays.asList("&5Ability: &eShoots a slowness arrow","&5Weakness: Burns during the day")));
        listMsgs.put("abilityInfo.husk", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.dolphin", new ArrayList<>(Arrays.asList("&5Passive: &eDolphins grace 2")));
        listMsgs.put("abilityInfo.drowned", new ArrayList<>(Arrays.asList("&5Passive: &eDolphins grace 2 and water breathing 8")));
        listMsgs.put("abilityInfo.cod", new ArrayList<>(Arrays.asList("&5Passive: &eDolphins grace 2")));
        listMsgs.put("abilityInfo.salmon", new ArrayList<>(Arrays.asList("&5Passive: &eDolphins grace 2")));
        listMsgs.put("abilityInfo.tropicalfish", new ArrayList<>(Arrays.asList("&5Passive: &eDolphins grace 2")));
        listMsgs.put("abilityInfo.pufferfish", new ArrayList<>(Arrays.asList("&5Passive: &eDolphins grace 2")));
        listMsgs.put("abilityInfo.phantom", new ArrayList<>(Arrays.asList("&5Passive: &eNight vision 3")));
        listMsgs.put("abilityInfo.turtle", new ArrayList<>(Arrays.asList("&5Passive: &eWater breathing 8, slowness 1 and damage resistance 3")));
        listMsgs.put("abilityInfo.bee", new ArrayList<>(Arrays.asList("&5Passive: &eAllows you to fly")));
        listMsgs.put("abilityInfo.strider", new ArrayList<>(Arrays.asList("&5Passive: &eSpeed and fire resistance when in lava","&5Weakness: Slowness 4 when not in lava")));
        listMsgs.put("abilityInfo.hoglin", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.zoglin", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.shulker", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.polar_bear", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.cat", new ArrayList<>(Arrays.asList("&5Passive: &eStops phantoms from targeting you")));
        listMsgs.put("abilityInfo.fox", new ArrayList<>(Arrays.asList("&cNo abilities found")));
        listMsgs.put("abilityInfo.panda", new ArrayList<>(Arrays.asList("&cNo abilities found")));

        stringMsgs.put("commands.morph", "&eMorph into a mob");
        stringMsgs.put("commands.unmorph", "&eMorph back into yourself");
        stringMsgs.put("commands.morph info <mob>", "&eShow the abilities that a morph gets");
        stringMsgs.put("commands.unmorph <all:player>", "&eUnmorph all players or a certain player");
        stringMsgs.put("commands.morph status", "&eDisplays what you are morphed as");
        stringMsgs.put("commands.morph near", "&eTells you if there is a player near you morphed");
        stringMsgs.put("commands.morph reload", "&eReloads the plugin");
        stringMsgs.put("commands.addmorph <player> <mob>", "&eAdd a morph to a player");
        stringMsgs.put("commands.delmorph <player> <mob>", "&eRemove a morph from a player");

        f = new File(Morph.pl.getDataFolder() + File.separator + "messages.yml");
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
        f = new File(Morph.pl.getDataFolder() + File.separator + "messages.yml");
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
        f = new File(Morph.pl.getDataFolder() + File.separator + "messages.yml");
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
