package com.github.jamies1211.minereset.Actions;

import com.github.jamies1211.minereset.MineReset;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.property.block.MatterProperty;

import java.util.Optional;

/**
 * Created by Jamie on 05-Jul-16.
 */
public class IsSolidBlock {

	public static boolean isSold(BlockType block) {

		ConfigurationNode config = MineReset.plugin.getConfig();

		Optional<MatterProperty> matterProperty = block.getProperty(MatterProperty.class);

		if (matterProperty.get().getValue() == MatterProperty.Matter.GAS) {
			return false;
		} else if (matterProperty.get().getValue() == MatterProperty.Matter.LIQUID) {
			return false;
		}



		if (MineReset.airBlocks.contains(block.getName())) {
			return false;
		}

		return true;
	}
}
