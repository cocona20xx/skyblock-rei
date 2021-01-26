package me.shedaniel.skyblockrei;

import me.shedaniel.skyblockrei.data.DataManager;
import me.shedaniel.skyblockrei.utils.Utils;
import net.fabricmc.api.ClientModInitializer;

public class SkyblockREI implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Utils.run(DataManager::init);
    }
}
