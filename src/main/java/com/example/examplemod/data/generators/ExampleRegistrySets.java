package com.example.examplemod.data.generators;

import com.example.examplemod.data.generators.worldgen.biome.ExampleBiomes;
import com.example.examplemod.data.generators.dimension.ExampleDimensions;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.data.generators.worldgen.configured_carver.ExampleConfiguredCarvers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
//import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
public class ExampleRegistrySets extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .buildPatch()
//            .buildPatch(Registries.BLOCK, ExampleMod::bootstrapBlocks)

//            .add(Registries.CONFIGURED_FEATURE, AetherConfiguredFeatures::bootstrap)
//            .add(Registries.PLACED_FEATURE, AetherPlacedFeatures::bootstrap)

//            .add(Registries.DENSITY_FUNCTION, ExampleDensityFunction::bootstrap)
//            .add(Registries.NOISE, ExampleNoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, ExampleNoiseSettings::bootstrap)
            .add(Registries.CONFIGURED_CARVER, ExampleConfiguredCarvers::bootstrap)
            .add(Registries.DIMENSION_TYPE, ExampleDimensions::bootstrapDimensionType)
            .add(Registries.LEVEL_STEM, ExampleDimensions::bootstrapLevelStem)
            .add(Registries.BIOME, ExampleBiomes::bootstrap);
//            .add(Registries.STRUCTURE, AetherStructures::bootstrap)
//            .add(Registries.STRUCTURE_SET, AetherStructureSets::bootstrap)
//            .add(Registries.DAMAGE_TYPE, AetherDamageTypes::bootstrap)
//            .add(Registries.TRIM_MATERIAL, AetherTrimMaterials::bootstrap)

    public ExampleRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(ExampleMod.MODID));
        ExampleMod.LOGGER.warn("ExampleRegistrySets");
    }

//    public static HolderLookup.Provider createLookup() {
//        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
//    }
}
