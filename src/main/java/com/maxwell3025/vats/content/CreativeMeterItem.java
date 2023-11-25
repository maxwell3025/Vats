package com.maxwell3025.vats.content;

import com.maxwell3025.vats.content.chemEngine.ChunkChemicalData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreativeMeterItem extends Item {
    private static final Logger LOGGER = LogManager.getLogger();
    private static CreativeMeterItem instance = null;
    public static CreativeMeterItem getInstance(){
        if(instance == null){
            instance = new CreativeMeterItem(new Item.Properties());
        }
        return instance;
    }
    public CreativeMeterItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult useOn(UseOnContext context){
        Capability<ChunkChemicalData> CHEMICAL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
        if(!context.getLevel().isClientSide){
            BlockPos clickedPos = context.getClickedPos();
            LevelChunk chunk = context.getLevel().getChunk(clickedPos.getX() >> 4, clickedPos.getZ() >> 4);
            LazyOptional<ChunkChemicalData> chemicalDataProvider = chunk.getCapability(CHEMICAL_CAPABILITY);
            chemicalDataProvider.ifPresent(chemicalData -> {
                LOGGER.warn(chemicalData.concentrations);
            });
        }

        return InteractionResult.SUCCESS;
    }
}
