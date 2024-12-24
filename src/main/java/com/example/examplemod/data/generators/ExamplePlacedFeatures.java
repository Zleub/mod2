package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.FixedPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

import java.util.List;

public class ExamplePlacedFeatures {
    public static final ResourceKey<PlacedFeature> TEST_VEIN = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_vein"));
    public static final ResourceKey<PlacedFeature> TEST_SCULK = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_sculk"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

        FixedPlacement placements = FixedPlacement.of(new BlockPos(50, 30, 50), new BlockPos(55, 4, 55), new BlockPos(60, 6, 60));
        context.register(TEST_VEIN, new PlacedFeature(holdergetter.getOrThrow(ExampleConfiguredFeature.TEST_VEIN), List.of(placements)));
        context.register(TEST_SCULK, new PlacedFeature(holdergetter.getOrThrow(ExampleConfiguredFeature.TEST_SCULK), List.of(PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)));
    }
}
