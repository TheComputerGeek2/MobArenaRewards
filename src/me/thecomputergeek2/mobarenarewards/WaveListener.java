package me.thecomputergeek2.mobarenarewards;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.garbagemule.MobArena.events.NewWaveEvent;

public class WaveListener implements Listener {

	@EventHandler
	public void onNewWaveEvent(NewWaveEvent event) {
		//TODO check for items and add them if appropriate
		ConfigManager.addRewardsForWave(event);		
	}
	
}
