package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ExampleBlockStateData extends BlockStateProvider {

    public ExampleBlockStateData(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> entry : ExampleBlocks.SIMPLE_BLOCK_LIST) {
            Block block = entry.get();
            this.simpleBlockWithItem(block, cubeAll(block));
        }

        for (RegistryObject<RotatedPillarBlock> entry : ExampleBlocks.ROTATED_BLOCK_LIST) {
            RotatedPillarBlock block = entry.get();
            this.axisBlock(block);

            ResourceLocation resourceLocation = blockTexture(block);
            this.simpleBlockItem(block, this.itemModels().cubeColumn(resourceLocation.toString(), extend(resourceLocation, "_side"), extend(resourceLocation, "_end")));
        }
    }
}
