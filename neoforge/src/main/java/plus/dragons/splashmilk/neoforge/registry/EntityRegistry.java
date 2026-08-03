package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;
import plus.dragons.splashmilk.entity.MilkBottleEntity;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.function.Supplier;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, SplashMilk.MOD_ID);
    public static final Supplier<EntityType<? extends MIlkAreaEffectCloudEntity>> MILK_AREA_EFFECT_CLOUD = ENTITIES.register("milk_area_effect_cloud",
            () -> EntityType.Builder.<MIlkAreaEffectCloudEntity>of(MIlkAreaEffectCloudEntity::new, MobCategory.MISC).fireImmune()
                    .sized(6.0F, 0.5F).updateInterval(10).setUpdateInterval(Integer.MAX_VALUE)
                    .build(ResourceKey.create(BuiltInRegistries.ENTITY_TYPE.key(), Identifier.fromNamespaceAndPath(SplashMilk.MOD_ID,"milk_area_effect_cloud"))));
    public static final Supplier<EntityType<? extends ThrowableItemProjectile>> MILK_BOTTLE = ENTITIES.register("milk_bottle",
            () -> EntityType.Builder.<MilkBottleEntity>of(MilkBottleEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).updateInterval(4).setUpdateInterval(20)
                    .build(ResourceKey.create(BuiltInRegistries.ENTITY_TYPE.key(), Identifier.fromNamespaceAndPath(SplashMilk.MOD_ID,"milk_bottle"))));
}
