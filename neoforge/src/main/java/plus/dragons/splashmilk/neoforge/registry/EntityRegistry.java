package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.registry.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;
import plus.dragons.splashmilk.entity.MilkBottleEntity;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.function.Supplier;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, SplashMilk.MOD_ID);
    public static final Supplier<EntityType<? extends MIlkAreaEffectCloudEntity>> MILK_AREA_EFFECT_CLOUD = ENTITIES.register("milk_area_effect_cloud",
            () -> EntityType.Builder.<MIlkAreaEffectCloudEntity>create(MIlkAreaEffectCloudEntity::new, SpawnGroup.MISC).makeFireImmune()
                    .setDimensions(6.0F, 0.5F).trackingTickInterval(10).setUpdateInterval(Integer.MAX_VALUE)
                    .build("milk_area_effect_cloud"));
    public static final Supplier<EntityType<? extends ThrownItemEntity>> MILK_BOTTLE = ENTITIES.register("milk_bottle",
            () -> EntityType.Builder.<MilkBottleEntity>create(MilkBottleEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.5F, 0.5F).trackingTickInterval(4).setUpdateInterval(20)
                    .build("milk_bottle"));
}
