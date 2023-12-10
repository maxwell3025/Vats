package com.maxwell3025.vats.api;

import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChemicalReactionSerializer implements RecipeSerializer<ChemicalReaction> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ChemicalReactionSerializer instance = null;

    public static ChemicalReactionSerializer getInstance() {
        if (instance == null) {
            instance = new ChemicalReactionSerializer();
        }
        return instance;
    }

    private ChemicalReactionSerializer() {
    }

    private Map<Chemical, Integer> toReactionSide(JsonObject jsonData) {
        Map<Chemical, Integer> formattedData = new HashMap<>();
        for (String key : jsonData.keySet()) {
            formattedData.put(Chemical.fromRegistryName(new ResourceLocation(key)), jsonData.getAsJsonPrimitive(key).getAsInt());
        }
        return formattedData;
    }

    @Override
    public ChemicalReaction fromJson(ResourceLocation id, JsonObject data) {
        double reactionRate = data.getAsJsonPrimitive("rate").getAsDouble();
        JsonObject inputObject = data.getAsJsonObject("input");
        JsonObject outputObject = data.getAsJsonObject("output");

        return new ChemicalReaction(id, toReactionSide(inputObject), toReactionSide(outputObject), reactionRate);
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
