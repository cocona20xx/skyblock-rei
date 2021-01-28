package com.cocona.skyblockrei.recipe.skyblockCrafting;

import com.cocona.skyblockrei.SkyblockREI;
import me.shedaniel.rei.api.RecipeCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class SkyblockCraftingCategory implements RecipeCategory<SkyblockCraftingRecipeDisplay> {
    @Override
    public @NotNull Identifier getIdentifier() {
        return SkyblockREI.SKYBLOCK_CRAFTING_ID;
    }

    @Override
    public @NotNull String getCategoryName() {
        return "Skyblock Custom Crafting";
    }
}
