package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.CHISELED_BOOKSHELF_SLOT_MODEL_CACHE;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;

public class ExampleModels extends ModelProvider {
    public ExampleModels(PackOutput output, String modId) {
        super(output, modId);
    }

    public void createBookshelf(BlockModelGenerators blockModels, Block side, Block ends) {
        TextureMapping texturemapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(ends));
        ResourceLocation resourcelocation = ModelTemplates.CUBE_COLUMN.create(side, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createSimpleBlock(side, resourcelocation));
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


        createBookshelf(blockModels, ExampleBlocks.YELLOW_BOOKSHELF.get(), ExampleBlocks.PLAIN_WALLPAPER.get());
        createBookshelf(blockModels, ExampleBlocks.YELLOW_SHELF.get(), ExampleBlocks.PLAIN_WALLPAPER.get());
//        blockModels.createBlock
//        blockModels.
//        blockModels.copyModel(Blocks.CHISELED_BOOKSHELF, ExampleBlocks.YELLOW_BOOKSHELF.get());
//        blockModels.createChiseledBookshelf();

//        CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.computeIfAbsent(blockmodelgenerators$bookslotmodelcachekey, (p_387964_) -> {
//            return template.createWithSuffix(Blocks.CHISELED_BOOKSHELF, s, texturemapping, this.modelOutput);
//        });

        Block block = ExampleBlocks.YELLOW_BOOKSHELF.get();


        var family = new BlockFamily.Builder(ExampleBlocks.PLAIN_WALLPAPER.get())
                .stairs(ExampleBlocks.PLAIN_WALLPAPER_STAIRS.get())
                .getFamily();
        blockModels.familyWithExistingFullBlock(family.getBaseBlock()).generateFor(family);


    }
}
