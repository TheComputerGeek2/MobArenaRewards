package me.thecomputergeek2.mobarenarewards.cmd;

import me.thecomputergeek2.mobarenarewards.ConfigManager;

import com.garbagemule.MobArena.commands.Command;
import com.garbagemule.MobArena.commands.CommandHandler;

public class CmdUtil {

	public static CommandHandler getMaCommandHandler() {
		return ConfigManager.getMobArena().getCommandHandler();
	}

	public static void registerCommand(Class<? extends Command> c) {
		getMaCommandHandler().register(c);
	}

	public static void registerCommands() {
		registerCommand(AddRewardCommand.class);
	}

}
