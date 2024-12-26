package com.example.examplemod;

import com.example.examplemod.data.ExampleData;
import com.example.examplemod.worldgen.ExampleFeaturesList;
import com.example.examplemod.worldgen.carver.LabyrinthCarverConfiguration;
import com.example.examplemod.worldgen.LabyrinthChunkGenerator;
import com.example.examplemod.worldgen.carver.LabyrinthWorldCarver;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
//import net.minecraftforge.event.server.ServerStartingEvent;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.ModLoadingContext;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.config.ModConfig;
//import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

@Mod(ExampleMod.MODID)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final Logger LOGGER = LogUtils.getLogger();
//    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(Registries.CARVER, MODID);
    public static final DeferredHolder<WorldCarver<?>, WorldCarver<?>> LABYRINTH = CARVERS.register("labyrinth", () -> new LabyrinthWorldCarver(LabyrinthCarverConfiguration.CODEC));



    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATOR = DeferredRegister.create(Registries.CHUNK_GENERATOR, MODID);
    public static final DeferredHolder<MapCodec<? extends ChunkGenerator>, MapCodec<? extends ChunkGenerator>> LABYRINTH_GEN = CHUNK_GENERATOR.register("labyrinth", () -> {
           ExampleMod.LOGGER.info("Chunk Generator registration");
           return LabyrinthChunkGenerator.CODEC;
    });


//    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("basic_yellow_wallpaper", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
//    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("basic_yellow_wallpaper", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

//    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
//            .alwaysEat().nutrition(1).saturationMod(2f).build())));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
//            .icon(() -> EXAMPLE_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
            }).build());

    public ExampleMod(IEventBus modEventBus, ModContainer modContainer)
    {
        LOGGER.warn("enqueue dataSetup");
        modEventBus.addListener(ExampleData::dataSetup);


        modEventBus.addListener(this::commonSetup);

        ExampleBlocks.BLOCKS.register(modEventBus);
        ExampleItems.ITEMS.register(modEventBus);
        ExampleFeaturesList.FEATURES.register(modEventBus);

        CHUNK_GENERATOR.register(modEventBus);
        CARVERS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == EXAMPLE_TAB.getKey()) {
            for(DeferredHolder<Item, ? extends Item> item : ExampleItems.ITEMS.getEntries()) {
                event.accept(item.value());
            }
        }
//
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
