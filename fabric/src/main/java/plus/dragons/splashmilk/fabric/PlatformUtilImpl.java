package plus.dragons.splashmilk.fabric;

import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;
import plus.dragons.splashmilk.fabric.registry.EntityRegistry;
import plus.dragons.splashmilk.fabric.registry.ItemRegistry;
import plus.dragons.splashmilk.fabric.registry.ParticleTypeRegistry;

import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class PlatformUtilImpl {
    public static Item.Properties milkBottleSetting() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE);
    }

    public static Supplier<Item> getLingerMIlkBottleItem() {
        return () -> ItemRegistry.LINGERING_MILK_BOTTLE;
    }

    public static Supplier<Item> getSplashMIlkBottleItem() {
        return () -> ItemRegistry.SPLASH_MILK_BOTTLE;
    }

    public static Supplier<EntityType<? extends ThrowableItemProjectile>> getMIlkBottleEntityType() {
        return () -> EntityRegistry.MILK_BOTTLE;
    }

    public static Supplier<EntityType<? extends MIlkAreaEffectCloudEntity>> getMilkCloudEntityType() {
        return () -> EntityRegistry.MILK_AREA_EFFECT_CLOUD;
    }

    public static Supplier<? extends ParticleOptions> getMilkCloudParticle() {
        return () -> ParticleTypeRegistry.MILK_AREA_EFFECT;
    }
}
