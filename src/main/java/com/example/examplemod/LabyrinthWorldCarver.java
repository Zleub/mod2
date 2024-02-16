package com.example.examplemod;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.example.examplemod.ExampleMod.MODID;
import static com.example.examplemod.LabyrinthCarverConfiguration.*;

public class LabyrinthWorldCarver extends WorldCarver<LabyrinthCarverConfiguration> {
    public LabyrinthWorldCarver(Codec<LabyrinthCarverConfiguration> p_159366_) {
        super(p_159366_);
    }

    @Override
    public boolean carve(CarvingContext p_224913_, LabyrinthCarverConfiguration p_224914_, ChunkAccess p_224915_, Function<BlockPos, Holder<Biome>> p_224916_, RandomSource p_224917_, Aquifer p_224918_, ChunkPos p_224919_, CarvingMask p_224920_) {
        return false;
    }

    @Override
    public boolean isStartChunk(LabyrinthCarverConfiguration p_224908_, RandomSource p_224909_) {
        return false;
    }
}
