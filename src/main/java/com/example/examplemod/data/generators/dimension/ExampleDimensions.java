
package com.example.examplemod.data.generators.dimension;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.data.generators.ExampleNoiseSettings;
import com.example.examplemod.data.generators.worldgen.biome.ExampleBiomes;
import com.example.examplemod.worldgen.LabyrinthChunkGenerator;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class ExampleDimensions {
    private final static ResourceLocation AETHER_LEVEL_ID = new ResourceLocation(ExampleMod.MODID, "the_aether");

    // DimensionType - Specifies the logic and settings for a dimension.
    public static final ResourceKey<DimensionType> AETHER_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, AETHER_LEVEL_ID);
    // Level - The dimension during runtime.
    public static final ResourceKey<Level> AETHER_LEVEL = ResourceKey.create(Registries.DIMENSION, AETHER_LEVEL_ID);
    // LevelStem - The dimension during lifecycle start and datagen.
    public static final ResourceKey<LevelStem> AETHER_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, AETHER_LEVEL_ID);
    // Time in ticks of how long a day/night cycle lasts.
    public static final int AETHER_TICKS_PER_DAY = (24000) * 3; // too scared to call Level.TICKS_PER_DAY because of static init problems, but just know this is Level.TICKS_PER_DAY * 3

    public static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
        context.register(AETHER_DIMENSION_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
                true,
                false,
                true,
                1.0,
                true,
                false,
                0,
                16,
                16,
                BlockTags.INFINIBURN_OVERWORLD,
                new ResourceLocation(ExampleMod.MODID, "the_aether"),
                0.0F,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));
    }

    public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);

//        BiomeSource source = buildAetherBiomeSource(biomes);
        BiomeSource source = new FixedBiomeSource(biomes.getOrThrow(ExampleBiomes.LEVEL_0));

        //NoiseSettings noiseSettings = new NoiseSettings(64, 10, 1, 1);
        //NoiseGeneratorSettings noiseGeneratorSettings = new NoiseGeneratorSettings(noiseSettings, Blocks.STONE.defaultBlockState(), Blocks.WATER.defaultBlockState(), NoiseRouterData.overworld(p_256460_.lookup(Registries.DENSITY_FUNCTION), p_256460_.lookup(Registries.NOISE), p_256318_, p_256427_), SurfaceRuleData.overworld(), (new OverworldBiomeBuilder()).spawnTarget(), 63, false, true, true, false);

//        LabyrinthChunkGenerator aetherChunkGen = new LabyrinthChunkGenerator(source);
//        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(source, noiseSettings.getOrThrow(ExampleNoiseSettings.LABYRINTH) );
        LabyrinthChunkGenerator aetherChunkGen = new LabyrinthChunkGenerator(source, noiseSettings.getOrThrow(ExampleNoiseSettings.LABYRINTH) );

        context.register(AETHER_LEVEL_STEM, new LevelStem(dimensionTypes.getOrThrow(ExampleDimensions.AETHER_DIMENSION_TYPE), aetherChunkGen));

//        }
    }
}
