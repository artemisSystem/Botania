/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.fabric.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.PermanentBifrostBlock;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityFabricMixin {
	@Unique
	private static boolean bifrost = false;

	@ModifyVariable(method = "tick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"))
	private static Block captureBifrost(Block obj) {
		bifrost = obj == BotaniaBlocks.bifrostPerm;
		return obj;
	}

	@ModifyVariable(method = "tick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/DyeColor;getTextureDiffuseColors()[F"))
	private static float[] bifrostColor(float[] obj, Level level) {
		if (bifrost) {
			return ((PermanentBifrostBlock) BotaniaBlocks.bifrostPerm).getBeaconColorMultiplier(
					BotaniaBlocks.bifrostPerm.defaultBlockState(),
					level, BlockPos.ZERO, BlockPos.ZERO);
		}
		return obj;
	}
}
