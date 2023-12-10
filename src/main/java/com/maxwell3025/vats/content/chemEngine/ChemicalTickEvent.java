package com.maxwell3025.vats.content.chemEngine;

import com.maxwell3025.vats.content.ChemicalMixBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.maxwell3025.vats.Vats.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ChemicalTickEvent extends Event {
    public Level level;
    public ChemicalTickEvent(Level level){
        this.level = level;

    }
    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event){
        MinecraftForge.EVENT_BUS.post(new ChemicalTickEvent(event.level));
    }
}
