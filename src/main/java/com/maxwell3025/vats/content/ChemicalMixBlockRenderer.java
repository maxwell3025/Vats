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
import org.joml.*;

@OnlyIn(Dist.CLIENT)
public class ChemicalMixBlockRenderer implements BlockEntityRenderer<ChemicalMixBlockEntity> {
    private static final Logger LOGGER = LogManager.getLogger();

    public ChemicalMixBlockRenderer(BlockEntityRendererProvider.Context p_173529_) {
    }

    @Override
    public void render(ChemicalMixBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        // For now, we use debugQuads since that's the only RenderType that lets you set a flat color w/o an underlying
        // texture file.
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.debugQuads());
        Matrix4f matrix4f = poseStack.last().pose();
        Vector4fc color = blockEntity.getColor();

        Vector3fc positiveX = new Vector3f(1, 0, 0);
        Vector3fc negativeX = new Vector3f(-1, 0, 0);
        Vector3fc positiveY = new Vector3f(0, 1, 0);
        Vector3fc negativeY = new Vector3f(0, -1, 0);
        Vector3fc positiveZ = new Vector3f(0, 0, 1);
        Vector3fc negativeZ = new Vector3f(0, 0, -1);

        Vector3fc corner000 = new Vector3f(0.01f, 0.01f, 0.01f);
        Vector3fc corner001 = new Vector3f(0.01f, 0.01f, 0.99f);
        Vector3fc corner010 = new Vector3f(0.01f, 0.99f, 0.01f);
        Vector3fc corner011 = new Vector3f(0.01f, 0.99f, 0.99f);
        Vector3fc corner100 = new Vector3f(0.99f, 0.01f, 0.01f);
        Vector3fc corner101 = new Vector3f(0.99f, 0.01f, 0.99f);
        Vector3fc corner110 = new Vector3f(0.99f, 0.99f, 0.01f);
        Vector3fc corner111 = new Vector3f(0.99f, 0.99f, 0.99f);

        addVertex(consumer, matrix4f, corner011, color, positiveY, combinedLight);
        addVertex(consumer, matrix4f, corner111, color, positiveY, combinedLight);
        addVertex(consumer, matrix4f, corner110, color, positiveY, combinedLight);
        addVertex(consumer, matrix4f, corner010, color, positiveY, combinedLight);

        addVertex(consumer, matrix4f, corner000, color, negativeY, combinedLight);
        addVertex(consumer, matrix4f, corner100, color, negativeY, combinedLight);
        addVertex(consumer, matrix4f, corner101, color, negativeY, combinedLight);
        addVertex(consumer, matrix4f, corner001, color, negativeY, combinedLight);

        addVertex(consumer, matrix4f, corner001, color, positiveZ, combinedLight);
        addVertex(consumer, matrix4f, corner101, color, positiveZ, combinedLight);
        addVertex(consumer, matrix4f, corner111, color, positiveZ, combinedLight);
        addVertex(consumer, matrix4f, corner011, color, positiveZ, combinedLight);

        addVertex(consumer, matrix4f, corner010, color, negativeZ, combinedLight);
        addVertex(consumer, matrix4f, corner110, color, negativeZ, combinedLight);
        addVertex(consumer, matrix4f, corner100, color, negativeZ, combinedLight);
        addVertex(consumer, matrix4f, corner000, color, negativeZ, combinedLight);

        addVertex(consumer, matrix4f, corner100, color, positiveX, combinedLight);
        addVertex(consumer, matrix4f, corner110, color, positiveX, combinedLight);
        addVertex(consumer, matrix4f, corner111, color, positiveX, combinedLight);
        addVertex(consumer, matrix4f, corner101, color, positiveX, combinedLight);

        addVertex(consumer, matrix4f, corner001, color, negativeX, combinedLight);
        addVertex(consumer, matrix4f, corner011, color, negativeX, combinedLight);
        addVertex(consumer, matrix4f, corner010, color, negativeX, combinedLight);
        addVertex(consumer, matrix4f, corner000, color, negativeX, combinedLight);

        poseStack.popPose();
    }

    private void addVertex(
            VertexConsumer consumer,
            Matrix4f matrix4f,
            Vector3fc position,
            Vector4fc color,
            Vector3fc normal,
            int combinedLight
    ) {
        consumer
                .vertex(matrix4f, position.x(), position.y(), position.z())
                .color(color.x(), color.y(), color.z(), color.w())
                .uv(0, 0)
                .uv2(combinedLight)
                .normal(normal.x(), normal.y(), normal.z())
                .endVertex();
    }
}
