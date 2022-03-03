package love.marblegate.splashmilk.registry;

import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RendererRegistry {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetUpEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.MILK_AREA_EFFECT_CLOUD.get(), NoopRenderer::new);
        event.registerEntityRenderer(EntityRegistry.MILK_BOTTLE.get(), ThrownItemRenderer::new);
    }
}