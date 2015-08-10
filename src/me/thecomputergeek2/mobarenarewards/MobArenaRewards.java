package me.thecomputergeek2.mobarenarewards;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MobArenaRewards extends JavaPlugin {

	@Override
	public void onEnable() {
		ConfigManager.getInstance().setup(this);
		Bukkit.getPluginManager().registerEvents(new WaveListener(), this);
		// TODO setup the listeners

		// TODO make a command to add the item stacks
	}

	@Override
	public void onDisable() {

	}

}
