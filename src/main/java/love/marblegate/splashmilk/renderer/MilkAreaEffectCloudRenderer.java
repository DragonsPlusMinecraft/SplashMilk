package love.marblegate.splashmilk.renderer;

import love.marblegate.splashmilk.entity.MIlkAreaEffectCloudEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

public class MilkAreaEffectCloudRenderer extends EntityRenderer<MIlkAreaEffectCloudEntity> {

    public MilkAreaEffectCloudRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(MIlkAreaEffectCloudEntity p_110775_1_) {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}
