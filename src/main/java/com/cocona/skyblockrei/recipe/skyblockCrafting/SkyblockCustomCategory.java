package com.cocona.skyblockrei.recipe.skyblockCrafting;

import com.cocona.skyblockrei.SkyblockREI;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.plugin.crafting.DefaultCraftingCategory;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class SkyblockCustomCategory extends DefaultCraftingCategory {

    @Override
    public @NotNull EntryStack getLogo() {
        return EntryStack.create(Items.GRASS_BLOCK);
    }

    @Override
    public @NotNull Identifier getIdentifier() {
        return SkyblockREI.SKYBLOCK_CRAFTING_ID;
    }

    @Override
    public @NotNull String getCategoryName() {
        return "SkyblockCraftingImpl";
    }


}
