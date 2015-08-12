package me.thecomputergeek2.mobarenarewards;

import me.thecomputergeek2.mobarenarewards.cmd.CmdUtil;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MobArenaRewards extends JavaPlugin {

	@Override
	public void onEnable() {
		ConfigManager.getInstance().setup(this);
		Bukkit.getPluginManager().registerEvents(new WaveListener(), this);
		// TODO setup the listeners

		CmdUtil.registerCommands();
	}

	@Override
	public void onDisable() {

	}

}
