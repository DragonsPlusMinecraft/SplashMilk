package plus.dragons.splashmilk.fabric.registry;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class RendererRegistry {
    public static void ini() {
        EntityRenderers.register(EntityRegistry.MILK_AREA_EFFECT_CLOUD, NoopRenderer::new);
        EntityRenderers.register(EntityRegistry.MILK_BOTTLE, ThrownItemRenderer::new);
    }
}
