package com.example.examplemod.worldgen.chunk_generator;

import com.example.examplemod.ExampleMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ExampleChunkGenerator {
    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATOR = DeferredRegister.create(Registries.CHUNK_GENERATOR, ExampleMod.MODID);

    public static final DeferredHolder<MapCodec<? extends ChunkGenerator>, MapCodec<? extends ChunkGenerator>> LABYRINTH_GEN = CHUNK_GENERATOR.register("labyrinth", () -> {
        ExampleMod.LOGGER.info("Chunk Generator registration");
        return LabyrinthChunkGenerator.CODEC;
    });
}
