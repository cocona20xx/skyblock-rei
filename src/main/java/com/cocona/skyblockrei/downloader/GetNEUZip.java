package com.cocona.skyblockrei.downloader;

import com.cocona.skyblockrei.SkyblockREI;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.sql.Timestamp;

public class GetNEUZip {
    public static void getZip(){
        try {
            File neu = FabricLoader.getInstance().getConfigDir().resolve("skyblockrei").resolve("NEU.zip").toFile();
            if(!neu.exists() || neu.lastModified() + (86400000 * 2) <= System.currentTimeMillis()){
                if(neu.exists()){
                    neu.delete();
                    SkyblockREI.LOG.info("Updating previously existing NEU file.");
                } else {
                    SkyblockREI.LOG.info("Downloading fresh NEU file.");
                }
                URL url = new URL("https", "www.dropbox.com", "/s/w04p5ez5dqrmy5w/NEU.zip?dl=1");
                InputStream in = url.openStream();
                Files.copy(in, FabricLoader.getInstance().getConfigDir().resolve("skyblockrei").resolve("NEU.zip"));
                in.close();
            } else {
                SkyblockREI.LOG.info("Not downloading a new NEU file to the configs folder. If you. the user, wants to force re-download the file, delete it and restart!");
                SkyblockREI.LOG.info("Files normally download once every 2 days.");
            }
        } catch (Exception e){
            SkyblockREI.LOG.error(e);
        }
    }

}
