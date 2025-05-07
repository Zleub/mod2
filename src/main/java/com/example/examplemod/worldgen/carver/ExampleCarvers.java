package com.example.examplemod.worldgen.carver;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ExampleCarvers {
    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(Registries.CARVER, ExampleMod.MODID);

    public static final DeferredHolder<WorldCarver<?>, WorldCarver<?>> LABYRINTH = CARVERS.register("labyrinth", () -> new LabyrinthWorldCarver(LabyrinthCarverConfiguration.CODEC));
}
