package com.example.examplemod.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.example.examplemod.ExampleMod.MODID;

public class ExampleFeaturesList {
    public static DeferredHolder<Feature<?>, ExampleFeature> TEST_FEATURE;
//    public static Feature<NoneFeatureConfiguration> EXAMPLE_FEATURE = new ExampleFeature(NoneFeatureConfiguration.CODEC);

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, MODID);


    static {
        TEST_FEATURE = FEATURES.register("test_feature", () -> new ExampleFeature(NoneFeatureConfiguration.CODEC));
    }
}
