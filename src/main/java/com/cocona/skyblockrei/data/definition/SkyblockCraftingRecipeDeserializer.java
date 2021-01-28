package com.cocona.skyblockrei.data.definition;

import com.cocona.skyblockrei.recipe.skyblockCrafting.SkyblockCraftingRecipe;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SkyblockCraftingRecipeDeserializer implements JsonDeserializer<SkyblockCraftingRecipe> {

    @Override
    public SkyblockCraftingRecipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        try {
            return new SkyblockCraftingRecipe(
                    jsonObject.get("a1").getAsString(),
                    jsonObject.get("a2").getAsString(),
                    jsonObject.get("a3").getAsString(),
                    jsonObject.get("b1").getAsString(),
                    jsonObject.get("b2").getAsString(),
                    jsonObject.get("b3").getAsString(),
                    jsonObject.get("c1").getAsString(),
                    jsonObject.get("c2").getAsString(),
                    jsonObject.get("c3").getAsString()
            );
        } catch (NullPointerException npe){
            //silently catches NPE and returns null
            return null;
        }
    }
}
