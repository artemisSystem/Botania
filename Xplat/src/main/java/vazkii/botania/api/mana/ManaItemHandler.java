/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.api.mana;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import vazkii.botania.api.ServiceUtil;

import java.util.Collections;
import java.util.List;

public interface ManaItemHandler {
	ManaItemHandler INSTANCE = ServiceUtil.findService(ManaItemHandler.class, () -> new ManaItemHandler() {});

	static ManaItemHandler instance() {
		return INSTANCE;
	}

	/**
	 * Gets a list containing all mana-holding items in a player's inventory.
	 * Also includes a call to ManaItemsEvent, so other mods can add items from
	 * their player-associated inventories.
	 * 
	 * @return The list of items
	 */
	default List<ItemStack> getManaItems(Player player) {
		return Collections.emptyList();
	}

	/**
	 * Gets a list containing all mana-holding items in a player's accessories inventory.
	 * 
	 * @return The list of items
	 */
	default List<ItemStack> getManaAccesories(Player player) {
		return Collections.emptyList();
	}

	/**
	 * Requests mana from items in a given player's inventory.
	 * 
	 * @param manaToGet How much mana is to be requested, if less mana exists than this amount,
	 *                  the amount of mana existent will be returned instead, if you want exact values use
	 *                  {@link #requestManaExact}.
	 * @param remove    If true, the mana will be removed from the target item. Set to false to just check.
	 * @return The amount of mana received from the request.
	 */
	default int requestMana(ItemStack stack, Player player, int manaToGet, boolean remove) {
		return 0;
	}

	/**
	 * Requests an exact amount of mana from items in a given player's inventory.
	 * 
	 * @param manaToGet How much mana is to be requested, if less mana exists than this amount,
	 *                  false will be returned instead, and nothing will happen.
	 * @param remove    If true, the mana will be removed from the target item. Set to false to just check.
	 * @return If the request was succesful.
	 */
	default boolean requestManaExact(ItemStack stack, Player player, int manaToGet, boolean remove) {
		return false;
	}

	/**
	 * Dispatches mana to items in a given player's inventory. Note that this method
	 * does not automatically remove mana from the item which is exporting.
	 * 
	 * @param manaToSend How much mana is to be sent.
	 * @param add        If true, the mana will be added from the target item. Set to false to just check.
	 * @return The amount of mana actually sent.
	 */
	default int dispatchMana(ItemStack stack, Player player, int manaToSend, boolean add) {
		return 0;
	}

	/**
	 * Dispatches an exact amount of mana to items in a given player's inventory. Note that this method
	 * does not automatically remove mana from the item which is exporting.
	 * 
	 * @param manaToSend How much mana is to be sent.
	 * @param add        If true, the mana will be added from the target item. Set to false to just check.
	 * @return If an item received the mana sent.
	 */
	default boolean dispatchManaExact(ItemStack stack, Player player, int manaToSend, boolean add) {
		return false;
	}

	/**
	 * Requests mana from items in a given player's inventory. This version also
	 * checks for IManaDiscountArmor items equipped to lower the cost.
	 * 
	 * @param manaToGet How much mana is to be requested, if less mana exists than this amount,
	 *                  the amount of mana existent will be returned instead, if you want exact values use
	 *                  requestManaExact.
	 * @param remove    If true, the mana will be removed from the target item. Set to false to just check.
	 * @return The amount of mana received from the request.
	 */
	default int requestManaForTool(ItemStack stack, Player player, int manaToGet, boolean remove) {
		return 0;
	}

	/**
	 * Requests an exact amount of mana from items in a given player's inventory. This version also
	 * checks for IManaDiscountArmor items equipped to lower the cost.
	 * 
	 * @param manaToGet How much mana is to be requested, if less mana exists than this amount,
	 *                  false will be returned instead, and nothing will happen.
	 * @param remove    If true, the mana will be removed from the target item. Set to false to just check.
	 * @return If the request was succesful.
	 */
	default boolean requestManaExactForTool(ItemStack stack, Player player, int manaToGet, boolean remove) {
		return false;
	}

	/**
	 * Determines how many times the given tool can get the requested amount of mana. Takes discounts into
	 * consideration.
	 * 
	 * @param manaToGet How much mana is to be requested per invocation
	 * @return The number of invocations that could be executed before exhausting the player's mana available
	 */
	default int getInvocationCountForTool(ItemStack stack, Player player, int manaToGet) {
		return 0;
	}

	/**
	 * Gets the sum of all the discounts on IManaDiscountArmor items equipped
	 * on the player passed in. This discount can vary based on what the passed tool is.
	 */
	default float getFullDiscountForTools(Player player, ItemStack tool) {
		return 0;
	}

	default boolean hasProficiency(Player player, ItemStack manaItem) {
		return false;
	}
}
