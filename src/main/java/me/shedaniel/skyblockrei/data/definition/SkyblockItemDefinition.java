package me.shedaniel.skyblockrei.data.definition;

import com.google.gson.annotations.SerializedName;

public class SkyblockItemDefinition {
    @SerializedName("internalname")
    public String internalName;
    @SerializedName("itemid")
    public String id;
    @SerializedName("displayname")
    public String displayName;
    @SerializedName("clickcommand")
    public String clickCommand;
    public int damage = 0;
    @SerializedName("nbttag")
    public String nbt = "{}";
    @SerializedName("modver")
    public String modVersion;
    @SerializedName("lore")
    public String[] lore;
}
