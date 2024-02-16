package com.example.examplemod.data.generators;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

public class Level0Carver<C extends CarverConfiguration> extends WorldCarver<C> {
    public Level0Carver(Codec<C> p_159366_) {
        super(p_159366_);
    }

    @Override
    public boolean carve(CarvingContext p_224913_, C p_224914_, ChunkAccess p_224915_, Function<BlockPos, Holder<Biome>> p_224916_, RandomSource p_224917_, Aquifer p_224918_, ChunkPos p_224919_, CarvingMask p_224920_) {
        return false;
    }


    @Override
    public boolean isStartChunk(CarverConfiguration p_224908_, RandomSource p_224909_) {
        return false;
    }

    private static boolean isDebugEnabled(CarverConfiguration p_159424_) {
        return p_159424_.debugSettings.isDebugMode();
    }
}
