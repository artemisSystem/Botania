package vazkii.botania.common;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class BotaniaDamageTypes {
	public static final Map<ResourceKey<DamageType>, DamageType> ALL = new HashMap<>();

	public static final ResourceKey<DamageType> PLAYER_ATTACK_ARMOR_PIERCING =
			register("player_attack_armor_piercing", new DamageType("player", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f));
	public static final ResourceKey<DamageType> RELIC_DAMAGE =
			register("relic_damage", new DamageType("botania-relic", DamageScaling.NEVER, 1f, DamageEffects.FREEZING));

	private static ResourceKey<DamageType> register(String id, DamageType damageType) {
		ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE, prefix(id));
		ALL.put(key, damageType);
		return key;
	}

	public static class Sources {

		private static Holder.Reference<DamageType> getHolder(RegistryAccess ra, ResourceKey<DamageType> key) {
			return ra.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
		}

		private static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey) {
			return new DamageSource(getHolder(ra, resourceKey));
		}

		private static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey, @Nullable Entity entity) {
			return new DamageSource(getHolder(ra, resourceKey), entity);
		}

		private static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey, @Nullable Entity entity, @Nullable Entity entity2) {
			return new DamageSource(getHolder(ra, resourceKey), entity, entity2);
		}

		public static DamageSource playerAttackArmorPiercing(RegistryAccess ra, Player player) {
			return source(ra, PLAYER_ATTACK_ARMOR_PIERCING, player);
		}

		public static DamageSource relicDamage(RegistryAccess ra) {
			return source(ra, RELIC_DAMAGE);
		}
	}
}
