package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.LabyrinthCarverConfiguration;
import com.example.examplemod.LabyrinthWorldCarver;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class ExampleCarvers {
    public static final ResourceKey<WorldCarver<?>> LABYRINTH = createKey("labyrinth");

    private static ResourceKey<WorldCarver<?>> createKey(String name) {
        return ResourceKey.create(Registries.CARVER, new ResourceLocation(ExampleMod.MODID, name));
    }
}
