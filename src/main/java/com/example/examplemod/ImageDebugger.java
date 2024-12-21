package com.example.examplemod;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.SequencedSet;
import java.util.Set;
import java.util.function.Function;

public class ImageDebugger {
    static LinkedHashMap<String, Function<Double, Integer>> colorMap = new LinkedHashMap<>();
    static {
        colorMap.put("red", value -> (255 << 24) | ((int)(255 * value) << 16) | ((int)(0 * value) << 8) | ((int)(0 * value)));
        colorMap.put("green", value -> (255 << 24) | ((int)(0 * value) << 16) | ((int)(255 * value) << 8) | ((int)(0 * value)));
        colorMap.put("blue", value -> (255 << 24) | ((int)(0 * value) << 16) | ((int)(0 * value) << 8) | ((int)(255 * value)));
//        colorMap.put("yellow", value -> (255 << 24) | ((int)(255 * value) << 16) | ((int)(255 * value) << 8) | ((int)(0 * value)));
        colorMap.put("cyan", value -> (255 << 24) | ((int)(0 * value) << 16) | ((int)(255 * value) << 8) | ((int)(255 * value)));
        colorMap.put("magenta", value -> (255 << 24) | ((int)(255 * value) << 16) | ((int)(0 * value) << 8) | ((int)(255 * value)));
//        colorMap.put("black", value -> (255 << 24) | ((int)(0 * value) << 16) | ((int)(0 * value) << 8) | ((int)(0 * value)));
        colorMap.put("white", value -> (255 << 24) | ((int)(255 * value) << 16) | ((int)(255 * value) << 8) | ((int)(255 * value)));
        colorMap.put("orange", value -> (255 << 24) | ((int)(255 * value) << 16) | ((int)(165 * value) << 8) | ((int)(0 * value)));
        colorMap.put("purple", value -> (255 << 24) | ((int)(128 * value) << 16) | ((int)(0 * value) << 8) | ((int)(128 * value)));
        colorMap.put("pink", value -> (255 << 24) | ((int)(255 * value) << 16) | ((int)(192 * value) << 8) | ((int)(203 * value)));
        colorMap.put("brown", value -> (255 << 24) | ((int)(165 * value) << 16) | ((int)(42 * value) << 8) | ((int)(42 * value)));
        colorMap.put("gray", value -> (255 << 24) | ((int)(128 * value) << 16) | ((int)(128 * value) << 8) | ((int)(128 * value)));
        colorMap.put("lime", value -> (255 << 24) | ((int)(0 * value) << 16) | ((int)(255 * value) << 8) | ((int)(0 * value)));
        colorMap.put("olive", value -> (255 << 24) | ((int)(128 * value) << 16) | ((int)(128 * value) << 8) | ((int)(0 * value)));
        colorMap.put("navy", value -> (255 << 24) | ((int)(0 * value) << 16) | ((int)(0 * value) << 8) | ((int)(128 * value)));
    }

    static Set<String> colorKeySet = colorMap.keySet();

    public int step = -1;
    int minX;
    int minZ;
    int maxX;
    int maxZ;
    int width;
    int height;

    BufferedImage image;
    File file = null;

    int minRange;
    int maxRange;

    int pixel_size = 4;
    int debug_width = 32;
    int fullstep_override = 0;

    public ImageDebugger(String fileName, int minX, int maxX, int minZ, int maxZ) {
        this.file = new File("logs\\" + fileName);
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.width = (maxX - minX) * pixel_size ;
        this.height = (maxZ - minZ) * pixel_size;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        ExampleMod.LOGGER.info("ImageDebugger x: {} -> {} | z: {} -> {} | pixel_size: {} | width: {} | height: {}", minX, maxX, minZ, maxZ, pixel_size, width, height);
    }

    public void initStep(int minRange, int maxRange) {
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public void draw(int x, int z, double value) {
        int orthoX = (x - minX);
        int orthoZ = (z - minZ);

        if (this.fullstep_override >= 0) {
            if (this.step == this.fullstep_override) {
                Object colorKey = colorKeySet.toArray()[this.fullstep_override];
                int color = colorMap.get(colorKey).apply((value + minRange) / maxRange);

                putPixel(orthoX, orthoZ, color);
            }
        }
        else if ((orthoX >= debug_width * this.step) && (orthoX < debug_width * (this.step + 1))) {
            Object colorKey = colorKeySet.toArray()[this.step];
            int color = colorMap.get(colorKey).apply((value + minRange) / maxRange);

            putPixel(orthoX, orthoZ, color);
        }

        if (x % 16 == 0 || z % 16 == 0) {
            image.setRGB(orthoX * pixel_size, orthoZ * pixel_size, (255 << 24) | (255 << 16) | (255 << 8) | (0));
        }
    }

    public void putPixel(int x, int z, int color) {
        int[] pixels = new int[pixel_size * pixel_size];
        for (int i = 0; i < pixel_size * pixel_size; i++) {
            pixels[i] = color;
        }

        image.setRGB(x * this.pixel_size, z * this.pixel_size, pixel_size, pixel_size, pixels, 0, pixel_size);
    }

    public void end() {
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
