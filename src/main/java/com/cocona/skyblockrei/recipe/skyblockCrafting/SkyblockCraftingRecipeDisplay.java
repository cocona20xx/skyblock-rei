package com.cocona.skyblockrei.recipe.skyblockCrafting;

import com.cocona.skyblockrei.SkyblockREI;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkyblockCraftingRecipeDisplay implements RecipeDisplay {
    @Override
    public @NotNull List<List<EntryStack>> getInputEntries() {
        return null;
    }

    @Override
    public @NotNull Identifier getRecipeCategory() {
        return SkyblockREI.SKYBLOCK_CRAFTING_ID;
    }
}
