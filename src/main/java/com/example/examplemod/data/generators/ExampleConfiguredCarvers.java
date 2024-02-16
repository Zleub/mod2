package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.LabyrinthCarverConfiguration;
import com.example.examplemod.LabyrinthWorldCarver;
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
                0.15F,
                UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)),
                UniformFloat.of(0.1F, 0.9F),
                VerticalAnchor.aboveBottom(8),
                CarverDebugSettings.of(true, Blocks.CRIMSON_BUTTON.defaultBlockState()),
                blockHolder.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
        );


        Holder.Reference<WorldCarver<?>> labyrinth = carverHolder.getOrThrow(ExampleCarvers.LABYRINTH);
        LabyrinthWorldCarver carver = (LabyrinthWorldCarver) labyrinth.get();
        context.register(ExampleConfiguredCarvers.LABYRINTH, carver.configured(config));
    }
}
