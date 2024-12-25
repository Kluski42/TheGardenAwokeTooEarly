package net.wetnoodle.eepygarden;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.wetnoodle.eepygarden.registry.EGBlocks;

public class TheGardenAwokeTooEarly implements ModInitializer {
    @Override
    public void onInitialize() {
        EGBlocks.init();
        clientInitialize();
    }

    private void clientInitialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.CLOSED_EYEBLOSSOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.OPEN_EYEBLOSSOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.POTTED_CLOSED_EYEBLOSSOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.POTTED_OPEN_EYEBLOSSOM, RenderType.cutout());

    }
}