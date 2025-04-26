package com.example.examplemod.data.generators;

//import com.aetherteam.aetherii.AetherII;
//import com.aetherteam.aetherii.data.generators.models.AetherIIBlockModels;
//import com.aetherteam.aetherii.data.generators.models.AetherIIItemModels;
import com.example.examplemod.ExampleMod;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ExampleModels extends ModelProvider {
    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final PackOutput.PathProvider modelPathProvider;

    public ExampleModels(PackOutput packOutput, String modId) {
        super(packOutput, modId);
        this.blockStatePathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.itemInfoPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }


    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        ItemInfoCollector itemModelOutput = new ItemInfoCollector(this::getKnownItems);
        BlockStateGeneratorCollector blockModelOutput = new BlockStateGeneratorCollector(this::getKnownBlocks);
        SimpleModelCollector modelOutput = new SimpleModelCollector();
        this.registerModels(new ExampleBlockModels(blockModelOutput, itemModelOutput, modelOutput), new ExampleItemModels(itemModelOutput, modelOutput));
        blockModelOutput.validate();
        itemModelOutput.finalizeAndValidate();
        return CompletableFuture.allOf(blockModelOutput.save(output, this.blockStatePathProvider), modelOutput.save(output, this.modelPathProvider), itemModelOutput.save(output, this.itemInfoPathProvider));
    }

    @Override
    protected @NotNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.listElements().filter((holder) -> holder.getKey().location().getNamespace().equals(ExampleMod.MODID) && !(holder.value() instanceof LiquidBlock));
    }
}
