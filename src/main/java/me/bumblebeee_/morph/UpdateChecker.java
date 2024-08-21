package me.bumblebeee_.morph;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {

    public static boolean update = false;

    public void run(String rid) {
        checkForUpdate(rid);
        if (update) {
            Bukkit.getServer().getLogger().info("-----------------------------------");
            Bukkit.getServer().getLogger().info("");
            Bukkit.getServer().getLogger().info("****Morph is out of date!****");
            Bukkit.getServer().getLogger().info("You are running Morph " + getVersion() + ". The latest version is " +
                    getUpdatedVersion(rid));
            Bukkit.getServer().getLogger().info("");
            Bukkit.getServer().getLogger().info("-----------------------------------");
        }
    }

    public void checkForUpdate(String rid) {
        if (getUpdatedVersion(rid) == null)
            return;
        double updated = Double.parseDouble(getUpdatedVersion(rid));
        double ver;
        if (getVersion().contains("-")) {
            ver = Double.parseDouble(getVersion().split("-")[0]);
        } else {
            ver = Double.parseDouble(getVersion());
        }
        if (updated > ver) {
            update = true;
        }
    }

    public String getUpdatedVersion(String rid) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "https://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream()
                    .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + rid)
                            .getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(
                    con.getInputStream())).readLine();
            if (version.length() <= 7) {
                return version;
            }
        } catch (Exception ex) {
            Bukkit.getServer().getLogger().info("[Morphy] Failed to check for a update on spigot.");
        }
        return null;
    }

    public String getVersion() {
        return Morph.pl.getDescription().getVersion();
    }

}
