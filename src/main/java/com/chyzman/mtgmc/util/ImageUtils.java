package com.chyzman.mtgmc.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.PathUtil;
import net.minecraft.util.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import static com.mojang.text2speech.Narrator.LOGGER;

@Environment(EnvType.CLIENT)
public class ImageUtils {

    public static CompletableFuture<Identifier> getTexture(Identifier textureId, Path path, String uri) {
        return CompletableFuture.supplyAsync(() -> {
            NativeImage nativeImage;
            try {
                nativeImage = downloadImage(path, uri);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            return nativeImage;
        }, Util.getDownloadWorkerExecutor().named("downloadMtgCard")).thenCompose(image -> registerTexture(textureId, image));
    }

    private static CompletableFuture<Identifier> registerTexture(Identifier textureId, NativeImage image) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        return CompletableFuture.supplyAsync(() -> {
            minecraftClient.getTextureManager().registerTexture(textureId, new NativeImageBackedTexture(image));
            return textureId;
        }, minecraftClient);
    }

    private static NativeImage downloadImage(Path path, String uri) throws IOException {
        if (Files.isRegularFile(path)) {
            LOGGER.debug("Loading HTTP texture from local cache ({})", path);
            InputStream inputStream = Files.newInputStream(path);

            NativeImage image;
            try {
                image = NativeImage.read(inputStream);
            } catch (Throwable var14) {
                try {
                    inputStream.close();
                } catch (Throwable var12) {
                    var14.addSuppressed(var12);
                }

                throw var14;
            }

            inputStream.close();

            return image;
        } else {
            HttpURLConnection httpURLConnection = null;
            LOGGER.debug("Downloading HTTP texture from {} to {}", uri, path);
            URI uRI = URI.create(uri);

            NativeImage iOException;
            try {
                httpURLConnection = (HttpURLConnection) uRI.toURL().openConnection(MinecraftClient.getInstance().getNetworkProxy());
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(false);
                httpURLConnection.connect();
                int i = httpURLConnection.getResponseCode();
                if (i / 100 != 2) {
                    throw new IOException("Failed to open " + uRI + ", HTTP error code: " + i);
                }

                var bufferedImage = ImageIO.read(httpURLConnection.getInputStream());
                var copy = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                var graphics = copy.createGraphics();
                graphics.setColor(Color.black);
                graphics.fillRect(0, 0, copy.getWidth(), copy.getHeight());
                graphics.drawImage(bufferedImage, 0, 0, null);
                graphics.dispose();
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                ImageIO.write(copy, "png", byteArrayOut);
                var bs = byteArrayOut.toByteArray();

                try {
                    PathUtil.createDirectories(path.getParent());
                    Files.write(path, bs);
                } catch (IOException var13) {
                    LOGGER.warn("Failed to cache texture {} in {}", uri, path);
                }

                iOException = NativeImage.read(bs);
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return iOException;
        }
    }
}
