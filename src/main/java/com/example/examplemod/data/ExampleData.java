package com.example.examplemod.data;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.data.generators.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.data.event.GatherDataEvent;
//import net.minecraftforge.common.Tags;
//import net.minecraftforge.common.data.ExistingFileHelper;
//import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ExampleData {
    public static void dataSetup(GatherDataEvent.Client event) {
        ExampleMod.LOGGER.warn("dataSetup");

        DataGenerator generator = event.getGenerator();
//        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        event.addProvider(new ExampleModels(packOutput, ExampleMod.MODID));
        event.addProvider(new ExampleRegistrySets(packOutput, lookupProvider));
        event.addProvider(new BlocksTagsProvider(packOutput, lookupProvider, ExampleMod.MODID));
//        event.addProvider(new ExampleTagsData(packOutput, BlockTags.OVERWORLD_CARVER_REPLACEABLES.registry(), lookupProvider, ExampleMod.MODID));

        // pack.mcmeta
//        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
//        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
//        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.aether.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), packTypes));
//        generator.addProvider(true, packMeta);
    }
}
