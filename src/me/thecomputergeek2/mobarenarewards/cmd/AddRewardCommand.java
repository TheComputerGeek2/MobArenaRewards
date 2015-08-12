package me.thecomputergeek2.mobarenarewards.cmd;

import me.thecomputergeek2.mobarenarewards.ConfigManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.garbagemule.MobArena.Messenger;
import com.garbagemule.MobArena.Msg;
import com.garbagemule.MobArena.commands.Command;
import com.garbagemule.MobArena.commands.CommandInfo;
import com.garbagemule.MobArena.commands.Commands;
import com.garbagemule.MobArena.framework.Arena;
import com.garbagemule.MobArena.framework.ArenaMaster;

@CommandInfo(desc = "Set your hand item as a reward in <arena> at wave <#>", name = "addreward", pattern = "addreward", permission = "ma.rewards.addreward", usage = "/ma addreward <arena> <wave number>")
public class AddRewardCommand implements Command {

	@Override
	public boolean execute(ArenaMaster am, CommandSender sender, String... args) {
		if (!Commands.isPlayer(sender)) {
			Messenger.tell(sender, Msg.MISC_NOT_FROM_CONSOLE);
			return true;
		}

		if (args.length < 2) {
			return false;
		}

		String arenaName = args[0];
		String waveNumberInput = args[1];
		Arena a = am.getArenaWithName(arenaName);
		if (a == null) {
			Messenger.tell(sender, Msg.ARENA_DOES_NOT_EXIST);
			return false;
		}
		int waveNumber;
		try {
			waveNumber = Integer.parseInt(waveNumberInput);
		} catch (NumberFormatException e) {
			return false;
		}

		ItemStack handItem = ((Player) sender).getItemInHand();
		if (handItem == null) {
			Messenger.tell(sender, "You must have an item in your hand");
			return false;
		}

		ConfigManager.addRewardToWave(a, waveNumber, handItem);
		Messenger.tell(sender, "Item successfully added to arena, " + arenaName
				+ " on wave " + waveNumber + ".");

		return true;
	}

}
