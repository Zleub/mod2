package com.example.examplemod;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;

public class ExampleItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MODID);

    public static final ArrayList<DeferredBlock<? extends Block>> simpleBlockItemList = new ArrayList<>();

    static {
        simpleBlockItemList.add(ExampleBlocks.PLAIN_WALLPAPER);
        simpleBlockItemList.add(ExampleBlocks.PLAIN_WALLPAPER_STAIRS);
        simpleBlockItemList.add(ExampleBlocks.YELLOW_WALLPAPER);
        simpleBlockItemList.add(ExampleBlocks.YELLOW_WALLPAPER_BORDERED);

        simpleBlockItemList.forEach((block) ->
            ITEMS.registerSimpleBlockItem(block.getId().getPath(), block, new Item.Properties())
        );
    }
}
