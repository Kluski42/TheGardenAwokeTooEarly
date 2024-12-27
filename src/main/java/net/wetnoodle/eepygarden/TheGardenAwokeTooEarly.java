package net.wetnoodle.eepygarden;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.wetnoodle.eepygarden.registry.EGBlocks;
import net.wetnoodle.eepygarden.registry.EGSounds;

public class TheGardenAwokeTooEarly implements ModInitializer {
    @Override
    public void onInitialize() {
        EGBlocks.init();
        EGSounds.init();
        clientInitialize();
    }

    private void clientInitialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.CLOSED_EYEBLOSSOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.OPEN_EYEBLOSSOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.POTTED_CLOSED_EYEBLOSSOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.POTTED_OPEN_EYEBLOSSOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGBlocks.RESIN_CLUMP, RenderType.cutout());
    }

    private void cutout(Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
    }
}