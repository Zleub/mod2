package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class ExampleNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> LABYRINTH = createKey("labyrinth");

    private static ResourceKey<NoiseGeneratorSettings> createKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name));
    }

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);

//        ResourceLocation resourceLocation = new ResourceLocation(ExampleMod.MODID, "examplemod:yellow_wallpaper");
////        ResourceKey<Block> defaultBlock = ResourceKey.create(ExampleMod.MODID, "examplemod:yellow_wallpaper");
//        blocks.getOrThrow();

        context.register(LABYRINTH, new NoiseGeneratorSettings(
                NoiseSettings.create(
                        0, // minY
                        16, // height
                        1, // noiseSizeHorizontal
                        4 // noiseSizeVertical
                ),
                blocks.getOrThrow(ExampleBlocks.YELLOW_WALLPAPER).value().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                new NoiseRouter(
                        DensityFunctions.zero(), // barrierNoise
                        DensityFunctions.zero(), // fluidLevelFloodednessNoise
                        DensityFunctions.zero(), // fluidLevelSpreadNoise
                        DensityFunctions.zero(), // lavaNoise
                        DensityFunctions.zero(), // temperature
                        DensityFunctions.zero(), // vegetation
                        DensityFunctions.zero(), // continents
                        DensityFunctions.zero(), // erosion
                        DensityFunctions.zero(), // depth
                        DensityFunctions.zero(), // ridges
                        DensityFunctions.zero(), // initialDensityWithoutJaggedness
                        DensityFunctions.constant(1), // finalDensity
                        DensityFunctions.zero(), // veinToggle
                        DensityFunctions.zero(), // veinRidged
                        DensityFunctions.zero()  // veinGap
                ),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(14), 0),
                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
                        SurfaceRules.ifTrue(
                                SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(2), 0)),
                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                        )
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.
//                        )
                ),
//                SurfaceRules.state(blocks.getOrThrow(ExampleBlocks.YELLOW_WALLPAPER).get().defaultBlockState()),
                (new OverworldBiomeBuilder()).spawnTarget(),
                0,
                false,
                false,
                false,
                false
        ));
    }

}
