package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;

import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ExampleBlockModels extends BlockModelGenerators {
    public ExampleBlockModels(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    public void createBookshelf(Block side, Block ends) {
        TextureMapping texturemapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(ends));
//        ResourceLocation resourcelocation = ModelTemplates.CUBE_COLUMN.create(side, texturemapping, blockModels.modelOutput);
        MultiVariant multivariant = plainVariant(ModelTemplates.CUBE_COLUMN.create(side, texturemapping, this.modelOutput));

        this.blockStateOutput.accept(createSimpleBlock(side, multivariant));
    }
    @Override
    public void run() {
        this.createTrivialCube(ExampleBlocks.PLAIN_WALLPAPER.get());
//                TexturedModel.CUBE.updateTexture(t -> t.put(TextureSlot.ALL, ResourceLocation.parse("block/" + ExampleBlocks.PLAIN_WALLPAPER.getId()))));

//        blockModels.createRotatableColumn(ExampleBlocks.YELLOW_WALLPAPER.get());
        this.createAxisAlignedPillarBlock(ExampleBlocks.YELLOW_WALLPAPER.get(), TexturedModel.COLUMN);
//                TexturedModel.COLUMN.updateTexture(t -> t
//                        .put(TextureSlot.SIDE, ResourceLocation.parse("block/" + ExampleBlocks.YELLOW_WALLPAPER.getId() + "_side"))
//                        .put(TextureSlot.END, ResourceLocation.parse("block/" + ExampleBlocks.YELLOW_WALLPAPER.getId() + "_end"))
//                ));
        this.createAxisAlignedPillarBlock(ExampleBlocks.YELLOW_WALLPAPER_BORDERED.get(), TexturedModel.COLUMN);
//                TexturedModel.COLUMN.updateTexture(t -> t
//                        .put(TextureSlot.SIDE, ResourceLocation.parse("block/" + ExampleBlocks.YELLOW_WALLPAPER_BORDERED.getId() + "_side"))
//                        .put(TextureSlot.END, ResourceLocation.parse("block/" + ExampleBlocks.YELLOW_WALLPAPER_BORDERED.getId() + "_end"))
//                ));


        createBookshelf(ExampleBlocks.YELLOW_BOOKSHELF.get(), ExampleBlocks.PLAIN_WALLPAPER.get());
        createBookshelf(ExampleBlocks.YELLOW_SHELF.get(), ExampleBlocks.PLAIN_WALLPAPER.get());
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
        this.familyWithExistingFullBlock(family.getBaseBlock()).generateFor(family);

        this.createTrivialCube(ExampleBlocks.STRANGE_MUD.get());
//        blockModels.copyModel(Blocks.WATER, ExampleBlocks.ALMOND_MILK.get());
//        blockModels.copyModel(Blocks.WATER_CAULDRON, ExampleBlocks.ALMOND_CAULDRON.get());

//        blockModels.copyModel(Blocks.WATER, ExampleBlocks.ALMOND_MILK.get());
    }

}
