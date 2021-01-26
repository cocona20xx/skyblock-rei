package me.shedaniel.skyblockrei.mixin;

import com.mojang.brigadier.StringReader;
import me.shedaniel.skyblockrei.hooks.StringNbtReaderHooks;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.nbt.Tag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StringNbtReader.class)
public class MixinStringNbtReader implements StringNbtReaderHooks {
    @Shadow @Final private StringReader reader;
    @Unique
    private boolean legacyStyle = false;
    
    @Override
    public void skyblockrei_markLegacy() {
        this.legacyStyle = true;
    }
    
    @Inject(method = "parseListTag", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;getCursor()I"))
    private void addUseless(CallbackInfoReturnable<Tag> cir) {
        if (legacyStyle) {
            // Legacy NBT format contains useless indices to the list tag, here we will just skip them as we go.
            while (reader.peek(-1) != ':') {
                reader.skip();
            }
        }
    }
}
