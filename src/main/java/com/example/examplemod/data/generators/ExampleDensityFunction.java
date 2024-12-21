package com.example.examplemod.data.generators;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouterData;

public class ExampleDensityFunction extends NoiseRouterData {
    public static final ResourceKey<DensityFunction> ZERO = createKey("zero");
    private static ResourceKey<DensityFunction> createKey(String p_209537_) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.withDefaultNamespace(p_209537_));
    }

    public static Holder<? extends DensityFunction> bootstrap(BootstrapContext<DensityFunction> p_256220_) {
        return p_256220_.register(ZERO, DensityFunctions.zero());
    }



        //    protected static NoiseRouter none() {
//        return new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());
//    }
}
