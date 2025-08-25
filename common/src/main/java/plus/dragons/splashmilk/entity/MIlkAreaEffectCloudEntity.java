package plus.dragons.splashmilk.entity;

import com.google.common.collect.Maps;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import plus.dragons.splashmilk.PlatformUtil;

import java.util.List;
import java.util.Map;


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
    @Nullable
    private LazyEntityReference<LivingEntity> owner;

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
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(DATA_RADIUS, 0.5F);
        builder.add(DATA_WAITING, false);
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

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
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
                    getWorld().addImportantParticleClient(PlatformUtil.getMilkCloudParticle().get(), getX() + (double) f3, getY(), getZ() + (double) f4, 0.98, 0.99, 1);
                }
            }
        } else {
            float f5 = (float) Math.PI * radius * radius;
            for (int k1 = 0; (float) k1 < f5; ++k1) {
                float f6 = random.nextFloat() * ((float) Math.PI * 2F);
                float f7 = MathHelper.sqrt(random.nextFloat()) * radius;
                float f8 = MathHelper.cos(f6) * f7;
                float f9 = MathHelper.sin(f6) * f7;
                getWorld().addImportantParticleClient(PlatformUtil.getMilkCloudParticle().get(), getX() + (double) f8, getY(), getZ() + (double) f9, 0.98, 0.99, 1);
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
    protected void readCustomData(ReadView view) {
        age = view.getInt("Age",0);
        duration = view.getInt("Duration",-1);
        waitTime = view.getInt("WaitTime",20);
        reapplicationDelay = view.getInt("ReapplicationDelay",20);
        durationOnUse = view.getInt("DurationOnUse",0);
        radiusOnUse = view.getFloat("RadiusOnUse",0);
        radiusPerTick = view.getFloat("RadiusPerTick",0);
        setRadius(view.getFloat("Radius",3.0F));
        this.owner = LazyEntityReference.fromData(view, "Owner");
    }

    @Override
    protected void writeCustomData(WriteView view) {
        view.putInt("Age", age);
        view.putInt("Duration", duration);
        view.putInt("WaitTime", waitTime);
        view.putInt("ReapplicationDelay", reapplicationDelay);
        view.putInt("DurationOnUse", durationOnUse);
        view.putFloat("RadiusOnUse", radiusOnUse);
        view.putFloat("RadiusPerTick", radiusPerTick);
        view.putFloat("Radius", getRadius());
        LazyEntityReference.writeData(this.owner, view, "Owner");
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
        return (LivingEntity)LazyEntityReference.resolve(this.owner, this.getWorld(), LivingEntity.class);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner != null ? new LazyEntityReference<>(owner) : null;
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
