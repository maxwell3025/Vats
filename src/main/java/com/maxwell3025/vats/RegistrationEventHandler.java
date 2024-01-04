package com.maxwell3025.vats;

import com.maxwell3025.vats.content.ChemicalMixBlockEntity;
import com.maxwell3025.vats.content.ChemicalMixBlockRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Every event that is subscribed to the Mod-Specific event bus
 */
public class RegistrationEventHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    @SubscribeEvent
    public void subscribeBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ChemicalMixBlockEntity.getTypeInstance(), ChemicalMixBlockRenderer::new);
    }
}
