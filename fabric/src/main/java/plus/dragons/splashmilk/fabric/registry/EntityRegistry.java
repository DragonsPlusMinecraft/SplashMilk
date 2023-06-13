package plus.dragons.splashmilk.fabric.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
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
        MILK_AREA_EFFECT_CLOUD = Registry.register(Registries.ENTITY_TYPE, new Identifier(SplashMilk.MOD_ID, "milk_area_effect_cloud"),
                FabricEntityTypeBuilder.<MIlkAreaEffectCloudEntity>create(SpawnGroup.MISC, MIlkAreaEffectCloudEntity::new)
                        .dimensions(EntityDimensions.fixed(6.0f, 0.5f)).fireImmune().trackRangeBlocks(10).build());
        MILK_BOTTLE = Registry.register(Registries.ENTITY_TYPE, new Identifier(SplashMilk.MOD_ID, "milk_bottle"),
                FabricEntityTypeBuilder.<MilkBottleEntity>create(SpawnGroup.MISC, MilkBottleEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).fireImmune().trackedUpdateRate(20).trackRangeBlocks(10).build());

    }
}