
# Introduction:
This Minecraft plugin is based on the Minecraft Forge Mod Morph created by IChun, You can transform into almost everything you kill and use the abilities that they have.

# Abilities:
- Bats, Ghast and Blazes can fly
- Creepers explode when the die, or when triggered
- Dolphins, drowned, fish, phantoms and turtles can swim underwater
- Enderdragon throws fireballs
- Endermen can teleport
- Evokers use their trap ability
- Ghasts and blazes can shoot fireballs
- Giants throw players
- Horses and Ocelots have speed
- Iron golems have strength
- Llamas can spit
- Pigmen have speed
- Pufferfish can poison players and mobs in a 7 block radius
- Rabbits have jump boost
- Slimes have jump boost
- Snowmen can throw snowballs
- Snowmen place snow wherever they walk
- Spiders can climb walls
- Spiders can throw webs
- Squids and Guardians have water breathing
- Zombies can eat their own flesh without getting poisoned
- And much more!

Most abilities are used by Shift+Clicking. These abilities can be disabled in the config if needed, or if the player desires they can use "/morph toggle" to enable/disable shift clicking abilities.

There is a settings section in the GUI that allows players to disable their own sounds, abilities, and choose if they want to see their own morph. All abilities that are manually triggered can be used by sneaking and pressing left click. To make your morph make a sound you can hold sneak for 2 seconds.

