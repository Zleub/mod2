package com.example.examplemod.data.generators.worldgen;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ExamplePlacedFeatures {
    public static final ResourceKey<PlacedFeature> TEST_VEIN = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_vein"));
    public static final ResourceKey<PlacedFeature> TEST_SCULK = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_sculk"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
//        HolderGetter<PlacedFeature> holdergetter2 = context.lookup(Registries.);

        FixedPlacement placements = FixedPlacement.of(new BlockPos(50, 30, 50), new BlockPos(55, 4, 55), new BlockPos(60, 6, 60));
//        context.register(TEST_VEIN, new PlacedFeature(holdergetter.getOrThrow(ExampleConfiguredFeature.TEST_VEIN), List.of(placements)));
        context.register(TEST_SCULK, new PlacedFeature(holdergetter.getOrThrow(ExampleConfiguredFeature.TEST_SCULK), List.of(
            RarityFilter.onAverageOnceEvery(4),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()
        )));
//        PlacementUtils.register(context, TEST_SCULK, holdergetter.getOrThrow(ExampleConfiguredFeature.TEST_SCULK), RarityFilter.onAverageOnceEvery(4),
////                CountPlacement.of(4),
////                InSquarePlacement.spread(),
//                PlacementUtils.HEIGHTMAP_WORLD_SURFACE
//       );
    }
}
