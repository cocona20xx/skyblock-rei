package com.cocona.skyblockrei.data;

import com.cocona.skyblockrei.data.definition.SkyblockCraftingRecipe;
import com.google.common.collect.Lists;
import me.shedaniel.rei.api.EntryStack;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackagedData {
    public static List<EntryStack> items = new ArrayList<>();
    public static List<Pair<String, SkyblockCraftingRecipe>> recipes = new ArrayList<>();
    public static List<Pair<String, EntryStack>> defList = new ArrayList<>();

    public PackagedData(List<EntryStack> i, List<Pair<String, SkyblockCraftingRecipe>> r, List<Pair<String, EntryStack>> d) {
        items = i;
        recipes = r;
        defList = d;
    }

    private EntryStack recipeMatcher(EntryStack current, Pair<String, EntryStack> definition, String internalName){
        int quantity;
        String name;
        if(internalName.contains(":")) {
            String[] split = internalName.split(":");
            quantity = Integer.parseInt(split[1]);
            name = split[0];
        } else {
            quantity = 1;
            name = internalName;
        }
        if(definition.getLeft().equals(name)){
            EntryStack returnValue = definition.getRight().copy();
            returnValue.setAmount(quantity);
            return returnValue;
        } else return current;
    }

    public List<Pair<EntryStack, List<EntryStack>>> recipePrep(){
        List<Pair<EntryStack, List<EntryStack>>> output = Lists.newArrayList();
        for(Pair<String, SkyblockCraftingRecipe> recipe : recipes){
            EntryStack a1 = EntryStack.empty(),
                    a2 = EntryStack.empty(),
                    a3 = EntryStack.empty(),
                    b1 = EntryStack.empty(),
                    b2 = EntryStack.empty(),
                    b3 = EntryStack.empty(),
                    c1 = EntryStack.empty(),
                    c2 = EntryStack.empty(),
                    c3 = EntryStack.empty(),
                    out = EntryStack.empty();

            for(Pair<String, EntryStack> definition : defList){
                out = recipeMatcher(out, definition, recipe.getLeft());
                a1 = recipeMatcher(a1, definition, recipe.getRight().a1);
                a2 = recipeMatcher(a2, definition, recipe.getRight().a2);
                a3 = recipeMatcher(a3, definition, recipe.getRight().a3);
                b1 = recipeMatcher(b1, definition, recipe.getRight().b1);
                b2 = recipeMatcher(b2, definition, recipe.getRight().b2);
                b3 = recipeMatcher(b3, definition, recipe.getRight().b3);
                c1 = recipeMatcher(c1, definition, recipe.getRight().c1);
                c2 = recipeMatcher(c2, definition, recipe.getRight().c2);
                c3 = recipeMatcher(c3, definition, recipe.getRight().c3);

            }
            List<EntryStack> inputList = new ArrayList<>();
            inputList.add(a1);
            inputList.add(a2);
            inputList.add(a3);
            inputList.add(b1);
            inputList.add(b2);
            inputList.add(b3);
            inputList.add(c1);
            inputList.add(c2);
            inputList.add(c3);
            Pair<EntryStack, List<EntryStack>> pairRecipe = new Pair<>(out, inputList);
            if(!pairRecipe.getLeft().isEmpty()) {
                output.add(pairRecipe);
            }
        }
        return output;
    }



}
