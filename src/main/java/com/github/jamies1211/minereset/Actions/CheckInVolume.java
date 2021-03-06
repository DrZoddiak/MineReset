package com.github.jamies1211.minereset.Actions;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Created by Jamie on 05-Jul-16.
 */
public class CheckInVolume {

	public static Boolean checkInVolume (Player player, Vector3i positionSmall, Vector3i positionLarge) {
		return checkInVolume(player, positionSmall.getX(), positionSmall.getY(), positionSmall.getZ(),
				positionLarge.getX(), positionLarge.getY(), positionLarge.getZ());
	}

	public static Boolean checkInVolume (Player player, int xSmall, int ySmall, int zSmall, int xLarge, int yLarge, int zLarge) {

		Location<World> location = player.getLocation();

		return location.getX() >= xSmall && location.getX() - 1 <= xLarge &&
				location.getY() >= ySmall && location.getY() <= yLarge &&
				location.getZ() >= zSmall && location.getZ() - 1 <= zLarge;
	}
}
