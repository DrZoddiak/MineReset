package com.github.jamies1211.minereset.Commands.MineSetupCommands;

import com.github.jamies1211.minereset.Actions.GetMineGroup;
import com.github.jamies1211.minereset.Config.GeneralDataConfig;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.minereset.Messages.*;

/**
 * Created by Jamie on 28-May-16.
 */
public class MineSetupSmartFill implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		ConfigurationNode config = GeneralDataConfig.getConfig().get();

		final String mine = args.<String>getOne("name").get().toUpperCase();
		final boolean smartFillEnabled = args.<Boolean>getOne("enabled").get();
		final int radius = args.<Integer>getOne("radius").get();
		final boolean smartFillOnlyAir = args.<Boolean>getOne("onlyAir").get();

		String group = GetMineGroup.getMineGroup(mine);

		if (group != null) {
			config.getNode("4 - MineGroups", group, mine, "SmartFill").setValue(smartFillEnabled);
			config.getNode("4 - MineGroups", group, mine, "SmartFillRadius").setValue(radius);
			config.getNode("4 - MineGroups", group, mine, "SmartFillOnlyAir").setValue(smartFillOnlyAir);
			GeneralDataConfig.getConfig().save();
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(minePrefix + redefinedMine.replace("%mine%", mine)));
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(minePrefix + mineDoesNotExist.replace("%mine%", mine)));
		}

		return CommandResult.success();
	}
}