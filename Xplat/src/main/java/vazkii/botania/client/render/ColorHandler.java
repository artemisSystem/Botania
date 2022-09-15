/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.client.render;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.BrewItem;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.PlatformBlock;
import vazkii.botania.common.block.block_entity.PlatformBlockEntity;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.block.decor.PetalBlock;
import vazkii.botania.common.block.mana.ManaPoolBlock;
import vazkii.botania.common.brew.BotaniaBrews;
import vazkii.botania.common.helper.ColorHelper;
import vazkii.botania.common.item.*;
import vazkii.botania.common.item.equipment.bauble.TaintedBloodPendantItem;
import vazkii.botania.common.item.equipment.tool.terrasteel.TerraShattererItem;
import vazkii.botania.common.item.lens.ItemLens;
import vazkii.botania.common.item.material.ItemPetal;
import vazkii.botania.mixin.client.AccessorMinecraft;
import vazkii.botania.xplat.IXplatAbstractions;

public final class ColorHandler {
	public interface BlockHandlerConsumer {
		void register(BlockColor handler, Block... blocks);
	}

	public interface ItemHandlerConsumer {
		void register(ItemColor handler, ItemLike... items);
	}

	public static void submitBlocks(BlockHandlerConsumer blocks) {
		// [VanillaCopy] BlockColors for vine
		BlockColor vineColor = (state, world, pos, tint) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor();
		blocks.register(vineColor, BotaniaBlocks.solidVines);

		// Pool
		blocks.register(
				(state, world, pos, tintIndex) -> {
					if (tintIndex != 0) {
						return -1;
					}

					int color = ColorHelper.getColorValue(DyeColor.WHITE);
					if (world != null && pos != null) {
						BlockEntity te = world.getBlockEntity(pos);
						if (te instanceof ManaPoolBlockEntity pool) {
							color = ColorHelper.getColorValue(pool.getColor());
						}
					}
					if (((ManaPoolBlock) state.getBlock()).variant == ManaPoolBlock.Variant.FABULOUS) {
						float time = (ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks) * 0.005F;
						int fabulousColor = Mth.hsvToRgb(time - (int) time, 0.6F, 1F);
						return vazkii.botania.common.helper.MathHelper.multiplyColor(fabulousColor, color);
					}
					return color;
				},
				BotaniaBlocks.manaPool, BotaniaBlocks.creativePool, BotaniaBlocks.dilutedPool, BotaniaBlocks.fabulousPool
		);

		// Spreader
		blocks.register(
				(state, world, pos, tintIndex) -> {
					float time = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks;
					return Mth.hsvToRgb(time * 5 % 360 / 360F, 0.4F, 0.9F);
				},
				BotaniaBlocks.gaiaSpreader
		);

		// Petal Block
		blocks.register((state, world, pos, tintIndex) -> tintIndex == 0 ? ColorHelper.getColorValue(((PetalBlock) state.getBlock()).color) : -1,
				BotaniaBlocks.petalBlockWhite, BotaniaBlocks.petalBlockOrange, BotaniaBlocks.petalBlockMagenta, BotaniaBlocks.petalBlockLightBlue,
				BotaniaBlocks.petalBlockYellow, BotaniaBlocks.petalBlockLime, BotaniaBlocks.petalBlockPink, BotaniaBlocks.petalBlockGray,
				BotaniaBlocks.petalBlockSilver, BotaniaBlocks.petalBlockCyan, BotaniaBlocks.petalBlockPurple, BotaniaBlocks.petalBlockBlue,
				BotaniaBlocks.petalBlockBrown, BotaniaBlocks.petalBlockGreen, BotaniaBlocks.petalBlockRed, BotaniaBlocks.petalBlockBlack
		);

		// Platforms
		blocks.register(
				(state, world, pos, tintIndex) -> {
					if (world != null && pos != null) {
						BlockEntity tile = world.getBlockEntity(pos);
						if (tile instanceof PlatformBlockEntity camo) {
							BlockState camoState = camo.getCamoState();
							if (camoState != null) {
								return camoState.getBlock() instanceof PlatformBlock
										? 0xFFFFFF
										: Minecraft.getInstance().getBlockColors().getColor(camoState, world, pos, tintIndex);
							}
						}
					}
					return 0xFFFFFF;
				}, BotaniaBlocks.abstrusePlatform, BotaniaBlocks.spectralPlatform, BotaniaBlocks.infrangiblePlatform);
	}

