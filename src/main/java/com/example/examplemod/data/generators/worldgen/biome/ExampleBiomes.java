package com.example.examplemod.data.generators.worldgen.biome;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.data.generators.worldgen.configured_carver.ExampleConfiguredCarvers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Optional;

public class ExampleBiomes {
    public static final ResourceKey<Biome> LEVEL_0 = createKey("level_0");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

//        ResourceKey<Registry<Registry<ConfiguredWorldCarver<?>>>> registryResourceKey = Registries.CONFIGURED_CARVER.registryKey();
//        ExampleMod.LOGGER.info("bootstrap biomes");
//        Optional<HolderLookup.RegistryLookup<Registry<ConfiguredWorldCarver<?>>>> registryRegistryLookup = context.registryLookup(registryResourceKey);

        Holder.Reference<ConfiguredWorldCarver<?>> testCarvers = context.lookup(Registries.CONFIGURED_CARVER).getOrThrow(ExampleConfiguredCarvers.LABYRINTH);

//        ExampleMod.LOGGER.warn(vanillaConfiguredCarvers.keyset());

        context.register(LEVEL_0, ExampleBiomeBuilders.level0(placedFeatures, vanillaConfiguredCarvers));
//        context.register(SKYROOT_GROVE, AetherBiomeBuilders.skyrootGroveBiome(placedFeatures, vanillaConfiguredCarvers));
//        context.register(SKYROOT_WOODLAND, AetherBiomeBuilders.skyrootWoodlandBiome(placedFeatures, vanillaConfiguredCarvers));
//        context.register(SKYROOT_FOREST, AetherBiomeBuilders.skyrootForestBiome(placedFeatures, vanillaConfiguredCarvers));
    }

}
