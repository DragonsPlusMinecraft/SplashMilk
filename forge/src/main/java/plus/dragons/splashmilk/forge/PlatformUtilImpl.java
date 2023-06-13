package plus.dragons.splashmilk.forge;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import plus.dragons.splashmilk.entity.MIlkAreaEffectCloudEntity;
import plus.dragons.splashmilk.forge.registry.EntityRegistry;
import plus.dragons.splashmilk.forge.registry.ItemRegistry;
import plus.dragons.splashmilk.forge.registry.ParticleTypeRegistry;

import java.util.function.Supplier;

public class PlatformUtilImpl {
    public static Item.Settings milkBottleSetting() {
        return new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE);
    }

    public static Supplier<Item> getLingerMIlkBottleItem() {
        return ItemRegistry.LINGERING_MILK_BOTTLE;
    }

    public static Supplier<Item> getSplashMIlkBottleItem() {
        return ItemRegistry.SPLASH_MILK_BOTTLE;
    }

    public static Supplier<EntityType<? extends ThrownItemEntity>> getMIlkBottleEntityType() {
        return EntityRegistry.MILK_BOTTLE;
    }

    public static Supplier<EntityType<? extends MIlkAreaEffectCloudEntity>> getMilkCloudEntityType() {
        return EntityRegistry.MILK_AREA_EFFECT_CLOUD;
    }

    public static Supplier<? extends ParticleEffect> getMilkCloudParticle() {
        return ParticleTypeRegistry.MILK_AREA_EFFECT;
    }
}