	public static void submitItems(ItemHandlerConsumer items) {
		items.register((s, t) -> t == 0 ? Mth.hsvToRgb(ClientTickHandler.ticksInGame * 2 % 360 / 360F, 0.25F, 1F) : -1,
				ModItems.lifeEssence, ModItems.gaiaIngot);

		items.register((s, t) -> t == 1 ? ColorHelper.getColorValue(DyeColor.byId(ItemTwigWand.getColor1(s)))
				: t == 2 ? ColorHelper.getColorValue(DyeColor.byId(ItemTwigWand.getColor2(s)))
				: -1,
				ModItems.twigWand, ModItems.dreamwoodWand);

		ItemColor petalHandler = (s, t) -> t == 0 ? ColorHelper.getColorValue(((ItemPetal) s.getItem()).color) : -1;
		for (DyeColor color : DyeColor.values()) {
			items.register(petalHandler, ModItems.getPetal(color));
		}

		items.register((s, t) -> t == 0 ? Minecraft.getInstance().getBlockColors().getColor(((BlockItem) s.getItem()).getBlock().defaultBlockState(), null, null, t) : -1,
				BotaniaBlocks.petalBlockWhite, BotaniaBlocks.petalBlockOrange, BotaniaBlocks.petalBlockMagenta, BotaniaBlocks.petalBlockLightBlue,
				BotaniaBlocks.petalBlockYellow, BotaniaBlocks.petalBlockLime, BotaniaBlocks.petalBlockPink, BotaniaBlocks.petalBlockGray,
				BotaniaBlocks.petalBlockSilver, BotaniaBlocks.petalBlockCyan, BotaniaBlocks.petalBlockPurple, BotaniaBlocks.petalBlockBlue,
				BotaniaBlocks.petalBlockBrown, BotaniaBlocks.petalBlockGreen, BotaniaBlocks.petalBlockRed, BotaniaBlocks.petalBlockBlack,
				BotaniaBlocks.manaPool, BotaniaBlocks.creativePool, BotaniaBlocks.dilutedPool, BotaniaBlocks.fabulousPool, BotaniaBlocks.gaiaSpreader);

		items.register((s, t) -> {
			if (t == 1) {
				var manaItem = IXplatAbstractions.INSTANCE.findManaItem(s);
				return Mth.hsvToRgb(0.528F, (float) manaItem.getMana() / (float) ManaPoolBlockEntity.MAX_MANA, 1F);
			}
			return -1;
		}, ModItems.manaMirror);

		items.register((s, t) -> {
			if (t == 1) {
				var manaItem = IXplatAbstractions.INSTANCE.findManaItem(s);
				return Mth.hsvToRgb(0.528F, (float) manaItem.getMana() / (float) ItemManaTablet.MAX_MANA, 1F);
			}
			return -1;
		}, ModItems.manaTablet);

		items.register((s, t) -> t == 0 ? Mth.hsvToRgb(0.55F, ((float) s.getMaxDamage() - (float) s.getDamageValue()) / (float) s.getMaxDamage() * 0.5F, 1F) : -1, ModItems.spellCloth);

		items.register((s, t) -> {
			if (t != 1) {
				return -1;
			}

			Brew brew = ((BrewItem) s.getItem()).getBrew(s);
			if (brew == BotaniaBrews.fallbackBrew) {
				return s.getItem() instanceof TaintedBloodPendantItem ? 0xC6000E : 0x989898;
			}

			int color = brew.getColor(s);
			double speed = s.is(ModItems.brewFlask) || s.is(ModItems.brewVial) ? 0.1 : 0.2;
			int add = (int) (Math.sin(ClientTickHandler.ticksInGame * speed) * 24);

			int r = Math.max(0, Math.min(255, (color >> 16 & 0xFF) + add));
			int g = Math.max(0, Math.min(255, (color >> 8 & 0xFF) + add));
			int b = Math.max(0, Math.min(255, (color & 0xFF) + add));

			return r << 16 | g << 8 | b;
		}, ModItems.bloodPendant, ModItems.incenseStick, ModItems.brewFlask, ModItems.brewVial);

		items.register((s, t) -> {
			ItemStack lens = ItemManaGun.getLens(s);
			if (!lens.isEmpty() && t == 0) {
				return ((AccessorMinecraft) Minecraft.getInstance()).getItemColors().getColor(lens, t);
			}

			if (t == 2) {
				BurstProperties props = ((ItemManaGun) s.getItem()).getBurstProps(Minecraft.getInstance().player, s, false, InteractionHand.MAIN_HAND);

				float mul = (float) (Math.sin((double) ClientTickHandler.ticksInGame / 5) * 0.15F);
				int c = (int) (255 * mul);

				int r = (props.color >> 16 & 0xFF) + c;
				int g = (props.color >> 8 & 0xFF) + c;
				int b = (props.color & 0xFF) + c;

				int cr = Mth.clamp(r, 0, 255);
				int cg = Mth.clamp(g, 0, 255);
				int cb = Mth.clamp(b, 0, 255);

				return cr << 16 | cg << 8 | cb;
			} else {
				return -1;
			}
		}, ModItems.manaGun);

		items.register((s, t) -> t == 1 ? Mth.hsvToRgb(0.75F, 1F, 1.5F - (float) Math.min(1F, Math.sin(Util.getMillis() / 100D) * 0.5 + 1.2F)) : -1, ModItems.enderDagger);

		items.register((s, t) -> t == 1 && TerraShattererItem.isEnabled(s) ? Mth.hsvToRgb(0.375F, (float) Math.min(1F, Math.sin(Util.getMillis() / 200D) * 0.5 + 1F), 1F) : -1, ModItems.terraPick);

		ItemColor lensHandler = (s, t) -> t == 0 ? ((ItemLens) s.getItem()).getLensColor(s, Minecraft.getInstance().level) : -1;
		items.register(lensHandler, ModItems.lensNormal, ModItems.lensSpeed, ModItems.lensPower, ModItems.lensTime,
				ModItems.lensEfficiency, ModItems.lensBounce, ModItems.lensGravity, ModItems.lensMine,
				ModItems.lensDamage, ModItems.lensPhantom, ModItems.lensMagnet, ModItems.lensExplosive,
				ModItems.lensInfluence, ModItems.lensWeight, ModItems.lensPaint, ModItems.lensFire,
				ModItems.lensPiston, ModItems.lensLight, ModItems.lensWarp, ModItems.lensRedirect,
				ModItems.lensFirework, ModItems.lensFlare, ModItems.lensMessenger, ModItems.lensTripwire,
				ModItems.lensStorm);
	}

	private ColorHandler() {}

}
