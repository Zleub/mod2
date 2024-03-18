package com.example.examplemod.worldgen.carver;

import com.example.examplemod.ExampleMod;
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
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import javax.annotation.Nullable;
import java.util.function.Function;

public class LabyrinthWorldCarver extends WorldCarver<LabyrinthCarverConfiguration> {
    public LabyrinthWorldCarver(Codec<LabyrinthCarverConfiguration> configurationCodec) {
        super(configurationCodec);
        ExampleMod.LOGGER.info("LabyrinthWorldCarver Constructor" + configurationCodec.fieldOf("config").fieldOf("debug_mode").toString());
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
        ChunkPos chunkPosition = chunkAccess.getPos();
        double middleX = (double) chunkPosition.getMiddleBlockX();
        double middleZ = (double) chunkPosition.getMiddleBlockZ();

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutablePos1 = new BlockPos.MutableBlockPos();
        MutableBoolean shouldCarve = new MutableBoolean(false);

        int randomY = carvingContext.getMinGenY() + randomSource.nextInt() % carvingContext.getGenDepth();
        ExampleMod.LOGGER.info("randomY: " + randomY);
        mutablePos.set(middleX, randomY, middleZ);

        NoiseChunk noise = chunkAccess.getOrCreateNoiseChunk((chunkAccess1) -> null);
//        noise.

//        ExampleMod.LOGGER.info("[" + carverConfiguration.debugSettings.isDebugMode() + "] Should be carved: " + mutablePos);
        this.carveBlock(carvingContext, carverConfiguration, chunkAccess, biomeProvider, carvingMask, mutablePos, mutablePos1, aquifer, shouldCarve);
        return true;
    }

    @Override
    protected boolean carveBlock(
            CarvingContext carvingContext,
            LabyrinthCarverConfiguration carverConfiguration,
            ChunkAccess chunkAccess,
            Function<BlockPos, Holder<Biome>> biomeProvider,
            CarvingMask carvingMask,
            BlockPos.MutableBlockPos mutablePos, BlockPos.MutableBlockPos mutablePos1, Aquifer aquifer, MutableBoolean shouldCarve) {
        BlockState blockState = chunkAccess.getBlockState(mutablePos);
        if (blockState.is(Blocks.GRASS_BLOCK) || blockState.is(Blocks.MYCELIUM)) {
            shouldCarve.setTrue();
        }

        if (!this.canReplaceBlock(carverConfiguration, blockState) && !isDebugEnabled(carverConfiguration)) {
            return false;
        } else {
            BlockState carvedState = this.getCarveState(carvingContext, carverConfiguration, mutablePos, aquifer);
            if (carvedState == null) {
                return false;
            } else {
                chunkAccess.setBlockState(mutablePos, carvedState, false);
                if (aquifer.shouldScheduleFluidUpdate() && !carvedState.getFluidState().isEmpty()) {
                    chunkAccess.markPosForPostprocessing(mutablePos);
                }

                if (shouldCarve.isTrue()) {
                    mutablePos1.setWithOffset(mutablePos, Direction.DOWN);
                    if (chunkAccess.getBlockState(mutablePos1).is(Blocks.DIRT)) {
                        carvingContext.topMaterial(biomeProvider, chunkAccess, mutablePos1, !carvedState.getFluidState().isEmpty()).ifPresent((topMaterialState) -> {
                            chunkAccess.setBlockState(mutablePos1, topMaterialState, false);
                            if (!topMaterialState.getFluidState().isEmpty()) {
                                chunkAccess.markPosForPostprocessing(mutablePos1);
                            }
                        });
                    }
                }

                return true;
            }
        }
    }

    @Nullable
    private BlockState getCarveState(CarvingContext carvingContext, LabyrinthCarverConfiguration carverConfiguration, BlockPos pos, Aquifer aquifer) {
//        ExampleMod.LOGGER.info("getCarveState: " + pos.getY() + " <= " + carverConfiguration.lavaLevel.resolveY(carvingContext));
        if (pos.getY() <= carverConfiguration.lavaLevel.resolveY(carvingContext)) {
            return LAVA.createLegacyBlock();
        } else {
            BlockState blockState = aquifer.computeSubstance(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()), 0.0D);
            if (blockState == null) {
                return isDebugEnabled(carverConfiguration) ? carverConfiguration.debugSettings.getBarrierState() : null;
            } else {
                return isDebugEnabled(carverConfiguration) ? getDebugState(carverConfiguration, blockState) : blockState;
            }
        }
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


