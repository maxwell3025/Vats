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
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChemicalReaction implements Recipe<Container> {
    private static RecipeType<ChemicalReaction> typeInstance = null;

    public Map<Chemical, Integer> getInputs() {
        return inputs;
    }

    public Map<Chemical, Integer> getOutputs() {
        return outputs;
    }

    private final Map<Chemical, Integer> inputs = new HashMap<>();
    private final Map<Chemical, Integer> outputs = new HashMap<>();

    public double getRateConstant() {
        return rateConstant;
    }

    private final double rateConstant;
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
    public ChemicalReaction(ResourceLocation id, Map<Chemical, Integer> inputs, Map<Chemical, Integer> outputs, double rateConstant) {
        this.id = id;
        this.rateConstant = rateConstant;
        this.inputs.putAll(inputs);
        this.outputs.putAll(outputs);
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

    public Mixture getMolInput(){
        Mixture inputMol = new Mixture();
        for(Map.Entry<Chemical, Integer> inputEntry: this.inputs.entrySet()){
            inputMol = inputMol.add(new Mixture(inputEntry.getKey(), inputEntry.getValue()));
        }
        return inputMol;
    }

    public Mixture getMolOutput(){
        Mixture outputMol = new Mixture();
        for(Map.Entry<Chemical, Integer> outputEntry: this.outputs.entrySet()){
            outputMol = outputMol.add(new Mixture(outputEntry.getKey(), outputEntry.getValue()));
        }
        return outputMol;
    }

    /**
     * Returns the change in chemical energy when one mole of the reaction is performed at the given temperature in kelvin
     */
    public double getEnergyChange(double temperature){
        double energyChange = 0;
        for(Chemical outputChemical: this.outputs.keySet()){
            energyChange += outputChemical.getEnergy(temperature) * outputs.get(outputChemical);
        }

        for(Chemical inputChemical: this.inputs.keySet()){
            energyChange -= inputChemical.getEnergy(temperature) * inputs.get(inputChemical);
        }
        return energyChange;
    }

    /**
     * Returns the change in entropy when one mole of the reaction is performed at the given temperature in kelvin.
     * <p>
     * This includes the entropy change resulting from heat generation.
     */
    public double getEntropyChange(double temperature){
        double entropyChange = 0;
        for(Chemical outputChemical: this.outputs.keySet()){
            entropyChange += outputChemical.getEntropy(temperature) * outputs.get(outputChemical);
        }

        for(Chemical inputChemical: this.inputs.keySet()){
            entropyChange -= inputChemical.getEntropy(temperature) * inputs.get(inputChemical);
        }
        if(temperature == 0) return 0; //Entropy can't change at absolute zero
        return entropyChange - getEnergyChange(temperature) / temperature;
    }
    @Override
    public String toString(){
        StringBuilder reactionFormula = new StringBuilder();
        reactionFormula.append(formatChemicalSet(inputs));
        reactionFormula.append(" -> ");
        reactionFormula.append(formatChemicalSet(outputs));
        return reactionFormula.toString();
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

}
