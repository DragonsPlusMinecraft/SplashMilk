package plus.dragons.splashmilk.forge.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;
import plus.dragons.splashmilk.entity.MilkBottleEntity;
import plus.dragons.splashmilk.forge.SplashMilk;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SplashMilk.MOD_ID);
    public static final RegistryObject<EntityType<? extends MIlkAreaEffectCloudEntity>> MILK_AREA_EFFECT_CLOUD = ENTITIES.register("milk_area_effect_cloud",
            () -> EntityType.Builder.<MIlkAreaEffectCloudEntity>create(MIlkAreaEffectCloudEntity::new, SpawnGroup.MISC).makeFireImmune()
                    .setDimensions(6.0F, 0.5F).trackingTickInterval(10).setUpdateInterval(Integer.MAX_VALUE)
                    .build("milk_area_effect_cloud"));
    public static final RegistryObject<EntityType<? extends ThrownItemEntity>> MILK_BOTTLE = ENTITIES.register("milk_bottle",
            () -> EntityType.Builder.<MilkBottleEntity>create(MilkBottleEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.5F, 0.5F).trackingTickInterval(4).setUpdateInterval(20)
                    .build("milk_bottle"));
}
