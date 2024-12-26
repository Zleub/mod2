package com.example.examplemod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
//import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ExampleBlocks {
    private static ResourceKey<Block> createKey(String name) {
        ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name);
        return ResourceKey.create(Registries.BLOCK, rl);
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExampleMod.MODID);

    public static DeferredBlock<Block> PLAIN_WALLPAPER = BLOCKS.registerBlock("basic_yellow_wallpaper",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
    );
    public static final DeferredBlock<RotatedPillarBlock> YELLOW_WALLPAPER = BLOCKS.registerBlock("small_yellow_wallpaper",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
    );
    public static final DeferredBlock<RotatedPillarBlock> YELLOW_WALLPAPER_BORDERED = BLOCKS.registerBlock("small_yellow_wallpaper_bordered",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
    );
    public static final DeferredBlock<StairBlock> PLAIN_WALLPAPER_STAIRS = BLOCKS.registerBlock("basic_yellow_wallpaper_stairs",
            (props) -> new StairBlock(PLAIN_WALLPAPER.get().defaultBlockState(), props),
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
    );
}
