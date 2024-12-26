package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.StairBlock;
//import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class ExampleBlockStateData extends BlockStateProvider {
//
//    private ResourceLocation extend(ResourceLocation rl, String suffix) {
//        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
//    }
//
//    @Override
//    protected void registerStatesAndModels() {
//        ArrayList<DeferredBlock<Block>> simpleBlockList = new ArrayList();
//        simpleBlockList.add(ExampleBlocks.PLAIN_WALLPAPER);
//
//        for (DeferredBlock<Block> entry : simpleBlockList) {
//            Block block = entry.get();
//            simpleBlock(block, cubeAll(block));
//        }
//
//        ArrayList<DeferredBlock<RotatedPillarBlock>> rotatedBlockList = new ArrayList();
//        rotatedBlockList.add(ExampleBlocks.YELLOW_WALLPAPER);
//        rotatedBlockList.add(ExampleBlocks.YELLOW_WALLPAPER_BORDERED);
//
//        for (DeferredBlock<RotatedPillarBlock> entry : rotatedBlockList) {
//            RotatedPillarBlock block = entry.get();
//            ResourceLocation resourceLocation = blockTexture(block);
//            this.axisBlock(block);//, this.itemModels().cubeColumn(resourceLocation.toString(), extend(resourceLocation, "_side"), extend(resourceLocation, "_end")));
//        }
//
//
//        this.stairsBlock(ExampleBlocks.PLAIN_WALLPAPER_STAIRS.get(), blockTexture(ExampleBlocks.PLAIN_WALLPAPER.get()));
//    }

    @Override
    protected BlockStateProviderType<?> type() {
        return null;
    }

    @Override
    public BlockState getState(RandomSource randomSource, BlockPos blockPos) {
        return null;
    }
}
