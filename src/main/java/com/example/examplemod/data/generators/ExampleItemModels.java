package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleFluids;
import com.example.examplemod.ExampleItems;
import com.example.examplemod.ExampleMod;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;

import java.util.Optional;
import java.util.function.BiConsumer;

public class ExampleItemModels extends ItemModelGenerators {
    public ExampleItemModels(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        DynamicFluidContainerModel.Textures dynBucket_textures = new DynamicFluidContainerModel.Textures(
                Optional.empty(),
                Optional.of(ResourceLocation.fromNamespaceAndPath("minecraft", "item/bucket")),
                Optional.of(ResourceLocation.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid")),
                Optional.of(ResourceLocation.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid_cover"))
        );

        this.itemModelOutput.accept(ExampleItems.ALMOMD_MILK_BUCKET.get(), new DynamicFluidContainerModel.Unbaked(
                dynBucket_textures,
                ExampleFluids.ALMOND_MILK.get(),
                false,
                true,
                true
        ));
        this.generateFlatItem(ExampleItems.ALMOND_MILK_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
    }


}
