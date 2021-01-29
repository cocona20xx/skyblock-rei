package com.cocona.skyblockrei.data;

import com.cocona.skyblockrei.hooks.StringNbtReaderHooks;
import com.cocona.skyblockrei.data.definition.SkyblockCraftingRecipe;
import com.cocona.skyblockrei.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import me.shedaniel.rei.api.EntryStack;
import com.cocona.skyblockrei.data.definition.SkyblockItemDefinition;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.datafixer.fix.ItemInstanceTheFlatteningFix;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.StreamSupport;

public class DataManager {
    private static final Path BASE_DIR = FabricLoader.getInstance().getConfigDir().resolve("skyblockrei");
    public static final List<EntryStack> ITEMS = new ArrayList<>();
    public static final List<Pair <String, SkyblockCraftingRecipe>> RECIPES = new ArrayList<>();
    public static final List<Pair<String, EntryStack>> DEFINITION_LIST = new ArrayList<>();


    public static void init() throws IOException {
        Files.createDirectories(BASE_DIR);
        RECIPES.clear();
        ITEMS.clear();
        DEFINITION_LIST.clear();
        readNEU(BASE_DIR.resolve("NEU.zip"));
    }
    
    private static void readNEU(Path path) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.disableHtmlEscaping().setPrettyPrinting().create();
        try (FileSystem system = Utils.openZip(path, false)) {
            StreamSupport.stream(system.getRootDirectories().spliterator(), false)
                    .flatMap(Utils.wrap(Files::walk))
                    .forEach(p -> {
                        Utils.runCatching(() -> {
                            if (p.toString().contains("/items/") && p.toString().endsWith(".json")) {
                                try (BufferedReader reader = Files.newBufferedReader(p)) {
                                    SkyblockItemDefinition item = gson.fromJson(reader, SkyblockItemDefinition.class);
                                    Identifier id;
                                    
                                    String fix = ItemInstanceTheFlatteningFix.getItem(item.id, item.damage);
                                    if (fix != null) {
                                        id = new Identifier(fix);
                                    } else {
                                        id = new Identifier(item.id);
                                    }

                                    synchronized (RECIPES){
                                        if(item.recipe != null) {
                                            RECIPES.add(new Pair<>(item.internalName, item.recipe));
                                    }
                                    
                                    ItemStack stack = new ItemStack(Registry.ITEM.get(id));
                                    StringNbtReader nbtReader = new StringNbtReader(new StringReader(item.nbt));
                                    ((StringNbtReaderHooks) nbtReader).skyblockrei_markLegacy();
                                    CompoundTag tag = nbtReader.parseCompoundTag();
                                    if (item.lore != null && item.lore.length > 0) {
                                        ListTag list = new ListTag();
                                        for (String s : item.lore) {
                                            list.add(StringTag.of(Text.Serializer.toJson(new LiteralText(s))));
                                        }
                                        CompoundTag display = tag.contains("display") ? tag.getCompound("display") : new CompoundTag();
                                        display.put("Lore", list);
                                        if (item.displayName != null) {
                                            display.put("Name", StringTag.of(Text.Serializer.toJson(new LiteralText(item.displayName))));
                                        }
                                        tag.put("display", display);
                                    }
                                    if (tag.contains("SkullOwner")){
                                        tag.getCompound("SkullOwner").put("Name", StringTag.of(Text.Serializer.toJson(new LiteralText(item.displayName + "NEUPLUGIN"))));
                                    }
                                    stack.setTag(tag);
                                    EntryStack entry = EntryStack.create(stack);
                                    entry.setting(EntryStack.Settings.CHECK_TAGS, EntryStack.Settings.TRUE);
                                    synchronized (ITEMS) {
                                        ITEMS.add(entry);
                                    }
                                    synchronized (DEFINITION_LIST) {
                                        Pair<String, EntryStack> pair = new Pair<>(item.internalName, entry);
                                        DEFINITION_LIST.add(pair);
                                    }
                                }
                            }
                        }
                    });
                    });
        }
    }

}

