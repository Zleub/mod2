package com.example.examplemod.client;

import com.example.examplemod.ExampleFluids;
import com.example.examplemod.client.particle.ExampleParticleFactories;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ExampleClient {
    public static void clientInit(IEventBus bus) {
        bus.addListener(ExampleClient::clientSetup);

        ExampleClient.eventSetup(bus);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
//            AetherIIAtlases.registerSkyrootChestAtlases();
//            registerTooltipOverrides();

            ItemBlockRenderTypes.setRenderLayer(ExampleFluids.FLOWING_ALMOND_MILK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ExampleFluids.ALMOND_MILK.get(), RenderType.translucent());
        });

//        AetherIIRenderers.registerAccessoryRenderers();
    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        neoBus.addListener(ExampleClientExtensions::registerClientItemExtensions);
        neoBus.addListener(ExampleParticleFactories::registerParticleFactories);
    }
}
