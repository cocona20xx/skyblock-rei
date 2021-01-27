package com.cocona.skyblockrei;

import com.cocona.skyblockrei.utils.Utils;
import com.cocona.skyblockrei.data.DataManager;
import net.fabricmc.api.ClientModInitializer;

public class SkyblockREI implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Utils.run(DataManager::init);
    }
}