# Installation/How to use:
This plugin requires [LibsDisguises](https://www.spigotmc.org/resources/libs-disguises.81/) and [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/). After you have all the required plugins and put the Morph plugin in your plugin folder all you need to do is kill any mob and type /morph (mob) to morph into it.


# Configuration:
<details>
<summary>config.yml</summary>

```yaml
#Do you want a sound/particle effect to be played when a player morphs
morph-sound: true
morph-particle: true

#Do you want a sound/particle effect to be played when a player unmorphs
unmorph-sound: true
unmorph-particle: true

#Allow morphing into players? (Possibly buggy)
enable-players: false

#Should players be able to see their own disguise?
viewSelfDisguise: true

#Should players be able to change if they view their disguises?
canChangeView: true

#When morphed as a mob do any other mobs ignore you
ignoreMobsWhenMorphed: true

#What worlds do you want this plugin enabled in?
#use <all> to enable in all worlds
enabled-worlds:
- "<all>"
- "world"
- "world_nether"
- "world_the_end"

#Reset all of the players morphs when they die
death-reset-all: false
#Reset only the morph they died as
death-reset-current: true

#When a player kills a morphed player, should they get the morph?
steal-morphs: true

#Enable morph power for flying
morph-power: true
#How fast morph power is used per second of flying
morphPower-use: 1
#How fast morph power is regained per second of not flying
morphPower-regain: 1

#The radius /morph near looks at
near-radius: 10

#Hand swap menu status
swapMenu: true
#Only open the menu when there is nothing in the players main hand?
onlyIfEmptyHand: false

#Disable the /morph GUI? Not usually needed
disableGUI: false

#Changing death messages if the killer is morphed
#Available placeholders are:
#{victim} {killer} {killerMob} {world}
overrideDeathMessage: true
randomMessage: false
creeperDeathMessage: false
deathMessages:
- "{killer} killed {victim} while morphed as a {killerMob}"
- "{victim} was killed by a {killerMob}"

morphItem:
type: BLAZE_ROD
data: 0
name: "&aMorph Menu"
lore:
- "&5Click to open the morph menu"
  dropItem: false
  giveOnJoin: false
  slot: 0

disableHealthSystem: false
checkForUpdates: true
debug: false
stats: true
```
</details>

<details>
<summary>mobConfig.yml</summary>

```yaml
#Abilities Configuration
#
#enabled can be used to enable or disable a mob
#health is how much health the mob will have while morphed - This ONLY works if disableHealthSystem is false in config.yml
#requiredKills is how many times players need to kill a mob to get the morph
#morph-time is how long you can stay morphed as a certain mob - set to 0 to disable
#morph-cooldown is how often you can morph back into the mob after unmorphing from that particular mob - set to 0 to disable
#ability-cooldown is the cooldown on the given ability of the mob - set to 0 to disable

#Enable flying?
flying: true

horse:
enabled: true
health: 15
requiredKills: 1
morph-time: 0
morph-cooldown: 0
speed: true
wolf:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
ocelot:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
speed: true
cow:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
pig:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
wither_skeleton:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
ability-cooldown: 0
bat:
enabled: true
health: 6
requiredKills: 1
morph-time: 0
morph-cooldown: 0
blaze:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
fire: true
ability-cooldown: 5
cave_spider:
enabled: true
health: 12
requiredKills: 1
morph-time: 0
morph-cooldown: 0
spider-climb: true
ability-cooldown: 0
chicken:
enabled: true
health: 4
requiredKills: 1
morph-time: 0
morph-cooldown: 0
egg: true
ability-cooldown: 120
creeper:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
explosion: true
explosion-damage: false
enderman:
enabled: true
health: 40
requiredKills: 1
morph-time: 0
morph-cooldown: 0
teleport: true
ability-cooldown: 5
endermite:
enabled: true
health: 8
requiredKills: 1
morph-time: 0
morph-cooldown: 0
ghast:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
fireball: true
ability-cooldown: 5
guardian:
enabled: true
health: 30
requiredKills: 1
morph-time: 0
waterbreathing: true
morph-cooldown: 0
iron_golem:
enabled: true
health: 100
requiredKills: 1
morph-time: 0
morph-cooldown: 0
strength: true
magma_cube:
enabled: true
health: 16
requiredKills: 1
morph-time: 0
morph-cooldown: 0
mushroom_cow:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
pig_zombie:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
eat: true
speed: true
sheep:
enabled: true
health: 8
requiredKills: 1
morph-time: 0
morph-cooldown: 0
silverfish:
enabled: true
health: 8
requiredKills: 1
morph-time: 0
morph-cooldown: 0
skeleton:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
shoot: true
slime:
enabled: true
health: 16
requiredKills: 1
morph-time: 0
morph-cooldown: 0
jump-boost: true
split: true
snowman:
enabled: true
health: 4
requiredKills: 1
morph-time: 0
morph-cooldown: 0
snow: true
shoot: true
ability-cooldown: 0
spider:
enabled: true
health: 16
requiredKills: 1
morph-time: 0
morph-cooldown: 0
climb: true
web: true
#Remove spider webs after they are shot?
removeSpiderWeb: true
#How long (seconds) should webs stay for before despawning
spiderWebRemove: 30
ability-cooldown: 0
squid:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
waterbreathing: true
villager:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
witch:
enabled: true
health: 26
requiredKills: 1
morph-time: 0
morph-cooldown: 0
wither:
enabled: true
health: 300
requiredKills: 1
morph-time: 0
morph-cooldown: 0
llama:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
spit: true
ability-cooldown: 0
vex:
enabled: true
health: 14
requiredKills: 1
morph-time: 0
morph-cooldown: 0
phase: true
#The amount of blocks vex can phase through
max-layers: 2
ability-cooldown: 30
vindicator:
enabled: true
health: 24
requiredKills: 1
morph-time: 0
morph-cooldown: 0
evoker:
enabled: true
health: 24
requiredKills: 1
morph-time: 0
morph-cooldown: 0
attack: true
spawnVex: true
attack-cooldown: 5
spawnVex-cooldown: 30
zombie:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
eat: true
rabbit:
enabled: true
health: 3
requiredKills: 1
morph-time: 0
morph-cooldown: 0
jump-boost: true
giant:
enabled: true
health: 100
requiredKills: 1
morph-time: 0
morph-cooldown: 0
throw: true
#This increases how high giants can throw people
force: 2
#Enables the giant to 'throw' blocks as he walks
#NOTE: This might cause random blocks to be placed/broken if enabled
walk-throw: false
ability-cooldown: 0
enderdragon:
enabled: true
health: 100
requiredKills: 1
morph-time: 0
morph-cooldown: 0
fireball: true
ability-cooldown: 0
mule:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
donkey:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
zombie_villager:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
parrot:
enabled: true
health: 6
requiredKills: 1
morph-time: 0
morph-cooldown: 0
illusioner:
enabled: true
health: 32
requiredKills: 1
morph-time: 0
morph-cooldown: 0
ability-cooldown: 0
stray:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
slow: true
shoot: true
ability-cooldown: 0
husk:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
hunger: true
dolphin:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
drowned:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
cod:
enabled: true
health: 3
requiredKills: 1
morph-time: 0
morph-cooldown: 0
salmon:
enabled: true
health: 3
requiredKills: 1
morph-time: 0
morph-cooldown: 0
tropicalfish:
enabled: true
health: 3
requiredKills: 1
morph-time: 0
morph-cooldown: 0
pufferfish:
enabled: true
health: 3
requiredKills: 1
morph-time: 0
morph-cooldown: 0
poison: true
ability-cooldown: 15
phantom:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
turtle:
enabled: true
health: 30
requiredKills: 1
morph-time: 0
morph-cooldown: 0
bee:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
strider:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
hoglin:
enabled: true
health: 40
requiredKills: 1
morph-time: 0
morph-cooldown: 0
zoglin:
enabled: true
health: 40
requiredKills: 1
morph-time: 0
morph-cooldown: 0
shulker:
enabled: true
health: 30
requiredKills: 1
morph-time: 0
morph-cooldown: 0
polar_bear:
enabled: true
health: 30
requiredKills: 1
morph-time: 0
morph-cooldown: 0
cat:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
fox:
enabled: true
health: 10
requiredKills: 1
morph-time: 0
morph-cooldown: 0
panda:
enabled: true
health: 20
requiredKills: 1
morph-time: 0
morph-cooldown: 0
```
</details>

# Commands
- /morph help: Displays this help screen - morph.morph
- /morph info <mob>: Shows the abilities that a particular morph has
- /morph <mob>: Morphs you into the given mob  - morph.morph
- /randommorph: Morphs you into a random morph - morph.randommorph
- /morph toggle: Enabled/Disables the players abilities - morph.toggle
- /morph view [true/false]: Sets if the player can see their own disguise (Must be enabled in the config) - morph.view
- /unmorph <all: player>: Morph back to yourself - morph.morph
- /morph status: Displays what you are morphed as - morph.morph
- /morph near: Shows if there is a player nearby morphed - morph.morph
- /forcemorph <player> <mob>: Force another player to morph - morph.forcemorph
- /addmorph <player> <mob>: Add a morph to the given player - morph.morph.modify
- /delmorph <player> <mob>: Remove a morph from the given player - morph.morph.modify

# Permissions
- morph.morph - Allows players to use the /morph command
- morph.* -  For all commands
- morph.changeview - Allows the player to use /morph view
- morph.toggle - Allows players to use /morph toggle
- morph.view - Allows players to use /morph view
- morph.into.* - Gives players ability to morph into all players.
- morph.into.mob - E.g morph.into.horse allows the rank to morping to a horse. morph.into.* overrides this.
- morph.bypasstime.* - Allows players to bypass the time limits on morphing (morph.bypasstime.<mob> for particular mobs)
- morph.bypasskill.mob - e.g morph.bypasskill.horse allows the rank to morph into a horse without having to kill it first. morph.bypasskill.* for all mobs


Need help with the plugin? [Click here to join my discord channel!](https://discord.gg/wubhY5Z)
