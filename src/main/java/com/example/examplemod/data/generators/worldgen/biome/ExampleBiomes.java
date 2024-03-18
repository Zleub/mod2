package com.example.examplemod.data.generators.worldgen.biome;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ExampleBiomes {
    public static final ResourceKey<Biome> LEVEL_0 = createKey("level_0");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(ExampleMod.MODID, name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
//        Holder.Reference<ConfiguredWorldCarver<?>> testCarvers = context.lookup(Registries.CONFIGURED_CARVER).getOrThrow(ExampleConfiguredCarvers.TEST);

//        ExampleMod.LOGGER.warn(vanillaConfiguredCarvers.);
        context.register(LEVEL_0, ExampleBiomeBuilders.level0(placedFeatures, vanillaConfiguredCarvers));
//        context.register(SKYROOT_GROVE, AetherBiomeBuilders.skyrootGroveBiome(placedFeatures, vanillaConfiguredCarvers));
//        context.register(SKYROOT_WOODLAND, AetherBiomeBuilders.skyrootWoodlandBiome(placedFeatures, vanillaConfiguredCarvers));
//        context.register(SKYROOT_FOREST, AetherBiomeBuilders.skyrootForestBiome(placedFeatures, vanillaConfiguredCarvers));
    }

}
