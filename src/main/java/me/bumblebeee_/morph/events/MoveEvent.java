package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoveEvent implements Listener {

    public static List<FallingBlock> falling = new ArrayList<>();
    MorphManager morph = new MorphManager();
    private HashMap<Player, Double> blockcd;
    private HashMap<Player, BukkitRunnable> cdTask;

    Plugin pl = null;
    public MoveEvent(Plugin plugin) {
        pl = plugin;
        blockcd = new HashMap<>();
        cdTask = new HashMap<>();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ())
            return;

        final Player p = e.getPlayer();
        if (!Morph.using.containsKey(p.getUniqueId()))
            return;

        String using = Morph.using.get(p.getUniqueId());

        if (using.equalsIgnoreCase("snowman")) {
            if (e.getTo().clone().add(0, -0.5, 0).getBlock().getTemperature() > 1) {
                p.setFireTicks(40);
            } else if (e.getTo().clone().add(0, -0.5, 0).getBlock().getType() == Material.WATER ||
                    e.getTo().clone().add(0, -0.5, 0).getBlock().getType() == Material.STATIONARY_WATER) {
                if (p.getHealth()-0.5 <= 0) {
                    p.setHealth(0);
                } else {
                    p.setHealth(p.getHealth() - 0.5);
                }
            } else if (pl.getConfig().getBoolean("snowman-snow")) {
                Block b = p.getLocation().getBlock();
                if (b.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR) {
                    b.setType(Material.SNOW);
                }
            }
        } else if (using.equalsIgnoreCase("giant")) {
            if (!Morph.pl.getConfig().getBoolean("giant-walk-throw"))
                return;

            if (!(blockcd.containsKey(p))) {

                Location typeLoc = p.getLocation().clone();
                Material m = typeLoc.subtract(0,1,0).getBlock().getType();
                for (int i = 0; i < 3; i++) {
                    if (m == Material.AIR) {
                        m = typeLoc.subtract(0, 1, 0).getBlock().getType();
                    }
                }

                for (int i = 0; i <= 2; i++) {
                    throwb(p, m, p.getLocation());
                }

                blockcd.put(p, 0.4);
                cdTask.put(p, new BukkitRunnable() {

                    public void run() {
                        blockcd.put(p, blockcd.get(p) - 1);
                        if (blockcd.get(p) <= 0) {
                            blockcd.remove(p);
                            cdTask.remove(p);
                            cancel();
                        }
                    }

                });
                cdTask.get(p).runTaskTimer(Morph.pl, 4, 4);
            }
        }
    }

    public void throwb(Player p, Material m, Location l) {
        double x;
        double y = 0.6;
        double z;
        BlockFace f = yawToFace(l.getYaw());

        l = right(f, right(f, right(f, p.getLocation())));
        x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
        z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));

        final FallingBlock block = p.getWorld().spawnFallingBlock(l, new MaterialData(m));
        block.setMetadata("morph", new FixedMetadataValue(Morph.pl, "true"));
        block.setVelocity(new Vector(x, y, z));
        block.setDropItem(false);
        block.setHurtEntities(false);
        falling.add(block);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Morph.pl, new Runnable() {
            @Override
            public void run() {
                block.remove();
            }
        }, 10);


        x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
        z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));
        l = left(f, left(f, left(f, p.getLocation())));

        final FallingBlock block2 = p.getWorld().spawnFallingBlock(l, new MaterialData(m));
        block2.setMetadata("morph", new FixedMetadataValue(Morph.pl, "true"));
        block2.setVelocity(new Vector(x, y, z));
        block2.setDropItem(false);
        block.setHurtEntities(false);
        falling.add(block2);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Morph.pl, new Runnable() {
            @Override
            public void run() {
                block2.remove();
            }
        }, 10);
    }

    public Location left(BlockFace f, Location loc) {
        Location l = null;
        switch (f) {
            case NORTH:
                l = loc.add(-1,0,0);
                break;
            case SOUTH:
                l = loc.add(1,0,0);
                break;
            case EAST:
                l = loc.add(0,0,-1);
                break;
            case WEST:
                l = loc.add(0,0,1);
                break;
        }
        return l;
    }
    public Location right(BlockFace f, Location loc) {
        Location l = null;
        switch (f) {
            case NORTH:
                l = loc.add(1,0,0);
                break;
            case SOUTH:
                l = loc.add(-1,0,0);
                break;
            case EAST:
                l = loc.add(0,0,1);
                break;
            case WEST:
                l = loc.add(0,0,-1);
                break;
        }
        return l;
    }

    public BlockFace yawToFace(float yaw) {
        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }
    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
}
