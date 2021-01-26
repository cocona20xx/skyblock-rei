package me.shedaniel.skyblockrei.utils;

import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.function.Function;

public class Utils {
    public static <T, R> Function<T, R> wrap(UnsafeFunction<T, R> function) {
        return t -> map(t, function);
    }
    
    public static void run(UnsafeRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            throw throwSilently(throwable);
        }
    }
    
    public static void runCatching(UnsafeRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    
    public static <T, R> R map(T t, UnsafeFunction<T, R> function) {
        try {
            return function.apply(t);
        } catch (Throwable throwable) {
            throw throwSilently(throwable);
        }
    }
    
    public static RuntimeException throwSilently(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        } else {
            return new RuntimeException(throwable);
        }
    }
    
    public static FileSystem openZip(Path uri, boolean create) throws IOException {
        return openZip(uri.toUri(), create);
    }
    
    public static FileSystem openZip(URI uri, boolean create) throws IOException {
        URI jarUri;
        try {
            jarUri = new URI("jar:" + uri.getScheme(), uri.getHost(), uri.getPath(), uri.getFragment());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
        
        return FileSystems.newFileSystem(jarUri, ImmutableMap.of("create", create));
    }
    
    @FunctionalInterface
    public interface UnsafeRunnable {
        void run() throws Throwable;
    }
    
    @FunctionalInterface
    public interface UnsafeFunction<T, R> {
        R apply(T object) throws Throwable;
    }
}
