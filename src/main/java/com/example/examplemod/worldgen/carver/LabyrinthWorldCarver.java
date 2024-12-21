package com.example.examplemod.worldgen.carver;

import com.example.examplemod.ExampleMod;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.apache.commons.lang3.mutable.MutableBoolean;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class LabyrinthWorldCarver extends WorldCarver<LabyrinthCarverConfiguration> {

    enum e {
        SHOULD_CARVE_DOORWAY
    }

//    ArrayList<Function<Pair<Double, Double>, e>> = new ArrayList();

    public static double noise_zoom = 4;
//    public static Function<Double, Double> value_formula = (Double value) -> {
//        return Math.round(Math.abs(value) * 3.2d) * 127.5;
//    };
    public static double value_formula(double value) {
        return Math.round(Math.abs(value) * 3.2d) * 127.5;
    }


//    public static Function<Double, Boolean> passthrough = (Double value) -> {
//        return value.intValue() != 0 && value.intValue() != 255;
//    };

    public static boolean passthrough(double value) {
        return (int)value != 0 && (int)value != 255;
    }

    public static double resolve(int x, int z) {
        return test.getValue(Math.cos(x / noise_zoom), 12, Math.cos(z / noise_zoom));
    }




//    public static FileWriter fileout;
//
//    static {
//        try {
//            fileout = new FileWriter("C:\\Users\\debra\\OneDrive\\forge-1.20.2-48.1.0-mdk\\run\\logs\\carver.txt");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
    public static NormalNoise test =  NormalNoise.create(new SingleThreadedRandomSource(12L), 1, 1);
//
//
//    static {
//        int width = 800;
//        int height = 800;
//
//
//        // For storing image in RAM
//        BufferedImage image = null;
//
//        File file = new File("C:\\Users\\debra\\OneDrive\\forge-1.20.2-48.1.0-mdk\\run\\logs\\img.png");
//        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//        for (int z = -(height / 8) / 2; z < (height / 8) / 2; z += 1) {
//            for (int x = -(width / 8) / 2; x < (width / 8) / 2; x += 1) {
////                double value = test.getValue(Math.cos(x / noise_zoom), 12, Math.cos(z / noise_zoom));
//                double value = resolve(x, z);
//                value = LabyrinthWorldCarver.value_formula(value);
//
////                int i = 0;
////                int j = 0;
////                boolean[][] neighbourgh = {
////                        { false, false, false },
////                        { false, false, false },
////                        { false, false, false },
////                };
////
////                for (int i = 0; i <= 2 ; i++) {
////                    for (int j = 0; j <= 2 ; j++) {
////                        double v = resolve(x + i - 1, z + j - 1);
////                        v = value_formula(v);
////
////                        neighbourgh[i][j] = passthrough(v);
////                    }
////                }
//
//                int _x = (x + (height / 8) / 2) * 8;
//                int _z = (z + (width / 8) / 2) * 8;
//
//                int p = 0;
//                if (passthrough(value)) {
//                    p = (255 << 24) | ( 127 << 16) | (0 << 8) | 0;
//                }
//                else {
//                    if (_x - 1 >= 0 && _z - 1 >= 0 && _x + 1 < width && _z + 1 < height) {
//                        int[][] neighbourgh = {
//                                {image.getRGB(_x - 1, _z - 1), image.getRGB(_x, _z - 1), image.getRGB(_x + 1, _z - 1)},
//                                {image.getRGB(_x - 1, _z), image.getRGB(_x, _z), image.getRGB(_x + 1, _z)},
//                                {image.getRGB(_x - 1, _z + 1), image.getRGB(_x, _z + 1), image.getRGB(_x + 1, _z + 1)},
//                        };
//
//
//                        if (neighbourgh[0][1] == 0 && neighbourgh[2][1] == 0 && p == 0) {
//                            p = (255 << 24) | (0 << 16) | ( 127 << 8) | 0;
//                        }
//                    }
//                }
//
//                int[] pixels = new int[8 * 8];
//                for (int i = 0; i < 8 * 8; i++) {
//                    pixels[i] = p;
//                }
//
//                image.setRGB(_x, _z, 8, 8, pixels, 0, 8);
//            }
//        }
//
//        try {
//            ImageIO.write(image, "png", file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
////        image.
//    }




    public LabyrinthWorldCarver(Codec<LabyrinthCarverConfiguration> configurationCodec) {
        super(configurationCodec);
        ExampleMod.LOGGER.info("LabyrinthWorldCarver Constructor");
    }

    private static int blockCoordToSurfaceCell(int p_198281_) {
        return p_198281_ >> 4;
    }

    private static int surfaceCellToBlockCoord(int p_198283_) {
        return p_198283_ << 4;
    }


    @Override
    public boolean carve(
            CarvingContext carvingContext,
            LabyrinthCarverConfiguration carverConfiguration,
            ChunkAccess chunkAccess,
            Function<BlockPos, Holder<Biome>> biomeProvider,
            RandomSource randomSource,
            Aquifer aquifer,
            ChunkPos chunkPos,
            CarvingMask carvingMask
    ) {
//        ExampleMod.LOGGER.info("carve " + chunkPos);

        ChunkPos chunkPosition = chunkAccess.getPos();

        double middleX = chunkPosition.getMiddleBlockX();
        double middleZ = chunkPosition.getMiddleBlockZ();

        BlockPos.MutableBlockPos chunkMiddle = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutablePos1 = new BlockPos.MutableBlockPos();
        MutableBoolean shouldCarve = new MutableBoolean(false);

        int randomY = Math.abs(carvingContext.getMinGenY() + randomSource.nextInt() % carvingContext.getGenDepth());
//        ExampleMod.LOGGER.info("randomY: " + randomY);
        chunkMiddle.set(middleX, 10, middleZ);

//        ExampleMod.LOGGER.info("chunkMiddle: " + chunkMiddle);

        NoiseChunk noise = chunkAccess.getOrCreateNoiseChunk((chunkAccess1) -> null);

        JsonObject json = new JsonObject();

//        try {
//            json.add("name", new JsonPrimitive(Thread.currentThread().getName()));
//            json.add("chunkPos", new JsonPrimitive(chunkPos.toString()));
//            json.add("chunkPosition", new JsonPrimitive(chunkPosition.toString()));
//            json.add("noise", new JsonPrimitive(test.getValue(chunkPosition.x / 20d, 12, chunkPosition.z / 20d)));
//            json.add("test_noise", new JsonPrimitive(test.getValue(Math.cos(chunkPosition.x / 20d), 12, Math.cos(chunkPosition.z / 20d))));
//            json.add("test_noise2", new JsonPrimitive(test.getValue(Math.cos(chunkPosition.x / 10d), 12, Math.cos(chunkPosition.z / 10d))));
//
//            fileout.write(json.toString() + System.lineSeparator());
//            fileout.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        fileout.write("noise: ", test.getValue());

        //        eMod.LOGGER.info("[" + carvingContext.hashCode() + "] Should be carved: " + chunkMiddle);
//        ExampleMod.LOGGER.info("chunkPos" + chunkPos);
//        ExampleMod.LOGGER.info("chunkPosition" + chunkPosition);

//        double value = test.getValue(Math.cos(chunkPosition.x / noise_zoom), 12, Math.cos(chunkPosition.z / noise_zoom));
//        double value = resolve(chunkPosition.x, chunkPosition.z);
//        value = LabyrinthWorldCarver.value_formula(value);

//        ExampleMod.LOGGER.info("{} : {}", chunkPosition, value);


//        if (passthrough(value)) {
            for (int x = chunkPosition.getMinBlockX(); x <= chunkPosition.getMaxBlockX(); x++) {
                for (int z = chunkPosition.getMinBlockZ(); z <= chunkPosition.getMaxBlockZ(); z++) {
                    double value = resolve(x / 2, z / 2);
                    value = value_formula(value);

                    if (passthrough(value)) {

                        for (int y = carvingContext.getMinGenY() + 3;  y < carvingContext.getGenDepth() + 3; y++) {
//                    int y = noise.preliminarySurfaceLevel(x, z);

//                    ExampleMod.LOGGER.info("{}, {}, {}", x, y, z);

                            mutablePos.set(x, y, z);
                            this.carveBlock(carvingContext, carverConfiguration, chunkAccess, biomeProvider, carvingMask, mutablePos, mutablePos1, aquifer, shouldCarve);
                        }
                    }
                    else {

                        Function<BlockPos, Integer> f = (BlockPos blockPos) -> {
                            BlockState bs = chunkAccess.getBlockState(blockPos);
//                            ExampleMod.LOGGER.info("{}: {}", blockPos, bs.getBlock().getName());
                            if (bs.is(Blocks.AIR)) {
                                return 0;
                            }
                            return 1;
                        };

                        int[][] neighbourgh = {
                                {0, 0, 0},
                                {0, 0, 0},
                                {0, 0, 0},
                        };

                        for (int i = 0; i < 2; i++) {
                            for (int j = 0; j < 2; j++) {
                                int offsetX = x / 8 + (i - 1) * 2;
                                int offsetZ = z / 8 + (j - 1) * 2;
                                BlockPos toCheck = new BlockPos(offsetX, carvingContext.getMinGenY() + carvingContext.getGenDepth() / 2, offsetZ);
                                neighbourgh[j][i] = f.apply(toCheck);
                            }
                        }


                        if (neighbourgh[0][1] == 0 && neighbourgh[2][1] == 0) {
//                            p = (255 << 24) | (0 << 16) | ( 127 << 8) | 0;
                            for (int y = carvingContext.getMinGenY() + 3; y < carvingContext.getGenDepth() + 3; y++) {
//                    int y = noise.preliminarySurfaceLevel(x, z);

//                    ExampleMod.LOGGER.info("{}, {}, {}", x, y, z);

                                mutablePos.set(x, y, z);
                                this.carveBlock(carvingContext, carverConfiguration, chunkAccess, biomeProvider, carvingMask, mutablePos, mutablePos1, aquifer, shouldCarve);
                            }
                        }
                    }
                }
            }
//        }
//        this.carveBlock(carvingContext, carverConfiguration, chunkAccess, biomeProvider, carvingMask, chunkMiddle, mutablePos1, aquifer, shouldCarve);
        return true;
    }

    @Override
    protected boolean carveBlock(
            CarvingContext carvingContext,
            LabyrinthCarverConfiguration carverConfiguration,
            ChunkAccess chunkAccess,
            Function<BlockPos, Holder<Biome>> biomeProvider,
            CarvingMask carvingMask,
            BlockPos.MutableBlockPos chunkMiddle,
            BlockPos.MutableBlockPos mutablePos1,
            Aquifer aquifer,
            MutableBoolean shouldCarve
    ) {
        BlockState blockState = chunkAccess.getBlockState(chunkMiddle);

//        ExampleMod.LOGGER.info("carveBlock, " + chunkMiddle + " -> " + blockState.getBlock().getName());

        if (blockState.is(Blocks.GRASS_BLOCK) || blockState.is(Blocks.MYCELIUM)) {
            shouldCarve.setTrue();
        }

//        if (!this.canReplaceBlock(carverConfiguration, blockState) && !isDebugEnabled(carverConfiguration)) {
//            return false;
//        } else {
            chunkAccess.setBlockState(chunkMiddle, Blocks.AIR.defaultBlockState(), false);
            return true;


            //            BlockState carvedState = chunkAccess
//             BlockState carvedState = this.getCarveState(carvingContext, carverConfiguration, chunkMiddle, aquifer);
////            ExampleMod.LOGGER.info("carvedState: ", carvedState.getBlock().getName());
//
//            if (carvedState == null) {
//                return false;
//            } else {
//                chunkAccess.setBlockState(chunkMiddle, Blocks.AIR.defaultBlockState(), false);
//                if (aquifer.shouldScheduleFluidUpdate() && !carvedState.getFluidState().isEmpty()) {
//                    chunkAccess.markPosForPostprocessing(chunkMiddle);
//                }
//
//                if (shouldCarve.isTrue()) {
//                    mutablePos1.setWithOffset(chunkMiddle, Direction.DOWN);
//                    if (chunkAccess.getBlockState(mutablePos1).is(Blocks.DIRT)) {
//                        carvingContext.topMaterial(biomeProvider, chunkAccess, mutablePos1, !carvedState.getFluidState().isEmpty()).ifPresent((topMaterialState) -> {
//                            chunkAccess.setBlockState(mutablePos1, topMaterialState, false);
//                            if (!topMaterialState.getFluidState().isEmpty()) {
//                                chunkAccess.markPosForPostprocessing(mutablePos1);
//                            }
//                        });
//                    }
//                }
//
//                return true;
//            }
//        }
    }

    @Nullable
    private BlockState getCarveState(CarvingContext carvingContext, LabyrinthCarverConfiguration carverConfiguration, BlockPos pos, Aquifer aquifer) {
//        ExampleMod.LOGGER.info("getCarveState: " + pos.getY() + " <= " + carverConfiguration.lavaLevel.resolveY(carvingContext));
//        if (pos.getY() <= carverConfiguration.lavaLevel.resolveY(carvingContext)) {
//            return LAVA.createLegacyBlock();
//        } else {
            BlockState blockState = aquifer.computeSubstance(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()), 0.0D);
            if (blockState == null) {
                return isDebugEnabled(carverConfiguration) ? carverConfiguration.debugSettings.getBarrierState() : null;
            } else {
                return isDebugEnabled(carverConfiguration) ? getDebugState(carverConfiguration, blockState) : blockState;
            }
//        }
    }

    private static BlockState getDebugState(CarverConfiguration carverConfiguration, BlockState blockState) {
        if (blockState.is(Blocks.AIR)) {
            return carverConfiguration.debugSettings.getAirState();
        } else if (blockState.is(Blocks.WATER)) {
            BlockState waterState = carverConfiguration.debugSettings.getWaterState();
            return waterState.hasProperty(BlockStateProperties.WATERLOGGED) ? waterState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)) : waterState;
        } else {
            return blockState.is(Blocks.LAVA) ? carverConfiguration.debugSettings.getLavaState() : blockState;
        }
    }

    private static boolean isDebugEnabled(CarverConfiguration carverConfiguration) {
        return carverConfiguration.debugSettings.isDebugMode();
    }

    @Override
    public boolean isStartChunk(LabyrinthCarverConfiguration carverConfiguration, RandomSource randomSource) {
        return true;
    }
}


