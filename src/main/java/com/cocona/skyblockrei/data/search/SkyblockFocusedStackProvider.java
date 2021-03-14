package com.cocona.skyblockrei.data.search;

import com.cocona.skyblockrei.SkyblockREI;
import com.cocona.skyblockrei.SkyblockREIPlugin;
import com.cocona.skyblockrei.mixin.HandledScreenAccess;
import com.mojang.brigadier.StringReader;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.FocusedStackProvider;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.TypedActionResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SkyblockFocusedStackProvider implements FocusedStackProvider {
    @Override
    public @NotNull TypedActionResult<EntryStack> provide(Screen screen) {
        if (screen instanceof HandledScreen) {
            HandledScreen<?> containerScreen = (HandledScreen<?>) screen;

            if (((HandledScreenAccess)containerScreen).getFocusedSlot() != null && !((HandledScreenAccess)containerScreen).getFocusedSlot().getStack().isEmpty()){
                ItemStack focusedItem = ((HandledScreenAccess)containerScreen).getFocusedSlot().getStack();
                try {
                    CompoundTag tag = focusedItem.getTag();
                    CompoundTag subtag = tag.getCompound("ExtraAttributes");
                    String attID = subtag.getString("id");
                    EntryStack result = SkyblockREI.packagedDataRef.getEntryFromMap(attID);
                    if(!result.equals(EntryStack.empty())){
                        return TypedActionResult.success(result.setting(EntryStack.Settings.CHECK_TAGS, () -> true));
                    } else {
                        return TypedActionResult.success(EntryStack.create(((HandledScreenAccess) containerScreen).getFocusedSlot().getStack()));
                    }

                } catch(Exception e){
                    SkyblockREI.LOG.error("Error caught while parsing item nbt for skyblock-specific data. Returning to default behavior!");
                    SkyblockREI.LOG.catching(e);
                    return TypedActionResult.success(EntryStack.create(((HandledScreenAccess) containerScreen).getFocusedSlot().getStack()));
                }
        }
    }
        return TypedActionResult.pass(EntryStack.empty());
    }

    @Override
    public double getPriority() {
        return 1000;
    }
}
