package com.maxwell3025.vats.api;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChemicalReaction implements Recipe<Container> {
    private static RecipeType<ChemicalReaction> typeInstance = null;
    private final Map<Chemical, Integer> inputs = new HashMap<>();
    private final Map<Chemical, Integer> outputs = new HashMap<>();
    private final double rate;


    public static RecipeType<ChemicalReaction> getTypeInstance() {
        if (typeInstance == null) {
            typeInstance = new RecipeType<ChemicalReaction>() {
                @Override
                public String toString() {
                    return "reaction";
                }
            };
        }
        return typeInstance;
    }

    protected final ResourceLocation id;

    public ChemicalReaction(ResourceLocation id, Map<Chemical, Integer> inputs, Map<Chemical, Integer> outputs, double rate) {
        this.id = id;
        this.rate = rate;
        this.inputs.putAll(inputs);
        this.outputs.putAll(outputs);
    }

    @Override
    public boolean matches(Container p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ChemicalReactionSerializer.getInstance();
    }

    @Override
    public RecipeType<ChemicalReaction> getType() {
        return getTypeInstance();
    }

    private String formatChemicalSet(Map<Chemical, Integer> chemicalSet){
        StringBuilder formattedString = new StringBuilder();
        int tokenCount = chemicalSet.size();
        int numPrinted = 0;
        for(Map.Entry<Chemical, Integer> entry : chemicalSet.entrySet()){
            formattedString.append(entry.getValue());
            formattedString.append(" ");
            formattedString.append(entry.getKey().toFormulaString());
            numPrinted++;
            if(numPrinted != tokenCount){
                formattedString.append(" + ");
            }
        }
        return formattedString.toString();
    }

    @Override
    public String toString(){
        StringBuilder reactionFormula = new StringBuilder();
        reactionFormula.append(formatChemicalSet(inputs));
        reactionFormula.append(" -> ");
        reactionFormula.append(formatChemicalSet(outputs));
        return reactionFormula.toString();
    }
}
