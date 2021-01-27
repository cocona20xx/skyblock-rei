package com.cocona.skyblockrei;

import com.google.common.base.Predicates;
import me.shedaniel.rei.api.EntryRegistry;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import com.cocona.skyblockrei.data.DataManager;
import com.cocona.skyblockrei.utils.Utils;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SkyblockREIPlugin implements REIPluginV0 {
    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier("skyblockrei:rei_plugin");
    }
    
    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        Utils.run(DataManager::init);
        entryRegistry.removeEntryIf(Predicates.alwaysTrue());
        List<EntryStack> stacks = new ArrayList<>(DataManager.ITEMS);
        stacks.sort(Comparator.<EntryStack, Identifier>comparing(stack -> Registry.ITEM.getId(stack.getItemStack().getItem()))
                .thenComparing(stack -> stack.asFormatStrippedText().asString()));
        entryRegistry.registerEntries(stacks.toArray(new EntryStack[0]));
    }
}
