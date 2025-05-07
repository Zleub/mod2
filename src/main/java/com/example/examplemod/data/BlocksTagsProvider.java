package com.example.examplemod.data;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.tags.ExampleBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BlocksTagsProvider extends BlockTagsProvider {
    public BlocksTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, lookupProvider, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ExampleBlockTags.YELLOW_WALLPAPER)
                .add(ExampleBlocks.PLAIN_WALLPAPER.get())
                .add(ExampleBlocks.PLAIN_WALLPAPER_STAIRS.get())
                .add(ExampleBlocks.PLAIN_WALLPAPER_SLAB.get())
                .add(ExampleBlocks.YELLOW_WALLPAPER.get())
                .add(ExampleBlocks.YELLOW_WALLPAPER_BORDERED.get());

    }
}
