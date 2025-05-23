package com.example.examplemod.worldgen.chunk_generator;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.worldgen.NoiseStack;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
//import net.minecraftforge.event.server.ServerStartingEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.loading.FMLPaths;
//import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.example.examplemod.ExampleMod.LOGGER;
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
                    (d, x, z) -> (double) Math.round(d * 3) / 3,
                    (d, x, z) -> d < 0.3 || d > 0.5 ? 0.5 : 0
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
                    (d, x, z) -> (double) Math.round(d * 3) / 3,
                    (d, x, z) -> d < 0.3 || d > 0.5 ? 0.5 : 0
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
                    (d, x, z) -> (double) Math.round(d * 3) / 3,
                    (d, x, z) -> d < 0.3 || d > 0.5 ? 0.5 : 0
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
                    (d, x, z) -> (double) Math.round(d * 3) / 3,
                    (d, x, z) -> d < 0.3 || d > 0.5 ? 0.5 : 0
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
                    (d, x, z) -> (double) Math.round(d * 3) / 3,
                    (d, x, z) -> d < 0.3 || d > 0.5 ? 0.5 : 0
            };
        }
    };

    static {
        LOGGER.info("HERE {}", FMLPaths.MODSDIR.get());
    }

    static NoiseStack basic = new NoiseStack(12L) {
        public String getName() { return "basic" + this.getNoise_zoom(); }

        public double getX(int x) { return x / this.getNoise_zoom(); }
        public double getY(int y) { return y / this.getNoise_zoom(); }

       public double getChunkSlope(int x, int y) {
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

           Pair<Integer, Integer> A = new Pair<>(x_start + 8, y_start);
           Pair<Integer, Integer> B = new Pair<>(x_start, y_start + 8);
           Pair<Integer, Integer> C = new Pair<>(x_end - 8, y_end);
           Pair<Integer, Integer> D = new Pair<>(x_end, y_end - 8);

            /*
                    A
                *-------*
                |       |
             B  |       |  D
                |       |
                *-------*
                    C

            */



           // Pure chunk middle
           Pair<Integer, Integer> M = new Pair<Integer, Integer>(x_start + 8, y_start + 8);
           // Relative chunk middle
//            Pair<Integer, Integer> M = new Pair<>(x, y);

           Function<Pair<Integer,Integer>, Double> f = coords ->
                   (this.noise.getValue(this.getX(coords.getFirst()), 12, this.getY(coords.getSecond())) + 1) / 2;

           double a = f.apply(M) - f.apply(A);
           double b = f.apply(M) - f.apply(B);
           double c = f.apply(M) - f.apply(C);
           double d = f.apply(M) - f.apply(D);
//                ExampleMod.LOGGER.info("{}, {}, {}, {}", a, b, c, d);
           double[] tab = { a, b, c, d };
           OptionalDouble max = Arrays.stream(tab).max();

           if (max.getAsDouble() == a) {
               return 1;
//                    return ((16 - x) % 16) / 16f;
           }
           if (max.getAsDouble() == b) {
//                    return ((16 - y - 1) % 16) / 16f;
               return 0;
           }
           if (max.getAsDouble() == c) {
               return 1;
//                    return ((16 - x - 1) % 16) / 16f;
           }
           if (max.getAsDouble() == d) {
               return 0;
//                    return (y % 16) / 16f;
           }

           return f.apply(M);

       }
        public double getValue(int x, int y) {
            double slope = getChunkSlope(x, y);

            return slope;
        }

        public double getNoise_zoom() { return 64; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
//                    d -> primitive_sin.
//                    (d, x, y) -> (double) Math.round(d * 3) / 3,
//                    (d, x, y) -> d < 0.3 || d > 0.5 ? 0.5 : 0,
                    (slope, x, y) -> {
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

                        Pair<Integer, Integer> M = new Pair<Integer, Integer>(x_start + 8, y_start + 8);

                        double left = getChunkSlope(M.getFirst() - 16, M.getSecond());
                        double right = getChunkSlope(M.getFirst() + 16, M.getSecond());
                        double up = getChunkSlope(M.getFirst(), M.getSecond() - 16);
                        double down = getChunkSlope(M.getFirst(), M.getSecond() + 16);

                        double local_x = x - x_start;
                        double local_y = y - y_start;

//                ExampleMod.LOGGER.info("{} {}", local_x, local_y);
                        if (local_x < 0 || local_x > 16)
                            LOGGER.info("Warning XXXX");
                        if (local_y < 0 || local_y > 16)
                            LOGGER.info("Warning YYYY");

                        if (slope == 1) {
                            if (left == 0 && up == 0 && right == 1 && down == 1)
                                return 0;
                            else if (left == 0 && down == 0 && right == 1 && up == 1)
                                return 0;
                            if (right == 0 && up == 0 && left == 1 && down == 1)
                                return 0;
                            else if (right == 0 && down == 0 && left == 1 && up == 1)
                                return 0;
                        }

                        return slope; // * rooms.resolve(x_start, y_start);
                    },
//                    (d, x, y) -> {
//                        int abs_x = Math.abs(x);
//                        int abs_y = Math.abs(y);
//
//                        if (x < 0)
//                            abs_x -= 1;
//                        if (y < 0)
//                            abs_y -= 1;
//
//                        int left_x = (int)Math.copySign(abs_x - mod(abs_x, 16), x);
//                        int right_x = (int)Math.copySign(abs_x + (16 - mod(abs_x, 16)), x);
//                        int lower_y = (int)Math.copySign(abs_y - mod(abs_y, 16), y);
//                        int upper_y = (int)Math.copySign(abs_y + (16 - mod(abs_y, 16)), y);
//
//                        int x_start = x >= 0 ? left_x : right_x;
//                        int y_start = y >= 0 ? lower_y : upper_y;
//
//                        return d * rooms.resolve(x_start, y_start);
//                    }
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

    static NoiseStack rooms = new NoiseStack(12L) {
        public String getName() { return "rooms" + this.getNoise_zoom(); }

        public double getX(int x) { return (x / this.getNoise_zoom()); }
        public double getY(int y) { return (y / this.getNoise_zoom()); }

        public double getValue(int x, int y) {
            return (this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1)/ 2;
        }

        public double getNoise_zoom() { return 4; }

        @Override
        public NoiseStack.Functor[] getStack() {
            return new NoiseStack.Functor[]{
//                d -> d + 1,
//                d -> d / 2,
//                    d -> (double) Math.round(d * 3) / 3,
                    (d, x, y) -> d > 0.6 ? 1 : 0,
                    (d, x, y) -> {
                        int abs_x = Math.abs(x);
                        int abs_y = Math.abs(y);

                        if (x < 0)
                            abs_x -= 1;
                        if (y < 0)
                            abs_y -= 1;

                        int left_x = (int)Math.copySign(abs_x, x);
                        int lower_y = (int)Math.copySign(abs_y, y);

                        if (x % 16 != 0) {
                            return rooms.resolve(x - 1, y);
                        }
                        if (y % 16 != 0) {
                            return rooms.resolve(x, y - 1);
                        }
                        return d;
                    }
//                    (d, x, y) -> d * 100,
//                    (d, x, y) -> Math.round(d) / 10.
//                    (d, x, y) -> {
//                        ArrayList<Vec3i> stack = new ArrayList<>();
//
//                        if (d == 0.5) {
//                            stack.add( new Vec3i(x, 12, y) );
//                        }
////                        if (d == 0) {
////                            double up = rooms.resolve(x - 1, y);
////                            double down = rooms.resolve(x + 1, y);
////                            double left = rooms.resolve(x,y - 1);
////                            double right = rooms.resolve(x, y + 1);
////
////                            LOGGER.info("{} {} : {}", x, y, up + down + left + right);
////                        }
//
//                        return d;
//                    }
            };
        }
    };

    public static NoiseStack[] noise_stack = new NoiseStack[] {
//        primitive_cos,
//        primitive_sin,
//        primitive_dot,
//        primitive_dot_altered,
//        test,
//        primitive_test,
        basic,
        rooms
    };

    static {
        for (NoiseStack ns : noise_stack) {
            ns.makeIMG(256, 256);
//            ns.makeIMG(2048, 2048);

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
        LOGGER.info("LabyrinthChunkGenerator constructor");
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender p_224313_, RandomState p_224314_, StructureManager p_224315_, ChunkAccess p_224316_) {
        NoiseSettings noisesettings = ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().clampToHeightAccessor(p_224316_.getHeightAccessorForGeneration());
        int i = noisesettings.minY();
        int j = Mth.floorDiv(i, noisesettings.getCellHeight());
        int k = Mth.floorDiv(noisesettings.height(), noisesettings.getCellHeight());
        return k <= 0 ? CompletableFuture.completedFuture(p_224316_) : CompletableFuture.supplyAsync(() -> {
            int l = p_224316_.getSectionIndex(k * noisesettings.getCellHeight() - 1 + i);
            int i1 = p_224316_.getSectionIndex(i);
            Set<LevelChunkSection> set = Sets.newHashSet();

            for(int j1 = l; j1 >= i1; --j1) {
                LevelChunkSection levelchunksection = p_224316_.getSection(j1);
                levelchunksection.acquire();
                set.add(levelchunksection);
            }

            ChunkAccess chunkaccess;
            try {
                chunkaccess = this.doFill(p_224313_, p_224315_, p_224314_, p_224316_, j, k);
            } finally {
                for(LevelChunkSection levelchunksection1 : set) {
                    levelchunksection1.release();
                }

            }

            return chunkaccess;
        }, Util.backgroundExecutor().forName("wgen_fill_noise"));
    }

    private ChunkAccess doFill(Blender blender, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess, int minCellY, int cellCountY) {
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
        int l = 16; // ?? noisechunk.cellHeight();

        ArrayList<Vec3i> stack = new ArrayList<>();

//        ExampleMod.LOGGER.info("getSectionsCount: {}", chunkAccess.getSectionsCount());
//        ExampleMod.LOGGER.info("minCellY: {}", minCellY);
//        ExampleMod.LOGGER.info("cellCountY: {}", cellCountY);

//        int i2 = chunkAccess.getSectionsCount() - 1;
//        LevelChunkSection levelchunksection = chunkAccess.getSection(cellCountY);
//        ExampleMod.LOGGER.info("i2: {}", i2);
//        for (int j2 = cellCountY - 1; j2 >= 0; j2--) {
//            for (int k2 = l - 1; k2 >= 0; k2--) {
//                int l2 = (minCellY + j2) * l + k2;
//                int j3 = chunkAccess.getSectionIndex(l2);
//                if (i2 != j3) {
//                    i2 = j3;
//                    levelchunksection = chunkAccess.getSection(j3);
//                    ExampleMod.LOGGER.info("j3: {}", j3);
//                }
//            levelchunksection = chunkAccess.getSection(i);

                for (int x = chunkPosition.getMinBlockX(); x <= chunkPosition.getMaxBlockX(); x++) {
                    for (int z = chunkPosition.getMinBlockZ(); z <= chunkPosition.getMaxBlockZ(); z++) {
                        double value = basic.resolve(x, z);

                        Block small_wallpaper = Blocks.WHITE_STAINED_GLASS;

                        int abs_x = Math.abs(x);
                        int abs_z = Math.abs(z);

                        if (x < 0)
                            abs_x -= 1;
                        if (z < 0)
                            abs_z -= 1;

//                        LOGGER.info("x: {} {}", x, x / 16);

                        small_wallpaper = (abs_x % 4 == 2) || (abs_z % 4 == 2) ?
                                ExampleBlocks.YELLOW_WALLPAPER.get() :
                                ExampleBlocks.PLAIN_WALLPAPER.get();

//                        small_wallpaper = ((abs_x % 16) % 4 == 0 && (abs_z % 16) % 4 == 0) ?
//                                Blocks.RED_WOOL :
//                                Blocks.WHITE_WOOL ;


                        for (int i = cellCountY - 1; i >= 0 ; i--) {
                            LevelChunkSection levelchunksection = chunkAccess.getSection(i);
                            for (int y = 15; y >= 0 ; y--) {
                                int absolute_y = y + i * 16;
                                BlockState toSet = Blocks.AIR.defaultBlockState();

                                if (absolute_y >= minY && absolute_y < minY + 2) {
                                    toSet = Blocks.BEDROCK.defaultBlockState();
                                    heightmap.update(x & 15, absolute_y, z & 15, toSet);
                                    heightmap1.update(x & 15, absolute_y, z & 15, toSet);
                                }

                                if (absolute_y >= minY + 2 && absolute_y < minY + 3) {
                                    toSet = this.generatorSettings().value().defaultBlock();
                                    heightmap.update(x & 15, absolute_y, z & 15, toSet);
                                    heightmap1.update(x & 15, absolute_y, z & 15, toSet);
                                }

                                if (absolute_y >= minY + 3 && absolute_y <= minY + 3) {
//                                    if (value == 0) {
////                                        toSet =
//
//                                        chunkAccess.markPosForPostprocessing(new BlockPos(x, y, z));
//                                    }
//                                    else
                                        toSet = small_wallpaper.defaultBlockState();
                                    heightmap.update(x & 15, absolute_y, z & 15, toSet);
                                    heightmap1.update(x & 15, absolute_y, z & 15, toSet);
                                }

                                if (absolute_y > minY + 3 && absolute_y < 12 && value == 1) {
                                    toSet = small_wallpaper.defaultBlockState();
                                    heightmap.update(x & 15, absolute_y, z & 15, toSet);
                                    heightmap1.update(x & 15, absolute_y, z & 15, toSet);
                                }

                                if (absolute_y >= 12 && absolute_y <= 14) {
                                    toSet = this.generatorSettings().value().defaultBlock();
                                }

                                if (absolute_y >= 14 && absolute_y <= 15) {
                                    toSet = Blocks.BEDROCK.defaultBlockState();
                                }

                                levelchunksection.setBlockState(x & 15, y & 15, z & 15, toSet, false);
//                                heightmap.update(x & 15, y & 15, z & 15, toSet);
//                                heightmap1.update(x & 15, y & 15, z & 15, toSet);
                            }
                        }
//
//                        for (int y = minY; y < minY + 3; y++) {
//                            levelchunksection.setBlockState(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock(), false);
////                    heightmap.update(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock());
////                    heightmap1.update(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock());
//                        }
//
//                        for (int y = minY + 3; y < (height - 3) * value; y++) {
//                            levelchunksection.setBlockState(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState(), false);
////                        heightmap.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
////                        heightmap1.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
//                        }
//
//                        for (int y = (int) ((height - 3) * value) + minY + 3; y < height - 3; y++) {
//                            levelchunksection.setBlockState(x & 15, y & 15, z & 15, Blocks.AIR.defaultBlockState(), false);
////                    heightmap.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
////                    heightmap1.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
//                        }
//                        for (int y = height - 3; y < height; y++) {
//                            levelchunksection.setBlockState(x & 15, y & 15, z & 15, this.generatorSettings().value().defaultBlock(), false);
////                    heightmap.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
////                    heightmap1.update(x & 15, y & 15, z & 15, small_wallpaper.defaultBlockState());
//                        }

                    }
//                }
//
//            }
        }


        return chunkAccess;
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("server starting from LabyrinthChunkGenerator");
    }
}
