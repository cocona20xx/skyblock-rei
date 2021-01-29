package com.cocona.skyblockrei.recipe.skyblockCrafting;

import com.cocona.skyblockrei.SkyblockREI;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.plugin.crafting.DefaultCustomDisplay;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkyblockCustomDisplay extends DefaultCustomDisplay {
    public SkyblockCustomDisplay(Recipe<?> possibleRecipe, List<List<EntryStack>> input, List<EntryStack> output) {
        super(possibleRecipe, input, output);
    }

    @Override
    public @NotNull Identifier getRecipeCategory() {
        return SkyblockREI.SKYBLOCK_CRAFTING_ID;
    }
}
