package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.stream.Stream;

public class ExampleModels extends ModelProvider {
    public ExampleModels(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(ExampleBlocks.PLAIN_WALLPAPER.get());
//                TexturedModel.CUBE.updateTexture(t -> t.put(TextureSlot.ALL, ResourceLocation.parse("block/" + ExampleBlocks.PLAIN_WALLPAPER.getId()))));

//        blockModels.createRotatableColumn(ExampleBlocks.YELLOW_WALLPAPER.get());
        blockModels.createAxisAlignedPillarBlock(ExampleBlocks.YELLOW_WALLPAPER.get(), TexturedModel.COLUMN);
//                TexturedModel.COLUMN.updateTexture(t -> t
//                        .put(TextureSlot.SIDE, ResourceLocation.parse("block/" + ExampleBlocks.YELLOW_WALLPAPER.getId() + "_side"))
//                        .put(TextureSlot.END, ResourceLocation.parse("block/" + ExampleBlocks.YELLOW_WALLPAPER.getId() + "_end"))
//                ));
        blockModels.createAxisAlignedPillarBlock(ExampleBlocks.YELLOW_WALLPAPER_BORDERED.get(), TexturedModel.COLUMN);
//                TexturedModel.COLUMN.updateTexture(t -> t
//                        .put(TextureSlot.SIDE, ResourceLocation.parse("block/" + ExampleBlocks.YELLOW_WALLPAPER_BORDERED.getId() + "_side"))
//                        .put(TextureSlot.END, ResourceLocation.parse("block/" + ExampleBlocks.YELLOW_WALLPAPER_BORDERED.getId() + "_end"))
//                ));

        var family = new BlockFamily.Builder(ExampleBlocks.PLAIN_WALLPAPER.get()).stairs(ExampleBlocks.PLAIN_WALLPAPER_STAIRS.get()).getFamily();
        blockModels.familyWithExistingFullBlock(family.getBaseBlock()).generateFor(family);


    }
}
