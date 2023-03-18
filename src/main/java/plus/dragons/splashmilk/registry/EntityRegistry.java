package plus.dragons.splashmilk.registry;

import plus.dragons.splashmilk.SplashMilk;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;
import plus.dragons.splashmilk.entity.MilkBottleEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SplashMilk.MOD_ID);
    public static final RegistryObject<EntityType<MIlkAreaEffectCloudEntity>> MILK_AREA_EFFECT_CLOUD = ENTITIES.register("milk_area_effect_cloud",
            () -> EntityType.Builder.<MIlkAreaEffectCloudEntity>of(MIlkAreaEffectCloudEntity::new, MobCategory.MISC)
                    .fireImmune().sized(6.0F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
                    .build("milk_area_effect_cloud"));
    public static final RegistryObject<EntityType<MilkBottleEntity>> MILK_BOTTLE = ENTITIES.register("milk_bottle",
            () -> EntityType.Builder.<MilkBottleEntity>of(MilkBottleEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build("milk_bottle"));
}
