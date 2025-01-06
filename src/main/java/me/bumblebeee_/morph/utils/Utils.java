package me.bumblebeee_.morph.utils;

import me.bumblebeee_.morph.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public final static ArrayList<Material> SPIDER_UNCLIMBABLE = new ArrayList<>(Arrays.asList(Material.AIR, Material.LAVA, Material.LEGACY_DOUBLE_PLANT));

    public static List<FallingBlock> falling = new ArrayList<>();
    public static void throwb(Player p, Material m, Location l) {
        double x;
        double y = 0.6;
        double z;
        BlockFace f = yawToFace(l.getYaw());

        l = right(f, right(f, right(f, p.getLocation())));
        x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
        z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));

        final FallingBlock block = p.getWorld().spawnFallingBlock(l, new MaterialData(m));
        block.setMetadata("morph", new FixedMetadataValue(Main.pl, "true"));
        block.setVelocity(new Vector(x, y, z));
        block.setDropItem(false);
        block.setHurtEntities(false);
        falling.add(block);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.pl, new Runnable() {
            @Override
            public void run() {
                block.remove();
            }
        }, 10);


        x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
        z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));
        l = left(f, left(f, left(f, p.getLocation())));

        final FallingBlock block2 = p.getWorld().spawnFallingBlock(l, new MaterialData(m));
        block2.setMetadata("morph", new FixedMetadataValue(Main.pl, "true"));
        block2.setVelocity(new Vector(x, y, z));
        block2.setDropItem(false);
        block.setHurtEntities(false);
        falling.add(block2);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.pl, new Runnable() {
            @Override
            public void run() {
                block2.remove();
            }
        }, 10);
    }

    public static Location left(BlockFace f, Location loc) {
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
    public static Location right(BlockFace f, Location loc) {
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

    public static Location forward(BlockFace f, Location loc) {
        Location l = null;
        switch (f) {
            case NORTH:
                l = loc.add(0,0,-1);
                break;
            case SOUTH:
                l = loc.add(0,0,1);
                break;
            case EAST:
                l = loc.add(1,0,0);
                break;
            case WEST:
                l = loc.add(-1,0,0);
                break;
        }
        return l;
    }

    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    public static BlockFace yawToFace(float yaw) {
        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }

    public static double getVersion(boolean majorMinor) {
        double version;
        try {
            String versionStr = Bukkit.getBukkitVersion().split("-")[0];

            if (majorMinor) {
                versionStr = versionStr.substring(0, 4);

            } else {
                versionStr = versionStr.substring(2);
            }

            version = Double.parseDouble(versionStr);
        } catch (ArrayIndexOutOfBoundsException e) {
            Main.pl.getLogger().severe("Failed to find a valid server version! Report this to the developer immediately");
            version = 1.2;
        }

        return version;
    }
}
