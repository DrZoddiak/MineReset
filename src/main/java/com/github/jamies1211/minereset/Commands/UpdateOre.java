package com.github.jamies1211.minereset.Commands;

import com.github.jamies1211.minereset.Messages;
import com.github.jamies1211.minereset.MineReset;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.HashMap;

/**
 * Created by Jamie on 28-May-16.
 */
public class UpdateOre implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		ConfigurationNode config = MineReset.plugin.getConfig();

		final String mine = args.<String>getOne("name").get().toUpperCase();
		final double percentage = args.<Double>getOne("percentage").get();

		String group = null;
		if (src instanceof Player) {

			Player player = (Player) src;

			String block = player.getLocation().sub(0, 1, 0).getBlock().toString();

			if (percentage == 0) {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Messages.MinePrefix + block + " " + Messages.OreRemoved));
			} else if (percentage > 0) {

				for (final Object groupObject : config.getNode("4 - MineGroups").getChildrenMap().keySet()) {
					if (config.getNode("4 - MineGroups", groupObject.toString()).getChildrenMap().containsKey(mine)) {
						group = groupObject.toString();
					}
				}

				if (group != null) {

					String itemIndex = null;

					for (final Object itemObject : config.getNode("4 - MineGroups", group, mine, "ores").getChildrenMap().keySet()) {
						if (!itemObject.toString().equalsIgnoreCase("fallback")) {
							if (config.getNode("4 - MineGroups", group, mine, "ores", itemObject, "BlockState").getString().equalsIgnoreCase(block)) {
								itemIndex = itemObject.toString();
							}
						}
					}

					if (itemIndex != null) {
						/** Total up percentages */
						float currentFullPercentage = 0;

						for (final Object groupItems : config.getNode("4 - MineGroups", group, mine, "ores").getChildrenMap().keySet()) { // For all ores in a mine
							if ((!groupItems.toString().equalsIgnoreCase("fallback")) && groupItems != block) { // If it is not the fallback
								currentFullPercentage += config.getNode("4 - MineGroups", group, mine, "ores", groupItems.toString(), "percentage").getFloat(); // Totals up the percentage.
							}
						}

						if (currentFullPercentage + percentage <= 100) {

							config.getNode("4 - MineGroups", group, mine, "ores", itemIndex, "percentage").setValue(percentage);
							MineReset.plugin.save();
							src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Messages.MinePrefix + block + " in " + mine + " updated to " + percentage + "%"));
						} else {
							src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Messages.MinePrefix + Messages.OrePrecentageError));
						}

					} else {
						src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Messages.MinePrefix + block + " " + Messages.OreNotInMine + " " + mine));
					}
				} else {
					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Messages.MinePrefix + mine + " " + Messages.MineDoesNotExist));
				}
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Messages.MinePrefix + Messages.PercentageTooSmallUpdate));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Messages.MinePrefix + Messages.PlayerOnlyCommand));
		}

		return CommandResult.success();
	}
}