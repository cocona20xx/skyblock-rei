package com.cocona.skyblockrei;

import com.cocona.skyblockrei.downloader.GetNEUZip;
import com.cocona.skyblockrei.utils.Utils;
import com.cocona.skyblockrei.data.DataManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SkyblockREI implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("skyblock-rei");

    public static final Identifier SKYBLOCK_CRAFTING_ID = new Identifier("skyblock-rei", "recipes/skyblock_crafting");

    @Override
    public void onInitializeClient() {
        GetNEUZip.getZip();
        Utils.run(DataManager::init);

    }
}
