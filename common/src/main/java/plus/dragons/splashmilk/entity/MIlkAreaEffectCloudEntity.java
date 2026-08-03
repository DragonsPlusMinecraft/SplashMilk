package plus.dragons.splashmilk.entity;

import com.google.common.collect.Maps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import plus.dragons.splashmilk.PlatformUtil;

import java.util.List;
import java.util.Map;


public class MIlkAreaEffectCloudEntity extends Entity {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(MIlkAreaEffectCloudEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.defineId(MIlkAreaEffectCloudEntity.class, EntityDataSerializers.BOOLEAN);
    private final Map<Entity, Integer> victims = Maps.newHashMap();
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;
    @Nullable
    private EntityReference<LivingEntity> owner;

    public MIlkAreaEffectCloudEntity(EntityType<? extends MIlkAreaEffectCloudEntity> entityType, Level world) {
        super(entityType, world);
        noPhysics = true;
        setRadius(3.0F);
    }

    public MIlkAreaEffectCloudEntity(Level world, double x, double y, double z) {
        this(PlatformUtil.getMilkCloudEntityType().get(), world);
        setPos(x, y, z);
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

    public void setRadius(float radius) {
        if (!level().isClientSide()) {
            getEntityData().set(DATA_RADIUS, radius);
        }
    }

    public boolean isWaiting() {
        return getEntityData().get(DATA_WAITING);
    }

    protected void setWaiting(boolean waiting) {
        getEntityData().set(DATA_WAITING, waiting);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int p_184486_1_) {
        duration = p_184486_1_;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_RADIUS, 0.5F);
        builder.define(DATA_WAITING, false);
    }

    @Override
    public void tick() {
        super.tick();
        float radius = getRadius();
        if (level().isClientSide()) {
            generateParticle(radius);
        } else {
            handleLifecycle(radius);

        }

    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        return false;
    }

    private void handleLifecycle(float radius) {
        boolean flag = isWaiting();
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
            radius += radiusPerTick;
            if (radius < 0.5F) {
                remove(RemovalReason.DISCARDED);
                return;
            }

            setRadius(radius);
        }

        if (tickCount % 5 == 0)
            findEntityAndApply(radius);
    }

    private void generateParticle(float radius) {
        if (isWaiting()) {
            if (random.nextBoolean()) {
                for (int i = 0; i < 2; i++) {
                    float f1 = random.nextFloat() * ((float) Math.PI * 2F);
                    float f2 = Mth.sqrt(random.nextFloat()) * 0.2F;
                    float f3 = Mth.cos(f1) * f2;
                    float f4 = Mth.sin(f1) * f2;
                    level().addAlwaysVisibleParticle(PlatformUtil.getMilkCloudParticle().get(), getX() + (double) f3, getY(), getZ() + (double) f4, 0.98, 0.99, 1);
                }
            }
        } else {
            float f5 = (float) Math.PI * radius * radius;
            for (int k1 = 0; (float) k1 < f5; ++k1) {
                float f6 = random.nextFloat() * ((float) Math.PI * 2F);
                float f7 = Mth.sqrt(random.nextFloat()) * radius;
                float f8 = Mth.cos(f6) * f7;
                float f9 = Mth.sin(f6) * f7;
                level().addAlwaysVisibleParticle(PlatformUtil.getMilkCloudParticle().get(), getX() + (double) f8, getY(), getZ() + (double) f9, 0.98, 0.99, 1);
            }
        }
    }

    private void findEntityAndApply(float radius) {
        victims.entrySet().removeIf(entry -> tickCount >= entry.getValue());
        List<LivingEntity> list1 = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox(), EntitySelector.NO_SPECTATORS);
        if (!list1.isEmpty()) {
            for (LivingEntity livingentity : list1) {
                if (!victims.containsKey(livingentity) && livingentity.isAffectedByPotions()) {
                    double d0 = livingentity.getX() - getX();
                    double d1 = livingentity.getZ() - getZ();
                    double d2 = d0 * d0 + d1 * d1;
                    if (d2 <= (double) (radius * radius)) {
                        victims.put(livingentity, tickCount + reapplicationDelay);
                        livingentity.removeAllEffects();
                        if (radiusOnUse != 0.0F) {
                            radius += radiusOnUse;
                            if (radius < 0.5F) {
                                remove(RemovalReason.DISCARDED);
                                return;
                            }
                            setRadius(radius);
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

    @Override
    protected void readAdditionalSaveData(ValueInput view) {
        tickCount = view.getIntOr("Age",0);
        duration = view.getIntOr("Duration",-1);
        waitTime = view.getIntOr("WaitTime",20);
        reapplicationDelay = view.getIntOr("ReapplicationDelay",20);
        durationOnUse = view.getIntOr("DurationOnUse",0);
        radiusOnUse = view.getFloatOr("RadiusOnUse",0);
        radiusPerTick = view.getFloatOr("RadiusPerTick",0);
        setRadius(view.getFloatOr("Radius",3.0F));
        this.owner = EntityReference.read(view, "Owner");
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput view) {
        view.putInt("Age", tickCount);
        view.putInt("Duration", duration);
        view.putInt("WaitTime", waitTime);
        view.putInt("ReapplicationDelay", reapplicationDelay);
        view.putInt("DurationOnUse", durationOnUse);
        view.putFloat("RadiusOnUse", radiusOnUse);
        view.putFloat("RadiusPerTick", radiusPerTick);
        view.putFloat("Radius", getRadius());
        EntityReference.store(this.owner, view, "Owner");
    }

    public void setRadiusOnUse(float radiusOnUse) {
        this.radiusOnUse = radiusOnUse;
    }

    public void setRadiusPerTick(float radiusPerTick) {
        this.radiusPerTick = radiusPerTick;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    @Nullable
    public LivingEntity getOwner() {
        return EntityReference.get(this.owner, level(), LivingEntity.class);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner != null ? EntityReference.of(owner) : null;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        if (DATA_RADIUS.equals(data)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(data);
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(getRadius() * 2.0F, 0.5F);
    }

}
