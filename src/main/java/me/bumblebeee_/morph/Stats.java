package me.bumblebeee_.morph;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class Stats {

    Messages msgs = new Messages();

    public void start() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Morph.pl, new Runnable() {
            @Override
            public void run() {
                sendStats();
            }
        }, 0, (60*20)*20);
    }

    public void sendStats() {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Morph.pl, new Runnable() {
            @Override
            public void run() {
                YamlConfiguration c = msgs.getYaml();
                UUID guid;
                if (c.getString("GUID") == null) {
                    guid = UUID.randomUUID();
                    c.set("GUID", String.valueOf(guid));
                    msgs.saveYaml(c);
                } else {
                    guid = UUID.fromString(c.getString("GUID"));
                }

                String args = "Plugin=Morph&GUID=" + guid + "&OS=" + getOS() + "&OSVersion=" + getOSVersion() + "&Arch=" + getArch() +
                        "&Cores=" + getCores() + "&ServerVersion=" + getServerVersion() + "&JavaVersion=" +
                        getJavaVersion() + "&Players=" + getOnlinePlayers() + "&PluginVersion=" + getPluginVersion()
                        + "&OnlineMode=" + getOnlineMode() + "&Status=Online&bStats=" + getBStats();

                try {
                    URL url = new URL("http://bumblebee.gq/stats/recieveStats.php");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestProperty("User-Agent", "SubmitMorphStats2");
                    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                    writer.write(args);
                    writer.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    writer.close();
                    reader.close();
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }
            }
        });
    }


    public String getOS() {
        return System.getProperty("os.name");
    }

    public String getArch() {
        String arch = System.getProperty("os.arch");
        if (arch.equals("amd64")) {
            arch = "x86_64";
        }
        return arch;
    }

    public String getBStats() {
        File f = new File(Morph.pl.getDataFolder().getParentFile() + File.separator + "bStats" + File.separator + "config.yml");
        if (!f.exists())
            return "0";

        YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
        boolean enabled = c.getBoolean("enabled");
        return enabled ? "1" : "0";
    }

    public String getOSVersion() {
        return System.getProperty("os.version");
    }

    public String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public int getCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    public int getOnlinePlayers() {
        return Bukkit.getServer().getOnlinePlayers().size();
    }

    public String getPluginVersion() {
        return Morph.pl.getDescription().getVersion();
    }

    public String getServerVersion() {
        return Bukkit.getServer().getBukkitVersion();
    }

    public boolean getOnlineMode() {
        return Bukkit.getServer().getOnlineMode();
    }


}
