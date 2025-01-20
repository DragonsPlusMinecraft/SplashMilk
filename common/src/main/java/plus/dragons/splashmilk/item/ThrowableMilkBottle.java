package plus.dragons.splashmilk.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import plus.dragons.splashmilk.PlatformUtil;
import plus.dragons.splashmilk.entity.MilkBottleEntity;

public class ThrowableMilkBottle extends Item implements ProjectileItem {
    public ThrowableMilkBottle(String id) {
        super(PlatformUtil.milkBottleSetting().registryKey(RegistryKey.of(Registries.ITEM.getKey(), Identifier.of("splash_milk",id))));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        boolean isLingering = itemstack.getItem().equals(PlatformUtil.getLingerMIlkBottleItem().get());
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                isLingering ? SoundEvents.ENTITY_LINGERING_POTION_THROW : SoundEvents.ENTITY_SPLASH_POTION_THROW,
                SoundCategory.NEUTRAL, 0.5F, (float) (0.4F / (Math.random() * 0.4F + 0.8F)));

        if (!world.isClient()) {
            ProjectileEntity.spawnWithVelocity(MilkBottleEntity::new, (ServerWorld) world, itemstack, user, -20.0F, 0.5F, 1.0F);
        }

        itemstack.decrementUnlessCreative(1, user);

        return ActionResult.SUCCESS;
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new MilkBottleEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    }
}
