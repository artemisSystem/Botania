/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.client.integration.jei.crafting;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.item.IAncientWillContainer;
import vazkii.botania.common.crafting.recipe.AncientWillRecipe;
import vazkii.botania.common.item.ItemAncientWill;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AncientWillRecipeWrapper implements ICraftingCategoryExtension {
	private final ResourceLocation name;

	public AncientWillRecipeWrapper(AncientWillRecipe recipe) {
		this.name = recipe.getId();
	}

	@Nullable
	@Override
	public ResourceLocation getRegistryName() {
		return name;
	}

	@Override
	public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ICraftingGridHelper helper, @NotNull IFocusGroup focusGroup) {
		var foci = focusGroup.getFocuses(VanillaTypes.ITEM_STACK, RecipeIngredientRole.INPUT)
				.filter(f -> f.getTypedValue().getIngredient().getItem() instanceof ItemAncientWill)
				.map(f -> f.getTypedValue().getIngredient())
				.toList();

		var willStacks = !foci.isEmpty() ? foci : List.of(
				new ItemStack(ModItems.ancientWillAhrim),
				new ItemStack(ModItems.ancientWillDharok),
				new ItemStack(ModItems.ancientWillGuthan),
				new ItemStack(ModItems.ancientWillTorag),
				new ItemStack(ModItems.ancientWillVerac),
				new ItemStack(ModItems.ancientWillKaril)
		);

		var outputStacks = new ArrayList<ItemStack>();
		for (var will : !foci.isEmpty() ? foci : willStacks) {
			var stack = new ItemStack(ModItems.terrasteelHelm);
			((IAncientWillContainer) stack.getItem()).addAncientWill(stack, ((ItemAncientWill) will.getItem()).type);
			outputStacks.add(stack);
		}

		helper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK,
				List.of(Collections.singletonList(new ItemStack(ModItems.terrasteelHelm)), willStacks), 0, 0);
		helper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, outputStacks);
	}
}
