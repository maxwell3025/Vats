package com.maxwell3025.vats.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class ChemicalMixBlockRenderer implements BlockEntityRenderer<ChemicalMixBlockEntity> {
    private static final Logger LOGGER = LogManager.getLogger();
    public ChemicalMixBlockRenderer(BlockEntityRendererProvider.Context p_173529_) {
    }
    @Override
    public void render(ChemicalMixBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        LOGGER.warn("Rendering");
        poseStack.pushPose();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.debugQuads());
        Matrix4f matrix4f = poseStack.last().pose();

        consumer.vertex(matrix4f, 0, 0, 0).color(0.9F, 0.9F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix4f, 1, 0, 0).color(0.9F, 0.9F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix4f, 1, 0, 1).color(0.9F, 0.9F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix4f, 0, 0, 1).color(0.9F, 0.9F, 0.0F, 1.0F).endVertex();
        poseStack.popPose();
    }
}
