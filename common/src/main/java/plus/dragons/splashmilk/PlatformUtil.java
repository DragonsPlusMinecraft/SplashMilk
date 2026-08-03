package plus.dragons.splashmilk;

import dev.architectury.injectables.annotations.ExpectPlatform;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;

import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;

public class PlatformUtil {
    @ExpectPlatform
    public static Item.Properties milkBottleSetting() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static Supplier<Item> getSplashMIlkBottleItem() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static Supplier<Item> getLingerMIlkBottleItem() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static Supplier<EntityType<? extends ThrowableItemProjectile>> getMIlkBottleEntityType() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static Supplier<EntityType<? extends MIlkAreaEffectCloudEntity>> getMilkCloudEntityType() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static Supplier<? extends ParticleOptions> getMilkCloudParticle() {
        throw new RuntimeException();
    }

}
