/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.fabric.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import vazkii.botania.common.BotaniaDamageTypes;
import vazkii.botania.data.*;
import vazkii.botania.data.recipes.*;

import java.util.concurrent.CompletableFuture;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {
	private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.DAMAGE_TYPE, bootstapContext -> BotaniaDamageTypes.ALL.forEach(bootstapContext::register));

	static class DynamicRegistryProvider implements DataProvider {
		private final CompletableFuture<HolderLookup.Provider> registriesFuture;

		public DynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			this.registriesFuture = registriesFuture;
		}

		@Override
		public CompletableFuture<?> run(CachedOutput cachedOutput) {
			return registriesFuture.thenApply(provider -> BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), provider));
		}

		@Override
		public String getName() {
			return "Dynamic registries";
		}
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		if (System.getProperty("botania.xplat_datagen") != null) {
			configureXplatDatagen(generator.createPack());
		} else {
			configureFabricDatagen(generator.createPack());
		}
	}

	private static void configureFabricDatagen(FabricDataGenerator.Pack pack) {
		pack.addProvider((PackOutput output) -> new FabricBlockLootProvider(output));
		var blockTagProvider = pack.addProvider(FabricBlockTagProvider::new);
		pack.addProvider((output, registriesFuture) -> new FabricItemTagProvider(output, registriesFuture, blockTagProvider.contentsGetter()));
		pack.addProvider((PackOutput output) -> new FabricRecipeProvider(output));
		pack.addProvider(FabricBiomeTagProvider::new);
	}

	private static void configureXplatDatagen(FabricDataGenerator.Pack pack) {
		pack.addProvider(DynamicRegistryProvider::new);
		pack.addProvider((PackOutput output) -> new BlockLootProvider(output));
		BlockTagProvider blockTagProvider = pack.addProvider(BlockTagProvider::new);
		pack.addProvider((output, registriesFuture) -> new ItemTagProvider(output, registriesFuture, blockTagProvider.contentsGetter()));
		pack.addProvider(EntityTagProvider::new);
		pack.addProvider(BannerPatternTagsProvider::new);
		pack.addProvider(BiomeTagProvider::new);
		pack.addProvider(DamageTypeTagProvider::new);
		pack.addProvider((PackOutput output) -> new StonecuttingProvider(output));
		pack.addProvider((PackOutput output) -> new CraftingRecipeProvider(output));
		pack.addProvider((PackOutput output) -> new SmeltingProvider(output));
		pack.addProvider((PackOutput output) -> new ElvenTradeProvider(output));
		pack.addProvider((PackOutput output) -> new ManaInfusionProvider(output));
		pack.addProvider((PackOutput output) -> new PureDaisyProvider(output));
		pack.addProvider((PackOutput output) -> new BrewProvider(output));
		pack.addProvider((PackOutput output) -> new PetalApothecaryProvider(output));
		pack.addProvider((PackOutput output) -> new RunicAltarProvider(output));
		pack.addProvider((PackOutput output) -> new TerrestrialAgglomerationProvider(output));
		pack.addProvider((PackOutput output) -> new OrechidProvider(output));
		pack.addProvider((PackOutput output) -> new BlockstateProvider(output));
		pack.addProvider((PackOutput output) -> new FloatingFlowerModelProvider(output));
		pack.addProvider((PackOutput output) -> new ItemModelProvider(output));
		pack.addProvider(AdvancementProvider::create);
	}
}
