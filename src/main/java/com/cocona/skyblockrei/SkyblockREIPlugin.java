package com.cocona.skyblockrei;

import com.cocona.skyblockrei.data.PackagedData;
import com.cocona.skyblockrei.recipe.skyblockCrafting.SkyblockCustomCategory;
import com.cocona.skyblockrei.recipe.skyblockCrafting.SkyblockCustomDisplay;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import me.shedaniel.rei.api.EntryRegistry;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import com.cocona.skyblockrei.data.DataManager;
import com.cocona.skyblockrei.utils.Utils;
import me.shedaniel.rei.plugin.crafting.DefaultCustomDisplay;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SkyblockREIPlugin implements REIPluginV0 {

    public PackagedData packagedData;

    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier("skyblockrei:rei_plugin");
    }
    
    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        Utils.run(DataManager::init);
        packagedData = new PackagedData(DataManager.ITEMS, DataManager.RECIPES, DataManager.DEFINITION_MAP);
        entryRegistry.removeEntryIf(Predicates.alwaysTrue());
        List<EntryStack> stacks = new ArrayList<>(DataManager.ITEMS);
        stacks.sort(Comparator.<EntryStack, Identifier>comparing(stack -> Registry.ITEM.getId(stack.getItemStack().getItem()))
                .thenComparing(stack -> stack.asFormatStrippedText().asString()));
        entryRegistry.registerEntries(stacks.toArray(new EntryStack[0]));
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        List<Pair<EntryStack, List<EntryStack>>> data = packagedData.recipePrep();
        List<List<EntryStack>> inputsList = Lists.newArrayListWithCapacity(1000);
        List<EntryStack> outputsList = Lists.newArrayListWithCapacity(1000);
        for(Pair<EntryStack, List<EntryStack>> p : data){
            inputsList.add(p.getRight());
            outputsList.add(p.getLeft());
        }
        for(EntryStack out : outputsList){
            List<EntryStack> in = inputsList.get(outputsList.indexOf(out));
            List<List<EntryStack>> inReady = new ArrayList<>();
            for(EntryStack i : in){
                List<EntryStack> iPrep = new ArrayList<>();
                iPrep.add(i);
                inReady.add(iPrep);
            }
            List<EntryStack> outReady = new ArrayList<>();
            outReady.add(out);
            recipeHelper.registerDisplay(new SkyblockCustomDisplay(null, inReady, outReady));

        }
    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        SkyblockCustomCategory category = new SkyblockCustomCategory();
        recipeHelper.registerCategory(category);
    }
}
