package com.cocona.skyblockrei.recipe.skyblockCrafting;

import net.minecraft.recipe.RecipeType;

public class SkyblockCraftingRecipe {
    private String a1;
    private String a2;
    private String a3;
    private String b1;
    private String b2;
    private String b3;
    private String c1;
    private String c2;
    private String c3;

    public SkyblockCraftingRecipe(String a1, String a2, String a3, String b1, String b2, String b3, String c1, String c2, String c3){
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    public String[][] getRecipeArray() {
        String[][] recipeArray = new String[3][3];
        recipeArray[0][0] = a1;
        recipeArray[0][1] = a2;
        recipeArray[0][2] = a3;
        recipeArray[1][0] = b1;
        recipeArray[1][1] = b2;
        recipeArray[1][2] = b3;
        recipeArray[2][0] = c1;
        recipeArray[2][1] = c2;
        recipeArray[2][2] = c3;
        return recipeArray;
    }

}
