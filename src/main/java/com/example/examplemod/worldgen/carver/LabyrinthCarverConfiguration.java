package com.example.examplemod.worldgen.carver;

import com.example.examplemod.ExampleMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class LabyrinthCarverConfiguration extends CarverConfiguration {
    public static final Codec<LabyrinthCarverConfiguration> CODEC = RecordCodecBuilder.create((codecBuilder) -> {
        return codecBuilder.group(CarverConfiguration.CODEC.forGetter((config) -> config)).apply(codecBuilder, LabyrinthCarverConfiguration::new);
    });

    public LabyrinthCarverConfiguration(float probability, HeightProvider y, FloatProvider yScaleProvider, VerticalAnchor lavaLevel, CarverDebugSettings debugSettings, HolderSet<Block> replaceableBlocks) {
        super(probability, y, yScaleProvider, lavaLevel, debugSettings, replaceableBlocks);
        ExampleMod.LOGGER.info("Debug setting: " + debugSettings.isDebugMode());
    }

    public LabyrinthCarverConfiguration(CarverConfiguration baseConfig) {
        super(baseConfig.probability, baseConfig.y, baseConfig.yScale, baseConfig.lavaLevel, baseConfig.debugSettings, baseConfig.replaceable);
        ExampleMod.LOGGER.info("Debug setting: " + baseConfig.debugSettings.isDebugMode());
    }
}
