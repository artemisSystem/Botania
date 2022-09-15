/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;

import vazkii.botania.common.lib.LibMisc;
import vazkii.botania.mixin.StatsAccessor;

public class BotaniaStats {
	public static final ResourceLocation CORPOREA_ITEMS_REQUESTED =
			StatsAccessor.botania_callRegisterCustom(LibMisc.MOD_ID + ":corporea_items_requested", StatFormatter.DEFAULT);

	public static final ResourceLocation LUMINIZER_ONE_CM =
			StatsAccessor.botania_callRegisterCustom(LibMisc.MOD_ID + ":luminizer_one_cm", StatFormatter.DISTANCE);

	public static final ResourceLocation TINY_POTATOES_PETTED =
			StatsAccessor.botania_callRegisterCustom(LibMisc.MOD_ID + ":tiny_potatoes_petted", StatFormatter.DEFAULT);

	public static void init() {

	}
}
