package com.example.examplemod.data.generators.worldgen;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.worldgen.feature.ExampleFeaturesList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ExampleConfiguredFeature {
    public static final ResourceKey<ConfiguredFeature<?, ?>> TEST_VEIN = createKey(ExampleMod.MODID, "test_vein");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TEST_SCULK = createKey(ExampleMod.MODID, "test_sculk");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SHELF_FEATURE_CONFIGURED = createKey(ExampleMod.MODID, "shelf_feature");

    static ResourceKey<ConfiguredFeature<?, ?>> createKey(String namespace, String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

        ResourceKey<Structure> key = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_room"));

        context.register(SHELF_FEATURE_CONFIGURED, new ConfiguredFeature<>(ExampleFeaturesList.SHELF_FEATURE.value(), FeatureConfiguration.NONE));


//        context.register(TEST_VEIN, new ConfiguredFeature<>(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 4), BlockStateProvider.simple(Blocks.RED_WOOL))));
//        context.register(TEST_SCULK, new ConfiguredFeature<>(ExampleFeaturesList.TEST_FEATURE.value(), FeatureConfiguration.NONE));
//        context.register(TEST_SCULK, new ConfiguredFeature<>(Feature.FOSSIL, new FossilFeatureConfiguration(
//                List.of(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_room")),
//                null,
//                null,
//                null,
//                0
//        )));
    }
}
