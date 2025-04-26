package com.example.examplemod.client;

import com.example.examplemod.ExampleFluids;
import com.example.examplemod.ExampleMod;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

public class ExampleClientExtensions {
    public static final IClientFluidTypeExtensions ALMOND_MILK_FLUID = new IClientFluidTypeExtensions() {
        @Override
        public @NotNull ResourceLocation getStillTexture() {
            return ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "fluid/almond_milk_still");
        }

        @Override
        public @NotNull ResourceLocation getFlowingTexture() {
            return ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "fluid/almond_milk_flow");
        }

        @Override
        public ResourceLocation getOverlayTexture() {
            return ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "fluid/almond_milk_overlay");
        }

        @Override
        public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
            return ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/misc/almond_milk.png");
        }

        @Override
        public @NotNull Vector4f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
            return new Vector4f(170 / 255.0F, 226 / 255.0F, 149 / 255.0F, 1F);
        }

        @Override
        public @NotNull FogParameters modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, FogParameters fogParameters) {
            return new FogParameters(0.0F, 12.0F, fogParameters.shape(), fogParameters.red(), fogParameters.green(), fogParameters.blue(), fogParameters.alpha());
        }

        @Override
        public int getTintColor() {
            return 0xFFFFFFFF;
        }
    };

    public static void registerClientItemExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(ALMOND_MILK_FLUID, ExampleFluids.ALMOND_MILK_TYPE.get());
    }
}
