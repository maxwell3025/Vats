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
import org.joml.Vector4fc;

@OnlyIn(Dist.CLIENT)
public class ChemicalMixBlockRenderer implements BlockEntityRenderer<ChemicalMixBlockEntity> {
    private static final Logger LOGGER = LogManager.getLogger();
    public ChemicalMixBlockRenderer(BlockEntityRendererProvider.Context p_173529_) {
    }
    @Override
    public void render(ChemicalMixBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.debugQuads());
        Matrix4f matrix4f = poseStack.last().pose();
        Vector4fc color = blockEntity.getColor();
        LOGGER.warn("Rendering Block Entity: {}", color.toString());
        float r = color.x();
        float g = color.y();
        float b = color.z();
        float a = color.w();

        addVertex(consumer, matrix4f, 0.01f, 0.99f, 0.99f, r, g, b, a, 0, 1, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.99f, 0.99f, r, g, b, a, 0, 1, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.99f, 0.01f, r, g, b, a, 0, 1, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.01f, 0.99f, 0.01f, r, g, b, a, 0, 1, 0, combinedLight);

        addVertex(consumer, matrix4f, 0.01f, 0.01f, 0.01f, r, g, b, a, 0, -1, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.01f, 0.01f, r, g, b, a, 0, -1, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.01f, 0.99f, r, g, b, a, 0, -1, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.01f, 0.01f, 0.99f, r, g, b, a, 0, -1, 0, combinedLight);

        addVertex(consumer, matrix4f, 0.01f, 0.01f, 0.99f, r, g, b, a, 0, 0, 1, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.01f, 0.99f, r, g, b, a, 0, 0, 1, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.99f, 0.99f, r, g, b, a, 0, 0, 1, combinedLight);
        addVertex(consumer, matrix4f, 0.01f, 0.99f, 0.99f, r, g, b, a, 0, 0, 1, combinedLight);

        addVertex(consumer, matrix4f, 0.01f, 0.99f, 0.01f, r, g, b, a, 0, 0, -1, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.99f, 0.01f, r, g, b, a, 0, 0, -1, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.01f, 0.01f, r, g, b, a, 0, 0, -1, combinedLight);
        addVertex(consumer, matrix4f, 0.01f, 0.01f, 0.01f, r, g, b, a, 0, 0, -1, combinedLight);

        addVertex(consumer, matrix4f, 0.99f, 0.01f, 0.01f, r, g, b, a, 1, 0, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.99f, 0.01f, r, g, b, a, 1, 0, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.99f, 0.99f, r, g, b, a, 1, 0, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.99f, 0.01f, 0.99f, r, g, b, a, 1, 0, 0, combinedLight);

        addVertex(consumer, matrix4f, 0.01f, 0.01f, 0.99f, r, g, b, a, -1, 0, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.01f, 0.99f, 0.99f, r, g, b, a, -1, 0, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.01f, 0.99f, 0.01f, r, g, b, a, -1, 0, 0, combinedLight);
        addVertex(consumer, matrix4f, 0.01f, 0.01f, 0.01f, r, g, b, a, -1, 0, 0, combinedLight);

        poseStack.popPose();
    }
    private void addVertex(VertexConsumer consumer, Matrix4f matrix4f, float x, float y, float z, float r, float g, float b, float a, float nx, float ny, float nz, int combinedLight){
        consumer.vertex(matrix4f, x, y, z).color(r, g, b, a).uv(0, 0).uv2(combinedLight).normal(nx, ny, nz).endVertex();
    }
}
