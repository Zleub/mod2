package com.example.examplemod;

import net.minecraft.core.registries.BuiltInRegistries;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ExampleBlocks {
    public static final ResourceKey<Block> YELLOW_WALLPAPER = createKey("small_yellow_wallpaper");
    public static final ResourceKey<Block> PLAIN_WALLPAPER = createKey("basic_yellow_wallpaper");

    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name));
    }


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, ExampleMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, ExampleMod.MODID);


    public static final ArrayList<DeferredHolder<Block, Block>> SIMPLE_BLOCK_LIST = new ArrayList<>();

    public static void registerSimpleBlock(ResourceKey<Block> name) {
        DeferredHolder<Block, Block> block = BLOCKS.register(name.location().getPath(), () -> new Block(
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.STONE)
                        .setId(name)
        ));
        ResourceKey<Item> itemName = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name.location().getPath()));
        DeferredHolder<Item, BlockItem> item = ITEMS.register(name.location().getPath(), () -> new BlockItem(block.get(), new Item.Properties().setId(itemName)));

        SIMPLE_BLOCK_LIST.add(block);
    }

    static {
        registerSimpleBlock(PLAIN_WALLPAPER);
    }

    public static final ArrayList<DeferredHolder<Block, RotatedPillarBlock>> ROTATED_BLOCK_LIST = new ArrayList<>();

    public static void registerRotatedBlock(ResourceKey<Block> name) {
        DeferredHolder<Block, RotatedPillarBlock> block = BLOCKS.register(name.location().getPath(), () -> new RotatedPillarBlock(
                BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(name)
            ));

        ResourceKey<Item> itemName = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name.location().getPath()));
        DeferredHolder<Item, BlockItem> item = ITEMS.register(name.location().getPath(), () -> new BlockItem(block.get(), new Item.Properties().setId(itemName)));

        ROTATED_BLOCK_LIST.add(block);
    }

    static {
//        registerRotatedBlock("small_yellow_wallpaper_bordered");

//        registerRotatedBlock("small_yellow_wallpaper");
        registerRotatedBlock(YELLOW_WALLPAPER);
    }
}
