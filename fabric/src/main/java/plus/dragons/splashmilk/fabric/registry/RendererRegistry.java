package plus.dragons.splashmilk.fabric.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class RendererRegistry {
    public static void ini() {
        EntityRendererRegistry.register(EntityRegistry.MILK_AREA_EFFECT_CLOUD, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MILK_BOTTLE, FlyingItemEntityRenderer::new);
    }
}