package com.example.examplemod.data;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.data.generators.ExampleBlockStateData;
import com.example.examplemod.data.generators.ExampleRegistrySets;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.server.packs.PackType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ExampleData {
    public static void dataSetup(GatherDataEvent event) {
        ExampleMod.LOGGER.warn("dataSetup");

        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        generator.addProvider(event.includeServer(), new ExampleRegistrySets(packOutput, lookupProvider));

        generator.addProvider(event.includeClient(), new ExampleBlockStateData(packOutput, ExampleMod.MODID, fileHelper));

        // pack.mcmeta
//        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
//        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
//        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.aether.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), packTypes));
//        generator.addProvider(true, packMeta);
    }
}
