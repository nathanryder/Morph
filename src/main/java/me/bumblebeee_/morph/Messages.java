package me.bumblebeee_.morph;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Messages {

    public static File f;
    public static YamlConfiguration c;

    public void setup() {
        f = new File(Morph.pl.getDataFolder() + File.separator + "messages.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            c = YamlConfiguration.loadConfiguration(f);
            //target, player, mob, command, description, canMorphAs
            createMessage("prefix", "&a[Morph]&e");
            createMessage("noPermissions", "&cYou do not have the required permissions");
            createMessage("invalidArguments", "&eInvalid arguments! Type /morph help for help");
            createMessage("invalidPlayer", "&eCould not find player called {target}!");
            createMessage("invalidMorph", "&eInvalid disguise type.");
            createMessage("removedMorph", "&eRemoved mob {mob} from user {target}");
            createMessage("targetCanAlreadyMorph", "&e{target} can already morph into a {mob}");
            createMessage("addedMorphToTarget", "&eAdded mob {mob} to user {target}");
            createMessage("disableInThisWorld", "&eThis plugin is disabled in this world");
            createMessage("morphedAs", "&eYou are morphed as a {mob}");
            createMessage("morphedNearby", "&e{target} is near you and is disguised as a {mob}");
            createMessage("nobodyNearby", "&eThere is nobody disguised near you!");
            createMessage("helpFormat", "&e{command}: {description}");
            createMessage("reloadedConfig", "&eSuccessfully reloaded config!");
            createMessage("cannotMorphAsAnything", "&eYou cannot morph into anything yet! Go kill some mobs!");
            createMessage("currentlyMorphedAs", "&eYou are morphed as a {mob}");
            createMessage("canMorphInto", "&eYou can morph into: {canMorphAs}");
            createMessage("notMorphedAsAnything", "&eYou are not morphed as anything!");
            createMessage("couldNotFindPlayer", "&eCould not find player {target}");
            createMessage("unableToMorphAsPlayer", "&eYou are unable to morph into {target}. Have you spelt it right? (Caps matter!)");
            createMessage("morphedAsPlayer", "&eYou have morphed into {target}");
            createMessage("endermenDisabled", "&eEndermen have been disabled due to them crashing other players");
            createMessage("alreadyMorphed", "&eYou are already morphed as a {mob}");
            createMessage("unableToMorph", "&eYou are unable to morph into a {mob}");
            createMessage("youHaveMorphed", "&eYou have morphed into a {mob}!");
            createMessage("noPlayersMorphed", "&eThere is not players currently morphed!");
            createMessage("unmorphedByTime", "&eYour morph has ran out of time and you have been unmorphed!");
            createMessage("unmorphedByStaff", "&eYou have been unmorphed by a staff member!");
            createMessage("unmorphedAllPlayers", "&eAll players have been unmorphed");
            createMessage("playerUnmorphed", "&ePlayer {target} has been unmorphed!");
            createMessage("morphReversed", "&eYou morphed back into {player}!");
            createMessage("cooldown", "&ePlease wait {time} seconds!");
            createMessage("morphOnCooldown", "&ePlease wait {time} seconds before morphing into this mob!");
            createMessage("getMorphByKill", "&eYou killed {target} who was morphed as a {mob}, you now get his morph!");
            createMessage("creeperExploded", "&eYou exploded!");
            createMessage("diedLostAll", "&eYou were killed by {player} so you lost all your morphs!");
            createMessage("diedLostCurrent", "&eYou were killed by {player} &eso lost your current morph: A {mob}");
            createMessage("vexTooManyLayers", "&eToo many layers to phase through!");
            createMessage("abilityToggledOn", "&eYour abilties have been enabled!");
            createMessage("abilityToggledOff", "&eYour abilties have been disabled!");
            createMessage("outOfMorphPower", "&eOut of morph power! You must land to regain.");
            createMessage("unableToChangeView", "&cPlayers are not allow to change their disguise view!");
            createMessage("changeViewSuccess", "&eYou can %status% see your own morph!");
            createMessage("changeSoundVolSuccess", "&eYour mob sounds have been %statusColor%!");
            createMessage("noBabyType", "&eThat mob does not have a baby type!");
            createMessage("morphedTitle", "Currently morphed as a {mob}");
            createMessage("unmorphedTitle", "You are not morphed as anything");
            createMessage("clickToMorph", "&bClick to morph into a {mob}");
            createMessage("morphPower", "&eMorph Power: &a");
            createMessage("timeLeftAsMorph", "&eTime left as {mob}: {min}:{second}");
            createMessage("openingInventory", "&eLoading inventory..");
            createMessage("unmorphedByWorld", "&cYou have been unmorphed because morphing is disabled in this world");
            createMessage("GUIdisabled", "&cMorph GUI is disabled. For more help do /morph help");

            createMessage("commands.morph", "&eMorph into a mob");
            createMessage("commands.unmorph", "&eMorph back into yourself");
            createMessage("commands.unmorph <all:player>", "&eUnmorph all players or a certain player");
            createMessage("commands.morph status", "&eDisplays what you are morphed as");
            createMessage("commands.morph near", "&eTells you if there is a player near you morphed");
            createMessage("commands.morph reload", "&eReloads the plugin");
            createMessage("commands.addmorph <player> <mob>", "&eAdd a morph to a player");
            createMessage("commands.delmorph <player> <mob>", "&eRemove a morph from a player");
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

    public String getMessage(String key) {
        f = new File(Morph.pl.getDataFolder() + File.separator + "messages.yml");
        c = YamlConfiguration.loadConfiguration(f);

        String msg = c.getString(key);
        if (msg == null) {
            Morph.pl.getLogger().warning(ChatColor.RED + "Failed to find message with key " + key);
            Morph.pl.getLogger().warning(ChatColor.RED + "Deleting the messages.yml file or adding the key will fix this");
            return ChatColor.translateAlternateColorCodes('&', "&cFailed to find message! Please report this to a server admin.");
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
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
