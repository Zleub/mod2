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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ExampleBlocks {
    public static final ResourceKey<Block> PLAIN_WALLPAPER = createKey("basic_yellow_wallpaper");
    public static final ResourceKey<Block> YELLOW_WALLPAPER = createKey("small_yellow_wallpaper");
    public static final ResourceKey<Block> YELLOW_WALLPAPER_BORDERED = createKey("small_yellow_wallpaper_bordered");

//    public static final ArrayList<ResourceKey<Item>> BLOCKITEM_KEYS = new ArrayList<>();

    private static ResourceKey<Block> createKey(String name) {
        ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name);
        return ResourceKey.create(Registries.BLOCK, rl);
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExampleMod.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MODID);

    public static final ArrayList<DeferredBlock<Block>> BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<DeferredItem<BlockItem>> BLOCKITEM_LIST = new ArrayList<>();

    public static void registerSimpleBlock(ResourceKey<Block> name) {
        DeferredBlock<Block> block = BLOCKS.register(name.location().getPath(), () -> new Block(
                BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(name)
        ));

        BLOCKITEM_LIST.add(
                ITEMS.registerSimpleBlockItem( name.location().getPath(), block, new Item.Properties()
                ) );
        BLOCK_LIST.add(block);
    }

    static {
        registerSimpleBlock(PLAIN_WALLPAPER);
    }

    public static final ArrayList<DeferredBlock<RotatedPillarBlock>> ROTATED_BLOCK_LIST = new ArrayList<>();

    public static void registerRotatedBlock(ResourceKey<Block> name) {
        DeferredBlock<RotatedPillarBlock> block = BLOCKS.register(name.location().getPath(), () -> new RotatedPillarBlock(
                BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(name)
            ));

//        ResourceKey<Item> itemName = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name.location().getPath()));
//        DeferredHolder<Item, BlockItem> item = ITEMS.register(name.location().getPath(), () -> new BlockItem(block.get(), new Item.Properties().setId(itemName)));

        ROTATED_BLOCK_LIST.add(block);
        BLOCKITEM_LIST.add(ITEMS.registerSimpleBlockItem(name.location().getPath(), block, new Item.Properties()));
    }

    static {
        registerRotatedBlock(YELLOW_WALLPAPER);
        registerRotatedBlock(YELLOW_WALLPAPER_BORDERED);
    }
}
