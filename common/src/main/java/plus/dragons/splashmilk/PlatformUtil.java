package plus.dragons.splashmilk;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;

import java.util.function.Supplier;

public class PlatformUtil {
    @ExpectPlatform
    public static Item.Settings milkBottleSetting() {
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
    public static Supplier<EntityType<? extends ThrownItemEntity>> getMIlkBottleEntityType() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static Supplier<EntityType<? extends MIlkAreaEffectCloudEntity>> getMilkCloudEntityType() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static Supplier<? extends ParticleEffect> getMilkCloudParticle() {
        throw new RuntimeException();
    }

}
