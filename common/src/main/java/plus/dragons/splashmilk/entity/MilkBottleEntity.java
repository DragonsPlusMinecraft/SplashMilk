package plus.dragons.splashmilk.entity;

import plus.dragons.splashmilk.PlatformUtil;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class MilkBottleEntity extends ThrowableItemProjectile {
    public static final Predicate<LivingEntity> WATER_SENSITIVE = LivingEntity::isSensitiveToWater;

    public MilkBottleEntity(EntityType<? extends MilkBottleEntity> entityType, Level world) {
        super(entityType, world);
    }

    public MilkBottleEntity(Level world, LivingEntity livingEntity, ItemStack itemStack) {
        super(PlatformUtil.getMIlkBottleEntityType().get(), livingEntity, world, itemStack);
    }

    public MilkBottleEntity(Level world, double x, double y, double z, ItemStack stack) {
        super(PlatformUtil.getMIlkBottleEntityType().get(), x, y, z, world, stack);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.05F;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!level().isClientSide()) {
            Direction direction = blockHitResult.getDirection();
            BlockPos blockpos = blockHitResult.getBlockPos();
            BlockPos blockpos1 = blockpos.relative(direction);

            extinguishFire(blockpos1);
            extinguishFire(blockpos1.relative(direction.getOpposite()));

            for (Direction direction1 : Direction.Plane.HORIZONTAL) {
                extinguishFire(blockpos1.relative(direction1));
            }

        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!level().isClientSide()) {

            applyWater();

            if (isLingering()) {
                makeAreaOfEffectCloud();
            } else {
                applySplash();
            }
            // 2007 see PotionEntity & WorldRenderer, 16253176 see PotionUtils#getColor
            level().levelEvent(2007, this.blockPosition(), 16777215);
            remove(RemovalReason.DISCARDED);
        }
    }

    private void applyWater() {
        AABB box = getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, box, WATER_SENSITIVE);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                double d0 = distanceToSqr(livingentity);
                if (d0 < 16.0D && livingentity.isSensitiveToWater()) {
                    livingentity.hurtServer((ServerLevel) level(), damageSources().indirectMagic(this, getOwner()), 1.0F);
                }
            }
        }

    }

    private void applySplash() {
        AABB box = getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, box, EntitySelector.NO_SPECTATORS);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                livingentity.removeAllEffects();
            }
        }

    }

    private void makeAreaOfEffectCloud() {
        MIlkAreaEffectCloudEntity cloudEntity = new MIlkAreaEffectCloudEntity(level(), getX(), getY(), getZ());
        Entity entity = getOwner();
        if (entity instanceof LivingEntity) {
            cloudEntity.setOwner((LivingEntity) entity);
        }

        cloudEntity.setRadius(3.0F);
        cloudEntity.setRadiusOnUse(-0.5F);
        cloudEntity.setWaitTime(10);
        cloudEntity.setRadiusPerTick(-cloudEntity.getRadius() / (float) cloudEntity.getDuration());

        level().addFreshEntity(cloudEntity);
    }

    private boolean isLingering() {
        return getItem().getItem() == PlatformUtil.getLingerMIlkBottleItem().get();
    }

    private void extinguishFire(BlockPos blockPos) {
        BlockState blockState = level().getBlockState(blockPos);
        if (blockState.is(BlockTags.FIRE)) {
            level().removeBlock(blockPos, false);
        } else if (AbstractCandleBlock.isLit(blockState)) {
            AbstractCandleBlock.extinguish(null, blockState, level(), blockPos);
        } else if (CampfireBlock.isLitCampfire(blockState)) {
            level().levelEvent(null, 1009, blockPos, 0);
            CampfireBlock.dowse(null, level(), blockPos, blockState);
            level().setBlockAndUpdate(blockPos, blockState.setValue(CampfireBlock.LIT, false));
        }

    }

    @Override
    protected Item getDefaultItem() {
        return PlatformUtil.getLingerMIlkBottleItem().get();
    }
}
