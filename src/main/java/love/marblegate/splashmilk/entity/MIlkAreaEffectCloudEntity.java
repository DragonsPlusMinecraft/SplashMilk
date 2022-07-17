package love.marblegate.splashmilk.entity;

import com.google.common.collect.Maps;
import love.marblegate.splashmilk.registry.EntityRegistry;
import love.marblegate.splashmilk.registry.ParticleTypeRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import net.minecraft.world.entity.Entity.RemovalReason;

public class MIlkAreaEffectCloudEntity extends Entity {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(MIlkAreaEffectCloudEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.defineId(MIlkAreaEffectCloudEntity.class, EntityDataSerializers.BOOLEAN);
    private static final ParticleOptions PARTICLE = ParticleTypeRegistry.MILK_AREA_EFFECT.get();
    private final Map<Entity, Integer> victims = Maps.newHashMap();
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;
    private LivingEntity owner;
    private UUID ownerUUID;

    public MIlkAreaEffectCloudEntity(EntityType<? extends MIlkAreaEffectCloudEntity> p_i50389_1_, Level p_i50389_2_) {
        super(p_i50389_1_, p_i50389_2_);
        noPhysics = true;
        setRadius(3.0F);
    }

    public MIlkAreaEffectCloudEntity(Level p_i46810_1_, double p_i46810_2_, double p_i46810_4_, double p_i46810_6_) {
        this(EntityRegistry.MILK_AREA_EFFECT_CLOUD.get(), p_i46810_1_);
        setPos(p_i46810_2_, p_i46810_4_, p_i46810_6_);
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(DATA_RADIUS, 0.5F);
        getEntityData().define(DATA_WAITING, false);
    }

    @Override
    public void refreshDimensions() {
        double d0 = getX();
        double d1 = getY();
        double d2 = getZ();
        super.refreshDimensions();
        setPos(d0, d1, d2);
    }

    public float getRadius() {
        return getEntityData().get(DATA_RADIUS);
    }

    public void setRadius(float p_184483_1_) {
        if (!level.isClientSide) {
            getEntityData().set(DATA_RADIUS, p_184483_1_);
        }
    }

    public boolean isWaiting() {
        return getEntityData().get(DATA_WAITING);
    }

    protected void setWaiting(boolean p_184488_1_) {
        getEntityData().set(DATA_WAITING, p_184488_1_);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int p_184486_1_) {
        duration = p_184486_1_;
    }

    @Override
    public void tick() {
        super.tick();
        boolean flag = isWaiting();
        float f = getRadius();
        if (level.isClientSide) {
            if (flag) {
                if (random.nextBoolean()) {
                    for (int i = 0; i < 2; ++i) {
                        float f1 = random.nextFloat() * ((float) Math.PI * 2F);
                        float f2 = Mth.sqrt(random.nextFloat()) * 0.2F;
                        float f3 = Mth.cos(f1) * f2;
                        float f4 = Mth.sin(f1) * f2;
                        level.addAlwaysVisibleParticle(PARTICLE, getX() + (double) f3, getY(), getZ() + (double) f4, 0.98, 0.99, 1);
                    }
                }
            } else {
                float f5 = (float) Math.PI * f * f;
                for (int k1 = 0; (float) k1 < f5; ++k1) {
                    float f6 = random.nextFloat() * ((float) Math.PI * 2F);
                    float f7 = Mth.sqrt(random.nextFloat()) * f;
                    float f8 = Mth.cos(f6) * f7;
                    float f9 = Mth.sin(f6) * f7;
                    level.addAlwaysVisibleParticle(PARTICLE, getX() + (double) f8, getY(), getZ() + (double) f9, 0.98, 0.99, 1);
                }
            }
        } else {
            if (tickCount >= waitTime + duration) {
                remove(RemovalReason.DISCARDED);
                return;
            }

            boolean flag1 = tickCount < waitTime;
            if (flag != flag1) {
                setWaiting(flag1);
            }

            if (flag1) {
                return;
            }

            if (radiusPerTick != 0.0F) {
                f += radiusPerTick;
                if (f < 0.5F) {
                    remove(RemovalReason.DISCARDED);
                    return;
                }

                setRadius(f);
            }

            if (tickCount % 5 == 0) {
                Iterator<Map.Entry<Entity, Integer>> iterator = victims.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Entity, Integer> entry = iterator.next();
                    if (tickCount >= entry.getValue()) {
                        iterator.remove();
                    }
                }

                List<LivingEntity> list1 = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox());
                if (!list1.isEmpty()) {
                    for (LivingEntity livingentity : list1) {
                        if (!victims.containsKey(livingentity) && livingentity.isAffectedByPotions()) {
                            double d0 = livingentity.getX() - getX();
                            double d1 = livingentity.getZ() - getZ();
                            double d2 = d0 * d0 + d1 * d1;
                            if (d2 <= (double) (f * f)) {
                                victims.put(livingentity, tickCount + reapplicationDelay);

                                livingentity.curePotionEffects(new ItemStack(Items.MILK_BUCKET));

                                if (radiusOnUse != 0.0F) {
                                    f += radiusOnUse;
                                    if (f < 0.5F) {
                                        remove(RemovalReason.DISCARDED);
                                        return;
                                    }

                                    setRadius(f);
                                }

                                if (durationOnUse != 0) {
                                    duration += durationOnUse;
                                    if (duration <= 0) {
                                        remove(RemovalReason.DISCARDED);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void setRadiusOnUse(float p_184495_1_) {
        radiusOnUse = p_184495_1_;
    }

    public void setRadiusPerTick(float p_184487_1_) {
        radiusPerTick = p_184487_1_;
    }

    public void setWaitTime(int p_184485_1_) {
        waitTime = p_184485_1_;
    }

    @Nullable
    public LivingEntity getOwner() {
        if (owner == null && ownerUUID != null && level instanceof ServerLevel) {
            Entity entity = ((ServerLevel) level).getEntity(ownerUUID);
            if (entity instanceof LivingEntity) {
                owner = (LivingEntity) entity;
            }
        }

        return owner;
    }

    public void setOwner(@Nullable LivingEntity p_184481_1_) {
        owner = p_184481_1_;
        ownerUUID = p_184481_1_ == null ? null : p_184481_1_.getUUID();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        tickCount = tag.getInt("Age");
        duration = tag.getInt("Duration");
        waitTime = tag.getInt("WaitTime");
        reapplicationDelay = tag.getInt("ReapplicationDelay");
        durationOnUse = tag.getInt("DurationOnUse");
        radiusOnUse = tag.getFloat("RadiusOnUse");
        radiusPerTick = tag.getFloat("RadiusPerTick");
        setRadius(tag.getFloat("Radius"));
        if (tag.hasUUID("Owner")) {
            ownerUUID = tag.getUUID("Owner");
        }

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Age", tickCount);
        tag.putInt("Duration", duration);
        tag.putInt("WaitTime", waitTime);
        tag.putInt("ReapplicationDelay", reapplicationDelay);
        tag.putInt("DurationOnUse", durationOnUse);
        tag.putFloat("RadiusOnUse", radiusOnUse);
        tag.putFloat("RadiusPerTick", radiusPerTick);
        tag.putFloat("Radius", getRadius());
        if (ownerUUID != null) {
            tag.putUUID("Owner", ownerUUID);
        }

    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_184206_1_) {
        if (DATA_RADIUS.equals(p_184206_1_)) {
            refreshDimensions();
        }

        super.onSyncedDataUpdated(p_184206_1_);
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public EntityDimensions getDimensions(Pose p_213305_1_) {
        return EntityDimensions.scalable(getRadius() * 2.0F, 0.5F);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
