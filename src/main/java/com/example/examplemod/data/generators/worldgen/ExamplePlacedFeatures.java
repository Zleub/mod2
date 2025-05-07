package com.example.examplemod.data.generators.worldgen;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.tags.ExampleBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ExamplePlacedFeatures {
    public static final ResourceKey<PlacedFeature> TEST_VEIN = createKey(ExampleMod.MODID, "test_vein");
    public static final ResourceKey<PlacedFeature> TEST_SCULK = createKey(ExampleMod.MODID, "test_sculk");
    public static final ResourceKey<PlacedFeature> SHELF_FEATURE_PLACED = createKey(ExampleMod.MODID, "test_sculk");

    public static final ResourceKey<PlacedFeature> createKey(String namespace, String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
//        HolderGetter<PlacedFeature> holdergetter2 = context.lookup(Registries.);

        FixedPlacement placements = FixedPlacement.of(new BlockPos(50, 30, 50), new BlockPos(55, 4, 55), new BlockPos(60, 6, 60));
//        context.register(TEST_VEIN, new PlacedFeature(holdergetter.getOrThrow(ExampleConfiguredFeature.TEST_VEIN), List.of(placements)));
        context.register(SHELF_FEATURE_PLACED, new PlacedFeature(holdergetter.getOrThrow(ExampleConfiguredFeature.SHELF_FEATURE_CONFIGURED), List.of(
            RarityFilter.onAverageOnceEvery(4),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.AIR))
//            EnvironmentScanPlacement.scanningFor(Direction.EAST, BlockPredicate.matchesTag(ExampleBlockTags.YELLOW_WALLPAPER), 1)
        )));
//        PlacementUtils.register(context, TEST_SCULK, holdergetter.getOrThrow(ExampleConfiguredFeature.TEST_SCULK), RarityFilter.onAverageOnceEvery(4),
////                CountPlacement.of(4),
////                InSquarePlacement.spread(),
//                PlacementUtils.HEIGHTMAP_WORLD_SURFACE
//       );
    }
}

