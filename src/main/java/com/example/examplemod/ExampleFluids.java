package com.example.examplemod;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.example.examplemod.ExampleBlocks.ALMOND_CAULDRON;

//import static com.example.examplemod.ExampleBlocks.ALMOND_CAULDRON;

public class ExampleFluids {
    private static ResourceKey<Block> createKey(String name) {
        ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name);
        return ResourceKey.create(Registries.BLOCK, rl);
    }

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ExampleMod.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, ExampleMod.MODID);

    public static final DeferredHolder<FluidType, FluidType> ALMOND_MILK_TYPE = FLUID_TYPES.register("almond_milk", () ->
            new FluidType(
                    FluidType.Properties.create()
                            .density(1024)
                            .viscosity(1024)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                            .addDripstoneDripping(0.1F, ParticleTypes.DRIPPING_WATER, ALMOND_CAULDRON.get(), SoundEvents.BUCKET_FILL)
            ));

    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_ALMOND_MILK = FLUIDS.register("flowing_almond_milk", () -> new AlmondMilkFluid.Flowing(ExampleFluids.ALMOND_MILK_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> ALMOND_MILK = FLUIDS.register("almond_milk", () -> new AlmondMilkFluid.Source(ExampleFluids.ALMOND_MILK_PROPERTIES));

    public static final BaseFlowingFluid.Properties ALMOND_MILK_PROPERTIES = new BaseFlowingFluid.Properties(ALMOND_MILK_TYPE, ALMOND_MILK, FLOWING_ALMOND_MILK)
            .block(ExampleBlocks.ALMOND_MILK)
            .bucket(ExampleItems.ALMOMD_MILK_BUCKET);

    abstract static class AlmondMilkFluid extends BaseFlowingFluid {
        protected AlmondMilkFluid(Properties properties) {
            super(properties);
        }

        @Override
        public BlockState createLegacyBlock(FluidState fluidState) {
            return ExampleBlocks.ALMOND_MILK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
        }

        @Override
        public Fluid getFlowing() {
            return FLOWING_ALMOND_MILK.get();
        }

        @Override
        public Fluid getSource() {
            return ALMOND_MILK.get();
        }

        public static class Source extends AlmondMilkFluid {
            public Source(Properties properties) {
                super(properties);
            }

            public int getAmount(FluidState fluidState) {
                return 8;
            }

            public boolean isSource(FluidState fluidState) {
                return true;
            }
        }

        public static class Flowing extends AlmondMilkFluid {
            public Flowing(Properties properties) {
                super(properties);
            }

            protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
                super.createFluidStateDefinition(builder);
                builder.add(LEVEL);
            }

            public int getAmount(FluidState fluidState) {
                return fluidState.getValue(LEVEL);
            }

            public boolean isSource(FluidState fluidState) {
                return false;
            }


        }
    }
}
