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
    public static Map<String, Pair<String, EntryStack>> defMap = new HashMap<>();

    public PackagedData(List<EntryStack> i, List<Pair<String, SkyblockCraftingRecipe>> r, Map<String, Pair<String, EntryStack>> d) {
        items = i;
        recipes = r;
        defMap = d;
    }

    public EntryStack getEntryFromMap(String key){
        Pair<String, EntryStack> defaultResult = new Pair<>("", EntryStack.empty());
        int amount = 1;
        //amount parsing
        String keyProcessed = key;
        if (key.contains(":")){
            String[] split = key.split(":");
            amount = Integer.parseInt(split[1]);
            keyProcessed = split[0];
        }
        Pair<String, EntryStack> attempt = defMap.getOrDefault(keyProcessed, defaultResult);
        //sanity check
        if(attempt.getLeft().matches(keyProcessed)){
            EntryStack returnValue = attempt.getRight().copy();
            returnValue.setAmount(amount);
            return returnValue;
        } else return EntryStack.empty();
    }

    public List<Pair<EntryStack, List<EntryStack>>> recipePrep(){
        List<Pair<EntryStack, List<EntryStack>>> output = new ArrayList<>();
        for(Pair<String, SkyblockCraftingRecipe> recipePair : recipes){
            EntryStack out = getEntryFromMap(recipePair.getLeft());
            EntryStack a1 = getEntryFromMap(recipePair.getRight().a1);
            EntryStack a2 = getEntryFromMap(recipePair.getRight().a2);
            EntryStack a3 = getEntryFromMap(recipePair.getRight().a3);
            EntryStack b1 = getEntryFromMap(recipePair.getRight().b1);
            EntryStack b2 = getEntryFromMap(recipePair.getRight().b2);
            EntryStack b3 = getEntryFromMap(recipePair.getRight().b3);
            EntryStack c1 = getEntryFromMap(recipePair.getRight().c1);
            EntryStack c2 = getEntryFromMap(recipePair.getRight().c2);
            EntryStack c3 = getEntryFromMap(recipePair.getRight().c3);
            if(!out.isEmpty()){
                List<EntryStack> outputSublist = new ArrayList<>();
                outputSublist.add(a1);
                outputSublist.add(a2);
                outputSublist.add(a3);
                outputSublist.add(b1);
                outputSublist.add(b2);
                outputSublist.add(b3);
                outputSublist.add(c1);
                outputSublist.add(c2);
                outputSublist.add(c3);
                output.add(new Pair<>(out, outputSublist));
            }
        }
        return output;
    }



}
