package com.example.examplemod.mixins;

import com.example.examplemod.ExampleBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import net.minecraft.world.level.block.PointedDripstoneBlock.FluidInfo;

import java.util.Optional;

import static com.example.examplemod.ExampleFluids.ALMOND_MILK;
import static com.example.examplemod.ExampleMod.LOGGER;

@Mixin(value = net.minecraft.world.level.block.PointedDripstoneBlock.class)
public class PointedDripstoneBlock {

    @Inject(method="lambda$getFluidAboveStalactite$11", at= @At(value = "RETURN"), locals=LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void lambda$getFluidAboveStalactite$11(Level level, BlockPos p_221876_, CallbackInfoReturnable<FluidInfo> cir, BlockPos blockpos, BlockState blockstate, Fluid fluid) {
        if (blockstate.is(ExampleBlocks.STRANGE_MUD)) {
            cir.setReturnValue(new FluidInfo(blockpos, ALMOND_MILK.get(), blockstate));
            LOGGER.info("tick");
        }
    }
}
