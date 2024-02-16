package com.example.examplemod;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class LabyrinthCarverConfiguration extends CarverConfiguration {
    public static final Codec<LabyrinthCarverConfiguration> CODEC = RecordCodecBuilder.create((p_159184_) -> {
        return p_159184_.group(CarverConfiguration.CODEC.forGetter((p_159192_) -> {
            return p_159192_;
        })).apply(p_159184_, LabyrinthCarverConfiguration::new);
    });

    public LabyrinthCarverConfiguration(float p_224832_, HeightProvider p_224833_, FloatProvider p_224834_, VerticalAnchor p_224835_, CarverDebugSettings p_224836_, HolderSet<Block> p_224837_) {
        super(p_224832_, p_224833_, p_224834_, p_224835_, p_224836_, p_224837_);
    }
    public LabyrinthCarverConfiguration(CarverConfiguration p_159179_) {
        super(p_159179_.probability, p_159179_.y, p_159179_.yScale, p_159179_.lavaLevel, p_159179_.debugSettings, p_159179_.replaceable);
    }
}
