package com.example.examplemod.worldgen;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.NoiseStack;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

import static org.apache.commons.lang3.reflect.FieldUtils.readField;

public class LabyrinthChunkGenerator extends NoiseBasedChunkGenerator {
    static NoiseStack primitive_cos = new NoiseStack(12L) {
        public String getName() { return "primitive_cos" + this.getNoise_zoom(); }

        public double getX(int x) { return Math.cos(x / this.getNoise_zoom()); }
        public double getY(int y) { return Math.cos(y / this.getNoise_zoom()); }

        public double getValue(int x, int y) {
            return (this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1)/ 2;
        }

        public double getNoise_zoom() { return 8; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
                    d -> (double) Math.round(d * 3) / 3,
                    d -> d < 0.3 || d > 0.5 ? 0.5 : 0
            };
        }
    };
    static NoiseStack primitive_sin = new NoiseStack(12L) {
        public String getName() { return "primitive_sin" + this.getNoise_zoom(); }

        public double getX(int x) { return Math.sin(x / this.getNoise_zoom()); }
        public double getY(int y) { return Math.sin(y / this.getNoise_zoom()); }

        public double getValue(int x, int y) {
            return (this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1)/ 2;
        }

        public double getNoise_zoom() { return 8; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
                    d -> (double) Math.round(d * 3) / 3,
                    d -> d < 0.3 || d > 0.5 ? 0.5 : 0
            };
        }
    };

    static NoiseStack primitive_dot = new NoiseStack(12L) {
        public String getName() { return "primitive_dot" + this.getNoise_zoom(); }

//        public double getX(int x) { return Math.sin(x / this.getNoise_zoom()); }
//        public double getY(int y) { return Math.sin(y / this.getNoise_zoom()); }

        public double getValue(int x, int y) {
//            return (this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1)/ 2;
            return primitive_cos.getValue(x, y) * primitive_sin.getValue(x, y);
        }

        public double getNoise_zoom() { return 8; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
//                    d -> primitive_sin.
                    d -> (double) Math.round(d * 3) / 3,
                    d -> d < 0.3 || d > 0.5 ? 0.5 : 0
            };
        }
    };

    static NoiseStack primitive_dot_altered = new NoiseStack(12L) {
        public String getName() { return "primitive_dot_altered" + this.getNoise_zoom(); }

//        public double getX(int x) { return Math.sin(x / this.getNoise_zoom()); }
//        public double getY(int y) { return Math.sin(y / this.getNoise_zoom()); }

        public double getValue(int x, int y) {
//            return (this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1)/ 2;
            return (primitive_cos.getValue(x, y) + primitive_sin.getValue(x, y)) / 2;
        }

        public double getNoise_zoom() { return 8; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
//                    d -> primitive_sin.
                    d -> (double) Math.round(d * 3) / 3,
                    d -> d < 0.3 || d > 0.5 ? 0.5 : 0
            };
        }
    };

    static private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
        {
            result += y;
        }
        return result;
    }

    static NoiseStack test = new NoiseStack(12L) {
        public String getName() { return "test" + this.getNoise_zoom(); }

//        public double getX(int x) { return Math.sin(x / this.getNoise_zoom()); }
//        public double getY(int y) { return Math.sin(y / this.getNoise_zoom()); }

        public double getValue(int x, int y) {
//            return (this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1)/ 2;
            return (primitive_cos.resolve(x, y) + primitive_sin.resolve(x, y)) / 2; //* primitive_cos.getValue(x, y);
        }

        public double getNoise_zoom() { return 8; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
//                    d -> primitive_sin.
                    d -> (double) Math.round(d * 3) / 3,
                    d -> d < 0.3 || d > 0.5 ? 0.5 : 0
            };
        }
    };

    static {
        ExampleMod.LOGGER.info("HERE {}", FMLPaths.MODSDIR.get());
    }

    static NoiseStack basic = new NoiseStack(12L) {
        public String getName() { return "basic" + this.getNoise_zoom(); }

        public double getX(int x) { return x / this.getNoise_zoom(); }
        public double getY(int y) { return y / this.getNoise_zoom(); }

        public double getValue(int x, int y) {
            int abs_x = Math.abs(x);
            int abs_y = Math.abs(y);

            if (x < 0)
                abs_x -= 1;
            if (y < 0)
                abs_y -= 1;

            int left_x = (int)Math.copySign(abs_x - mod(abs_x, 16), x);
            int right_x = (int)Math.copySign(abs_x + (16 - mod(abs_x, 16)), x);
            int lower_y = (int)Math.copySign(abs_y - mod(abs_y, 16), y);
            int upper_y = (int)Math.copySign(abs_y + (16 - mod(abs_y, 16)), y);

            int x_start = x >= 0 ? left_x : right_x;
            int y_start = y >= 0 ? lower_y : upper_y;
            int x_end = x < 0 ? left_x: right_x;
            int y_end = y < 0 ? lower_y: upper_y;

            Pair<Integer, Integer> A = new Pair<Integer, Integer>(x_start + 8, y_start);
            Pair<Integer, Integer> B = new Pair<Integer, Integer>(x_start, y_start + 8);
            Pair<Integer, Integer> C = new Pair<Integer, Integer>(x_end - 8, y_end);
            Pair<Integer, Integer> D = new Pair<Integer, Integer>(x_end, y_end - 8);

            Pair<Integer, Integer> M = new Pair<Integer, Integer>(x_start + 8, y_start + 8);

            Function<Pair<Integer,Integer>, Double> f = coords ->
                    (this.noise.getValue(this.getX(coords.getFirst()), 12, this.getY(coords.getSecond())) + 1) / 2;

//            ExampleMod.LOGGER.info("x: {} -> {}:{}", x, left_x, right_x);
//            ExampleMod.LOGGER.info("y: {} -> {}:{}", y, lower_y, upper_y);
//            if ((x == -16) && (y == -16)) {
                double a = f.apply(M) - f.apply(A);
                double b = f.apply(M) - f.apply(B);
                double c = f.apply(M) - f.apply(C);
                double d = f.apply(M) - f.apply(D);
//                ExampleMod.LOGGER.info("{}, {}, {}, {}", a, b, c, d);
                double[] tab = { a, b, c, d };
                OptionalDouble max = Arrays.stream(tab).max();
                if (max.getAsDouble() == a) {
                    return 0;
//                    return ((16 - x) % 16) / 16f;
                }
                if (max.getAsDouble() == b) {
//                    return ((16 - y - 1) % 16) / 16f;
                    return 1;
                }
                if (max.getAsDouble() == c) {
                    return 0;
//                    return ((16 - x - 1) % 16) / 16f;
                }
                if (max.getAsDouble() == d) {
                    return 1;
//                    return (y % 16) / 16f;
                }
//            }//            , 16 - (x % 16)
//            return ((this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1) / 2);
//            return (primitive_cos.resolve(x, y) + primitive_sin.resolve(x, y)) / 2; //* primitive_cos.getValue(x, y);
            return f.apply(M);
        }

        public double getNoise_zoom() { return 128; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
//                    d -> primitive_sin.
//                    d -> (double) Math.round(d * 3) / 3,
//                    d -> d < 0.3 || d > 0.5 ? 0.5 : 0
            };
        }
    };

    static NoiseStack primitive_test = new NoiseStack(12L) {
        public String getName() { return "primitive_test" + this.getNoise_zoom(); }

        public double getX(int x) { return Math.cos(x / this.getNoise_zoom()); }
        public double getY(int y) { return Math.cos(y / this.getNoise_zoom()); }

        public double getValue(int x, int y) {
            if (basic.getValue(x, y) == 0) {
                return primitive_cos.resolve(x, y);
            } else {
                return primitive_sin.resolve(x, y);
            }
//            return basic.getValue(x, y) * primitive_cos.resolve(x, y);
//            return (this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1)/ 2;
        }

        public double getNoise_zoom() { return 8; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
//                    d -> (double) Math.round(d * 3) / 3,
//                    d -> d < 0.3 || d > 0.5 ? 0.5 : 0
            };
        }
    };

    public static NoiseStack[] noise_stack = new NoiseStack[] {
        primitive_cos,
        primitive_sin,
        primitive_dot,
        primitive_dot_altered,
        test,
        basic,
        primitive_test
    };

    static {
        for (NoiseStack ns : noise_stack) {
            ns.makeIMG(1024, 1024);
        }
    }

    public static final MapCodec<LabyrinthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
            p_255585_ -> p_255585_.group(
                            BiomeSource.CODEC.fieldOf("aaa_biome_source").forGetter(p_255584_ -> p_255584_.biomeSource),
                            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(LabyrinthChunkGenerator::generatorSettings)
                    )
                    .apply(p_255585_, p_255585_.stable(LabyrinthChunkGenerator::new))
    );

    private final Holder<NoiseGeneratorSettings> settings;

    public LabyrinthChunkGenerator(BiomeSource p_256415_, Holder<NoiseGeneratorSettings> settings) {
        super(p_256415_, settings);
        this.settings = settings;
        ExampleMod.LOGGER.info("LabyrinthChunkGenerator constructor");
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(
            Executor p_224312_, Blender p_224313_, RandomState p_224314_, StructureManager p_224315_, ChunkAccess p_224316_
    ) {
        NoiseSettings noisesettings = this.settings.value().noiseSettings().clampToHeightAccessor(p_224316_.getHeightAccessorForGeneration());
        int i = noisesettings.minY();
        int j = Mth.floorDiv(i, noisesettings.getCellHeight());
        int k = Mth.floorDiv(noisesettings.height(), noisesettings.getCellHeight());
        if (k <= 0) {
            return CompletableFuture.completedFuture(p_224316_);
        } else {
            int l = p_224316_.getSectionIndex(k * noisesettings.getCellHeight() - 1 + i);
            int i1 = p_224316_.getSectionIndex(i);
            Set<LevelChunkSection> set = Sets.newHashSet();

            for (int j1 = l; j1 >= i1; j1--) {
                LevelChunkSection levelchunksection = p_224316_.getSection(j1);
                levelchunksection.acquire();
                set.add(levelchunksection);
            }

            return CompletableFuture.supplyAsync(
                            Util.wrapThreadWithTaskName("wgen_fill_noise", () -> this.doFill(p_224313_, p_224315_, p_224314_, p_224316_, j, k)), Util.backgroundExecutor()
                    )
                    .whenCompleteAsync((p_224309_, p_224310_) -> {
                        for (LevelChunkSection levelchunksection1 : set) {
                            levelchunksection1.release();
                        }
                    }, p_224312_);
        }
    }

    private ChunkAccess doFill(Blender blender, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess, int minSectionIndex, int sectionHeight) {
        ChunkPos chunkPosition = chunkAccess.getPos();

        double middleX = chunkPosition.getMiddleBlockX();
        double middleZ = chunkPosition.getMiddleBlockZ();

        BlockPos.MutableBlockPos chunkMiddle = new BlockPos.MutableBlockPos();

        Heightmap heightmap = chunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = chunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);

        chunkMiddle.set(middleX, 10, middleZ);

        NoiseSettings noisesettings = this.generatorSettings().value().noiseSettings();
        int minY = noisesettings.minY();
        int height = noisesettings.height();
        ArrayList<Vec3i> stack = new ArrayList<>();


        int i2 = chunkAccess.getSectionsCount() - 1;
        LevelChunkSection levelchunksection = chunkAccess.getSection(i2);

        for (int x = chunkPosition.getMinBlockX(); x <= chunkPosition.getMaxBlockX(); x++) {
            for (int z = chunkPosition.getMinBlockZ(); z <= chunkPosition.getMaxBlockZ(); z++) {
//                double value = resolve(x, z);
//                value = value_formula(value);
                double value = basic.resolve(x, z);
//                ExampleMod.LOGGER.info("Value: {}", value);

                Block small_wallpaper = (x % 3 == 0 && z % 3 == 0) ? ForgeRegistries.BLOCKS.getValue(ExampleBlocks.YELLOW_WALLPAPER.location()) : ForgeRegistries.BLOCKS.getValue(ExampleBlocks.PLAIN_WALLPAPER.location());

                for (int y = minY;  y < minY + 3; y++) {
                    levelchunksection.setBlockState(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock(), false);
//                    heightmap.update(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock());
//                    heightmap1.update(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock());
                }

//                if (passthrough(value)) {
//                    for (int y = minY + 3;  y < height; y++) {
//                        levelchunksection.setBlockState(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock(), false);
////                        heightmap.update(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock());
////                        heightmap1.update(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock());
//                    }
//                }

//                if ((int)value != 2) {
                    for (int y = minY + 3;  y < (height - 3) * value; y++) {
                        levelchunksection.setBlockState(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState(), false);
//                        heightmap.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
//                        heightmap1.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
                    }

                for (int y = (int) ((height - 3) * value) + minY + 3; y < height - 1; y++) {
                    levelchunksection.setBlockState(x & 15, y & 15, z & 15, Blocks.AIR.defaultBlockState(), false);
//                    heightmap.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
//                    heightmap1.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
                }
                for (int y = height - 1; y < height + 5; y++) {
                    levelchunksection.setBlockState(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock(), false);
//                    heightmap.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
//                    heightmap1.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
                }
//                }
//                    p = (255 << 24) | (255 << 16) | (0 << 8) | 0;
//                else {
//                    int up = (int)value_formula(resolve(x, z - 1));
//                    int down = (int)value_formula(resolve(x, z + 1));
//                    int left = (int)value_formula(resolve(x - 1, z));
//                    int right = (int)value_formula(resolve(x + 1, z));
//
//                    if ((up != 2 || down != 2) && (left != 2 || right != 2)) {
////                        stack.add(new Pair<Integer, Integer>(x, z));
//                        stack.add(new Vec3i(x, z - 1, 0));
//                        stack.add(new Vec3i(x, z + 1, 0));
//                        stack.add(new Vec3i(x - 1, z, 0));
//                        stack.add(new Vec3i(x + 1, z, 0));
//
//                    }
//                }
            }
        }

//        while (!stack.isEmpty()) {
////            ExampleMod.LOGGER.info("stack length {}", stack.size());
//            Vec3i coords = stack.removeFirst();
//
//            int first = coords.getX();
//            int second = coords.getY();
//            int third = coords.getZ();
//
//
////            BufferedImage finalImage = image;
//            IntBinaryOperator valueFromData = (int localX, int localZ) -> {
//                int rgb = 0;
//                try {
////                    int imgX = (localX + (width / pixel_size) / 2) * pixel_size;
////                    int imgZ = (localZ + (height / pixel_size) / 2) * pixel_size;
////                    ExampleMod.LOGGER.info("[{},{}] -> [{}, {}]", localX, localZ, imgX, imgZ);
////                    rgb = finalImage.getRGB(imgX, imgZ);
//                    return heightmap1.getFirstAvailable(localX & 15, localZ & 15);
//                } catch (ArrayIndexOutOfBoundsException e) {
////                    ExampleMod.LOGGER.info("catch");
//                }
//                return rgb;
//            };
//
//            int local = valueFromData.applyAsInt(first, second);
//
//            int up = valueFromData.applyAsInt(first, second - 1);
//            int down = valueFromData.applyAsInt(first, second + 1);
//            int left = valueFromData.applyAsInt(first - 1, second);
//            int right = valueFromData.applyAsInt(first + 1, second);
//
//            ExampleMod.LOGGER.info("local:{}", local);
//            ExampleMod.LOGGER.info("up:{} down:{} left:{} right:{}", up, down, left, right);
//
//            if (third < 12 && local == 16 && (up == 3 || down == 3) && (left == 3 || right == 3)) {
//                for (int y = minY + 3;  y < height; y++) {
//                    levelchunksection.setBlockState(first & 15, y & 15, second & 15, Blocks.AIR.defaultBlockState(), false);
//                    heightmap.update(first & 15, y & 15, second & 15, Blocks.AIR.defaultBlockState());
//                    heightmap1.update(first & 15, y & 15, second & 15, Blocks.AIR.defaultBlockState());
//
////                    levelchunksection.setBlockState(first & 15, y & 15, second & 15, Blocks.AIR.defaultBlockState(), false);
////                    heightmap.update(first & 15, y & 15, second & 15, Blocks.AIR.defaultBlockState());
////                    heightmap1.update(first & 15, y & 15, second & 15, Blocks.AIR.defaultBlockState());
//                }
////                int p = (255 << 24) | (255 << 16) | (0 << 8) | 0;
//
////                int[] pixs = new int[pixel_size * pixel_size];
////                for (int i = 0; i < pixel_size * pixel_size; i++) {
////                    pixs[i] = p;
////                }
////                try {
////                    image.setRGB((first + (width / pixel_size) / 2) * pixel_size, (second + (height / pixel_size) / 2) * pixel_size, pixel_size, pixel_size, pixs, 0, pixel_size);
////                } catch (ArrayIndexOutOfBoundsException e) {}
//
//                stack.add(new Vec3i(first, second - 1, third + 1));
//                stack.add(new Vec3i(first, second + 1, third + 1));
//                stack.add(new Vec3i(first + 1, second, third + 1));
//                stack.add(new Vec3i(first - 1, second, third + 1));
//            }
//        }

        return chunkAccess;
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        ExampleMod.LOGGER.info("server starting from LabyrinthChunkGenerator");
    }
}
