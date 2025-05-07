package com.example.examplemod;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;

import static net.minecraft.world.item.Items.BUCKET;

public class ExampleItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MODID);

    public static final ArrayList<DeferredBlock<? extends Block>> simpleBlockItemList = new ArrayList<>();

    static {
        simpleBlockItemList.add(ExampleBlocks.PLAIN_WALLPAPER);
        simpleBlockItemList.add(ExampleBlocks.PLAIN_WALLPAPER_STAIRS);
        simpleBlockItemList.add(ExampleBlocks.PLAIN_WALLPAPER_SLAB);

        simpleBlockItemList.add(ExampleBlocks.YELLOW_WALLPAPER);
        simpleBlockItemList.add(ExampleBlocks.YELLOW_WALLPAPER_BORDERED);
        simpleBlockItemList.add(ExampleBlocks.YELLOW_BOOKSHELF);
        simpleBlockItemList.add(ExampleBlocks.YELLOW_SHELF);
        simpleBlockItemList.add(ExampleBlocks.STRANGE_MUD);

        simpleBlockItemList.forEach((block) ->
                ITEMS.registerSimpleBlockItem(block.getId().getPath(), block, new Item.Properties())
        );
    }

    public static DeferredItem<BucketItem> ALMOMD_MILK_BUCKET = ITEMS.registerItem("almond_milk_bucket", (p_370817_) -> new BucketItem(ExampleFluids.ALMOND_MILK.get(), p_370817_), (new Item.Properties()).craftRemainder(BUCKET).stacksTo(1));
    public static final DeferredItem<Item> ALMOND_MILK_BOTTLE = ITEMS.registerSimpleItem("almond_milk_bottle", new Item.Properties().food(ExampleFoods.ALMOND_MILK, Consumables.DEFAULT_DRINK));

}
