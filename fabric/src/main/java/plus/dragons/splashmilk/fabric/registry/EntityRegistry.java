package plus.dragons.splashmilk.fabric.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;
import plus.dragons.splashmilk.entity.MilkBottleEntity;
import plus.dragons.splashmilk.fabric.SplashMilk;

public class EntityRegistry {
    public static EntityType<MIlkAreaEffectCloudEntity> MILK_AREA_EFFECT_CLOUD;
    public static EntityType<MilkBottleEntity> MILK_BOTTLE;

    public static void ini() {
        MILK_AREA_EFFECT_CLOUD = Registry.register(Registries.ENTITY_TYPE, Identifier.of(SplashMilk.MOD_ID, "milk_area_effect_cloud"),
                EntityType.Builder.<MIlkAreaEffectCloudEntity>create(MIlkAreaEffectCloudEntity::new,SpawnGroup.MISC)
                        .dimensions(6.0f, 0.5f).makeFireImmune().trackingTickInterval(10).build());
        MILK_BOTTLE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(SplashMilk.MOD_ID, "milk_bottle"),
                EntityType.Builder.<MilkBottleEntity>create(MilkBottleEntity::new,SpawnGroup.MISC)
                        .dimensions(0.5f, 0.5f).makeFireImmune().trackingTickInterval(20).maxTrackingRange(10).build());

    }
}