package love.marblegate.splashmilk.entity;

import love.marblegate.splashmilk.registry.EntityRegistry;
import love.marblegate.splashmilk.registry.ItemRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.protocol.Packet;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class MilkBottleEntity extends ThrowableItemProjectile implements ItemSupplier {
    public static final Predicate<LivingEntity> WATER_SENSITIVE = LivingEntity::isSensitiveToWater;

    public MilkBottleEntity(EntityType<? extends MilkBottleEntity> entityType, Level world) {
        super(entityType, world);
    }

    public MilkBottleEntity(Level world, LivingEntity livingEntity) {
        super(EntityRegistry.MILK_BOTTLE.get(), livingEntity, world);
    }

    public MilkBottleEntity(Level world, double x, double y, double z) {
        super(EntityRegistry.MILK_BOTTLE.get(), x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.SPLASH_MILK_BOTTLE.get();
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);
        if (!level.isClientSide) {
            Direction direction = blockRayTraceResult.getDirection();
            BlockPos blockpos = blockRayTraceResult.getBlockPos();
            BlockPos blockpos1 = blockpos.relative(direction);

            dowseFire(blockpos1);
            dowseFire(blockpos1.relative(direction.getOpposite()));

            for (Direction direction1 : Direction.Plane.HORIZONTAL) {
                dowseFire(blockpos1.relative(direction1));
            }

        }
    }

    @Override
    protected void onHit(HitResult rayTraceResult) {
        super.onHit(rayTraceResult);
        if (!level.isClientSide) {

            applyWater();

            if (isLingering()) {
                makeAreaOfEffectCloud();
            } else {
                applySplash(rayTraceResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) rayTraceResult).getEntity() : null);
            }
            // 2007 see PotionEntity & WorldRenderer, 16253176 see PotionUtils#getColor
            this.level.levelEvent(2007, this.blockPosition(), 16777215);
            remove(RemovalReason.DISCARDED);
        }
    }

    private void applyWater() {
        AABB axisalignedbb = getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, axisalignedbb, WATER_SENSITIVE);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                double d0 = distanceToSqr(livingentity);
                if (d0 < 16.0D && livingentity.isSensitiveToWater()) {
                    livingentity.hurt(DamageSource.indirectMagic(livingentity, getOwner()), 1.0F);
                }
            }
        }

    }

    private void applySplash(Entity entity) {
        AABB axisalignedbb = getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, axisalignedbb);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                livingentity.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
            }
        }

    }

    private void makeAreaOfEffectCloud() {
        MIlkAreaEffectCloudEntity cloudEntity = new MIlkAreaEffectCloudEntity(level, getX(), getY(), getZ());
        Entity entity = getOwner();
        if (entity instanceof LivingEntity) {
            cloudEntity.setOwner((LivingEntity) entity);
        }

        cloudEntity.setRadius(3.0F);
        cloudEntity.setRadiusOnUse(-0.5F);
        cloudEntity.setWaitTime(10);
        cloudEntity.setRadiusPerTick(-cloudEntity.getRadius() / (float) cloudEntity.getDuration());

        level.addFreshEntity(cloudEntity);
    }

    private boolean isLingering() {
        return getItem().getItem() == ItemRegistry.LINGERING_MILK_BOTTLE.get();
    }

    private void dowseFire(BlockPos blockPos) {
        BlockState blockstate = level.getBlockState(blockPos);
        if (blockstate.is(BlockTags.FIRE)) {
            level.removeBlock(blockPos, false);
        } else if (CampfireBlock.isLitCampfire(blockstate)) {
            level.levelEvent((Player) null, 1009, blockPos, 0);
            CampfireBlock.dowse(null,level, blockPos, blockstate);
            level.setBlockAndUpdate(blockPos, blockstate.setValue(CampfireBlock.LIT, false));
        }

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
