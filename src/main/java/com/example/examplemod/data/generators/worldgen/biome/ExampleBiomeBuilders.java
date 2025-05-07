package com.example.examplemod.data.generators.worldgen.biome;

import com.example.examplemod.data.generators.worldgen.ExamplePlacedFeatures;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ExampleBiomeBuilders {
    public static Biome level0(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        class ExampleGenerationStep extends GenerationStep {

        }

//        ExampleGenerationStep.Carving.AIR = GenerationStep.Carving.valueOf("test");

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(19.9F)
                .downfall(0.0F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0x93_93_bc)
                        .skyColor(0xc0_c0_ff)
                        .waterColor(0x3f_76_e4)
                        .waterFogColor(0x05_05_33)
                        .grassColorOverride(0xb1_ff_cb)
                        .foliageColorOverride(0xb1_ff_cb)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .backgroundMusic((Music) null)
//                        .backgroundMusic(new Music(AetherSoundEvents.MUSIC_AETHER.getHolder().orElseThrow(), 12000, 24000, true))
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
//                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, ExamplePlacedFeatures.TEST_VEIN)
                        .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ExamplePlacedFeatures.SHELF_FEATURE_PLACED)
                        .build())
//                        .addCarver(ExampleGenerationStep.Carving.AIR, worldCarvers.getOrThrow(Carvers.CAVE)).build())
//                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).build())
//                .generationSettings(new BiomeGenerationSettings.PlainBuilder().build())
//                .generationSettings(new BiomeGenerationSettings.PlainBuilder().build())

                    // .addCarver(ExampleGenerationStep.Carving.AIR, worldCarvers.getOrThrow(ExampleConfiguredCarvers.LABYRINTH)).build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build();
    }

    public static BiomeSource buildAetherBiomeSource(HolderGetter<Biome> biomes) {
        Climate.Parameter fullRange = Climate.Parameter.span(-1.0F, 1.0F);
        Climate.Parameter temps1 = Climate.Parameter.span(-1.0F, -0.8F);
        Climate.Parameter temps2 = Climate.Parameter.span(-0.8F, 0.0F);
        Climate.Parameter temps3 = Climate.Parameter.span(0.0F, 0.4F);
        Climate.Parameter temps4 = Climate.Parameter.span(0.4F, 0.93F);
        Climate.Parameter temps5 = Climate.Parameter.span(0.93F, 0.94F);
        Climate.Parameter temps6 = Climate.Parameter.span(0.94F, 1.0F);
        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                // Row 1
                Pair.of(new Climate.ParameterPoint(temps1, fullRange, fullRange, fullRange, fullRange, fullRange, 0),
                        biomes.getOrThrow(ExampleBiomes.LEVEL_0))
                // Row 2
//                Pair.of(new Climate.ParameterPoint(temps2, Climate.Parameter.span(-1.0F, 0.0F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_MEADOW)),
//                Pair.of(new Climate.ParameterPoint(temps2, Climate.Parameter.span(0.0F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST)),
//                // Row 3
//                Pair.of(new Climate.ParameterPoint(temps3, Climate.Parameter.span(-1.0F, 0.0F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_GROVE)),
//                Pair.of(new Climate.ParameterPoint(temps3, Climate.Parameter.span(0.0F, 0.8F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST)),
//                Pair.of(new Climate.ParameterPoint(temps3, Climate.Parameter.span(0.8F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_GROVE)),
//                // Row 4
//                Pair.of(new Climate.ParameterPoint(temps4, Climate.Parameter.span(-1.0F, -0.1F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_GROVE)),
//                Pair.of(new Climate.ParameterPoint(temps4, Climate.Parameter.span(-0.1F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST)),
//                // Row 5
//                Pair.of(new Climate.ParameterPoint(temps5, Climate.Parameter.span(-1.0F, -0.6F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_MEADOW)),
//                Pair.of(new Climate.ParameterPoint(temps5, Climate.Parameter.span(-0.6F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_GROVE)),
//                Pair.of(new Climate.ParameterPoint(temps5, Climate.Parameter.span(-0.3F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST)),
//                // Row 6
//                Pair.of(new Climate.ParameterPoint(temps6, Climate.Parameter.span(-1.0F, -0.1F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_MEADOW)),
//                Pair.of(new Climate.ParameterPoint(temps6, Climate.Parameter.span(-0.1F, 0.8F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_WOODLAND)),
//                Pair.of(new Climate.ParameterPoint(temps5, Climate.Parameter.span(0.8F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
//                        biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST))
        )));
    }
}
