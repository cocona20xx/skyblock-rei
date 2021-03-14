package com.cocona.skyblockrei;

import com.cocona.skyblockrei.data.PackagedData;
import com.cocona.skyblockrei.downloader.GetNEUZip;
import com.cocona.skyblockrei.utils.Utils;
import com.cocona.skyblockrei.data.DataManager;
import me.shedaniel.rei.api.EntryStack;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class SkyblockREI implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("skyblock-rei");

    public static PackagedData packagedDataRef;


    public static final Identifier SKYBLOCK_CRAFTING_ID = new Identifier("skyblock-rei", "recipes/skyblock_crafting");

    @Override
    public void onInitializeClient() {
        GetNEUZip.getZip();
        Utils.run(DataManager::init);

    }

    public static void updatePackagedDataRef(PackagedData update){
        packagedDataRef = update;
    }

    public static boolean isAllEntryStacksNull(List<EntryStack> list) {
        int potential = list.size();
        for(EntryStack e : list){
            if(e == null){
                potential--;
            }
        }
        if(potential <= 0){
            return true;
        }else return false;
    }
}
