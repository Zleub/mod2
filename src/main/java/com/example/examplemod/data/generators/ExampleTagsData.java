package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ExampleTagsData extends TagsProvider {


    public ExampleTagsData(PackOutput p_256596_, ResourceKey p_255886_, CompletableFuture p_256513_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256596_, p_255886_, p_256513_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(ExampleBlocks.YELLOW_WALLPAPER);
    }
}