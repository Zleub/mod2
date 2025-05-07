package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;

import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.block.model.multipart.Condition;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
        MultiVariant multivariant = plainVariant(ModelTemplates.CUBE_COLUMN.create(side, texturemapping, this.modelOutput));

        this.blockStateOutput.accept(createSimpleBlock(side, multivariant));
    }

    public void createChiseledBookshelf(Block block) {
//        Block block = Blocks.CHISELED_BOOKSHELF;
        MultiVariant multivariant = plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiPartGenerator multipartgenerator = MultiPartGenerator.multiPart(block);
        List.of(Pair.of(Direction.NORTH, NOP), Pair.of(Direction.EAST, Y_ROT_90), Pair.of(Direction.SOUTH, Y_ROT_180), Pair.of(Direction.WEST, Y_ROT_270)).forEach((p_408994_) -> {
            Direction direction = (Direction)p_408994_.getFirst();
            VariantMutator variantmutator = (VariantMutator)p_408994_.getSecond();
            Condition condition = condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).build();
            multipartgenerator.with(condition, multivariant.with(variantmutator).with(UV_LOCK));
            this.addSlotStateAndRotationVariants(multipartgenerator, condition, variantmutator);
        });
        this.blockStateOutput.accept(multipartgenerator);
        this.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block, "_inventory"));
        CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.clear();
    }

    @Override
    public void run() {
        this.createTrivialCube(ExampleBlocks.PLAIN_WALLPAPER.get());
        BlockFamily family = new BlockFamily.Builder(ExampleBlocks.PLAIN_WALLPAPER.get())
                .stairs(ExampleBlocks.PLAIN_WALLPAPER_STAIRS.get())
                .slab(ExampleBlocks.PLAIN_WALLPAPER_SLAB.get())
                .getFamily();
        this.familyWithExistingFullBlock(family.getBaseBlock()).generateFor(family);



        this.createAxisAlignedPillarBlock(ExampleBlocks.YELLOW_WALLPAPER.get(), TexturedModel.COLUMN);
        this.createAxisAlignedPillarBlock(ExampleBlocks.YELLOW_WALLPAPER_BORDERED.get(), TexturedModel.COLUMN);

        createBookshelf(ExampleBlocks.YELLOW_BOOKSHELF.get(), ExampleBlocks.PLAIN_WALLPAPER.get());
        createBookshelf(ExampleBlocks.YELLOW_SHELF.get(), ExampleBlocks.PLAIN_WALLPAPER.get());

//        createChiseledBookshelf(ExampleBlocks.YELLOW_SHELF.get());

//        blockModels.createBlock
//        blockModels.
//        blockModels.copyModel(Blocks.CHISELED_BOOKSHELF, ExampleBlocks.YELLOW_BOOKSHELF.get());
//        blockModels.createChiseledBookshelf();

//        CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.computeIfAbsent(blockmodelgenerators$bookslotmodelcachekey, (p_387964_) -> {
//            return template.createWithSuffix(Blocks.CHISELED_BOOKSHELF, s, texturemapping, this.modelOutput);
//        });

//        Block block = ExampleBlocks.YELLOW_BOOKSHELF.get();



        this.createTrivialCube(ExampleBlocks.STRANGE_MUD.get());
//        blockModels.copyModel(Blocks.WATER, ExampleBlocks.ALMOND_MILK.get());
//        blockModels.copyModel(Blocks.WATER_CAULDRON, ExampleBlocks.ALMOND_CAULDRON.get());

//        blockModels.copyModel(Blocks.WATER, ExampleBlocks.ALMOND_MILK.get());
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(ExampleBlocks.ALMOND_CAULDRON.get())
                .with(PropertyDispatch.initial(LayeredCauldronBlock.LEVEL)
                    .select(1, plainVariant(ModelTemplates.CAULDRON_LEVEL1.createWithSuffix(ExampleBlocks.ALMOND_CAULDRON.get(), "_level1", TextureMapping.cauldron(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "fluid/almond_milk_still")), this.modelOutput)))
                    .select(2, plainVariant(ModelTemplates.CAULDRON_LEVEL2.createWithSuffix(ExampleBlocks.ALMOND_CAULDRON.get(), "_level2", TextureMapping.cauldron(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "fluid/almond_milk_still")), this.modelOutput)))
                    .select(3, plainVariant(ModelTemplates.CAULDRON_FULL.createWithSuffix(ExampleBlocks.ALMOND_CAULDRON.get(), "_full", TextureMapping.cauldron(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "fluid/almond_milk_still")), this.modelOutput)))));

//        this.create
    }

}
