package plus.dragons.splashmilk.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import plus.dragons.splashmilk.PlatformUtil;
import plus.dragons.splashmilk.entity.MilkBottleEntity;

public class ThrowableMilkBottle extends Item implements ProjectileItem {
    public ThrowableMilkBottle(String id) {
        super(PlatformUtil.milkBottleSetting().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath("splash_milk",id))));
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemstack = user.getItemInHand(hand);
        boolean isLingering = itemstack.getItem().equals(PlatformUtil.getLingerMIlkBottleItem().get());
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                isLingering ? SoundEvents.LINGERING_POTION_THROW : SoundEvents.SPLASH_POTION_THROW,
                SoundSource.NEUTRAL, 0.5F, (float) (0.4F / (Math.random() * 0.4F + 0.8F)));

        if (!world.isClientSide()) {
            Projectile.spawnProjectileFromRotation(MilkBottleEntity::new, (ServerLevel) world, itemstack, user, -20.0F, 0.5F, 1.0F);
        }

        itemstack.consume(1, user);

        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        return new MilkBottleEntity(world, pos.x(), pos.y(), pos.z(), stack);
    }
}
