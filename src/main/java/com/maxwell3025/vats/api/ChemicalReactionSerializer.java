package com.maxwell3025.vats.api;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class ChemicalReactionSerializer implements RecipeSerializer<ChemicalReaction> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ChemicalReactionSerializer instance = null;
    public static ChemicalReactionSerializer getInstance(){
        if(instance == null){
            instance = new ChemicalReactionSerializer();
        }
        return instance;
    }
    private ChemicalReactionSerializer(){

    }
    @Override
    public ChemicalReaction fromJson(ResourceLocation p_44103_, JsonObject p_44104_) {
        LOGGER.error("Hello!");
        return null; //TODO
    }

    @Override
    public @Nullable ChemicalReaction fromNetwork(ResourceLocation p_44105_, FriendlyByteBuf p_44106_) {
        return null; //TODO
    }

    @Override
    public void toNetwork(FriendlyByteBuf p_44101_, ChemicalReaction p_44102_) {
        //TODO
    }
}
