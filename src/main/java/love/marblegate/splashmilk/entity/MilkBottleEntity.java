package love.marblegate.splashmilk.entity;

import love.marblegate.splashmilk.registry.EntityRegistry;
import love.marblegate.splashmilk.registry.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.function.Predicate;

public class MilkBottleEntity extends ProjectileItemEntity implements IRendersAsItem {
    public static final Predicate<LivingEntity> WATER_SENSITIVE = LivingEntity::isSensitiveToWater;

    public MilkBottleEntity(EntityType<? extends MilkBottleEntity> entityType, World world) {
        super(entityType, world);
    }

    public MilkBottleEntity(World world, LivingEntity livingEntity) {
        super(EntityRegistry.MILK_BOTTLE.get(), livingEntity, world);
    }

    public MilkBottleEntity(World world, double x, double y, double z) {
        super(EntityRegistry.MILK_BOTTLE.get(), x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.SPLASH_MILK_BOTTLE.get();
    }

    protected float getGravity() {
        return 0.05F;
    }

    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
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

    protected void onHit(RayTraceResult rayTraceResult) {
        super.onHit(rayTraceResult);
        if (!level.isClientSide) {

            applyWater();

            if (isLingering()) {
                makeAreaOfEffectCloud();
            } else {
                applySplash(rayTraceResult.getType() == RayTraceResult.Type.ENTITY ? ((EntityRayTraceResult) rayTraceResult).getEntity() : null);
            }
            // 2007 see PotionEntity & WorldRenderer, 16253176 see PotionUtils#getColor
            this.level.levelEvent(2007, this.blockPosition(), 16777215);
            remove();
        }
    }

    private void applyWater() {
        AxisAlignedBB axisalignedbb = getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
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
        AxisAlignedBB axisalignedbb = getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
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
            level.levelEvent((PlayerEntity) null, 1009, blockPos, 0);
            CampfireBlock.dowse(level, blockPos, blockstate);
            level.setBlockAndUpdate(blockPos, blockstate.setValue(CampfireBlock.LIT, false));
        }

    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
