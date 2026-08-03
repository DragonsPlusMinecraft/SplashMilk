package plus.dragons.splashmilk.fabric.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class RendererRegistry {
    public static void ini() {
        EntityRendererRegistry.register(EntityRegistry.MILK_AREA_EFFECT_CLOUD, NoopRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MILK_BOTTLE, ThrownItemRenderer::new);
    }
}