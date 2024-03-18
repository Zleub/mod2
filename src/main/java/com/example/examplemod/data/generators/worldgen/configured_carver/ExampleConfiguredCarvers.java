package com.example.examplemod.data.generators.worldgen.configured_carver;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.worldgen.carver.LabyrinthCarverConfiguration;
import com.example.examplemod.worldgen.carver.LabyrinthWorldCarver;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class ExampleConfiguredCarvers {
    public static final ResourceKey<ConfiguredWorldCarver<?>> LABYRINTH = createKey("labyrinth");

    private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(ExampleMod.MODID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
        HolderGetter<Block> blockHolder = context.lookup(Registries.BLOCK);
        HolderGetter<WorldCarver<?>> carverHolder = context.lookup(Registries.CARVER);

        LabyrinthCarverConfiguration config = new LabyrinthCarverConfiguration(
                0.15F, // probability
                UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)), // HeightProvider
                UniformFloat.of(0.1F, 0.9F), // yScale
                VerticalAnchor.aboveBottom(0), // lavaLevel
                CarverDebugSettings.of(true, Blocks.CRIMSON_BUTTON.defaultBlockState()), // debugSettings
                blockHolder.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES) // replaceable
        );


        ResourceKey<WorldCarver<?>> labyrinthCarver = ResourceKey.create(Registries.CARVER, new ResourceLocation(ExampleMod.MODID, "labyrinth"));
        Holder.Reference<WorldCarver<?>> labyrinth = carverHolder.getOrThrow(labyrinthCarver);

        LabyrinthWorldCarver carver = (LabyrinthWorldCarver) labyrinth.get();
        context.register(ExampleConfiguredCarvers.LABYRINTH, carver.configured(config));
    }
}
