package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Runnables;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        boolean rain = e.toWeatherState();
        if (rain) {
            Runnables.raining = true;
        } else {
            Runnables.raining = false;
        }
    }

}
