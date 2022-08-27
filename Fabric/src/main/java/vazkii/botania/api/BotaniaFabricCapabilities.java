package vazkii.botania.api;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;

import vazkii.botania.api.block.ExoflameHeatable;
import vazkii.botania.api.block.HornHarvestable;
import vazkii.botania.api.block.HourglassTrigger;
import vazkii.botania.api.block.Wandable;
import vazkii.botania.api.item.IAvatarWieldable;
import vazkii.botania.api.item.IBlockProvider;
import vazkii.botania.api.item.ICoordBoundItem;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.mana.IManaCollisionGhost;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.IManaTrigger;
import vazkii.botania.api.mana.spark.ISparkAttachable;

public final class BotaniaFabricCapabilities {
	public static final ItemApiLookup<IAvatarWieldable, Unit> AVATAR_WIELDABLE = ItemApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "avatar_wieldable"), IAvatarWieldable.class, Unit.class);
	public static final ItemApiLookup<IBlockProvider, Unit> BLOCK_PROVIDER = ItemApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "block_provider"), IBlockProvider.class, Unit.class);
	public static final ItemApiLookup<ICoordBoundItem, Unit> COORD_BOUND_ITEM = ItemApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "coord_bound_item"),
			ICoordBoundItem.class, Unit.class);
	public static final ItemApiLookup<IManaItem, Unit> MANA_ITEM = ItemApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "mana_item"),
			IManaItem.class, Unit.class);
	public static final ItemApiLookup<IRelic, Unit> RELIC = ItemApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "relic"),
			IRelic.class, Unit.class);

	public static final BlockApiLookup<ExoflameHeatable, Unit> EXOFLAME_HEATABLE = BlockApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "exoflame_heatable"), ExoflameHeatable.class, Unit.class);
	public static final BlockApiLookup<HornHarvestable, Unit> HORN_HARVEST = BlockApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "horn_harvestable"), HornHarvestable.class, Unit.class);
	public static final BlockApiLookup<HourglassTrigger, Unit> HOURGLASS_TRIGGER = BlockApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "hourglass_trigger"), HourglassTrigger.class, Unit.class);
	public static final BlockApiLookup<IManaCollisionGhost, Unit> MANA_GHOST = BlockApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "mana_ghost"), IManaCollisionGhost.class, Unit.class);
	public static final BlockApiLookup<IManaReceiver, /* @Nullable */ Direction> MANA_RECEIVER = BlockApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "mana_receiver"), IManaReceiver.class, Direction.class);
	public static final BlockApiLookup<ISparkAttachable, Direction> SPARK_ATTACHABLE = BlockApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "spark_attachable"), ISparkAttachable.class, Direction.class);
	public static final BlockApiLookup<IManaTrigger, Unit> MANA_TRIGGER = BlockApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "mana_trigger"), IManaTrigger.class, Unit.class);
	public static final BlockApiLookup<Wandable, Unit> WANDABLE = BlockApiLookup.get(new ResourceLocation(BotaniaAPI.MODID, "wandable"), Wandable.class, Unit.class);

	private BotaniaFabricCapabilities() {}
}
