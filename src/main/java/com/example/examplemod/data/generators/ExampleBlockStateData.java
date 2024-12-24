package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
//import net.minecraftforge.client.model.generators.BlockStateProvider;
//import net.minecraftforge.client.model.generators.ItemModelBuilder;
//import net.minecraftforge.common.data.ExistingFileHelper;
//import net.minecraftforge.registries.RegistryObject;

public class ExampleBlockStateData extends BlockStateProvider {

    public ExampleBlockStateData(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }

    @Override
    protected void registerStatesAndModels() {
        for (DeferredHolder<Block, Block> entry : ExampleBlocks.BLOCK_LIST) {
            Block block = entry.get();
            this.simpleBlock(block, cubeAll(block));
        }

        for (DeferredBlock<RotatedPillarBlock> entry : ExampleBlocks.ROTATED_BLOCK_LIST) {
            RotatedPillarBlock block = entry.get();
            this.axisBlock(block);

//            ResourceLocation resourceLocation = blockTexture(block);
//            this.simpleBlockItem(block, this.itemModels().cubeColumn(resourceLocation.toString(), extend(resourceLocation, "_side"), extend(resourceLocation, "_end")));
        }
    }
}
