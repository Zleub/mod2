package com.example.examplemod.worldgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.example.examplemod.ExampleMod.MODID;

public class ExampleFeaturesList {
    public static DeferredHolder<Feature<?>, ShelfFeature> SHELF_FEATURE;
//    public static Feature<NoneFeatureConfiguration> EXAMPLE_FEATURE = new ExampleFeature(NoneFeatureConfiguration.CODEC);

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, MODID);


    static {
        SHELF_FEATURE = FEATURES.register("shelf_feature", () -> new ShelfFeature(NoneFeatureConfiguration.CODEC));
    }
}
