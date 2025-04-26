package com.example.examplemod;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.level.block.Blocks.CAULDRON;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;


public class ExampleBlocks {
    private static ResourceKey<Block> createKey(String name) {
        ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name);
        return ResourceKey.create(Registries.BLOCK, rl);
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExampleMod.MODID);

    public static DeferredBlock<Block> PLAIN_WALLPAPER = BLOCKS.registerBlock("basic_yellow_wallpaper",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CONCRETE)
    );
    public static final DeferredBlock<RotatedPillarBlock> YELLOW_WALLPAPER = BLOCKS.registerBlock("small_yellow_wallpaper",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CONCRETE)
    );
    public static final DeferredBlock<RotatedPillarBlock> YELLOW_WALLPAPER_BORDERED = BLOCKS.registerBlock("small_yellow_wallpaper_bordered",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CONCRETE)
    );
    public static final DeferredBlock<StairBlock> PLAIN_WALLPAPER_STAIRS = BLOCKS.registerBlock("basic_yellow_wallpaper_stairs",
            (props) -> new StairBlock(PLAIN_WALLPAPER.get().defaultBlockState(), props),
            BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CONCRETE)
    );
    public static final DeferredBlock<Block> YELLOW_BOOKSHELF = BLOCKS.registerBlock("yellow_bookshelf",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CONCRETE)
    );

    public static final DeferredBlock<Block> YELLOW_SHELF = BLOCKS.registerBlock("yellow_shelf",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CONCRETE)
    );

    public static final DeferredBlock<Block> STRANGE_MUD = BLOCKS.registerBlock("strange_mud",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.MUD)
    );

//    public static final DeferredBlock<LiquidBlock> ACID = registerWithoutItem("acid", (properties) -> new AcidLiquidBlock(AetherIIFluids.ACID.get(), properties), () -> Block.Properties.of().mapColor(MapColor.FIRE).replaceable().noCollission().randomTicks().strength(100.0F).lightLevel(AetherIIBlocks::lightLevel8).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));

    public static final DeferredBlock<LiquidBlock> ALMOND_MILK = BLOCKS.registerBlock("almond_milk",
            (properties) -> new LiquidBlock(ExampleFluids.ALMOND_MILK.get(), properties),
            Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .replaceable()
                    .noCollission()
                    .randomTicks()
                    .strength(100.0F)
                    .pushReaction(PushReaction.DESTROY)
                    .noLootTable()
                    .liquid()
                    .sound(SoundType.EMPTY)
    );
//    public static final DeferredBlock<LiquidBlock> ACID = registerWithoutItem("acid",
//            (properties) -> new AcidLiquidBlock(AetherIIFluids.ACID.get(), properties),
//            () -> Block.Properties.of().mapColor(MapColor.FIRE).replaceable().noCollission().randomTicks().strength(100.0F).lightLevel(AetherIIBlocks::lightLevel8).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));

//    public static final DeferredBlock<Block> ALMOND_CAULDRON = BLOCKS.registerBlock("almond_milk_cauldron",
//            (properties) -> new LayeredCauldronBlock(Biome.Precipitation.NONE, CauldronInteraction.WATER, properties),
//            BlockBehaviour.Properties.ofLegacyCopy(CAULDRON));

}
