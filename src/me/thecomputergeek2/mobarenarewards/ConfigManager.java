package me.thecomputergeek2.mobarenarewards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.garbagemule.MobArena.MAUtils;
import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.events.NewWaveEvent;
import com.garbagemule.MobArena.framework.Arena;

public class ConfigManager {

	public enum ConfigKey {
		ARENAS("arenas");
		
		;
		private String value;
		
		private ConfigKey(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	private static ConfigManager instance = new ConfigManager();

	private ConfigManager() {
	}

	public static ConfigManager getInstance() {
		return instance;
	}
	
	private MobArenaRewards plugin;
	
	private MobArena ma;
	
	public static MobArenaRewards getPlugin() {
		return instance.plugin;
	}
	
	public void setup(MobArenaRewards plugin) {
		this.plugin = plugin;
		this.ma = (MobArena) Bukkit.getPluginManager().getPlugin("MobArena");
		//make sure each arena has a section in the config
		createArenaSections();
	}
	
	public static FileConfiguration getConfig() {
		return instance.plugin.getConfig();
	}
	
	public static void saveConfig() {
		instance.plugin.saveConfig();
	}
	
	
	public static void createArenaSections() {
		for (Arena a: instance.ma.getArenaMaster().getArenas()) {
			String name = a.arenaName();
			if (!getConfig().contains(ConfigKey.ARENAS + "." + name)) {
				getConfig().createSection(ConfigKey.ARENAS + "." + name);
			}
		}
		saveConfig();
	}
	
	public static ConfigurationSection getSectionForArena(Arena a) {
		return getConfig().getConfigurationSection(ConfigKey.ARENAS + "." + a.arenaName());
	}
	
	public static Set<ItemStack> getItemStacksForWave(Arena a, int waveNumber) {
		ConfigurationSection arenaSection = getSectionForArena(a);
		if (!isRewardForWave(a, waveNumber)) {
			return null;
		}
		ConfigurationSection wave = arenaSection.getConfigurationSection(waveNumber + "");
		HashSet<ItemStack> items = new HashSet<ItemStack>();
		for (String s: wave.getKeys(false)) {
			ItemStack i = wave.getConfigurationSection(s).getItemStack("item");
			if (i != null) {
				items.add(i);
			}
		}
		return items;
	}
	
	public static void addRewardToWave(Arena a, int waveNumber, ItemStack stack) {
		ConfigurationSection arenaSection = getSectionForArena(a);
		if (arenaSection == null) {
			return;
		}
		ConfigurationSection waveSection = arenaSection.getConfigurationSection(waveNumber + "");
		String newKey = getNextRewardKey(waveSection);
		waveSection.set(newKey, stack.serialize());
		ConfigManager.saveConfig();
	}
	
	public static String getNextRewardKey(ConfigurationSection wave) {
		Set<String> keys = wave.getKeys(false);
		int currentMax = 1;
		for (String s: keys) {
			try {
				int value = Integer.parseInt(s);
				currentMax = Math.max(currentMax, value);
			} catch (NumberFormatException nfe) {
				
			}
		}
		return currentMax + "";
	}
	
	public static boolean isRewardForWave(Arena a, int wave) {
		return getSectionForArena(a).getKeys(false).contains(wave + "");
	}
	
	public static void addRewardsForWave(NewWaveEvent event) {
		if (isRewardForWave(event.getArena(), event.getWaveNumber())) {
			Arena arena = event.getArena();
			Set<ItemStack> items = getItemStacksForWave(arena, event.getWaveNumber());
			ArrayList<ItemStack> itemList = new ArrayList<ItemStack>(items);
			for (Player p: arena.getPlayersInArena()) {
				arena.getRewardManager().addReward(p, MAUtils.getRandomReward(itemList));
			}
		}
	}
	
	public static MobArena getMobArena() {
		return instance.ma;
	}
	

}
