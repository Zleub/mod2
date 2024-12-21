package com.example.examplemod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ExampleBlocks {
    public static final ResourceKey<Block> YELLOW_WALLPAPER = createKey("small_yellow_wallpaper");
    public static final ResourceKey<Block> PLAIN_WALLPAPER = createKey("basic_yellow_wallpaper");
    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, new ResourceLocation(ExampleMod.MODID, name));
    }


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);


    public static final ArrayList<RegistryObject<Block>> SIMPLE_BLOCK_LIST = new ArrayList<>();

    public static void registerSimpleBlock(String name) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new Block(
                BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
        RegistryObject<BlockItem> item = ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

        SIMPLE_BLOCK_LIST.add(block);
    }

    static {
        registerSimpleBlock("basic_yellow_wallpaper");
    }

    public static final ArrayList<RegistryObject<RotatedPillarBlock>> ROTATED_BLOCK_LIST = new ArrayList<>();

    public static void registerRotatedBlock(String name) {
        RegistryObject<RotatedPillarBlock> block = BLOCKS.register(name, () -> new RotatedPillarBlock(
                BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            ));
        RegistryObject<BlockItem> item = ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

        ROTATED_BLOCK_LIST.add(block);
    }

    static {
        registerRotatedBlock("small_yellow_wallpaper_bordered");

        registerRotatedBlock("small_yellow_wallpaper");
        registerRotatedBlock("yellow_wallpaper");
    }
}
