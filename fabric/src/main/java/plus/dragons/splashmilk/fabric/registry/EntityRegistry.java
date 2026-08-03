package plus.dragons.splashmilk.fabric.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;
import plus.dragons.splashmilk.entity.MilkBottleEntity;
import plus.dragons.splashmilk.fabric.SplashMilk;

public class EntityRegistry {
    public static EntityType<MIlkAreaEffectCloudEntity> MILK_AREA_EFFECT_CLOUD;
    public static EntityType<MilkBottleEntity> MILK_BOTTLE;

    public static void ini() {
        MILK_AREA_EFFECT_CLOUD = Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(SplashMilk.MOD_ID, "milk_area_effect_cloud"),
                EntityType.Builder.<MIlkAreaEffectCloudEntity>of(MIlkAreaEffectCloudEntity::new,MobCategory.MISC)
                        .sized(6.0f, 0.5f).fireImmune().updateInterval(10).build(ResourceKey.create(Registries.ENTITY_TYPE,Identifier.fromNamespaceAndPath(SplashMilk.MOD_ID, "milk_area_effect_cloud"))));
        MILK_BOTTLE = Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(SplashMilk.MOD_ID, "milk_bottle"),
                EntityType.Builder.<MilkBottleEntity>of(MilkBottleEntity::new,MobCategory.MISC)
                        .sized(0.5f, 0.5f).fireImmune().updateInterval(20).clientTrackingRange(10).build(ResourceKey.create(Registries.ENTITY_TYPE,Identifier.fromNamespaceAndPath(SplashMilk.MOD_ID, "milk_bottle"))));

    }
}
