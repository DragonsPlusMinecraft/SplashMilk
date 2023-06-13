package plus.dragons.splashmilk.entity;

import com.google.common.collect.Maps;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import plus.dragons.splashmilk.PlatformUtil;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class MIlkAreaEffectCloudEntity extends Entity {
    private static final TrackedData<Float> DATA_RADIUS = DataTracker.registerData(MIlkAreaEffectCloudEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> DATA_WAITING = DataTracker.registerData(MIlkAreaEffectCloudEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final Map<Entity, Integer> victims = Maps.newHashMap();
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;
    private LivingEntity owner;
    private UUID ownerUUID;

    public MIlkAreaEffectCloudEntity(EntityType<? extends MIlkAreaEffectCloudEntity> entityType, World world) {
        super(entityType, world);
        noClip = true;
        setRadius(3.0F);
    }

    public MIlkAreaEffectCloudEntity(World world, double x, double y, double z) {
        this(PlatformUtil.getMilkCloudEntityType().get(), world);
        setPosition(x, y, z);
    }

    @Override
    public void calculateDimensions() {
        double d0 = getX();
        double d1 = getY();
        double d2 = getZ();
        super.calculateDimensions();
        setPosition(d0, d1, d2);
    }

    public float getRadius() {
        return getDataTracker().get(DATA_RADIUS);
    }

    public void setRadius(float radius) {
        if (!getWorld().isClient()) {
            getDataTracker().set(DATA_RADIUS, radius);
        }
    }

    public boolean isWaiting() {
        return getDataTracker().get(DATA_WAITING);
    }

    protected void setWaiting(boolean waiting) {
        getDataTracker().set(DATA_WAITING, waiting);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int p_184486_1_) {
        duration = p_184486_1_;
    }

    @Override
    protected void initDataTracker() {
        getDataTracker().startTracking(DATA_RADIUS, 0.5F);
        getDataTracker().startTracking(DATA_WAITING, false);
    }

    @Override
    public void tick() {
        super.tick();
        float radius = getRadius();
        if (getWorld().isClient()) {
            generateParticle(radius);
        } else {
            handleLifecycle(radius);

        }

    }

    private void handleLifecycle(float radius) {
        boolean flag = isWaiting();
        if (age >= waitTime + duration) {
            remove(RemovalReason.DISCARDED);
            return;
        }
        boolean flag1 = age < waitTime;
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

        if (age % 5 == 0)
            findEntityAndApply(radius);
    }

    private void generateParticle(float radius) {
        if (isWaiting()) {
            if (random.nextBoolean()) {
                for (int i = 0; i < 2; i++) {
                    float f1 = random.nextFloat() * ((float) Math.PI * 2F);
                    float f2 = MathHelper.sqrt(random.nextFloat()) * 0.2F;
                    float f3 = MathHelper.cos(f1) * f2;
                    float f4 = MathHelper.sin(f1) * f2;
                    getWorld().addImportantParticle(PlatformUtil.getMilkCloudParticle().get(), getX() + (double) f3, getY(), getZ() + (double) f4, 0.98, 0.99, 1);
                }
            }
        } else {
            float f5 = (float) Math.PI * radius * radius;
            for (int k1 = 0; (float) k1 < f5; ++k1) {
                float f6 = random.nextFloat() * ((float) Math.PI * 2F);
                float f7 = MathHelper.sqrt(random.nextFloat()) * radius;
                float f8 = MathHelper.cos(f6) * f7;
                float f9 = MathHelper.sin(f6) * f7;
                getWorld().addImportantParticle(PlatformUtil.getMilkCloudParticle().get(), getX() + (double) f8, getY(), getZ() + (double) f9, 0.98, 0.99, 1);
            }
        }
    }

    private void findEntityAndApply(float radius) {
        victims.entrySet().removeIf(entry -> age >= entry.getValue());
        List<LivingEntity> list1 = getWorld().getNonSpectatingEntities(LivingEntity.class, getBoundingBox());
        if (!list1.isEmpty()) {
            for (LivingEntity livingentity : list1) {
                if (!victims.containsKey(livingentity) && livingentity.isAffectedBySplashPotions()) {
                    double d0 = livingentity.getX() - getX();
                    double d1 = livingentity.getZ() - getZ();
                    double d2 = d0 * d0 + d1 * d1;
                    if (d2 <= (double) (radius * radius)) {
                        victims.put(livingentity, age + reapplicationDelay);
                        livingentity.clearStatusEffects();
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
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        age = nbt.getInt("Age");
        duration = nbt.getInt("Duration");
        waitTime = nbt.getInt("WaitTime");
        reapplicationDelay = nbt.getInt("ReapplicationDelay");
        durationOnUse = nbt.getInt("DurationOnUse");
        radiusOnUse = nbt.getFloat("RadiusOnUse");
        radiusPerTick = nbt.getFloat("RadiusPerTick");
        setRadius(nbt.getFloat("Radius"));
        if (nbt.containsUuid("Owner")) {
            ownerUUID = nbt.getUuid("Owner");
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Age", age);
        nbt.putInt("Duration", duration);
        nbt.putInt("WaitTime", waitTime);
        nbt.putInt("ReapplicationDelay", reapplicationDelay);
        nbt.putInt("DurationOnUse", durationOnUse);
        nbt.putFloat("RadiusOnUse", radiusOnUse);
        nbt.putFloat("RadiusPerTick", radiusPerTick);
        nbt.putFloat("Radius", getRadius());
        if (ownerUUID != null) {
            nbt.putUuid("Owner", ownerUUID);
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
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
        if (owner == null && ownerUUID != null && getWorld() instanceof ServerWorld) {
            Entity entity = ((ServerWorld) getWorld()).getEntity(ownerUUID);
            if (entity instanceof LivingEntity) {
                owner = (LivingEntity) entity;
            }
        }
        return owner;
    }

    public void setOwner(@Nullable LivingEntity livingEntity) {
        owner = livingEntity;
        ownerUUID = livingEntity == null ? null : livingEntity.getUuid();
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (DATA_RADIUS.equals(data)) {
            this.calculateDimensions();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.IGNORE;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.changing(getRadius() * 2.0F, 0.5F);
    }

}
