package com.example.examplemod.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

public class ExampleClient {
    public static void clientInit(IEventBus bus) {
        ExampleClient.eventSetup(bus);
    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        neoBus.addListener(ExampleClientExtensions::registerClientItemExtensions);
    }
}
