package plus.dragons.splashmilk.entity;

import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import plus.dragons.splashmilk.PlatformUtil;

import java.util.List;
import java.util.function.Predicate;

public class MilkBottleEntity extends ThrownItemEntity implements FlyingItemEntity {
    public static final Predicate<LivingEntity> WATER_SENSITIVE = LivingEntity::hurtByWater;

    public MilkBottleEntity(EntityType<? extends MilkBottleEntity> entityType, World world) {
        super(entityType, world);
    }

    public MilkBottleEntity(World world, LivingEntity livingEntity) {
        super(PlatformUtil.getMIlkBottleEntityType().get(), livingEntity, world);
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!getWorld().isClient()) {
            Direction direction = blockHitResult.getSide();
            BlockPos blockpos = blockHitResult.getBlockPos();
            BlockPos blockpos1 = blockpos.offset(direction);

            extinguishFire(blockpos1);
            extinguishFire(blockpos1.offset(direction.getOpposite()));

            for (Direction direction1 : Direction.Type.HORIZONTAL) {
                extinguishFire(blockpos1.offset(direction1));
            }

        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!getWorld().isClient()) {

            applyWater();

            if (isLingering()) {
                makeAreaOfEffectCloud();
            } else {
                applySplash();
            }
            // 2007 see PotionEntity & WorldRenderer, 16253176 see PotionUtils#getColor
            getWorld().syncWorldEvent(2007, this.getBlockPos(), 16777215);
            remove(RemovalReason.DISCARDED);
        }
    }

    private void applyWater() {
        Box box = getBoundingBox().expand(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = getWorld().getEntitiesByClass(LivingEntity.class, box, WATER_SENSITIVE);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                double d0 = squaredDistanceTo(livingentity);
                if (d0 < 16.0D && livingentity.hurtByWater()) {
                    livingentity.damage(getDamageSources().indirectMagic(this, getOwner()), 1.0F);
                }
            }
        }

    }

    private void applySplash() {
        Box box = getBoundingBox().expand(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                livingentity.clearStatusEffects();
            }
        }

    }

    private void makeAreaOfEffectCloud() {
        MIlkAreaEffectCloudEntity cloudEntity = new MIlkAreaEffectCloudEntity(getWorld(), getX(), getY(), getZ());
        Entity entity = getOwner();
        if (entity instanceof LivingEntity) {
            cloudEntity.setOwner((LivingEntity) entity);
        }

        cloudEntity.setRadius(3.0F);
        cloudEntity.setRadiusOnUse(-0.5F);
        cloudEntity.setWaitTime(10);
        cloudEntity.setRadiusPerTick(-cloudEntity.getRadius() / (float) cloudEntity.getDuration());

        getWorld().spawnEntity(cloudEntity);
    }

    private boolean isLingering() {
        return getStack().getItem() == PlatformUtil.getLingerMIlkBottleItem().get();
    }

    private void extinguishFire(BlockPos blockPos) {
        BlockState blockState = getWorld().getBlockState(blockPos);
        if (blockState.isIn(BlockTags.FIRE)) {
            getWorld().removeBlock(blockPos, false);
        } else if (AbstractCandleBlock.isLitCandle(blockState)) {
            AbstractCandleBlock.extinguish(null, blockState, this.getWorld(), blockPos);
        } else if (CampfireBlock.isLitCampfire(blockState)) {
            getWorld().syncWorldEvent(null, 1009, blockPos, 0);
            CampfireBlock.extinguish(null, getWorld(), blockPos, blockState);
            getWorld().setBlockState(blockPos, blockState.with(CampfireBlock.LIT, false));
        }

    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return PlatformUtil.getLingerMIlkBottleItem().get();
    }
}
