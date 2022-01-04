package love.marblegate.splashmilk.renderer;

import love.marblegate.splashmilk.entity.MIlkAreaEffectCloudEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class MilkAreaEffectCloudRenderer extends EntityRenderer<MIlkAreaEffectCloudEntity> {

    public MilkAreaEffectCloudRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(MIlkAreaEffectCloudEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
